/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.user.management.services;
 
import java.io.Serializable;
import java.security.Principal; 
import java.util.Map;  
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;  
import javax.ws.rs.POST;
import javax.ws.rs.PUT; 
import javax.ws.rs.Path; 
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessToken.Access; 
import se.nrm.dina.user.management.logic.ClientManagement;
import se.nrm.dina.user.management.logic.RealmManagement;
import se.nrm.dina.user.management.logic.RoleManagement;
import se.nrm.dina.user.management.logic.TsvUploader;
import se.nrm.dina.user.management.logic.UserManagement; 
import se.nrm.dina.user.management.utils.ClientAction;
import se.nrm.dina.user.management.utils.CommonString;
import se.nrm.dina.user.management.utils.PATCH;

/**
 *
 * @author idali
 */
@Path("/user/api/v01/secure")
@Stateless
@Slf4j
public class UserManagementServices implements Serializable {
    
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Inject
    private TsvUploader tsv;
    
    @Inject
    private UserManagement userManagement;
    
    @Inject
    private ClientManagement clientManagement;
    
    @Inject
    private RealmManagement realmManagement;
    
    @Inject
    private RoleManagement roleManagement;
    
    @GET
    @Produces("text/plain")
    public Response doGet() {
        log.info("doGet");
        return Response.ok("Hello from WildFly Swarm!").build();
    }
     
    @GET    
    @Path("/users")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})     
    public Response getUsers(@Context HttpServletRequest req) {
        log.info("getUsers"); 
        
        return Response.ok(userManagement.getUsers()).build();
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
    public Response searchUsers(@Context HttpServletRequest req, @Context UriInfo info) {
        log.info("searchUsers");
 
        MultivaluedMap<String, String> map = info.getQueryParameters();
        String action = map.getFirst("filter[action]");
        log.info("action : {}", action);
        
        String username = map.getFirst("filter[email]");
        if(action == null) {
            return Response.ok(userManagement.getUsers()).build();
        } else {
            if(action.equals(ClientAction.validatePassword.name())) { 
                String password = map.getFirst("filter[password]"); 
                return Response.ok(userManagement.verifyPassword(username, password)).build();
            } else if(action.equals(ClientAction.validateAccount.name())) { 
                return Response.ok(userManagement.getUserByUserName(username)).build();
            } else if(action.equals(ClientAction.filterStatus.name())) {
                String status = map.getFirst("filter[status]"); 
                if(status.equals("loggedIn")) {
                    return Response.ok(userManagement.getLoggedInUser()).build(); 
                } else {
                    StringBuilder sb = new StringBuilder(status);
                    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                    status = sb.toString();
                    return Response.ok(userManagement.getUserByAccountStatus(status)).build(); 
                } 
            }    
                
                
            return Response.ok(userManagement.getUsers()).build();
        }
        
        
//        if (action != null && action.equals(ClientAction.validatePassword.name())) {  
//            String password = map.getFirst("filter[password]");
//            
//            return Response.ok(userManagement.verifyPassword(username, password)).build();
//        } else {
//            String status = map.getFirst("filter[status]"); 
//                
//            if (username != null) {
//                return Response.ok(userManagement.getUserByUserName(username)).build();
//            } else if (status != null && !status.isEmpty()) {
//                StringBuilder sb = new StringBuilder(status);
//                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
//                status = sb.toString();
//                return Response.ok(userManagement.getUserByAccountStatus(status)).build();
//            } else {
//                return Response.ok(userManagement.getUsers()).build();
//            }
//        } 
    }

    @GET    
    @Path("/clients/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})     
    public Response getClientsById(@Context HttpServletRequest req, @PathParam("id") String id) {
        log.info("getClientsById: {}", id);  
        return Response.ok(clientManagement.getClientById(id)).build();
    }
    
    @GET    
    @Path("/clients")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})     
    public Response getRealmClients(@Context HttpServletRequest req, @QueryParam("realm") String realm) {
        log.info("getRealmClients: {}", realm);  
        return Response.ok(clientManagement.getAllTheClients()).build();
    }
    
    @GET    
    @Path("/roles/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})     
    public Response getRolesById(@Context HttpServletRequest req, @PathParam("id") String id) {
        log.info("getRolesById: {}", id);  
        return Response.ok(roleManagement.getRoleById(id)).build();
    }
    
    @GET    
    @Path("/realms")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})     
    public Response getRealmByName(@Context HttpServletRequest req, @QueryParam("realm") String realm) {
        log.info("getRealmsByName: {}", realm); 
         
        return Response.ok(realmManagement.getRealmByRealmName(realm)).build();
    }
    
    @GET    
    @Path("/realms/search")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})     
    public Response getRealmsByName(@Context HttpServletRequest req, @QueryParam("filter[realm]") String realm) {
        log.info("getRealmsByName: {}", realm); 
         
        return Response.ok(realmManagement.getRealmByRealmName(realm)).build();
    }
   
    private boolean isAdminRole(HttpServletRequest req) { 
        Principal userPrincipal = req.getUserPrincipal();        
        if (userPrincipal instanceof KeycloakPrincipal) {   
            KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) userPrincipal;
            AccessToken token = kp.getKeycloakSecurityContext().getToken();        
            Map<String, Access> accessMap = token.getResourceAccess();
            Access access = accessMap.get(CommonString.getInstance().getUserManagementClientId());
            return access.getRoles().toString().equals("[admin]"); 
        }
        return false;
    }
      
    @POST
    @Path("/users")
    public Response createUser(@Context HttpServletRequest req, String json) {
        log.info("createUser : {}", json);
          
        if(isAdminRole(req)) {
            return Response.ok(userManagement.createUser(json, true)).build();
        } 
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    
    @POST
    @Path("/group")
    public Response addGroup(String json) {
        
        return Response.ok("Tsv upload").build();
    }
    
    @POST
    @Path("/tsv")    
    public Response tsvUpload(String tsvFile) {
        
        log.info("tsvUpload : {}", tsvFile);
        
        tsv.upload(tsvFile);
        return Response.ok("Tsv upload").build();
    }
    
    
    @POST
    @Path("/sendemail")    
    public Response sendEmail(@Context HttpServletRequest req, @QueryParam("id") String id, @QueryParam("isPendingUser") boolean isPendingUser) { 
        log.info("sendEmail : {} -- {}", id, isPendingUser); 
        
        if(isAdminRole(req)) {
            return Response.ok(userManagement.sendEmail(id, isPendingUser)).build(); 
        } 
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    
    
    
    @PUT
    @Path("/enableDisableUser")
    public Response userActions(@Context HttpServletRequest req, @QueryParam("id") String id, @QueryParam("action") String action) {
        log.info("userActions : {} -- {}", id, action); 
        
        if(isAdminRole(req)) {
            return Response.ok(action.equals("enableUser") ? userManagement.enableUser(id) : userManagement.disableUser(id)).build();
        } 
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    
    @PATCH
    @Path("/users/{id}")
    public Response updateUser(@Context HttpServletRequest req, String json, @PathParam("id") String id) {
        log.info("updateUser : {}  --  {}", json, id);
        return Response.ok(userManagement.updateUser(json)).build();
    }
    
    @PUT
    @Path("/logout")
    public Response logout(@QueryParam("id") String id) {
        log.info("logout : {}", id);  
        return Response.ok(userManagement.logout(id)).build();
    } 
    
    @DELETE
    @Path("/users/{id}") 
    public Response delete(@Context HttpServletRequest req, @PathParam("id") String id) {
        log.info("delete : {}", id);
        
         if(isAdminRole(req)) {
            userManagement.deleteUser(id);
            return Response.noContent().build();
        } 
        return Response.status(Response.Status.UNAUTHORIZED).build();  
    } 
}
