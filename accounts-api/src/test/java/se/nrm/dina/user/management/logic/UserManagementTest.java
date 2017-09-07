/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.user.management.logic;
 
import java.util.ArrayList;
import java.util.List;
import javax.json.JsonObject;
import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.UserSessionRepresentation;
import static org.mockito.Matchers.anyObject;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import se.nrm.dina.user.management.json.JsonConverter;
import se.nrm.dina.user.management.keycloak.properties.ConfigurationProperties;

/**
 *
 * @author idali
 */
@RunWith(MockitoJUnitRunner.class)
public class UserManagementTest {
     
    @Mock
    private Keycloak keycloakClient;
    @Mock
    private ConfigurationProperties config;
    @Mock
    private RealmManagement realm;
    @Mock
    private RealmResource realmResource;
    @Mock
    private UsersResource usersResource;
    @Mock
    private UserResource userResource;
    @Mock
    private UserRepresentation userRepresentation;
    @Mock
    private UserSessionRepresentation userSessiontRepresentation;
    @Mock
    private JsonConverter json;
    @Mock
    private JsonObject jsonObj;
    
    private List<UserRepresentation> userRepresentations;
    private List<UserSessionRepresentation> userSessionRepresentations;
    private UserManagement instance;
    private String id;
    private int usersCount;
    
    private String jsonString;
    
    public UserManagementTest() {
    }
 
    @Before
    public void setUp() {
        instance = new UserManagement(keycloakClient, config, realm, json);
        
        id = "12345678";
        usersCount = 8;
        userRepresentations = new ArrayList<>();
        userRepresentations = Mockito.spy(userRepresentations);
        userRepresentations.add(userRepresentation);
        userRepresentations.add(userRepresentation);
        
        userSessionRepresentations = new ArrayList<>();
        userSessionRepresentations = Mockito.spy(userSessionRepresentations);
         
        
        Mockito.when(realm.getDinaRealmResource()).thenReturn(realmResource);
        Mockito.when(realmResource.users()).thenReturn(usersResource);
        Mockito.when(usersResource.count()).thenReturn(usersCount);
        Mockito.when(usersResource.search(null, 0, usersCount)).thenReturn(userRepresentations);
        
        Mockito.when(userRepresentation.getId()).thenReturn(id);
        Mockito.when(usersResource.get(id)).thenReturn(userResource);
        
        Mockito.when(json.converterUsers(anyObject())).thenReturn(jsonObj);
        
        jsonString = "{ \"data\": {\"type\": \"roles\", \"id\": \"9e5731e7-caeb-40d1-ab2e-a31bba8b4413\", \"attributes\": {" +
"			\"role_name\": \"super admin\",\n" +
"			\"description\": \"super admin\",\n" +
"			\"is_client\": false,\n" +
"			\"role_belong_to\": \"dina\"\n" +
"		}\n" +
"	}\n" +
"}";
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createUser method, of class UserManagement.
     */
    @Ignore
    @Test
    public void testCreateUser() {
        System.out.println("createUser");
        
        String jsonString = "";
        
        boolean createdByAdmin = false;
        
        JsonObject expResult = null;
        JsonObject result = instance.createUser(jsonString, createdByAdmin);
        assertEquals(expResult, result); 
    }

    /**
     * Test of sendEmail method, of class UserManagement.
     */
    @Ignore
    @Test
    public void testSendEmail() {
        System.out.println("sendEmail");
        String id = "";
        boolean isPendingUser = false;
        UserManagement instance = new UserManagement();
        JsonObject expResult = null;
        JsonObject result = instance.sendEmail(id, isPendingUser);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of recoverPassword method, of class UserManagement.
     */
    @Ignore
    @Test
    public void testRecoverPassword() {
        System.out.println("recoverPassword");
        String email = "";
        UserManagement instance = new UserManagement();
        JsonObject expResult = null;
        JsonObject result = instance.recoverPassword(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of disableUser method, of class UserManagement.
     */
    @Ignore
    @Test
    public void testDisableUser() {
        System.out.println("disableUser");
        String id = "";
        UserManagement instance = new UserManagement();
        JsonObject expResult = null;
        JsonObject result = instance.disableUser(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rejectUser method, of class UserManagement.
     */
    @Ignore
    @Test
    public void testRejectUser() {
        System.out.println("rejectUser");
        String id = "";
        UserManagement instance = new UserManagement();
        JsonObject expResult = null;
        JsonObject result = instance.rejectUser(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteUser method, of class UserManagement.
     */
    @Ignore
    @Test
    public void testDeleteUser() {
        System.out.println("deleteUser");
        String id = "";
        UserManagement instance = new UserManagement();
        JsonObject expResult = null;
        JsonObject result = instance.deleteUser(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enableUser method, of class UserManagement.
     */
    @Ignore
    @Test
    public void testEnableUser() {
        System.out.println("enableUser");
        String id = "";
        UserManagement instance = new UserManagement();
        JsonObject expResult = null;
        JsonObject result = instance.enableUser(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateUser method, of class UserManagement.
     */
    @Ignore
    @Test
    public void testUpdateUser() {
        System.out.println("updateUser");
        String jsonString = "";
        UserManagement instance = new UserManagement();
        JsonObject expResult = null;
        JsonObject result = instance.updateUser(jsonString);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of logout method, of class UserManagement.
     */
    @Ignore
    @Test
    public void testLogout() {
        System.out.println("logout");
        String id = "";
        UserManagement instance = new UserManagement();
        JsonObject expResult = null;
        JsonObject result = instance.logout(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserById method, of class UserManagement.
     */
    @Ignore
    @Test
    public void testGetUserById() {
        System.out.println("getUserById");
        String id = "";
        UserManagement instance = new UserManagement();
        JsonObject expResult = null;
        JsonObject result = instance.getUserById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserByUserName method, of class UserManagement.
     */
    @Ignore
    @Test
    public void testGetUserByUserName() {
        System.out.println("getUserByUserName");
        String userName = "";
        UserManagement instance = new UserManagement();
        JsonObject expResult = null;
        JsonObject result = instance.getUserByUserName(userName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserByAccountStatus method, of class UserManagement.
     */
    @Ignore
    @Test
    public void testGetUserByAccountStatus() {
        System.out.println("getUserByAccountStatus");
        String status = "Pending";
 
        JsonObject expResult = null;
        JsonObject result = instance.getUserByAccountStatus(status);
        assertEquals(expResult, result); 
    }

    /**
     * Test of getUsers method, of class UserManagement.
     */
    @Ignore
    @Test
    public void testGetUsers() {
        System.out.println("getUsers"); 
        
        JsonObject expResult = null;
        JsonObject result = instance.getUsers();
        assertEquals(expResult, result); 
    }

    /**
     * Test of getLoggedInUser method, of class UserManagement.
     */
    @Test
    public void testGetLoggedInUserWithEmptyData() {
        System.out.println("testGetLoggedInUserWithEmptyData");
  
        Mockito.when(userResource.getUserSessions()).thenReturn(userSessionRepresentations);
         
        JsonObject expResult = jsonObj;
        JsonObject result = instance.getLoggedInUser();
        assertEquals(expResult, result); 
        assertNull(result.getString("data"));
        verify(usersResource, times(1)).count();
        verify(usersResource, times(1)).search(null, 0, usersCount);
    }
    
    /**
     * Test of getLoggedInUser method, of class UserManagement.
     */
    @Test
    public void testGetLoggedInUser() {
        System.out.println("getLoggedInUser");
  
        userSessionRepresentations.add(userSessiontRepresentation);
        userSessionRepresentations.add(userSessiontRepresentation);
        Mockito.when(userResource.getUserSessions()).thenReturn(userSessionRepresentations);
         
        JsonObject expResult = jsonObj; 
        JsonObject result = instance.getLoggedInUser();
 
        assertEquals(expResult, result); 
        
        verify(usersResource, times(1)).count();
        verify(usersResource, times(1)).search(null, 0, usersCount);
    }
    
}
