package com.autobuds.PMDReportAggregator.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.autobuds.PMDReportAggregator.model.OrgId;
import com.autobuds.PMDReportAggregator.utility.ApexFile;
import com.autobuds.PMDReportAggregator.utility.EmailField;
import com.autobuds.PMDReportAggregator.utility.PMDReportPOJO;
import com.autobuds.PMDReportAggregator.utility.Violation;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmailService {
	@Autowired
	private UserService userService;
	
	@Autowired
	private SFOrgService orgService;

    private JavaMailSender javaMailSender;
    
//    private static Integer HIGH = 1;
//    private static Integer LOW = 5;
//    private static  Integer MED = 3;
    public enum PRIORITY { HIGH(1), HIGH_MED(2), MEDIUM(3), LOW_MED(4), LOW(5); private int value; private PRIORITY(int value) { this.value = value; } };
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String toEmail, String subject, String message) {

        var mailMessage = new SimpleMailMessage();

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailMessage.setFrom("pmd-autobuds1-9bc467@inbox.mailtrap.io");

        javaMailSender.send(mailMessage);
    }
    
    public void sendMessageWithAttachment(
    		  String[] to, String subject, String text, String pathToAttachment) throws MessagingException {
    		    System.out.println("Inside Attachment");
    		    MimeMessage message = javaMailSender.createMimeMessage();
    		     
    		    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    		    
    		    helper.setFrom("noreply@autobuds.com");
    		    helper.setTo(to);
    		    helper.setSubject(subject);
    		    helper.setText(text);
    		        
    		    FileSystemResource file 
    		      = new FileSystemResource(pathToAttachment);
    		    helper.addAttachment("PMD_Report", file);

    		    javaMailSender.send(message);

    		}
    
	public void emailNotification(String email, String orgId, PMDReportPOJO reportObj) {
		String emailSubject;
		String userName = userService.getUser(email).getUserName();
		OrgId orgPrimaryKey = new OrgId(email, orgId);
		String orgName = orgService.getOrgById(orgPrimaryKey).getOrgName();
		emailSubject = "Apex Pmd Analysis report on : " + orgName + "(" + orgId + ")";
		StringBuilder sb = new StringBuilder();
		sb.append("Greetings "+ userName + ",\n");
		sb.append("Report Details are given below \n");
		sb.append("SF Org Name: "+ orgName + "( OrgId:" + orgId + ")\n");
		sb.append("Triggered Time: " + reportObj.getTimestamp() +"\n");
		sb.append("Files \n");
		List<ApexFile> files = reportObj.getFiles();
		for(ApexFile file : files) {
			String fileName = FilenameUtils.getName(file.getFilename());
			List<Violation> violations = file.getViolations();
			StringBuffer highPriority = new StringBuffer("Priority HIGH: [");
			StringBuffer mediumPriority = new StringBuffer("Priority MEDIUM: [");
			StringBuffer lowPriority = new StringBuffer("Priority LOW: [");
			for(Violation violate : violations) {
				 if(violate.getPriority().equals(PRIORITY.MEDIUM.value)) {
						mediumPriority.append("\n Description: " + violate.getDescription() + ", at line: " + violate.getBeginline());
					}else if(violate.getPriority().equals(PRIORITY.LOW.value)) {
						lowPriority.append("\n Description: " + violate.getDescription() + ", at line: " + violate.getBeginline());
					}else {
					highPriority.append("\n Description: " + violate.getDescription() + ", at line: " + violate.getBeginline());
				}
			}
			sb.append("Filename: " + fileName + "\n");
			sb.append(highPriority);
			sb.append("] \n");
			sb.append(mediumPriority);
			sb.append("] \n");
			sb.append(lowPriority);	
			sb.append("] \n");
		}
		sb.append("Regards, \n AutoBuds1 PMD Report Team.");
		String body =  sb.toString();
		sendMail(email, emailSubject, body);
	} 
	
	public void reviewerEmailNotification(EmailField emailField) throws IOException, MessagingException {
		String emailSubject;
		ObjectMapper mapper = new ObjectMapper();
		PMDReportPOJO reportObj = mapper.readValue(emailField.getReportString(), PMDReportPOJO.class);
		OrgId orgPrimaryKey = new OrgId(emailField.getUserEmail(), emailField.getOrgId());
		String orgName = orgService.getOrgById(orgPrimaryKey).getOrgName();
		emailSubject = "Apex Pmd Analysis report on : " + orgName + "(" + emailField.getOrgId() + ")";
		String filepath = "./userData/" + emailField.getUserEmail() + "/" + emailField.getUserEmail() + "PMD_Report_"+ java.time.LocalDate.now();
		FileWriter fw = new FileWriter(filepath);
		fw.write(emailField.getReportString());
		fw.close();
		StringBuilder sb = new StringBuilder();
		sb.append("Greetings User,\n");
		sb.append("Report Details are given below. \n");
		sb.append("SF Org Name: "+ orgName + "( OrgId:" + emailField.getOrgId() + ")\n");
		sb.append("Triggered Time: " + reportObj.getTimestamp() +"\n");
		sb.append("PMD Version: " + reportObj.getPmdVersion());
		sb.append("Files \n");
		int[] violationCount = new int[6]; // 0 contains total count
		List<ApexFile> files = reportObj.getFiles();
		for(ApexFile file : files) {
			String fileName = FilenameUtils.getName(file.getFilename());
			List<Violation> violations = file.getViolations();
			sb.append(fileName + ", ");
			for(Violation violate : violations) {
				violationCount[0]++;
				violationCount[violate.getPriority().intValue()]++;
			}
		}
		sb.append(" \n  Violation Summary \n");
		sb.append("Total Violations: "  + violationCount[0] + "\n");
		sb.append("Priority 1: "  + violationCount[1] + "\n");
		sb.append("Priority 2: "  + violationCount[2] + "\n");
		sb.append("Priority 3: "  + violationCount[3] + "\n");
		sb.append("Priority 4: "  + violationCount[4] + "\n");
		sb.append("Priority 5: "  + violationCount[5] + "\n");
		sb.append("Regards,\nAutoBuds1 PMD Report Team.");
		String body =  sb.toString();
		System.out.println("Inside review email::");
		sendMessageWithAttachment(emailField.getRecipients(), emailSubject, body, filepath);
	}
}
