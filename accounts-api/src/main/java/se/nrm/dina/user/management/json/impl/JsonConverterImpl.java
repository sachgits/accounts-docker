/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.user.management.json.impl;

import java.io.Serializable;
import java.io.StringReader; 
import java.util.List;
import java.util.Map; 
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;  
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation; 
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.dina.user.management.json.JsonConverter;
import se.nrm.dina.user.management.utils.CommonString; 

/**
 *
 * @author idali
 */
public class JsonConverterImpl implements Serializable, JsonConverter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final JsonBuilderFactory JSON_FACTORY = Json.createBuilderFactory(null);
    
    private JsonConvertHelper helper;

    public JsonConverterImpl() {

    }
    
    @Override
    public JsonObject converterRole(RoleRepresentation roleRepresentation, String roleBelongTo) {   
          
        logger.info("converterRole");
        
        JsonObjectBuilder jsonBuilder = JSON_FACTORY.createObjectBuilder();
        JsonObjectBuilder dataBuilder = Json.createObjectBuilder();
        buildRoleJson(roleRepresentation, dataBuilder, true, roleBelongTo);
         
        jsonBuilder.add(CommonString.getInstance().getData(), dataBuilder);
        return jsonBuilder.build(); 
    }
    
    @Override
    public JsonObject converterClient(ClientRepresentation clienRepresentation, List<RoleRepresentation> roleRepresentations) {
        logger.info("converterClient");
        
        JsonObjectBuilder jsonBuilder = JSON_FACTORY.createObjectBuilder();
        JsonObjectBuilder relBuilder = Json.createObjectBuilder();
        
        JsonObjectBuilder dataBuilder = Json.createObjectBuilder(); 
        
        buildClientData(clienRepresentation, dataBuilder);
        buildClientRolesRelationship(roleRepresentations, relBuilder);
        
        dataBuilder.add(CommonString.getInstance().getRelationships(), relBuilder); 
        jsonBuilder.add(CommonString.getInstance().getData(), dataBuilder);
        return jsonBuilder.build(); 
    }
    

    @Override
    public JsonObject converterClients(Map<ClientRepresentation, List<RoleRepresentation>> clientRepresentationRolesMap) {

        JsonObjectBuilder jsonBuilder = JSON_FACTORY.createObjectBuilder();
        JsonObjectBuilder dataBuilder = Json.createObjectBuilder();
        JsonArrayBuilder dataArrBuilder = Json.createArrayBuilder();
        
        JsonObjectBuilder relBuilder = Json.createObjectBuilder();

        if (clientRepresentationRolesMap != null && !clientRepresentationRolesMap.isEmpty()) {
            clientRepresentationRolesMap.entrySet().stream()
                    .forEach(c -> {
                        buildClientData(c.getKey(), dataBuilder);
                        buildClientRoles(c.getValue(), relBuilder);
                        dataBuilder.add(CommonString.getInstance().getRelationships(), relBuilder);  
                        dataArrBuilder.add(dataBuilder);  
                    }); 
        }

        jsonBuilder.add(CommonString.getInstance().getData(), dataArrBuilder);
        return jsonBuilder.build();
    }
    
    private void buildClientRoles(List<RoleRepresentation> roleRepresentations, JsonObjectBuilder relBuilder) {
         
        JsonObjectBuilder subBuilder = Json.createObjectBuilder();  
        JsonObjectBuilder subDataBuilder = Json.createObjectBuilder();  
        JsonArrayBuilder subDataArrBuilder = Json.createArrayBuilder();
         
        if (roleRepresentations != null && !roleRepresentations.isEmpty()) {
     
            roleRepresentations.stream()
                                .forEach(r -> {  
                                    buildRoleJson(r, subDataBuilder, false, "dina");
                                    subDataArrBuilder.add(subDataBuilder);
                                }); 
            subBuilder.add(CommonString.getInstance().getData(), subDataArrBuilder);
            relBuilder.add("roles", subBuilder);  
        }
    }
    
    private void buildRoleJson(RoleRepresentation roleRepresentation, 
                               JsonObjectBuilder subDataBuilder, 
                               boolean addAttributes, 
                               String roleBelongTo) { 
        
        subDataBuilder.add(CommonString.getInstance().getType(), CommonString.getInstance().getTypeRoles());
        subDataBuilder.add(CommonString.getInstance().getId(), roleRepresentation.getId()); 
        
        helper = new JsonConvertHelper();
        if(addAttributes) {
            JsonObjectBuilder attBuilder = Json.createObjectBuilder(); 
            helper.addAttributes(attBuilder, CommonString.getInstance().getRoleName(), roleRepresentation.getName()); 
            helper.addAttributes(attBuilder, CommonString.getInstance().getDescription(), roleRepresentation.getDescription());
            helper.addAttributes(attBuilder, CommonString.getInstance().getIsClient(), roleRepresentation.getClientRole()); 
            helper.addAttributes(attBuilder, CommonString.getInstance().getRoleBelongTo(), roleBelongTo); 
            subDataBuilder.add(CommonString.getInstance().getAttributes(), attBuilder);
        }
    }
 
    @Override
    public JsonObject readInJson(String json) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(json))) {
            return jsonReader.readObject();
        }
    }

    @Override
    public JsonObject converterUser(UserRepresentation userRepresentation, 
                                    List<RoleRepresentation> realmRoles, 
                                    Map<String, List<RoleRepresentation>> clientRoles ) {
        logger.info("converterUser");
 
        JsonObjectBuilder jsonBuilder = JSON_FACTORY.createObjectBuilder(); 
        JsonObjectBuilder dataBuilder = Json.createObjectBuilder();
         
        if(userRepresentation != null) { 
            buildUserData(userRepresentation, dataBuilder);
            
            if(realmRoles != null) {
                JsonObjectBuilder relBuilder = Json.createObjectBuilder();
                buildRolesRelationship(realmRoles, clientRoles, relBuilder);  
                dataBuilder.add(CommonString.getInstance().getRelationships(), relBuilder); 
            }
        } 
        jsonBuilder.add(CommonString.getInstance().getData(), dataBuilder);
        return jsonBuilder.build();
    }

    @Override
    public JsonObject converterUsers(List<UserRepresentation> userList) {
        
        logger.info("converterUsers");

        JsonObjectBuilder jsonBuilder = JSON_FACTORY.createObjectBuilder();
        JsonObjectBuilder dataBuilder = Json.createObjectBuilder();
        JsonArrayBuilder dataArrBuilder = Json.createArrayBuilder();

        if (userList != null && !userList.isEmpty()) {
            userList.stream()
                    .forEach(u -> { 
                        buildUserData(u, dataBuilder);
                        dataArrBuilder.add(dataBuilder);
                    });
        }

        jsonBuilder.add(CommonString.getInstance().getData(), dataArrBuilder);
        return jsonBuilder.build();
    }
    
    @Override
    public JsonObject converterRealm( RealmRepresentation realmRepresentation, 
                                        List<RoleRepresentation> roleRepresentations, 
                                        List<ClientRepresentation> clientRepresentations) {
        JsonObjectBuilder jsonBuilder = JSON_FACTORY.createObjectBuilder();
        JsonObjectBuilder relBuilder = Json.createObjectBuilder();
        
        JsonObjectBuilder dataBuilder = Json.createObjectBuilder(); 
         
        buildRealmData(realmRepresentation, dataBuilder);
        buildRolesRelationship(roleRepresentations, null, relBuilder);
        buildClientsRelationship(clientRepresentations, relBuilder);
  
        dataBuilder.add(CommonString.getInstance().getRelationships(), relBuilder); 
        jsonBuilder.add(CommonString.getInstance().getData(), dataBuilder);
        return jsonBuilder.build();
    }
    
    @Override
    public JsonObject successJson(String message) {
        JsonObjectBuilder jsonBuilder = JSON_FACTORY.createObjectBuilder();
        jsonBuilder.add("response", message);
        return jsonBuilder.build(); 
    }
    
    @Override
    public JsonObject buildErrorMessages(String error, List<String> errMsgs) {
        JsonObjectBuilder jsonBuilder = JSON_FACTORY.createObjectBuilder();
        
        if(errMsgs != null && !errMsgs.isEmpty()) {
            jsonBuilder.add("errormsgs", errMsgs.toString());
        } else {
            jsonBuilder.add("errormsgs", error);
        }
        
        return jsonBuilder.build(); 
    }
    
    private void buildRealmData(RealmRepresentation realmRepresentation, JsonObjectBuilder dataBuilder) {
        JsonObjectBuilder attBuilder = Json.createObjectBuilder();
        
        dataBuilder.add(CommonString.getInstance().getType(), CommonString.getInstance().getRealmType());
        dataBuilder.add(CommonString.getInstance().getId(), realmRepresentation.getId());
        
        helper = new JsonConvertHelper();
        helper.addAttributes(attBuilder, "realm_name", realmRepresentation.getRealm());
        helper.addAttributes(attBuilder, "realm_id", realmRepresentation.getId());
        helper.addAttributes(attBuilder, "description", realmRepresentation.getDisplayName());
        
        dataBuilder.add(CommonString.getInstance().getAttributes(), attBuilder);
        
    }
    
    private void buildClientsRelationship(List<ClientRepresentation> clientRepresentations, JsonObjectBuilder relBuilder) {
        JsonObjectBuilder subBuilder = Json.createObjectBuilder();
        JsonObjectBuilder subDataBuilder = Json.createObjectBuilder();
        JsonArrayBuilder subDataArrBuilder = Json.createArrayBuilder();
        
        String type = CommonString.getInstance().getTypeClients();
        clientRepresentations.stream()
                            .forEach(c -> {
                                subDataBuilder.add(CommonString.getInstance().getType(), type);
                                subDataBuilder.add(CommonString.getInstance().getId(), c.getId());
                                subDataArrBuilder.add(subDataBuilder); 
                            }); 
        subBuilder.add(CommonString.getInstance().getData(), subDataArrBuilder);
        relBuilder.add(CommonString.getInstance().getTypeClients(), subBuilder); 
    }
    
    private void buildClientRolesRelationship(List<RoleRepresentation> clientRoles, JsonObjectBuilder relBuilder) {

        JsonObjectBuilder subBuilder = Json.createObjectBuilder();
        JsonObjectBuilder subDataBuilder = Json.createObjectBuilder();
        JsonArrayBuilder subDataArrBuilder = Json.createArrayBuilder();
  
        String type = CommonString.getInstance().getTypeRoles();  
        
        if(clientRoles != null) {
            clientRoles.stream() 
                    .forEach(clientRole -> {
                        subDataBuilder.add(CommonString.getInstance().getType(), type);
                        subDataBuilder.add(CommonString.getInstance().getId(), clientRole.getId());
                        subDataArrBuilder.add(subDataBuilder);
                     });
        }
        subBuilder.add(CommonString.getInstance().getData(), subDataArrBuilder);
        relBuilder.add(CommonString.getInstance().getTypeRoles(), subBuilder); 
    }

    
    private void buildRolesRelationship(List<RoleRepresentation> realmRoleRepresentations, 
                                            Map<String, List<RoleRepresentation>> clientRoles, 
                                            JsonObjectBuilder relBuilder) {

        JsonObjectBuilder subBuilder = Json.createObjectBuilder();
        JsonObjectBuilder subDataBuilder = Json.createObjectBuilder();
        JsonArrayBuilder subDataArrBuilder = Json.createArrayBuilder();
  
        String type = CommonString.getInstance().getTypeRole(); 
        realmRoleRepresentations.stream()
//                .filter( filterDefaultRealmRoles() )
                .forEach(r -> {
                    subDataBuilder.add(CommonString.getInstance().getType(), type);
                    subDataBuilder.add(CommonString.getInstance().getId(), r.getId());
                    subDataArrBuilder.add(subDataBuilder);
                });
        
        if(clientRoles != null) {
            clientRoles.entrySet().stream() 
                                    .forEach(clientRole -> {
                                        List<RoleRepresentation> list = clientRole.getValue();
                                        list.stream()
                                                .forEach(r -> {
                                                    subDataBuilder.add(CommonString.getInstance().getType(), type);
                                                    subDataBuilder.add(CommonString.getInstance().getId(), r.getId());
                                                    subDataArrBuilder.add(subDataBuilder);
                                                }); 
                                    });
        }
        subBuilder.add(CommonString.getInstance().getData(), subDataArrBuilder);
        relBuilder.add(CommonString.getInstance().getTypeRoles(), subBuilder); 
    }
  

    private void buildClientData(ClientRepresentation clientRepresentation, JsonObjectBuilder dataBuilder) {

        JsonObjectBuilder attBuilder = Json.createObjectBuilder();

        dataBuilder.add(CommonString.getInstance().getType(), CommonString.getInstance().getTypeClients());
        dataBuilder.add(CommonString.getInstance().getId(), clientRepresentation.getId());
       
        helper = new JsonConvertHelper();
        helper.addAttributes(attBuilder, CommonString.getInstance().getClientName(), clientRepresentation.getName());
        helper.addAttributes(attBuilder, CommonString.getInstance().getClientId(), clientRepresentation.getClientId());
        helper.addAttributes(attBuilder, CommonString.getInstance().getDescriptions(), clientRepresentation.getDescription());
          
        dataBuilder.add(CommonString.getInstance().getAttributes(), attBuilder);
    }
     
    private void buildUserData(UserRepresentation userRepresentation, JsonObjectBuilder dataBuilder) {
        
        

        JsonObjectBuilder attBuilder = Json.createObjectBuilder();

        dataBuilder.add(CommonString.getInstance().getType(), CommonString.getInstance().getTypeUsers());
        dataBuilder.add(CommonString.getInstance().getId(), userRepresentation.getId());

        helper = new JsonConvertHelper();
        helper.addAttributes(attBuilder, CommonString.getInstance().getFirstName(), userRepresentation.getFirstName());
        helper.addAttributes(attBuilder, CommonString.getInstance().getLastName(), userRepresentation.getLastName());
        helper.addAttributes(attBuilder, CommonString.getInstance().getEmail(), userRepresentation.getEmail());
        helper.addAttributes(attBuilder, CommonString.getInstance().getUsername(), userRepresentation.getUsername());
        helper.addAttributes(attBuilder, CommonString.getInstance().getTimestampCreated(), userRepresentation.getCreatedTimestamp());
        helper.addAttributes(attBuilder, CommonString.getInstance().isUserEnabled(), userRepresentation.isEnabled());
        helper.addAttributes(attBuilder, CommonString.getInstance().getIsEmailVerified(), userRepresentation.isEmailVerified());

        Map<String, List<String>> attrs = userRepresentation.getAttributes();
        if(attrs != null && !attrs.isEmpty()) {
            List<String> purposes = attrs.get(CommonString.getInstance().getPurpose());
            if (purposes != null && !purposes.isEmpty()) {
                helper.addAttributes(attBuilder, CommonString.getInstance().getPurpose(), purposes.get(0));
            }
            List<String> status = attrs.get(CommonString.getInstance().getStatus());
            if (status != null && !status.isEmpty()) {
                helper.addAttributes(attBuilder, CommonString.getInstance().getStatus(), status.get(0));
            } 
        }
         
        dataBuilder.add(CommonString.getInstance().getAttributes(), attBuilder);
    }

//    private void addAttributes(JsonObjectBuilder attBuilder, String key, Object value) {
//
//        if (key != null && value != null) {
//            if (value instanceof Integer) {
//                attBuilder.add(key, (int) value);
//            } else if (value instanceof Short) {
//                attBuilder.add(key, (Short) value);
//            } else if (value instanceof Date) {
//                attBuilder.add(key, Util.getInstance().dateToString((Date) value));
//            } else if (value instanceof java.util.Date) {
//                attBuilder.add(key, Util.getInstance().dateToString((java.util.Date) value));
//            } else if (value instanceof BigDecimal) {
//                attBuilder.add(key, (BigDecimal) value);
//            } else if (value instanceof Boolean) { 
//                attBuilder.add(key, (Boolean) value);
//            } else if (value instanceof Double) {
//                attBuilder.add(key, (Double) value);
//            } else if (value instanceof Float) {
//                attBuilder.add(key, (Float) value);
//            } else if (value instanceof Long) {
//                attBuilder.add(key, (Long) value);
//            } else if(value instanceof java.util.LinkedList) {
//                attBuilder.add(key, value.toString());
//            } else {
//                attBuilder.add(key, (String) value);
//            }
//        } 
//    } 
}
