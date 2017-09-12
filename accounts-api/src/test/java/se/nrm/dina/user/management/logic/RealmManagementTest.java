/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.user.management.logic;

import java.util.ArrayList;
import java.util.List;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*; 
import org.junit.runner.RunWith;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RealmsResource;
import org.keycloak.admin.client.resource.RoleMappingResource; 
import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import se.nrm.dina.user.management.json.JsonConverter;
import se.nrm.dina.user.management.keycloak.properties.ConfigurationProperties;
import se.nrm.dina.user.management.utils.CommonString;

/**
 *
 * @author idali
 */
@RunWith(MockitoJUnitRunner.class)
public class RealmManagementTest {

    @Mock
    private Keycloak keycloakClient;
    @Mock
    private ConfigurationProperties config;

    @Mock
    private RealmsResource realmsResource;
    @Mock
    private RealmResource realmResource;
    @Mock
    private RealmRepresentation realmRepresentation;

    @Mock
    private ClientsResource clientsResource;
    @Mock
    private ClientResource clientResource;
    @Mock
    private ClientRepresentation clientRepresentation;
  
    @Mock
    private UserResource userResource;
    @Mock
    private UsersResource usersResource;

    @Mock
    private RoleMappingResource roleMappingResource;
    @Mock
    private RoleScopeResource roleScopeResource;
    @Mock
    private RolesResource rolesResource;

    @Mock
    private JsonConverter json;
    @Mock
    private JsonObject jsonObj;

    @Mock
    private Response response;

    private List<RoleRepresentation> roleRepresentationList;
    private List<ClientRepresentation> clients;
    private List<RealmRepresentation> realmRepresentationList;
    private final String string = "http://sso:8080/auth/admin/realms/dina/users/8a86adb2-c214-462c-bc17-3deef9a73304";

    @Mock
    private RoleRepresentation roleRepresentation;

    private RealmManagement instance;

    private String dinaRestClientId;
    private String userManagementClientId;
    private String id;
    private String adminUsername;
    private String adminPassword;
    private String adminFirstname;
    private String adminLastname;

    public RealmManagementTest() {
    }

    @Before
    public void setUp() {
        instance = new RealmManagement(keycloakClient, config, json);

        dinaRestClientId = CommonString.getInstance().getDinaRestClientId();
        userManagementClientId = CommonString.getInstance().getUserManagementClientId();

        id = "12345678";

        adminUsername = "admin@dina.se";
        adminPassword = "password";
        adminFirstname = "admin";
        adminLastname = "dina";
        
        clients = new ArrayList<>();
        clients = Mockito.spy(clients);
        clients.add(clientRepresentation);
        
        realmRepresentationList = new ArrayList<>();
        realmRepresentationList = Mockito.spy(realmRepresentationList);
        realmRepresentationList.add(realmRepresentation);
        
        roleRepresentationList = new ArrayList();
        roleRepresentationList = Mockito.spy(roleRepresentationList);
        roleRepresentationList.add(roleRepresentation);
        roleRepresentationList.add(roleRepresentation);

        Mockito.when(config.getRealm()).thenReturn("dina");
        Mockito.when(config.getAdminUsername()).thenReturn(adminUsername);
        Mockito.when(config.getAdminUsername()).thenReturn(adminPassword);
        Mockito.when(config.getAdminUsername()).thenReturn(adminFirstname);
        Mockito.when(config.getAdminUsername()).thenReturn(adminLastname);

        Mockito.when(keycloakClient.realms()).thenReturn(realmsResource);
        Mockito.when(keycloakClient.realm(config.getRealm())).thenReturn(realmResource);
        Mockito.when(realmsResource.findAll()).thenReturn(realmRepresentationList);
        
        Mockito.when(realmsResource.realm("dina")).thenReturn(realmResource);
        Mockito.when(realmResource.clients()).thenReturn(clientsResource);
        Mockito.when(realmResource.users()).thenReturn(usersResource);
        Mockito.when(usersResource.create(anyObject())).thenReturn(response);

        Mockito.when(clientsResource.findByClientId(dinaRestClientId)).thenReturn(clients);
        Mockito.when(clientsResource.findByClientId(userManagementClientId)).thenReturn(clients);
        Mockito.when(clients.get(anyInt())).thenReturn(clientRepresentation);
        Mockito.when(clientRepresentation.getName()).thenReturn("dina");
        Mockito.when(clientRepresentation.getId()).thenReturn(id);

        Mockito.when(clientsResource.get(id)).thenReturn(clientResource); 
        Mockito.when(clientResource.roles()).thenReturn(rolesResource);
        
      
        Mockito.when(clientsResource.findAll()).thenReturn(clients);

        Mockito.when(realmResource.roles()).thenReturn(rolesResource);
        Mockito.when(rolesResource.list()).thenReturn(roleRepresentationList);
        Mockito.when(roleRepresentation.getName()).thenReturn("admin");

        Mockito.when(userResource.roles()).thenReturn(roleMappingResource);
        Mockito.when(roleMappingResource.realmLevel()).thenReturn(roleScopeResource);
        Mockito.when(roleScopeResource.listAll()).thenReturn(roleRepresentationList);
        Mockito.when(roleMappingResource.clientLevel(id)).thenReturn(roleScopeResource);
        
        Mockito.when(json.converterRealm(anyObject(), anyObject(), anyObject())).thenReturn(jsonObj); 
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getDinaRealmResource method, of class RealmManagement.
     */
    @Test
    public void testGetDinaRealmResource() {
        System.out.println("getDinaRealmResource");

        RealmResource expResult = realmResource;
        RealmResource result = instance.getDinaRealmResource();
        assertEquals(expResult, result);
        verify(realmsResource, times(1)).realm("dina");
        verify(config, times(2)).getRealm();
    }

    /**
     * Test of getRealmResources method, of class RealmManagement.
     */
    @Test
    public void testGetRealmResources() {
        System.out.println("getRealmResources");

        RealmsResource expResult = realmsResource;
        RealmsResource result = instance.getRealmResources();
        assertEquals(expResult, result);
        verify(keycloakClient, times(1)).realms();
    }

    /**
     * Test of isRealmExist method, of class RealmManagement.
     */ 
    @Test
    public void testIsRealmExistWithTrue() {
        System.out.println("isRealmExist"); 
        
        Mockito.when(realmRepresentation.getDisplayName()).thenReturn("dina");
        
        boolean expResult = true;
        boolean result = instance.isRealmExist();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isRealmExist method, of class RealmManagement.
     */ 
    @Test
    public void testIsRealmExistWithFalse() {
        System.out.println("isRealmExist"); 
        
        Mockito.when(realmRepresentation.getDisplayName()).thenReturn("test");
        
        boolean expResult = false;
        boolean result = instance.isRealmExist();
        assertEquals(expResult, result);
    }

    /**
     * Test of createClientRoles method, of class RealmManagement.
     */ 
    @Test
    public void testCreateClientRoles() {
        System.out.println("createClientRoles");

        instance.createClientRoles();

    }

    /**
     * Test of createClientRole method, of class RealmManagement.
     */
    @Test
    public void testCreateClientRole() {
        System.out.println("createClientRole");

        String role = "admin";
        String description = "Test client";

        instance.createClientRole(dinaRestClientId, role, description);
        
    }

    /**
     * Test of createRealmClient method, of class RealmManagement.
     */
    @Test
    public void testCreateRealmClientWithDirectAccessGrantsNotEnabled() {
        System.out.println("testCreateRealmClientWithDirectAccessGrantsNotEnabled");

        String clientName = "test client";
        String clientDescription = "Test client";
        boolean directAccessGrantsEnabled = false;

        instance.createRealmClient(dinaRestClientId, clientName, clientDescription, directAccessGrantsEnabled);
        verify(clientsResource, times(1)).create(anyObject());
        verify(config, times(0)).getUiURL();
    }

    /**
     * Test of createRealmClient method, of class RealmManagement.
     */
    @Test
    public void testCreateRealmClientWithDirectAccessGrantsEnabled() {
        System.out.println("testCreateRealmClientWithDirectAccessGrantsEnabled");

        String clientName = "test client";
        String clientDescription = "Test client";
        boolean directAccessGrantsEnabled = true;

        instance.createRealmClient(dinaRestClientId, clientName, clientDescription, directAccessGrantsEnabled);
        verify(clientsResource, times(1)).create(anyObject());
//        verify(config, times(1)).getUiURL();
    }

    /**
     * Test of createRealmRoles method, of class RealmManagement.
     */
    @Test
    public void testCreateRealmRoles() {
        System.out.println("createRealmRoles");
        instance.createRealmRoles();
        verify(rolesResource, times(4)).create(anyObject());
    }

    /**
     * Test of createRealmRole method, of class RealmManagement.
     */
    @Test
    public void testCreateRealmRole() {
        System.out.println("createRealmRole");

        String role = "admin";
        String roleDescription = "Admin role";
        instance.createRealmRole(role, roleDescription);
        verify(rolesResource, times(1)).create(anyObject());
    }

    /**
     * Test of createRealm method, of class RealmManagement.
     */
    @Test
    public void testCreateRealm() {
        System.out.println("createRealm");

        instance.createRealm();
        verify(realmsResource, times(1)).create(anyObject());
    }

    /**
     * Test of getRealmByRealmName method, of class RealmManagement.
     */ 
    @Test
    public void testGetRealmByRealmName() {
        System.out.println("getRealmByRealmName");

        String realmName = "dina";
 

        JsonObject result = instance.getRealmByRealmName(realmName);
       
    }

    /**
     * Test of createRealmInitialUsers method, of class RealmManagement.
     */
    @Test
    public void testCreateRealmInitialUsers() {
        System.out.println("createRealmInitialUsers");

        instance.createRealmInitialUsers();

        verify(config, times(1)).getSuperAdminUsername();
        verify(config, times(1)).getSuperAdminPassword();
        verify(config, times(1)).getSuperAdminFirstname();
        verify(config, times(1)).getSuperAdminLastname();

        verify(config, times(1)).getAdminUsername();
        verify(config, times(1)).getAdminPassword();
        verify(config, times(1)).getAdminFirstname();
        verify(config, times(1)).getAdminLastname();

        verify(config, times(1)).getUserUsername();
        verify(config, times(1)).getUserPassword();
        verify(config, times(1)).getUserFirstname();
        verify(config, times(1)).getUserLastname();

        verify(usersResource, times(3)).create(anyObject());
    }

    /**
     * Test of createRealmInitialUser method, of class RealmManagement.
     */
    @Test
    public void testCreateRealmInitialUser() {
        System.out.println("createRealmInitialUser");

        Mockito.when(response.getHeaderString(CommonString.getInstance().getLocation())).thenReturn(string);
        Mockito.when(usersResource.get("8a86adb2-c214-462c-bc17-3deef9a73304")).thenReturn(userResource);

        String purpose = "Test";
        String realmRole = "admin";
        String clientRole = "admin";
        instance.createRealmInitialUser(adminUsername, adminPassword, adminFirstname, adminLastname, purpose, realmRole, clientRole);
        verify(usersResource, times(1)).create(anyObject());
        verify(response, times(1)).getHeaderString(CommonString.getInstance().getLocation());
        verify(response, times(1)).close();
        verify(usersResource, times(1)).get("8a86adb2-c214-462c-bc17-3deef9a73304");
        verify(userResource, times(1)).resetPassword(anyObject());
        verify(roleScopeResource, times(3)).add(anyObject());
        verify(roleScopeResource, times(1)).remove(roleRepresentationList); 
    }
    
    
    /**
     * Test of createRealmInitialUser method, of class RealmManagement.
     */
    @Test
    public void testCreateRealmInitialUserWithEmptyHeader() {
        System.out.println("createRealmInitialUser");

        Mockito.when(response.getHeaderString(CommonString.getInstance().getLocation())).thenReturn(null); 

        String purpose = "Test";
        String realmRole = "admin";
        String clientRole = "admin";
        instance.createRealmInitialUser(adminUsername, adminPassword, adminFirstname, adminLastname, purpose, realmRole, clientRole);
        verify(usersResource, times(1)).create(anyObject());
        verify(response, times(1)).getHeaderString(CommonString.getInstance().getLocation());
        verify(response, times(1)).close();
        verify(usersResource, times(0)).get("8a86adb2-c214-462c-bc17-3deef9a73304");
        verify(userResource, times(0)).resetPassword(anyObject());
        verify(roleScopeResource, times(0)).add(anyObject());
        verify(roleScopeResource, times(0)).remove(roleRepresentationList); 
    }

    /**
     * Test of setClientRole method, of class RealmManagement.
     */
    @Test
    public void testSetClientAdminRole() {

        System.out.println("testSetClientAdminRole");

        String role = "admin";
        instance.setClientRole(dinaRestClientId, userResource, role);

        verify(roleScopeResource, times(1)).add(anyObject());
    }

    /**
     * Test of setClientRole method, of class RealmManagement.
     */
    @Test
    public void testSetClientOtherRole() {

        System.out.println("testSetClientAdminRole");

        String role = "other";
        instance.setClientRole(dinaRestClientId, userResource, role);

        verify(roleScopeResource, times(0)).add(anyObject());
    }

    /**
     * Test of getClientRolesById method, of class RealmManagement.
     */
    @Test
    public void testGetClientRolesById() {
        System.out.println("getClientRolesById");

        List<RoleRepresentation> expResult = roleRepresentationList;
        List<RoleRepresentation> result = instance.getClientRolesById(id);
        assertEquals(expResult, result);
        verify(clientsResource, times(1)).get(id);
        verify(clientResource, times(1)).roles();
        verify(rolesResource, times(1)).list();
    }

    /**
     * Test of getClientRepresentationByClientId method, of class
     * RealmManagement.
     */
    @Test
    public void testGetClientRepresentationByClientId() {
        System.out.println("getClientRepresentationByClientId");

        ClientRepresentation expResult = clientRepresentation;
        ClientRepresentation result = instance.getClientRepresentationByClientId(dinaRestClientId);
        assertEquals(expResult, result);
        verify(clientsResource, times(1)).findByClientId(dinaRestClientId);
        verify(clients, times(1)).get(0);
    }

    /**
     * Test of getClientResourceById method, of class RealmManagement.
     */
    @Test
    public void testGetClientResourceById() {
        System.out.println("getClientResourceById");

        ClientResource expResult = clientResource;
        ClientResource result = instance.getClientResourceById(id);
        assertEquals(expResult, result);
        verify(clientsResource, times(1)).get(id);
    }

    /**
     * Test of getDinaClientResources method, of class RealmManagement.
     */
    @Test
    public void testGetDinaClientResources() {
        System.out.println("getDinaClientResources");

        ClientsResource expResult = clientsResource;
        ClientsResource result = instance.getDinaClientResources();
        assertEquals(expResult, result);
        verify(realmResource, times(1)).clients();
    }

    /**
     * Test of removeRealmRolesFromUser method, of class RealmManagement.
     */
    @Test
    public void testRemoveRealmRolesFromUser() {
        System.out.println("removeRealmRolesFromUser");
        instance.removeRealmRolesFromUser(userResource);

        verify(userResource, times(2)).roles();
        verify(roleScopeResource, times(1)).remove(roleRepresentationList);
    }

    /**
     * Test of addRealmRolesToUser method, of class RealmManagement.
     */
    @Test
    public void testAddRealmRolesToUser() {
        System.out.println("addRealmRolesToUser");

        String role = "admin";
        instance.addRealmRolesToUser(userResource, role);
        verify(userResource, times(3)).roles();
        verify(roleScopeResource, times(1)).remove(roleRepresentationList);
        verify(roleScopeResource, times(1)).add(anyObject());
    }

}
