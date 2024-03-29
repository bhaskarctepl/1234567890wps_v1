
/**
 * Created Date: Nov 3, 2020
 * Class Name  : SendGridEmailUtil.java
 * Description : Description of the package.
 *
 * © Copyright 2008 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                rmaram          Nov 3, 2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.common.utils;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

/**
 * Send Grid Email Implementation.
 * 
 * @author rmaram
 * @since w1.0
 * @version w1.0
 * 
 */
@Service
public class SendGridEmailUtil {
	private static final Logger LOGGER = LogManager.getLogger(SendGridEmailUtil.class);

	@Value("${sendgrid.mailFrom}")
	private String mailFrom;
	
	@Value("${sendgrid.apiKey}")
	private String apiKey;

	public boolean sendMail(String recipient, String subject, String messageBody) {
		LOGGER.info("mailFrom is {}", mailFrom);
		// LOGGER.info("apiKey is {}", apiKey);
		Email from = new Email(mailFrom);
		Email to = new Email(recipient);
		Content content = new Content(MediaType.TEXT_HTML, messageBody);
		Mail mail = new Mail(from, subject, to, content);
		SendGrid sg = new SendGrid(apiKey);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			LOGGER.info("statusCode is {}", response.getStatusCode());
			LOGGER.info("body is {}", response.getBody());
			LOGGER.info("headers are {}", response.getHeaders());
			return true;
		} catch (IOException ex) {
			return false;
		}
	}
}
