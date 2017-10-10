/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.user.management.services;

import java.io.Serializable; 
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL; 
import javax.ejb.Stateless;
import javax.inject.Inject; 
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam; 
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j; 
import se.nrm.dina.user.management.keycloak.properties.ConfigurationProperties;
import se.nrm.dina.user.management.logic.UserManagement;
import se.nrm.dina.user.management.utils.PATCH;

/**
 *
 * @author idali
 */
@Path("/user/api/v01")
@Stateless
@Slf4j
public class UserManagementUnsecureService implements Serializable {
 
    @Inject
    private UserManagement userManagement;
    
    @Inject
    private ConfigurationProperties config;
    
    @GET
    @Produces("text/plain")
    public Response doGet() {
        log.info("doGet");
        return Response.ok("Hello from WildFly Swarm!").build();
    }
    
      
    @GET    
    @Path("/users/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})     
    public Response getUserById(@Context HttpServletRequest req, @PathParam("id") String id) {
        log.info("getUserById : id :  {}", id); 
        
        return Response.ok(userManagement.getUserById(id)).build();
    } 
     

    @GET
    @Path("/users/search")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getUsers(@QueryParam("filter[email]") String email) {
        log.info("getUsers : email :  {} ", email);
         
        return Response.ok(userManagement.getUserByUserName(email)).build();    
    }
    
    @GET
    @Path("/setup-password")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response setupPassword(@QueryParam("key") long key, @QueryParam("id") String id) {
        log.info("setupPassword");
          
        StringBuilder sb = new StringBuilder();
        sb.append(config.getUiURL());
        if(userManagement.isEmailExpired(key)) {
            sb.append("/#/email-expired");
        } else {
            userManagement.setupPassword(id);
            sb.append("/#/password-setup/");
            sb.append(id);
        }
         
        URI targetURI = null;
        try { 
            URL url = new URL(sb.toString());  
            targetURI = url.toURI();  
        } catch (MalformedURLException | URISyntaxException ex) {
           log.error(ex.getMessage());
        }
        return Response.temporaryRedirect(targetURI).build();
    }
  
    @GET
    @Path("/email-verification")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response emailVerification(@QueryParam("key") long key, @QueryParam("id") String id) {
        log.info("emailVerification : key :  {} -- {}", key, id);
          
        StringBuilder sb = new StringBuilder();
        sb.append(config.getUiURL());
        if(userManagement.isEmailExpired(key)) {
            sb.append("/#/email-expired");
        } else {
            userManagement.emailVerification(id);
            sb.append("/#/email-verification");
        }
         
        URI targetURI = null;
        try { 
            URL url = new URL(sb.toString());  
            targetURI = url.toURI();  
        } catch (MalformedURLException | URISyntaxException ex) {
           log.error(ex.getMessage());
        }
        return Response.temporaryRedirect(targetURI).build();
    }
    
    @POST
    @Path("/users")
    public Response createUser(String json) { 
        log.info("createUser : {}", json); 
        return Response.ok(userManagement.createUser(json, false)).build(); 
    }

    @POST
    @Path("/sendemail")    
    public Response sendEmail(@QueryParam("id") String id) {
        
        log.info("sendEmail : {}", id); 
         
        return Response.ok(userManagement.sendEmail(id, true)).build();
    }
    
    @PUT
    @Path("/recover-password")    
    public Response recoverPassword(@QueryParam("email") String email) {
        
        log.info("recoverPassword : {} ", email); 
         
        return Response.ok(userManagement.recoverPassword(email)).build();
    }
    
    @PATCH
    @Path("/users/{id}")
    public Response updatePassword(@Context HttpServletRequest req, String json, @PathParam("id") String id) {
        log.info("updatePassword : {}  --  {}", json, id);
        return Response.ok(userManagement.updatePassword(json)).build();
    }
}
