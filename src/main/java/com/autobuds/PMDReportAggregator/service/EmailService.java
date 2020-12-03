package com.autobuds.PMDReportAggregator.service;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.autobuds.PMDReportAggregator.model.OrgId;
import com.autobuds.PMDReportAggregator.utility.ApexFile;
import com.autobuds.PMDReportAggregator.utility.PMDReportPOJO;
import com.autobuds.PMDReportAggregator.utility.Violation;

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
    public enum PRIORITY { HIGH(1), LOW(5), MEDIUM(3); private int value; private PRIORITY(int value) { this.value = value; } };
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
}
