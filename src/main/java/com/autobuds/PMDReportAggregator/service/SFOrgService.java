package com.autobuds.PMDReportAggregator.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.autobuds.PMDReportAggregator.model.Org;
import com.autobuds.PMDReportAggregator.model.OrgId;
import com.autobuds.PMDReportAggregator.repository.OrgRepository;
import com.autobuds.PMDReportAggregator.utility.SFOrgResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sforce.soap.metadata.AsyncResult;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.metadata.PackageTypeMembers;
import com.sforce.soap.metadata.RetrieveMessage;
import com.sforce.soap.metadata.RetrieveRequest;
import com.sforce.soap.metadata.RetrieveResult;
import com.sforce.soap.metadata.RetrieveStatus;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@Service
public class SFOrgService {

	private String Url = "https://login.salesforce.com/services/oauth2/token";
	private static final long ONE_SECOND = 1000;
	// maximum number of attempts to retrieve the results
	private static final int MAX_NUM_POLL_REQUESTS = 50;
	// manifest file that controls which components get retrieved
	private static final String MANIFEST_FILE = "D:\\package.xml";

	private static final double API_VERSION = 49.0;
	private String base="./userData";

	@Autowired
	private ObjectMapper objMapper;

	@Autowired
	private OrgRepository orgRepo;

	public List<String> retrieve(OrgId orgId) {
		
		try {
		Org org = orgRepo.findById(orgId).orElse(null);
		String orgid = orgId.getOrgId() ;
		if (org == null) {
			throw new RuntimeException("no org with id " + orgId);
		}
		SFOrgResponse orgResponse = newAccessToken(org.getRefreshToken());
		System.out.println(orgResponse);
		String basepath = base+"/"+org.getId().getUserId();
		// creating dir if not exists
		File path = new File(basepath);
		path.mkdirs();
		// retrieve call for apex classes
        retrieveApex(orgResponse,org.getSalesforceVersion().toString(),basepath);
        
        // unzipping 
        unzip(basepath,orgid);
        // deleting zip file 
        new File(basepath+"\\retrieveResults.zip").delete();
        // getting list from directories
        return getFiles(basepath+"\\"+orgid+"\\unpackaged\\classes");
		} catch(Exception ex)
		{
			throw new RuntimeException("Some error "+ex.getMessage());
		}
        
       // FileUtils.deleteDirectory(new File(basepath+"\\"+orgid));
	}
	
	private List<String> getFiles(String path) throws IOException
	{
		 File directoryPath = new File(path);
	      //List of all files and directories
	      File filesList[] = directoryPath.listFiles();
	      List<String> list = new ArrayList<>();
	      for(File file : filesList) {
	    	  if(file.getName().endsWith(".cls")) {
	    	  list.add(file.getName());
	    	  }
	      }
		  
	      return list;
	}

	public void saveOrg(String code, String state) {
		SFOrgResponse sfResponse = getSFAccessToken(code);
		String orgid = sfResponse.getAccess_token().substring(0, 15);
		String data[] = new String(Base64.decodeBase64(state)).split(",");
		//System.out.println(data[0] + " " + data[1] + " " + data[2]);
		OrgId orgId = new OrgId();
		orgId.setUserId(data[0]);
		orgId.setOrgId(orgid);
		Org org = new Org();
		org.setOrgName(data[1]);
		org.setId(orgId);
		org.setAccessToken(sfResponse.getAccess_token());
		org.setRefreshToken(sfResponse.getRefresh_token());
		org.setSalesforceVersion(new BigDecimal(data[2]));
		saveOrg(org);
	}

	private SFOrgResponse getSFAccessToken(String code) {
		try {

			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(Url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("client_id",
					"3MVG9n_HvETGhr3A9b6vwY7kpQRZo57VT6U9j1gnh2l2JX7G1cGRXjW5GE.eusqTgBRwxsQ0BynPaW17UQpmR"));
			params.add(new BasicNameValuePair("client_secret",
					"E806E7ED1BB94CAA565DA8F6756340033BDDBD1F662263ECF898AFCC5B300438"));
			params.add(new BasicNameValuePair("grant_type", "authorization_code"));
			params.add(new BasicNameValuePair("redirect_uri", "http://localhost:8081/api/auth/callback"));
			params.add(new BasicNameValuePair("code", code));
			post.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(post);
			String body = EntityUtils.toString(response.getEntity());
			SFOrgResponse sfResponse = objMapper.readValue(body, SFOrgResponse.class);

			return sfResponse;
		} catch (Exception ex) {
			//System.out.println(ex);
			ex.printStackTrace();
			throw new RuntimeException("get Sf Acess Token");
		}
	}

	private void saveOrg(Org org) {
		
		//System.out.println(org);
		orgRepo.save(org);
	}

	public List<Org> getOrgs(String email) {
		return orgRepo.findAllOrg(email);
	}
	
	public Org getOrgById(OrgId orgId) {
		return orgRepo.getOne(orgId);
	}

	private SFOrgResponse newAccessToken(String refreshToken) throws Exception {
		
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(Url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("client_id",
					"3MVG9n_HvETGhr3A9b6vwY7kpQRZo57VT6U9j1gnh2l2JX7G1cGRXjW5GE.eusqTgBRwxsQ0BynPaW17UQpmR"));
			params.add(new BasicNameValuePair("client_secret",
					"E806E7ED1BB94CAA565DA8F6756340033BDDBD1F662263ECF898AFCC5B300438"));
			params.add(new BasicNameValuePair("grant_type", "refresh_token"));
			params.add(new BasicNameValuePair("refresh_token", refreshToken));
			post.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(post);
			String body = EntityUtils.toString(response.getEntity());
			SFOrgResponse sfResponse = objMapper.readValue(body, SFOrgResponse.class);
            System.out.println(body + sfResponse);
			return sfResponse;
		
	}

	private static MetadataConnection createMetadataConnection(String MetadataServerurl, String Accesstoken,
			String version) throws ConnectionException {
		final ConnectorConfig config = new ConnectorConfig();
		config.setServiceEndpoint(MetadataServerurl + "/services/Soap/m/" + version);
		config.setSessionId(Accesstoken);
		MetadataConnection metadataConnection = new MetadataConnection(config);
		return metadataConnection;
	}

	private void retrieveApex(SFOrgResponse sfResponse, String version,String basepath) throws Exception {
		RetrieveRequest retrieveRequest = new RetrieveRequest();
		// The version in package.xml overrides the version in RetrieveRequest
		retrieveRequest.setApiVersion(API_VERSION);
		setUnpackaged(retrieveRequest);

		// Start the retrieve operation
		MetadataConnection metadataConnection = createMetadataConnection(sfResponse.getInstance_url(),
				sfResponse.getAccess_token(), version);
		AsyncResult asyncResult = metadataConnection.retrieve(retrieveRequest);
		String asyncResultId = asyncResult.getId();
		System.out.println(asyncResultId);

		// Wait for the retrieve to complete
		int poll = 0;
		long waitTimeMilliSecs = ONE_SECOND;
		RetrieveResult result = null;
		do {
			Thread.sleep(waitTimeMilliSecs);
			// Double the wait time for the next iteration
			waitTimeMilliSecs *= 2;
			if (poll++ > MAX_NUM_POLL_REQUESTS) {
				throw new Exception("Request timed out.  If this is a large set "
						+ "of metadata components, check that the time allowed "
						+ "by MAX_NUM_POLL_REQUESTS is sufficient.");
			}
			result = metadataConnection.checkRetrieveStatus(asyncResultId, true);
			System.out.println("Retrieve Status: " + result.getStatus());
		} while (!result.isDone());

		if (result.getStatus() == RetrieveStatus.Failed) {
			throw new Exception(result.getErrorStatusCode() + " msg: " + result.getErrorMessage());
		} else if (result.getStatus() == RetrieveStatus.Succeeded) {
			// Print out any warning messages
			StringBuilder buf = new StringBuilder();
			if (result.getMessages() != null) {
				for (RetrieveMessage rm : result.getMessages()) {
					buf.append(rm.getFileName() + " - " + rm.getProblem());
				}
			}
			if (buf.length() > 0) {
				System.out.println("Retrieve warnings:\n" + buf);
			}

			// Write the zip to the file system
			System.out.println("Writing results to zip file");
			ByteArrayInputStream bais = new ByteArrayInputStream(result.getZipFile());
			File resultsFile = new File(basepath+"/retrieveResults.zip");
			FileOutputStream os = new FileOutputStream(resultsFile);
			try {
				ReadableByteChannel src = Channels.newChannel(bais);
				FileChannel dest = os.getChannel();
				copy(src, dest);

				System.out.println("Results written to " + resultsFile.getAbsolutePath());
			} finally {
				os.close();
			}
		}

	}

	private void copy(ReadableByteChannel src, WritableByteChannel dest) throws IOException {
		// Use an in-memory byte buffer
		ByteBuffer buffer = ByteBuffer.allocate(8092);
		while (src.read(buffer) != -1) {
			buffer.flip();
			while (buffer.hasRemaining()) {
				dest.write(buffer);
			}
			buffer.clear();
		}
	}
	
	 private void setUnpackaged(RetrieveRequest request) throws Exception
	    {
	        // Edit the path, if necessary, if your package.xml file is located elsewhere
	        File unpackedManifest = new File(MANIFEST_FILE);
	        System.out.println("Manifest file: " + unpackedManifest.getAbsolutePath());
	        
	        if (!unpackedManifest.exists() || !unpackedManifest.isFile())
	            throw new Exception("Should provide a valid retrieve manifest " +
	                    "for unpackaged content. " +
	                    "Looking for " + unpackedManifest.getAbsolutePath());
	 
	        // Note that we populate the _package object by parsing a manifest file here.
	        // You could populate the _package based on any source for your
	        // particular application.
	        com.sforce.soap.metadata.Package p = parsePackage(unpackedManifest);
	        request.setUnpackaged(p);
	    }
	 
	    private com.sforce.soap.metadata.Package parsePackage(File file) throws Exception {
	        
	            InputStream is = new FileInputStream(file);
	            List<PackageTypeMembers> pd = new ArrayList<PackageTypeMembers>();
	            DocumentBuilder db =
	                DocumentBuilderFactory.newInstance().newDocumentBuilder();
	            Element d = db.parse(is).getDocumentElement();
	            for (Node c = d.getFirstChild(); c != null; c = c.getNextSibling()) {
	                if (c instanceof Element) {
	                    Element ce = (Element) c;
	                    //
	                    NodeList namee =  ce.getElementsByTagName("name");
	                    if (namee.getLength() == 0) {
	                        // not
	                        continue;
	                    }
	                    String name = namee.item(0).getTextContent();
	                    NodeList m = ce.getElementsByTagName("members");
	                    List<String> members = new ArrayList<String>();
	                    for (int i = 0; i < m.getLength(); i++) {
	                        Node mm = m.item(i);
	                        members.add(mm.getTextContent());
	                    }
	                    PackageTypeMembers pdi = new PackageTypeMembers();
	                    pdi.setName(name);
	                    pdi.setMembers(members.toArray(new String[members.size()]));
	                    pd.add(pdi);
	                }
	            }
	            com.sforce.soap.metadata.Package r = new com.sforce.soap.metadata.Package();
	            r.setTypes(pd.toArray(new PackageTypeMembers[pd.size()]));
	            r.setVersion(API_VERSION + "");
	            return r;
	       
	    }
	    
        public static void unzip(String path,String orgId) throws IOException {
	    	
        	String path1 = path+"\\"+orgId;
	        File destDir = new File(path1+"\\unpackaged\\classes\\");
	        if (!destDir.exists()) {
	            destDir.mkdirs();
	        }
	        
	        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(path+"\\retrieveResults.zip"));
	        ZipEntry entry = zipIn.getNextEntry();
	        // iterates over entries in the zip file
	        while (entry != null) {
	            String filePath = path1 + File.separator + entry.getName();
	            System.out.println(""+entry.getName());
	            if (!entry.isDirectory()) {
	                // if the entry is a file, extracts it
	                extractFile(zipIn, filePath);
	            } else {
	                // if the entry is a directory, make the directory
	            	System.out.println(""+entry.getName());
	                File dir = new File(filePath);
	                dir.mkdir();
	            }
	            zipIn.closeEntry();
	            entry = zipIn.getNextEntry();
	        }
	        zipIn.close();
	    }
        
        private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        	
	        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
	        byte[] bytesIn = new byte[4096];
	        int read = 0;
	        while ((read = zipIn.read(bytesIn)) != -1) {
	            bos.write(bytesIn, 0, read);
	        }
	        bos.close();
	    }

}