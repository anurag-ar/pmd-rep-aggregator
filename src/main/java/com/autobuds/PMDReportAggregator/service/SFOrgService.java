package com.autobuds.PMDReportAggregator.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import com.autobuds.PMDReportAggregator.model.Org;
import com.autobuds.PMDReportAggregator.model.OrgId;
import com.autobuds.PMDReportAggregator.repository.OrgRepository;
import com.autobuds.PMDReportAggregator.utility.SFOrgResponse;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class SFOrgService {
 
	private String Url = "https://login.salesforce.com/services/oauth2/token";
 	
	@Autowired
 	private ObjectMapper objMapper ;
	
	@Autowired
	private OrgRepository orgRepo;
	
	public void saveOrg(String code ,String state)
	{
		SFOrgResponse sfResponse = getSFAccessToken(code);
		String orgid = sfResponse.getAccess_token().substring(0,15);
		String data[] = new String(Base64.decodeBase64(state)).split(",");
		System.out.println(data[0]+" "+data[1]+" "+data[2]);
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
	
	private SFOrgResponse getSFAccessToken(String code)
	{
		try {
				
	    HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(Url);
		    List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("client_id", "3MVG9n_HvETGhr3A9b6vwY7kpQRZo57VT6U9j1gnh2l2JX7G1cGRXjW5GE.eusqTgBRwxsQ0BynPaW17UQpmR"));
	        params.add(new BasicNameValuePair("client_secret","E806E7ED1BB94CAA565DA8F6756340033BDDBD1F662263ECF898AFCC5B300438"));
	        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
	        params.add(new BasicNameValuePair("redirect_uri", "http://localhost:8081/api/auth/callback"));
	        params.add(new BasicNameValuePair("code",code));
	        post.setEntity(new UrlEncodedFormEntity(params));
	        HttpResponse response = client.execute(post);
	        String body = EntityUtils.toString(response.getEntity());
	        SFOrgResponse sfResponse = objMapper.readValue(body, SFOrgResponse.class);
	   
             return sfResponse;
		}
		catch(Exception ex)
		{
			System.out.println(ex);
			ex.printStackTrace();
		    throw new RuntimeException("get Sf Acess Token");
		}
	  }
	
	private void saveOrg(Org org)
	{
		System.out.println(org);
		orgRepo.save(org);
	}
	
	public List<Org> getOrgs(String email)
	{
		return orgRepo.findAllOrg(email);
	}
}
