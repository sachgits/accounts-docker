/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.user.management.mails;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Properties;  
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import lombok.extern.slf4j.Slf4j;
import se.nrm.dina.user.management.keycloak.properties.ConfigurationProperties;

/**
 *
 * @author idali
 */
@Slf4j
public class MailHandler implements Serializable  {
     
     
    private final String MAIL_HOST = "mail.smtps.host"; 
       
    private final String MAIL_PROTOCOL = "mail.transport.protocol";
    private final String SMTP = "smtp";
    private final String MAIL_AUTH = "mail.smtps.auth";
    private final String MAIL_PORT = "mail.smtp.port";
    private final String MAIL_ENABLE = "mail.smtp.starttls.enable"; 
    
    private final String TEXT_HTML = "text/html; charset=ISO-8859-1";
    private final String UTF_8 = "utf-8";
    private final String B = "B";
     
    private Session session;
    private Message message;
    
    private String mailHost;
    private int mailPort;
    private String mailFrom;
    private String mailUsername;
    private String mailPassword;
              
    private String apiUrl;
    private String superAdminEmail;
      
    @Inject
    public MailHandler(ConfigurationProperties config) {
        log.info("MailHandler"); 
         
        this.superAdminEmail = config.getSuperAdminUsername();
        this.mailHost = config.getEmailHost();
        this.mailPort = Integer.parseInt(config.getEmilPort());
        this.mailFrom = config.getEmailFrom();
        this.mailUsername = config.getEmailUsername();
        this.mailPassword = config.getEmailPassword();
        this.apiUrl = config.getApiURL();

        Properties props = new Properties();
        props.put(MAIL_PROTOCOL, SMTP);
        props.put(MAIL_HOST, mailHost);
        props.put(MAIL_AUTH, String.valueOf(Boolean.TRUE));
        props.put(MAIL_PORT, mailPort);
        props.put(MAIL_ENABLE, String.valueOf(Boolean.TRUE));

        session = Session.getInstance(props, null);
        session.setDebug(true);

        message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(mailFrom));
        } catch (MessagingException ex) {
            log.error(ex.getMessage());
        }
    }
    
    public void sendSetPasswordEmail(String id, String email) {
        log.info("sendSetPasswordEmail");
        
        try {  
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(MimeUtility.encodeText(MailMessages.getInstance().getSetupPasswordSubject(), UTF_8, B));
  
            message.setContent(MailMessages.getInstance().getSetupPasswordBody(apiUrl, id), TEXT_HTML);
            sendMail(email);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
        } 
    }
    
    public void sendEmailVerificationNotication(String id, String email) {
        log.info("sendEmailVerificationNotication");
          
        try {  
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(MimeUtility.encodeText(MailMessages.getInstance().getEmailVerificationNotificationSubject(), UTF_8, B));
  
            message.setContent(MailMessages.getInstance().getEmailVerificationNotificationBody(apiUrl, id), TEXT_HTML);
            sendMail(email);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
        } 
    }
     
    public void sendEmailToAdmin() {
        log.info("sendEmailToAdmin");
 
        // For test purpose
        superAdminEmail = "ida.li@nrm.se";

        try { 
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(superAdminEmail));
            message.setSubject(MimeUtility.encodeText(MailMessages.getInstance().getNewAccountNotificationSubject(), UTF_8, B));

            message.setContent(MailMessages.getInstance().getNewAccountNotificationBody(), TEXT_HTML);
            InternetAddress[] toaddress = new InternetAddress[]{new InternetAddress(superAdminEmail)};
            sendMail(superAdminEmail);
//            Transport transport = session.getTransport();
//            transport.connect(mailHost, mailPort, mailUsername, mailPassword);
//            transport.sendMessage(message, toaddress);
//            transport.close();
        } catch (MessagingException | UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
        } 
    }
    
    private void sendMail(String toAddress) {
        try {
            InternetAddress[] toaddress = new InternetAddress[]{new InternetAddress(toAddress)};

            Transport transport = session.getTransport(); 
            transport.connect(mailHost, mailPort, mailUsername, mailPassword); 
            transport.sendMessage(message, toaddress);
            transport.close();
        } catch (AddressException ex) {
            log.error(ex.getMessage());
        } catch (MessagingException ex) {
            log.error(ex.getMessage());
        }
    }
}
