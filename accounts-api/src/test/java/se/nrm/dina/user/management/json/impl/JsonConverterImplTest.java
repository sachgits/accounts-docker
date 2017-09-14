/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.user.management.json.impl;

import java.util.List;
import java.util.Map;
import javax.json.JsonObject;
import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import se.nrm.dina.user.management.json.JsonConverter;
import se.nrm.dina.user.management.utils.CommonString;

/**
 *
 * @author idali
 */
public class JsonConverterImplTest {
    
    private JsonConverter instance;
    
    public JsonConverterImplTest() {
    }
 
    @Before
    public void setUp() {
        instance = new JsonConverterImpl();
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of converterRole method, of class JsonConverterImpl.
     */
    @Test
    public void testConverterRole() {
        System.out.println("converterRole");
          
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName("user");
        roleRepresentation.setDescription("user role");
        roleRepresentation.setId("abcd");
        
        String roleBelongTo = "client"; 
        String expType = "roles";
        String expId = "abcd";
        
        JsonObject result = instance.converterRole(roleRepresentation, roleBelongTo);
        result.getJsonObject(CommonString.getInstance().getData()).getString(CommonString.getInstance().getType());
        assertEquals(expType, result.getJsonObject(CommonString.getInstance().getData()).getString(CommonString.getInstance().getType())); 
        assertEquals(expId, result.getJsonObject(CommonString.getInstance().getData()).getString(CommonString.getInstance().getId())); 
        
        assertNotNull(result.getJsonObject(CommonString.getInstance().getData()).get(CommonString.getInstance().getAttributes()));
        
    }

    /**
     * Test of converterClient method, of class JsonConverterImpl.
     */
    @Ignore
    @Test
    public void testConverterClient() {
        System.out.println("converterClient");
        ClientRepresentation clienRepresentation = null;
        List<RoleRepresentation> roleRepresentations = null;
        JsonConverterImpl instance = new JsonConverterImpl();
        JsonObject expResult = null;
        JsonObject result = instance.converterClient(clienRepresentation, roleRepresentations);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of converterClients method, of class JsonConverterImpl.
     */
    @Ignore
    @Test
    public void testConverterClients() {
        System.out.println("converterClients");
        Map<ClientRepresentation, List<RoleRepresentation>> clientRepresentationRolesMap = null;
        JsonConverterImpl instance = new JsonConverterImpl();
        JsonObject expResult = null;
        JsonObject result = instance.converterClients(clientRepresentationRolesMap);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readInJson method, of class JsonConverterImpl.
     */
    @Ignore
    @Test
    public void testReadInJson() {
        System.out.println("readInJson");
        String json = "";
        JsonConverterImpl instance = new JsonConverterImpl();
        JsonObject expResult = null;
        JsonObject result = instance.readInJson(json);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of converterUser method, of class JsonConverterImpl.
     */
    @Ignore
    @Test
    public void testConverterUser() {
        System.out.println("converterUser");
        UserRepresentation userRepresentation = null;
        List<RoleRepresentation> realmRoles = null;
        Map<String, List<RoleRepresentation>> clientRoles = null;
        JsonConverterImpl instance = new JsonConverterImpl();
        JsonObject expResult = null;
        JsonObject result = instance.converterUser(userRepresentation, realmRoles, clientRoles);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of converterUsers method, of class JsonConverterImpl.
     */
    @Ignore
    @Test
    public void testConverterUsers() {
        System.out.println("converterUsers");
        List<UserRepresentation> userList = null;
        JsonConverterImpl instance = new JsonConverterImpl();
        JsonObject expResult = null;
        JsonObject result = instance.converterUsers(userList);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of converterRealm method, of class JsonConverterImpl.
     */
    @Ignore
    @Test
    public void testConverterRealm() {
        System.out.println("converterRealm");
        RealmRepresentation realmRepresentation = null;
        List<RoleRepresentation> roleRepresentations = null;
        List<ClientRepresentation> clientRepresentations = null;
        JsonConverterImpl instance = new JsonConverterImpl();
        JsonObject expResult = null;
        JsonObject result = instance.converterRealm(realmRepresentation, roleRepresentations, clientRepresentations);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of successJson method, of class JsonConverterImpl.
     */
    @Ignore
    @Test
    public void testSuccessJson() {
        System.out.println("successJson");
        String message = "";
        JsonConverterImpl instance = new JsonConverterImpl();
        JsonObject expResult = null;
        JsonObject result = instance.successJson(message);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildErrorMessages method, of class JsonConverterImpl.
     */
    @Ignore
    @Test
    public void testBuildErrorMessages() {
        System.out.println("buildErrorMessages");
        String error = "";
        List<String> errMsgs = null;
        JsonConverterImpl instance = new JsonConverterImpl();
        JsonObject expResult = null;
        JsonObject result = instance.buildErrorMessages(error, errMsgs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
