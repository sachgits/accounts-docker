/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.user.management.mails;

import se.nrm.dina.user.management.utils.Util;

/**
 *
 * @author idali
 */
public class MailMessages {
    
    private static final String NEW_ACCOUNT_NOTIFICATION_SUBJECT = "New account notification";
    private static final String NEW_ACCOUNT_NOTIFICATION_BODY = "New account notification";
    private static final String EMAIL_VARIFICATION_NOTIFICATION_SUBJECT = "Email varification";
    private static final String EMAIL_VARIFICATION_NOTIFICATION_BODY = "Email varification";
 
    private static MailMessages instance = null;
    
    public static synchronized MailMessages getInstance() {
        if (instance == null) {
            instance = new MailMessages();
        }
        return instance;
    }
    
    public String getEmailVerificationNotificationSubject() {
        return EMAIL_VARIFICATION_NOTIFICATION_SUBJECT;
    }
    
    public String getEmailVerificationNotificationBody(String apiUrl, String id) {
        
        long nowlong = Util.getInstance().getNowLong();
        
        StringBuilder sb = new StringBuilder();
        sb.append(EMAIL_VARIFICATION_NOTIFICATION_BODY);
        sb.append("<br/><br/>");
        sb.append(apiUrl);
        sb.append("/user/api/v01/email-verification?key=");
        sb.append(nowlong);
        sb.append("&id=");
        sb.append(id);
        
        System.out.println("string: " + sb.toString());
        return sb.toString();
    }
    
    public String getNewAccountNotificationSubject() {
        return NEW_ACCOUNT_NOTIFICATION_SUBJECT;
    }
    
    public String getNewAccountNotificationBody() {
        return NEW_ACCOUNT_NOTIFICATION_BODY;
    }
}
