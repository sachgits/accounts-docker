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
    private static final String EMAIL_VARIFICATION_NOTIFICATION_SUBJECT = "Confirm your email for DINA";
    private static final String EMAIL_TITLE = "Dear user,";
    private static final String REGARDS = "Regards";
    private static final String WELCOME = "Welcome!";
    private static final String DINA_TEAM = "The DINA team";
    private static final String DOT = ". ";
    private static final String SEPARATER_LINE = "--";
    private static final String SUPPORT_EMAIL_ADDRESS = "dina-admin@nrm.se";
    private static final String EMAIL_VARIFICATION_NOTIFICATION_BODY_1 = "Please confirm your email address for DINA by going to this address:";
    private static final String EMAIL_VARIFICATION_NOTIFICATION_BODY_2 = "After confirming your email, DINA administrator will accept you as a user. You will receive another email when this has been done. Then you can log in and start using DINA.";
    private static final String EMAIL_VARIFICATION_NOTIFICATION_BODY_3 =  " If you did not confirm your email during this time, please request a new link from DINA administrator at "; 
    
    private static final String SETUP_PASSWORD_SUBJECT = "Select a password for DINA";
    private static final String SETUP_PASSWORD_BODY_1 = "You have now been accepted as a user of the DINA system with your username ";
    private static final String SETUP_PASSWORD_BODY_2 = "  Please click on the link below to select a password for yourself. ";
    private static final String SETUP_PASSWORD_BODY_3 = "After doing this, you can sign in to DINA at ";
    private static final String SETUP_PASSWORD_BODY_4 = " If you have questions about using DINA, please contact ";
    
    private static final String LINK_EXPIRE = "The link will expire in 24 hours. ";
    private static final String API_ROOT = "/user/api/v01";
    private static final String QUERY_ID = "&id=";
    private static final String EMAIL_VERIFICATION_LINK = "/email-verification?key=";
    private static final String SETUP_PASSWORD_LINK = "/setup-password?key=";
    private static final String NEW_LINE = "<br/>";
 
    private static MailMessages instance = null;
    
    public static synchronized MailMessages getInstance() {
        if (instance == null) {
            instance = new MailMessages();
        }
        return instance;
    }
    
    public String getEmailTitle() {
        return EMAIL_TITLE;
    }
    
    public String getSetupPasswordSubject() {
        return SETUP_PASSWORD_SUBJECT;
    }
    
    public String getSetupPasswordBody(String apiUrl, String uiUrl, String id, String email) {
        
        long nowlong = Util.getInstance().getNowLong();
        
        StringBuilder sb = new StringBuilder();
        sb.append(EMAIL_TITLE);
        sb.append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append(SETUP_PASSWORD_BODY_1);
        sb.append(email);
        sb.append(DOT);
        sb.append(SETUP_PASSWORD_BODY_2);
        sb.append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append(apiUrl);
        sb.append(API_ROOT);
        sb.append(SETUP_PASSWORD_LINK); 
        sb.append(nowlong);
        sb.append(QUERY_ID);
        sb.append(id); 
        sb.append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append(SETUP_PASSWORD_BODY_3);
        sb.append(uiUrl);
        sb.append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append(LINK_EXPIRE);
        sb.append(SETUP_PASSWORD_BODY_4);
        sb.append(SUPPORT_EMAIL_ADDRESS);
        sb.append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append(WELCOME);
        sb.append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append(SEPARATER_LINE);
        sb.append(NEW_LINE);
        sb.append(REGARDS);
        sb.append(NEW_LINE);
        sb.append(DINA_TEAM);
        sb.append(NEW_LINE);
        sb.append(SUPPORT_EMAIL_ADDRESS);
        
        
        return sb.toString();
    }
    
    
    public String getEmailVerificationNotificationSubject() {
        return EMAIL_VARIFICATION_NOTIFICATION_SUBJECT;
    }
    
    public String getEmailVerificationNotificationBody(String apiUrl, String id) {
        
        long nowlong = Util.getInstance().getNowLong();
        
        StringBuilder sb = new StringBuilder();
        sb.append(EMAIL_TITLE);
        sb.append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append(EMAIL_VARIFICATION_NOTIFICATION_BODY_1);
        sb.append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append(apiUrl);
        sb.append(API_ROOT);
        sb.append(EMAIL_VERIFICATION_LINK); 
        sb.append(nowlong);
        sb.append(QUERY_ID);
        sb.append(id);
        sb.append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append(EMAIL_VARIFICATION_NOTIFICATION_BODY_2);
        sb.append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append(LINK_EXPIRE);
        sb.append(EMAIL_VARIFICATION_NOTIFICATION_BODY_3);
        sb.append(SUPPORT_EMAIL_ADDRESS);
        sb.append(DOT);
        sb.append(NEW_LINE);
        sb.append(NEW_LINE);  
        sb.append(SEPARATER_LINE);
        sb.append(NEW_LINE);
        sb.append(REGARDS);
        sb.append(NEW_LINE);
        sb.append(DINA_TEAM);
        sb.append(NEW_LINE);
        sb.append(SUPPORT_EMAIL_ADDRESS);
        
        return sb.toString();
    }
    
    public String getNewAccountNotificationSubject() {
        return NEW_ACCOUNT_NOTIFICATION_SUBJECT;
    }
    
    public String getNewAccountNotificationBody() {
        return NEW_ACCOUNT_NOTIFICATION_BODY;
    }
}
