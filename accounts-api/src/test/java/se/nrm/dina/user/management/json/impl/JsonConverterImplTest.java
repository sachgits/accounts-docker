/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.user.management.json.impl;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.json.JsonArray; 
import javax.json.JsonObject;
import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;
import static org.junit.Assert.*; 
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import se.nrm.dina.user.management.json.JsonConverter;
import se.nrm.dina.user.management.utils.AccountStatus;
import se.nrm.dina.user.management.utils.CommonString;

/**
 *
 * @author idali
 */
public class JsonConverterImplTest {
    
    private JsonConverter instance;
    private JsonObject result;
    private JsonObject dataJson;
    private JsonArray dataArrayJson;
    private JsonObject attrJson;
    private JsonObject relJson;
    private JsonObject relTypeJson;
    private JsonArray subDataArr; 
    private JsonObject subDataJson;
      
    private String id;
    private String roleName;
    private String roleDescription; 
    private String roleBelongTo;
    
    private String expType;
    private String expId;
    
    public JsonConverterImplTest() {
    }
 
    @Before
    public void setUp() {
        instance = new JsonConverterImpl();
        id = "abcd";
        roleName = "user";
        roleDescription = "user role";
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of converterRole method, of class JsonConverterImpl.
     */
    @Test
    public void testConverterClientRole() {
        System.out.println("converterRole");
             
        roleBelongTo = "client"; 
        
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(roleDescription);
        roleRepresentation.setId(id);
        roleRepresentation.setClientRole(Boolean.TRUE);
        
        expType = CommonString.getInstance().getTypeRoles();
        expId = id;
        
        result = instance.converterRole(roleRepresentation, roleBelongTo);
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships());
         
        assertEquals(expType, dataJson.getString(CommonString.getInstance().getType())); 
        assertEquals(expId, dataJson.getString(CommonString.getInstance().getId())); 
        
        assertNotNull(attrJson);  
        assertEquals(attrJson.getString(CommonString.getInstance().getRoleName()), roleName);
        assertEquals(attrJson.getString(CommonString.getInstance().getDescription()), roleDescription);
        assertEquals(attrJson.getString(CommonString.getInstance().getRoleBelongTo()), roleBelongTo);
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsClient()), Boolean.TRUE);
        
        assertNull(relJson);
    }
    
    /**
     * Test of converterRole method, of class JsonConverterImpl.
     */
    @Test
    public void testConverterRealmRole() {
        System.out.println("converterRole");
             
        roleBelongTo = "realm"; 
        
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(roleDescription);
        roleRepresentation.setId(id);
        roleRepresentation.setClientRole(Boolean.FALSE);
        
        expType = CommonString.getInstance().getTypeRoles();
        expId = id;
        
        result = instance.converterRole(roleRepresentation, roleBelongTo);
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships());
         
        assertEquals(expType, dataJson.getString(CommonString.getInstance().getType())); 
        assertEquals(expId, dataJson.getString(CommonString.getInstance().getId())); 
        
        assertNotNull(attrJson);  
        assertEquals(attrJson.getString(CommonString.getInstance().getRoleName()), roleName);
        assertEquals(attrJson.getString(CommonString.getInstance().getDescription()), roleDescription);
        assertEquals(attrJson.getString(CommonString.getInstance().getRoleBelongTo()), roleBelongTo);
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsClient()), Boolean.FALSE);
        
        assertNull(relJson);
    }

    /**
     * Test of converterClient method, of class JsonConverterImpl.
     */ 
    @Test
    public void testConverterClient() {
        System.out.println("converterClient");
         
        ClientRepresentation clienRepresentation = new ClientRepresentation();
        clienRepresentation.setClientId(CommonString.getInstance().getDinaRestClientId());
        clienRepresentation.setName(CommonString.getInstance().getDinaRestClientName());
        clienRepresentation.setDescription(CommonString.getInstance().getDinaRestClientDescription());
        clienRepresentation.setId(id);
        
        roleBelongTo = "client";
        List<RoleRepresentation> roleRepresentations = new ArrayList();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(roleDescription);
        roleRepresentation.setClientRole(Boolean.TRUE);
        roleRepresentation.setId("abcd"); 
        roleRepresentations.add(roleRepresentation);
    
        result = instance.converterClient(clienRepresentation, roleRepresentations);
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships());
        
        expId = id;
        expType = CommonString.getInstance().getTypeClients();
        
        assertEquals(expType, dataJson.getString(CommonString.getInstance().getType())); 
        assertEquals(expId, dataJson.getString(CommonString.getInstance().getId())); 
        
        assertNotNull(attrJson);  
        assertEquals(attrJson.getString(CommonString.getInstance().getClientId()), CommonString.getInstance().getDinaRestClientId());
        assertEquals(attrJson.getString(CommonString.getInstance().getClientName()), CommonString.getInstance().getDinaRestClientName());
        assertEquals(attrJson.getString(CommonString.getInstance().getDescriptions()), CommonString.getInstance().getDinaRestClientDescription()); 
        
        assertNotNull(relJson); 
        relTypeJson = relJson.getJsonObject(CommonString.getInstance().getTypeRoles());
        subDataArr = relTypeJson.getJsonArray(CommonString.getInstance().getData());
        
        assertEquals(subDataArr.size(), 1);
        subDataJson = subDataArr.getJsonObject(0);
        assertEquals(subDataJson.getString(CommonString.getInstance().getType()), CommonString.getInstance().getTypeRoles());
        assertEquals(expId, subDataJson.getString(CommonString.getInstance().getId())); 
    }
    
    /**
     * Test of converterClient method, of class JsonConverterImpl.
     */ 
    @Test
    public void testConverterClientWithNullClientRoles() {
        System.out.println("converterClient");
         
        ClientRepresentation clienRepresentation = new ClientRepresentation();
        clienRepresentation.setClientId(CommonString.getInstance().getDinaRestClientId());
        clienRepresentation.setName(CommonString.getInstance().getDinaRestClientName());
        clienRepresentation.setDescription(CommonString.getInstance().getDinaRestClientDescription());
        clienRepresentation.setId(id);
        
        roleBelongTo = "client";
        List<RoleRepresentation> roleRepresentations = null; 
    
        result = instance.converterClient(clienRepresentation, roleRepresentations);
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships());
        
        expId = id;
        expType = CommonString.getInstance().getTypeClients();
        
        assertEquals(expType, dataJson.getString(CommonString.getInstance().getType())); 
        assertEquals(expId, dataJson.getString(CommonString.getInstance().getId())); 
        
        assertNotNull(attrJson);  
        assertEquals(attrJson.getString(CommonString.getInstance().getClientId()), CommonString.getInstance().getDinaRestClientId());
        assertEquals(attrJson.getString(CommonString.getInstance().getClientName()), CommonString.getInstance().getDinaRestClientName());
        assertEquals(attrJson.getString(CommonString.getInstance().getDescriptions()), CommonString.getInstance().getDinaRestClientDescription()); 
        
        assertNotNull(relJson); 
        relTypeJson = relJson.getJsonObject(CommonString.getInstance().getTypeRoles());
        subDataArr = relTypeJson.getJsonArray(CommonString.getInstance().getData());
        
        assertEquals(subDataArr.size(), 0); 
    }
   
    /**
     * Test of converterClient method, of class JsonConverterImpl.
     */ 
    @Test
    public void testConverterClientWithEmptyClientRoles() {
        System.out.println("converterClient");
         
        ClientRepresentation clienRepresentation = new ClientRepresentation();
        clienRepresentation.setClientId(CommonString.getInstance().getDinaRestClientId());
        clienRepresentation.setName(CommonString.getInstance().getDinaRestClientName());
        clienRepresentation.setDescription(CommonString.getInstance().getDinaRestClientDescription());
        clienRepresentation.setId(id);
        
        roleBelongTo = "client";
        List<RoleRepresentation> roleRepresentations = new ArrayList(); 
    
        result = instance.converterClient(clienRepresentation, roleRepresentations);
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships());
        
        expId = id;
        expType = CommonString.getInstance().getTypeClients();
        
        assertEquals(expType, dataJson.getString(CommonString.getInstance().getType())); 
        assertEquals(expId, dataJson.getString(CommonString.getInstance().getId())); 
        
        assertNotNull(attrJson);  
        assertEquals(attrJson.getString(CommonString.getInstance().getClientId()), CommonString.getInstance().getDinaRestClientId());
        assertEquals(attrJson.getString(CommonString.getInstance().getClientName()), CommonString.getInstance().getDinaRestClientName());
        assertEquals(attrJson.getString(CommonString.getInstance().getDescriptions()), CommonString.getInstance().getDinaRestClientDescription()); 
        
        assertNotNull(relJson); 
        relTypeJson = relJson.getJsonObject(CommonString.getInstance().getTypeRoles());
        subDataArr = relTypeJson.getJsonArray(CommonString.getInstance().getData());
        
        assertEquals(subDataArr.size(), 0); 
    }

    /**
     * Test of converterClients method, of class JsonConverterImpl.
     */ 
    @Test
    public void testConverterClients() {
        System.out.println("converterClients");
        
        roleBelongTo = "client";
        List<RoleRepresentation> roleRepresentations = new ArrayList();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(roleDescription);
        roleRepresentation.setClientRole(Boolean.TRUE);
        roleRepresentation.setId("abcd"); 
        roleRepresentations.add(roleRepresentation);
        
        Map<ClientRepresentation, List<RoleRepresentation>> clientRepresentationRolesMap = new LinkedHashMap<>();
        
        ClientRepresentation clienRepresentation = new ClientRepresentation(); 
        clienRepresentation.setClientId(CommonString.getInstance().getUserManagementClientId());
        clienRepresentation.setName(CommonString.getInstance().getUserManagementClientName());
        clienRepresentation.setDescription(CommonString.getInstance().getUserManagementClientDescription());
        clienRepresentation.setId(id);
        clientRepresentationRolesMap.put(clienRepresentation, roleRepresentations);
         
        clienRepresentation = new ClientRepresentation();
        clienRepresentation.setClientId(CommonString.getInstance().getDinaRestClientId());
        clienRepresentation.setName(CommonString.getInstance().getDinaRestClientName());
        clienRepresentation.setDescription(CommonString.getInstance().getDinaRestClientDescription());
        clienRepresentation.setId(id);
        clientRepresentationRolesMap.put(clienRepresentation, roleRepresentations);
          
        expType = CommonString.getInstance().getTypeClients();
        expId = id;
        
        result = instance.converterClients(clientRepresentationRolesMap);
        dataArrayJson = result.getJsonArray(CommonString.getInstance().getData());
          
        assertNotNull(dataArrayJson);
        assertEquals(dataArrayJson.size(), 2);
        
        JsonObject dataJson1 = dataArrayJson.getJsonObject(0);
        
        assertEquals(expType, dataJson1.getString(CommonString.getInstance().getType()));
        assertEquals(expId, dataJson1.getString(CommonString.getInstance().getId()));
        
        attrJson = dataJson1.getJsonObject(CommonString.getInstance().getAttributes());
        assertNotNull(attrJson);
        assertEquals(attrJson.getString(CommonString.getInstance().getClientId()), CommonString.getInstance().getUserManagementClientId());
        assertEquals(attrJson.getString(CommonString.getInstance().getClientName()), CommonString.getInstance().getUserManagementClientName());
        assertEquals(attrJson.getString(CommonString.getInstance().getDescriptions()), CommonString.getInstance().getUserManagementClientDescription()); 
        
        relJson = dataJson1.getJsonObject(CommonString.getInstance().getRelationships());
        assertNotNull(relJson); 
        relTypeJson = relJson.getJsonObject(CommonString.getInstance().getTypeRoles());
        
        subDataArr = relTypeJson.getJsonArray(CommonString.getInstance().getData());
        
        assertEquals(subDataArr.size(), 1);
        subDataJson = subDataArr.getJsonObject(0);
        
        assertEquals(subDataJson.getString(CommonString.getInstance().getType()), CommonString.getInstance().getTypeRoles());
        assertEquals(expId, subDataJson.getString(CommonString.getInstance().getId())); 
        
        JsonObject dataJson2 = dataArrayJson.getJsonObject(1);
        
        assertEquals(expType, dataJson2.getString(CommonString.getInstance().getType()));
        assertEquals(expId, dataJson2.getString(CommonString.getInstance().getId()));
        
        attrJson = dataJson2.getJsonObject(CommonString.getInstance().getAttributes());
        assertNotNull(attrJson);
        assertEquals(attrJson.getString(CommonString.getInstance().getClientId()), CommonString.getInstance().getDinaRestClientId());
        assertEquals(attrJson.getString(CommonString.getInstance().getClientName()), CommonString.getInstance().getDinaRestClientName());
        assertEquals(attrJson.getString(CommonString.getInstance().getDescriptions()), CommonString.getInstance().getDinaRestClientDescription()); 
        
        relJson = dataJson2.getJsonObject(CommonString.getInstance().getRelationships());
        assertNotNull(relJson); 
        relTypeJson = relJson.getJsonObject(CommonString.getInstance().getTypeRoles());
        subDataArr = relTypeJson.getJsonArray(CommonString.getInstance().getData());
        
        assertEquals(subDataArr.size(), 1);
        subDataJson = subDataArr.getJsonObject(0);
        assertEquals(subDataJson.getString(CommonString.getInstance().getType()), CommonString.getInstance().getTypeRoles());
        assertEquals(expId, subDataJson.getString(CommonString.getInstance().getId())); 
    }
    
    /**
     * Test of converterClients method, of class JsonConverterImpl.
     */ 
    @Test
    public void testConverterClientsWithNull() {
        System.out.println("converterClients");
          
        result = instance.converterClients(null);
        dataArrayJson = result.getJsonArray(CommonString.getInstance().getData());
           
        assertNotNull(dataArrayJson);
        assertEquals(dataArrayJson.size(), 0); 
    }

    /**
     * Test of converterClients method, of class JsonConverterImpl.
     */ 
    @Test
    public void testConverterClientsWithEmptyValue() {
        System.out.println("converterClients");
         
        Map<ClientRepresentation, List<RoleRepresentation>> clientRepresentationRolesMap = new LinkedHashMap<>();
        result = instance.converterClients(clientRepresentationRolesMap);
        dataArrayJson = result.getJsonArray(CommonString.getInstance().getData());
           
        assertNotNull(dataArrayJson);
        assertEquals(dataArrayJson.size(), 0);
    }

    /**
     * Test of converterClients method, of class JsonConverterImpl.
     */
    @Test
    public void testConverterClientsWithNullRoles() {
        System.out.println("converterClients");

        Map<ClientRepresentation, List<RoleRepresentation>> clientRepresentationRolesMap = new LinkedHashMap<>();
        
        ClientRepresentation clienRepresentation = new ClientRepresentation(); 
        clienRepresentation.setClientId(CommonString.getInstance().getUserManagementClientId());
        clienRepresentation.setName(CommonString.getInstance().getUserManagementClientName());
        clienRepresentation.setDescription(CommonString.getInstance().getUserManagementClientDescription());
        clienRepresentation.setId(id);
        
        List<RoleRepresentation> roleRepresentations = new ArrayList();
        clientRepresentationRolesMap.put(clienRepresentation, roleRepresentations);
          
        result = instance.converterClients(clientRepresentationRolesMap);
        dataArrayJson = result.getJsonArray(CommonString.getInstance().getData());
        
        expType = CommonString.getInstance().getTypeClients();
        expId = id;
 
        assertNotNull(dataArrayJson);
        assertEquals(dataArrayJson.size(), 1);  
        dataJson = dataArrayJson.getJsonObject(0);
        
        assertEquals(expType, dataJson.getString(CommonString.getInstance().getType()));
        assertEquals(expId, dataJson.getString(CommonString.getInstance().getId()));
        
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        assertNotNull(attrJson);
        assertEquals(attrJson.getString(CommonString.getInstance().getClientId()), CommonString.getInstance().getUserManagementClientId());
        assertEquals(attrJson.getString(CommonString.getInstance().getClientName()), CommonString.getInstance().getUserManagementClientName());
        assertEquals(attrJson.getString(CommonString.getInstance().getDescriptions()), CommonString.getInstance().getUserManagementClientDescription()); 
        
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships()); 
        assertNull(relJson.getJsonObject(CommonString.getInstance().getTypeRoles()));  
    }
    
    /**
     * Test of converterClients method, of class JsonConverterImpl.
     */
    @Test
    public void testConverterClientsWithEmptyRoles() {
        System.out.println("converterClients");

        Map<ClientRepresentation, List<RoleRepresentation>> clientRepresentationRolesMap = new LinkedHashMap<>();
        
        ClientRepresentation clienRepresentation = new ClientRepresentation(); 
        clienRepresentation.setClientId(CommonString.getInstance().getUserManagementClientId());
        clienRepresentation.setName(CommonString.getInstance().getUserManagementClientName());
        clienRepresentation.setDescription(CommonString.getInstance().getUserManagementClientDescription());
        clienRepresentation.setId(id);
        clientRepresentationRolesMap.put(clienRepresentation, null);
          
        result = instance.converterClients(clientRepresentationRolesMap);
        dataArrayJson = result.getJsonArray(CommonString.getInstance().getData());
        
        expType = CommonString.getInstance().getTypeClients();
        expId = id;
 
        assertNotNull(dataArrayJson);
        assertEquals(dataArrayJson.size(), 1);  
        dataJson = dataArrayJson.getJsonObject(0);
        
        assertEquals(expType, dataJson.getString(CommonString.getInstance().getType()));
        assertEquals(expId, dataJson.getString(CommonString.getInstance().getId()));
        
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        assertNotNull(attrJson);
        assertEquals(attrJson.getString(CommonString.getInstance().getClientId()), CommonString.getInstance().getUserManagementClientId());
        assertEquals(attrJson.getString(CommonString.getInstance().getClientName()), CommonString.getInstance().getUserManagementClientName());
        assertEquals(attrJson.getString(CommonString.getInstance().getDescriptions()), CommonString.getInstance().getUserManagementClientDescription()); 
        
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships()); 
        assertNull(relJson.getJsonObject(CommonString.getInstance().getTypeRoles()));  
    }

    /**
     * Test of readInJson method, of class JsonConverterImpl.
     */  
    @Test
    public void testReadInJson() {
        System.out.println("readInJson");
        
        String json = "{\n" +
                        "\"data\": {\n" +
                            "\"attributes\": {\n" +
                            "\"username\": null,\n" +
                            "\"first_name\": \"Test\",\n" +
                            "\"last_name\": \"User\",\n" +
                            "\"email\": \"test.user@test.se\",\n" +
                            "\"purpose\": \"test\",\n" +
                            "\"password\": null,\n" +
                            "\"timestamp_created\": null,\n" +
                            "\"is_email_verified\": false,\n" +
                            "\"status\": null\n" +
                            "},\n" +
                            "\"relationships\": {\n" +
                                "\"roles\": {\n" +
                                    "\"data\": []\n" +
                                "}\n" +
                            "},\n" +
                            "\"type\": \"users\"\n" +
                            "}\n" +
                        "}"; 
         
        result = instance.readInJson(json); 
        assertNotNull(result);
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        
        expType = CommonString.getInstance().getTypeUsers();
        String expFirstName = "Test";
        String expLastName = "User";
        String expEmail = "test.user@test.se";
        String expPurpose = "test";
        boolean expIsEmailVerified = false;
        
        assertEquals(expType, dataJson.getString(CommonString.getInstance().getType()));
        
        assertNotNull(attrJson);
        assertEquals(expFirstName, attrJson.getString(CommonString.getInstance().getFirstName()));
        assertEquals(expLastName, attrJson.getString(CommonString.getInstance().getLastName()));
        assertEquals(expEmail, attrJson.getString(CommonString.getInstance().getEmail()));
        assertEquals(expPurpose, attrJson.getString(CommonString.getInstance().getPurpose()));
        assertEquals(expIsEmailVerified, attrJson.getBoolean(CommonString.getInstance().getIsEmailVerified()));
    }

    /**
     * Test of converterUser method, of class JsonConverterImpl.
     */  
    @Test
    public void testConverterUser() {
        System.out.println("converterUser");
        
        String firstName = "Test";
        String lastName = "User";
        String email = "test.user@test.se";
        String purpose = "test";
         
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEmail(email);
        userRepresentation.setUsername(email);
        userRepresentation.setEmailVerified(Boolean.FALSE);
        userRepresentation.setId(id);
        userRepresentation.setEnabled(true);      
           
        userRepresentation.singleAttribute(CommonString.getInstance().getStatus(), AccountStatus.Enabled.name());
        userRepresentation.singleAttribute(CommonString.getInstance().getPurpose(), purpose);
         
        List<RoleRepresentation> roleRepresentations = new ArrayList();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(roleDescription);
        roleRepresentation.setClientRole(Boolean.TRUE);
        roleRepresentation.setId("abcd"); 
        roleRepresentations.add(roleRepresentation);
          
        expType = CommonString.getInstance().getTypeUsers();
        expId = id; 
        Map<String, List<RoleRepresentation>> clientRoles = new HashMap<>(); 
        clientRoles.put(CommonString.getInstance().getDinaRestClientId(), roleRepresentations);
        clientRoles.put(CommonString.getInstance().getUserManagementClientId(), roleRepresentations);   
 
        result = instance.converterUser(userRepresentation, roleRepresentations, clientRoles); 
   
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships());
        
        assertEquals(dataJson.getString(CommonString.getInstance().getType()), expType);
        assertEquals(dataJson.getString(CommonString.getInstance().getId()), expId);
        assertEquals(attrJson.getString(CommonString.getInstance().getFirstName()), firstName);
        assertEquals(attrJson.getString(CommonString.getInstance().getLastName()), lastName);
        assertEquals(attrJson.getString(CommonString.getInstance().getEmail()), email);
        assertEquals(attrJson.getString(CommonString.getInstance().getUsername()), email);
        assertEquals(attrJson.getString(CommonString.getInstance().getPurpose()), purpose);
        assertEquals(attrJson.getString(CommonString.getInstance().getStatus()), AccountStatus.Enabled.name());
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsUserEnabled()), Boolean.TRUE);
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsEmailVerified()), Boolean.FALSE);
        
        relTypeJson = relJson.getJsonObject(CommonString.getInstance().getTypeRoles());
        subDataArr = relTypeJson.getJsonArray(CommonString.getInstance().getData());
        assertEquals(subDataArr.size(), 3);
        
        subDataJson = subDataArr.getJsonObject(0);
        assertEquals(subDataJson.getString(CommonString.getInstance().getType()), CommonString.getInstance().getTypeRole());
        assertEquals(subDataJson.getString(CommonString.getInstance().getId()), id);
    }
    
    /**
     * Test of converterUser method, of class JsonConverterImpl.
     */  
    @Test
    public void testConverterUserWithNullStatus() {
        System.out.println("converterUser");
        
        String firstName = "Test";
        String lastName = "User";
        String email = "test.user@test.se";
        String purpose = "test";
         
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEmail(email);
        userRepresentation.setUsername(email);
        userRepresentation.setEmailVerified(Boolean.FALSE);
        userRepresentation.setId(id);
        userRepresentation.setEnabled(true);      
            
        userRepresentation.singleAttribute(CommonString.getInstance().getPurpose(), purpose);
         
        List<RoleRepresentation> roleRepresentations = new ArrayList();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(roleDescription);
        roleRepresentation.setClientRole(Boolean.TRUE);
        roleRepresentation.setId("abcd"); 
        roleRepresentations.add(roleRepresentation);
          
        expType = CommonString.getInstance().getTypeUsers();
        expId = id; 
        Map<String, List<RoleRepresentation>> clientRoles = new HashMap<>(); 
        clientRoles.put(CommonString.getInstance().getDinaRestClientId(), roleRepresentations);
        clientRoles.put(CommonString.getInstance().getUserManagementClientId(), roleRepresentations);   
 
        result = instance.converterUser(userRepresentation, roleRepresentations, clientRoles); 
   
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships());
        
        assertEquals(dataJson.getString(CommonString.getInstance().getType()), expType);
        assertEquals(dataJson.getString(CommonString.getInstance().getId()), expId);
        assertEquals(attrJson.getString(CommonString.getInstance().getFirstName()), firstName);
        assertEquals(attrJson.getString(CommonString.getInstance().getLastName()), lastName);
        assertEquals(attrJson.getString(CommonString.getInstance().getEmail()), email);
        assertEquals(attrJson.getString(CommonString.getInstance().getUsername()), email);
        assertEquals(attrJson.getString(CommonString.getInstance().getPurpose()), purpose);
        assertFalse(attrJson.containsKey(CommonString.getInstance().getStatus())); 
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsUserEnabled()), Boolean.TRUE);
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsEmailVerified()), Boolean.FALSE);
        
        relTypeJson = relJson.getJsonObject(CommonString.getInstance().getTypeRoles());
        subDataArr = relTypeJson.getJsonArray(CommonString.getInstance().getData());
        assertEquals(subDataArr.size(), 3);
        
        subDataJson = subDataArr.getJsonObject(0);
        assertEquals(subDataJson.getString(CommonString.getInstance().getType()), CommonString.getInstance().getTypeRole());
        assertEquals(subDataJson.getString(CommonString.getInstance().getId()), id);
    }
    
    /**
     * Test of converterUser method, of class JsonConverterImpl.
     */  
    @Test
    public void testConverterUserWithEmptyStatus() {
        System.out.println("converterUser");
        
        String firstName = "Test";
        String lastName = "User";
        String email = "test.user@test.se";
        String purpose = "test";
         
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEmail(email);
        userRepresentation.setUsername(email);
        userRepresentation.setEmailVerified(Boolean.FALSE);
        userRepresentation.setId(id);
        userRepresentation.setEnabled(true);      
            
        userRepresentation.singleAttribute(CommonString.getInstance().getPurpose(), purpose);
        
        Map<String, List<String>> statusMap = new HashMap();
        statusMap.put(CommonString.getInstance().getStatus(), new ArrayList());
         
        List<RoleRepresentation> roleRepresentations = new ArrayList();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(roleDescription);
        roleRepresentation.setClientRole(Boolean.TRUE);
        roleRepresentation.setId("abcd"); 
        roleRepresentations.add(roleRepresentation);
          
        expType = CommonString.getInstance().getTypeUsers();
        expId = id; 
        Map<String, List<RoleRepresentation>> clientRoles = new HashMap<>(); 
        clientRoles.put(CommonString.getInstance().getDinaRestClientId(), roleRepresentations);
        clientRoles.put(CommonString.getInstance().getUserManagementClientId(), roleRepresentations);   
 
        result = instance.converterUser(userRepresentation, roleRepresentations, clientRoles); 
   
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships());
        
        assertEquals(dataJson.getString(CommonString.getInstance().getType()), expType);
        assertEquals(dataJson.getString(CommonString.getInstance().getId()), expId);
        assertEquals(attrJson.getString(CommonString.getInstance().getFirstName()), firstName);
        assertEquals(attrJson.getString(CommonString.getInstance().getLastName()), lastName);
        assertEquals(attrJson.getString(CommonString.getInstance().getEmail()), email);
        assertEquals(attrJson.getString(CommonString.getInstance().getUsername()), email);
        assertEquals(attrJson.getString(CommonString.getInstance().getPurpose()), purpose);
        assertFalse(attrJson.containsKey(CommonString.getInstance().getStatus())); 
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsUserEnabled()), Boolean.TRUE);
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsEmailVerified()), Boolean.FALSE);
        
        relTypeJson = relJson.getJsonObject(CommonString.getInstance().getTypeRoles());
        subDataArr = relTypeJson.getJsonArray(CommonString.getInstance().getData());
        assertEquals(subDataArr.size(), 3);
        
        subDataJson = subDataArr.getJsonObject(0);
        assertEquals(subDataJson.getString(CommonString.getInstance().getType()), CommonString.getInstance().getTypeRole());
        assertEquals(subDataJson.getString(CommonString.getInstance().getId()), id);
    }
    
    /**
     * Test of converterUser method, of class JsonConverterImpl.
     */  
    @Test
    public void testConverterUserWithNullPurpose() {
        System.out.println("converterUser");
        
        String firstName = "Test";
        String lastName = "User";
        String email = "test.user@test.se";
        String purpose = "test";
         
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEmail(email);
        userRepresentation.setUsername(email);
        userRepresentation.setEmailVerified(Boolean.FALSE);
        userRepresentation.setId(id);
        userRepresentation.setEnabled(true);      
            
        userRepresentation.singleAttribute(CommonString.getInstance().getStatus(), AccountStatus.Enabled.name());
         
        List<RoleRepresentation> roleRepresentations = new ArrayList();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(roleDescription);
        roleRepresentation.setClientRole(Boolean.TRUE);
        roleRepresentation.setId("abcd"); 
        roleRepresentations.add(roleRepresentation);
          
        expType = CommonString.getInstance().getTypeUsers();
        expId = id; 
        Map<String, List<RoleRepresentation>> clientRoles = new HashMap<>(); 
        clientRoles.put(CommonString.getInstance().getDinaRestClientId(), roleRepresentations);
        clientRoles.put(CommonString.getInstance().getUserManagementClientId(), roleRepresentations);   
 
        result = instance.converterUser(userRepresentation, roleRepresentations, clientRoles); 
   
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships());
        
        assertEquals(dataJson.getString(CommonString.getInstance().getType()), expType);
        assertEquals(dataJson.getString(CommonString.getInstance().getId()), expId);
        assertEquals(attrJson.getString(CommonString.getInstance().getFirstName()), firstName);
        assertEquals(attrJson.getString(CommonString.getInstance().getLastName()), lastName);
        assertEquals(attrJson.getString(CommonString.getInstance().getEmail()), email);
        assertEquals(attrJson.getString(CommonString.getInstance().getUsername()), email);
        assertEquals(attrJson.getString(CommonString.getInstance().getStatus()), AccountStatus.Enabled.name());
        assertFalse(attrJson.containsKey(CommonString.getInstance().getPurpose())); 
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsUserEnabled()), Boolean.TRUE);
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsEmailVerified()), Boolean.FALSE);
        
        relTypeJson = relJson.getJsonObject(CommonString.getInstance().getTypeRoles());
        subDataArr = relTypeJson.getJsonArray(CommonString.getInstance().getData());
        assertEquals(subDataArr.size(), 3);
        
        subDataJson = subDataArr.getJsonObject(0);
        assertEquals(subDataJson.getString(CommonString.getInstance().getType()), CommonString.getInstance().getTypeRole());
        assertEquals(subDataJson.getString(CommonString.getInstance().getId()), id);
    }
    
    /**
     * Test of converterUser method, of class JsonConverterImpl.
     */  
    @Test
    public void testConverterUserWithEmptyPurpose() {
        System.out.println("converterUser");
        
        String firstName = "Test";
        String lastName = "User";
        String email = "test.user@test.se";
        String purpose = "test";
         
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEmail(email);
        userRepresentation.setUsername(email);
        userRepresentation.setEmailVerified(Boolean.FALSE);
        userRepresentation.setId(id);
        userRepresentation.setEnabled(true);      
            
        userRepresentation.singleAttribute(CommonString.getInstance().getStatus(), AccountStatus.Enabled.name());
        
        Map<String, List<String>> statusMap = new HashMap();
        statusMap.put(CommonString.getInstance().getStatus(), new ArrayList());
         
        List<RoleRepresentation> roleRepresentations = new ArrayList();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(roleDescription);
        roleRepresentation.setClientRole(Boolean.TRUE);
        roleRepresentation.setId("abcd"); 
        roleRepresentations.add(roleRepresentation);
          
        expType = CommonString.getInstance().getTypeUsers();
        expId = id; 
        Map<String, List<RoleRepresentation>> clientRoles = new HashMap<>(); 
        clientRoles.put(CommonString.getInstance().getDinaRestClientId(), roleRepresentations);
        clientRoles.put(CommonString.getInstance().getUserManagementClientId(), roleRepresentations);   
 
        result = instance.converterUser(userRepresentation, roleRepresentations, clientRoles); 
   
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships());
        
        assertEquals(dataJson.getString(CommonString.getInstance().getType()), expType);
        assertEquals(dataJson.getString(CommonString.getInstance().getId()), expId);
        assertEquals(attrJson.getString(CommonString.getInstance().getFirstName()), firstName);
        assertEquals(attrJson.getString(CommonString.getInstance().getLastName()), lastName);
        assertEquals(attrJson.getString(CommonString.getInstance().getEmail()), email);
        assertEquals(attrJson.getString(CommonString.getInstance().getUsername()), email);
        assertEquals(attrJson.getString(CommonString.getInstance().getStatus()), AccountStatus.Enabled.name());
        assertFalse(attrJson.containsKey(CommonString.getInstance().getPurpose())); 
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsUserEnabled()), Boolean.TRUE);
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsEmailVerified()), Boolean.FALSE);
        
        relTypeJson = relJson.getJsonObject(CommonString.getInstance().getTypeRoles());
        subDataArr = relTypeJson.getJsonArray(CommonString.getInstance().getData());
        assertEquals(subDataArr.size(), 3);
        
        subDataJson = subDataArr.getJsonObject(0);
        assertEquals(subDataJson.getString(CommonString.getInstance().getType()), CommonString.getInstance().getTypeRole());
        assertEquals(subDataJson.getString(CommonString.getInstance().getId()), id);
    }
    
    /**
     * Test of converterUser method, of class JsonConverterImpl.
     */  
    @Test
    public void testConverterUserWithNullAttrs() {
        System.out.println("converterUser");
        
        String firstName = "Test";
        String lastName = "User";
        String email = "test.user@test.se";
        String purpose = "test";
         
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEmail(email);
        userRepresentation.setUsername(email);
        userRepresentation.setEmailVerified(Boolean.FALSE);
        userRepresentation.setId(id);
        userRepresentation.setEnabled(true);      
        userRepresentation.setAttributes(null);
        
        List<RoleRepresentation> roleRepresentations = new ArrayList();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(roleDescription);
        roleRepresentation.setClientRole(Boolean.TRUE);
        roleRepresentation.setId("abcd"); 
        roleRepresentations.add(roleRepresentation);
          
        expType = CommonString.getInstance().getTypeUsers();
        expId = id; 
        Map<String, List<RoleRepresentation>> clientRoles = new HashMap<>(); 
        clientRoles.put(CommonString.getInstance().getDinaRestClientId(), roleRepresentations);
        clientRoles.put(CommonString.getInstance().getUserManagementClientId(), roleRepresentations);   
 
        result = instance.converterUser(userRepresentation, roleRepresentations, clientRoles); 
   
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships());
        
        assertEquals(dataJson.getString(CommonString.getInstance().getType()), expType);
        assertEquals(dataJson.getString(CommonString.getInstance().getId()), expId);
        assertEquals(attrJson.getString(CommonString.getInstance().getFirstName()), firstName);
        assertEquals(attrJson.getString(CommonString.getInstance().getLastName()), lastName);
        assertEquals(attrJson.getString(CommonString.getInstance().getEmail()), email);
        assertEquals(attrJson.getString(CommonString.getInstance().getUsername()), email);
        assertFalse(attrJson.containsKey(CommonString.getInstance().getPurpose()));
        assertFalse(attrJson.containsKey(CommonString.getInstance().getStatus())); 
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsUserEnabled()), Boolean.TRUE);
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsEmailVerified()), Boolean.FALSE);
        
        relTypeJson = relJson.getJsonObject(CommonString.getInstance().getTypeRoles());
        subDataArr = relTypeJson.getJsonArray(CommonString.getInstance().getData());
        assertEquals(subDataArr.size(), 3);
        
        subDataJson = subDataArr.getJsonObject(0);
        assertEquals(subDataJson.getString(CommonString.getInstance().getType()), CommonString.getInstance().getTypeRole());
        assertEquals(subDataJson.getString(CommonString.getInstance().getId()), id);
    }
    
    /**
     * Test of converterUser method, of class JsonConverterImpl.
     */  
    @Test
    public void testConverterUserWithEmptyAttrs() {
        System.out.println("converterUser");
        
        String firstName = "Test";
        String lastName = "User";
        String email = "test.user@test.se";
        String purpose = "test";
         
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEmail(email);
        userRepresentation.setUsername(email);
        userRepresentation.setEmailVerified(Boolean.FALSE);
        userRepresentation.setId(id);
        userRepresentation.setEnabled(true);      
        
        Map<String, List<String>> map = new HashMap();
        userRepresentation.setAttributes(map);
        
        List<RoleRepresentation> roleRepresentations = new ArrayList();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(roleDescription);
        roleRepresentation.setClientRole(Boolean.TRUE);
        roleRepresentation.setId("abcd"); 
        roleRepresentations.add(roleRepresentation);
          
        expType = CommonString.getInstance().getTypeUsers();
        expId = id; 
        Map<String, List<RoleRepresentation>> clientRoles = new HashMap<>(); 
        clientRoles.put(CommonString.getInstance().getDinaRestClientId(), roleRepresentations);
        clientRoles.put(CommonString.getInstance().getUserManagementClientId(), roleRepresentations);   
 
        result = instance.converterUser(userRepresentation, roleRepresentations, clientRoles); 
   
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships());
        
        assertEquals(dataJson.getString(CommonString.getInstance().getType()), expType);
        assertEquals(dataJson.getString(CommonString.getInstance().getId()), expId);
        assertEquals(attrJson.getString(CommonString.getInstance().getFirstName()), firstName);
        assertEquals(attrJson.getString(CommonString.getInstance().getLastName()), lastName);
        assertEquals(attrJson.getString(CommonString.getInstance().getEmail()), email);
        assertEquals(attrJson.getString(CommonString.getInstance().getUsername()), email);
        assertFalse(attrJson.containsKey(CommonString.getInstance().getPurpose()));
        assertFalse(attrJson.containsKey(CommonString.getInstance().getStatus())); 
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsUserEnabled()), Boolean.TRUE);
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsEmailVerified()), Boolean.FALSE);
        
        relTypeJson = relJson.getJsonObject(CommonString.getInstance().getTypeRoles());
        subDataArr = relTypeJson.getJsonArray(CommonString.getInstance().getData());
        assertEquals(subDataArr.size(), 3);
        
        subDataJson = subDataArr.getJsonObject(0);
        assertEquals(subDataJson.getString(CommonString.getInstance().getType()), CommonString.getInstance().getTypeRole());
        assertEquals(subDataJson.getString(CommonString.getInstance().getId()), id);
    }
    
    /**
     * Test of converterUser method, of class JsonConverterImpl.
     */  
    @Test
    public void testConverterUserWithNullValue() {
        System.out.println("converterUser");
        
        List<RoleRepresentation> roleRepresentations = new ArrayList();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(roleDescription);
        roleRepresentation.setClientRole(Boolean.TRUE);
        roleRepresentation.setId("abcd"); 
        roleRepresentations.add(roleRepresentation);
           
        Map<String, List<RoleRepresentation>> clientRoles = new HashMap<>(); 
        clientRoles.put(CommonString.getInstance().getDinaRestClientId(), roleRepresentations);
        clientRoles.put(CommonString.getInstance().getUserManagementClientId(), roleRepresentations);   
  
        result = instance.converterUser(null, roleRepresentations, clientRoles); 
   
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships());
            
        assertNull(attrJson);
        assertNull(relJson);
    }
    
    /**
     * Test of converterUser method, of class JsonConverterImpl.
     */  
    @Test
    public void testConverterUserWithNullRealmRoles() {
        System.out.println("converterUser");
        
        List<RoleRepresentation> roleRepresentations = null;
        
        String firstName = "Test";
        String lastName = "User";
        String email = "test.user@test.se";
        String purpose = "test";
         
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEmail(email);
        userRepresentation.setUsername(email);
        userRepresentation.setEmailVerified(Boolean.FALSE);
        userRepresentation.setId(id);
        userRepresentation.setEnabled(true);      
           
        userRepresentation.singleAttribute(CommonString.getInstance().getStatus(), AccountStatus.Enabled.name());
        userRepresentation.singleAttribute(CommonString.getInstance().getPurpose(), purpose);
           
        Map<String, List<RoleRepresentation>> clientRoles = new HashMap<>(); 
        clientRoles.put(CommonString.getInstance().getDinaRestClientId(), roleRepresentations);
        clientRoles.put(CommonString.getInstance().getUserManagementClientId(), roleRepresentations);   
  
        result = instance.converterUser(userRepresentation, roleRepresentations, clientRoles); 
   
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships());
            
        assertNotNull(attrJson);
        assertNull(relJson);
    }

    /**
     * Test of converterUsers method, of class JsonConverterImpl.
     */ 
    @Test
    public void testConverterUsers() {
        System.out.println("converterUsers");
        
        List<UserRepresentation> userList = new ArrayList<>();
        
        String firstName = "Test";
        String lastName = "User";
        String email = "test.user@test.se";
        String purpose = "test";
         
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEmail(email);
        userRepresentation.setUsername(email);
        userRepresentation.setEmailVerified(Boolean.FALSE);
        userRepresentation.setId(id);
        userRepresentation.setEnabled(true);      
           
        userRepresentation.singleAttribute(CommonString.getInstance().getStatus(), AccountStatus.Enabled.name());
        userRepresentation.singleAttribute(CommonString.getInstance().getPurpose(), purpose);
        
        userList.add(userRepresentation);
          
        expType = CommonString.getInstance().getTypeUsers();
        expId = id; 
         
        result = instance.converterUsers(userList);  
        dataArrayJson = result.getJsonArray(CommonString.getInstance().getData());
        assertEquals(dataArrayJson.size(), 1);
        
        subDataJson = dataArrayJson.getJsonObject(0); 
        attrJson = subDataJson.getJsonObject(CommonString.getInstance().getAttributes());
        assertEquals(subDataJson.getString(CommonString.getInstance().getType()), expType);
        assertEquals(subDataJson.getString(CommonString.getInstance().getId()), expId);
     
        assertEquals(subDataJson.getString(CommonString.getInstance().getType()), expType);
        assertEquals(subDataJson.getString(CommonString.getInstance().getId()), expId);
        assertEquals(attrJson.getString(CommonString.getInstance().getFirstName()), firstName);
        assertEquals(attrJson.getString(CommonString.getInstance().getLastName()), lastName);
        assertEquals(attrJson.getString(CommonString.getInstance().getEmail()), email);
        assertEquals(attrJson.getString(CommonString.getInstance().getUsername()), email);
        assertEquals(attrJson.getString(CommonString.getInstance().getPurpose()), purpose);
        assertEquals(attrJson.getString(CommonString.getInstance().getStatus()), AccountStatus.Enabled.name());
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsUserEnabled()), Boolean.TRUE);
        assertEquals(attrJson.getBoolean(CommonString.getInstance().getIsEmailVerified()), Boolean.FALSE);
    }
    
    /**
     * Test of converterUsers method, of class JsonConverterImpl.
     */ 
    @Test
    public void testConverterUsersWithNullData() {
        System.out.println("converterUsers");
        
        List<UserRepresentation> userList = null;
          
        result = instance.converterUsers(userList);  
        dataArrayJson = result.getJsonArray(CommonString.getInstance().getData());
        assertEquals(dataArrayJson.size(), 0); 
    }

    /**
     * Test of converterUsers method, of class JsonConverterImpl.
     */ 
    @Test
    public void testConverterUsersWithEmptyData() {
        System.out.println("converterUsers");
        
        List<UserRepresentation> userList = new ArrayList();
          
        result = instance.converterUsers(userList);  
        dataArrayJson = result.getJsonArray(CommonString.getInstance().getData());
        assertEquals(dataArrayJson.size(), 0); 
    }
    
    /**
     * Test of converterRealm method, of class JsonConverterImpl.
     */ 
    @Test
    public void testConverterRealm() {
        System.out.println("converterRealm");
        
        String realmName = "dina";
        
        RealmRepresentation realmRepresentation = new RealmRepresentation(); 
        realmRepresentation.setRealm(realmName);
        realmRepresentation.setDisplayName(realmName);
        realmRepresentation.setId(id);
        
        List<RoleRepresentation> roleRepresentations = new ArrayList();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(roleDescription);
        roleRepresentation.setClientRole(Boolean.TRUE);
        roleRepresentation.setId("abcd"); 
        roleRepresentations.add(roleRepresentation);
        roleRepresentations.add(roleRepresentation);
        
        List<ClientRepresentation> clientRepresentations = new ArrayList<>(); 
        ClientRepresentation clienRepresentation = new ClientRepresentation(); 
        clienRepresentation.setClientId(CommonString.getInstance().getUserManagementClientId());
        clienRepresentation.setName(CommonString.getInstance().getUserManagementClientName());
        clienRepresentation.setDescription(CommonString.getInstance().getUserManagementClientDescription());
        clienRepresentation.setId(id);
        clientRepresentations.add(clienRepresentation);
         
        clienRepresentation = new ClientRepresentation();
        clienRepresentation.setClientId(CommonString.getInstance().getDinaRestClientId());
        clienRepresentation.setName(CommonString.getInstance().getDinaRestClientName());
        clienRepresentation.setDescription(CommonString.getInstance().getDinaRestClientDescription());
        clienRepresentation.setId(id);
        clientRepresentations.add(clienRepresentation);
         
        expType = "realms";
        expId = id;
        result = instance.converterRealm(realmRepresentation, roleRepresentations, clientRepresentations);
 
        dataJson = result.getJsonObject(CommonString.getInstance().getData());
        attrJson = dataJson.getJsonObject(CommonString.getInstance().getAttributes());
        relJson = dataJson.getJsonObject(CommonString.getInstance().getRelationships());
        
        assertEquals(expType, dataJson.getString(CommonString.getInstance().getType()));
        assertEquals(expId, dataJson.getString(CommonString.getInstance().getId()));
        assertEquals(realmName, attrJson.getString(CommonString.getInstance().getRealmName()));
        assertEquals(id, attrJson.getString(CommonString.getInstance().getRealmId()));
        assertEquals(realmName, attrJson.getString(CommonString.getInstance().getDescription()));
        
        assertTrue(relJson.containsKey(CommonString.getInstance().getTypeRoles()));
        assertTrue(relJson.containsKey(CommonString.getInstance().getTypeClients()));
    }

    /**
     * Test of successJson method, of class JsonConverterImpl.
     */ 
    @Test
    public void testSuccessJson() {
        System.out.println("successJson");
        
        String message = "success";  
        result = instance.successJson(message);
         
        assertEquals(message, result.getString(CommonString.getInstance().getResponse())); 
    }

    /**
     * Test of buildErrorMessages method, of class JsonConverterImpl.
     */ 
    @Test
    public void testBuildErrorMessages() {
        System.out.println("buildErrorMessages");
        
        String error = "error";
        
        List<String> errMsgs = new ArrayList();
        errMsgs.add(error); 
      
        result = instance.buildErrorMessages(error, errMsgs);  
        assertEquals(result.getString(CommonString.getInstance().getErrorMsgs()), "[error]");
    } 
    
    
    /**
     * Test of buildErrorMessages method, of class JsonConverterImpl.
     */ 
    @Test
    public void testBuildErrorMessagesNull() {
        System.out.println("buildErrorMessages");
        
        String error = "error"; 
        result = instance.buildErrorMessages(error, null); 
        assertEquals(result.getString(CommonString.getInstance().getErrorMsgs()), error);
    } 
    
    /**
     * Test of buildErrorMessages method, of class JsonConverterImpl.
     */ 
    @Test
    public void testBuildErrorMessagesEmpty() {
        System.out.println("buildErrorMessages");
        
        String error = "error"; 
        List<String> errMsgs = new ArrayList();
        result = instance.buildErrorMessages(error, errMsgs); 
        assertEquals(result.getString(CommonString.getInstance().getErrorMsgs()), error);
    } 
}
