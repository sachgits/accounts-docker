/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.user.management.logic;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import se.nrm.dina.user.management.utils.CommonString;

/**
 *
 * @author idali
 */
class KeycloakPredicates {
    
    private final static String UMA_AUTHORIZATION_ROLE = "uma_authorization";
    private final static String OFFLINE_ACCESS_ROLE = "offline_access";
    private final static String DISABLED_USER_ROLE = "disabled_user";
    private final static String DINA = "dina";
    
    static Predicate<RoleRepresentation> isFiltoutRealmRoles() {
        return r -> !(r.getName().equals(UMA_AUTHORIZATION_ROLE) || r.getName().equals(OFFLINE_ACCESS_ROLE) || r.getName().equals(DISABLED_USER_ROLE));
    }
    
    static Predicate<RoleRepresentation> isNotRealmDefaultRoles() {
        return r -> !(r.getName().equals(UMA_AUTHORIZATION_ROLE) || r.getName().equals(OFFLINE_ACCESS_ROLE));
    }
     
    static Predicate<RoleRepresentation> isRealmRole(String roleName) {
        return role -> role.getName().equals(roleName);
    }
    
    static Predicate<ClientRepresentation> isDinaRealmClients() {
        return c -> c.getName().startsWith(DINA);
    }
     
    static Predicate<RealmRepresentation> isDinaRealmExists(String realmName) {
        return r -> r.getDisplayName().equals(realmName);
    } 
     
    static Predicate<UserRepresentation> isLoggedIn(UsersResource usersResource) {
        return u -> !usersResource.get(u.getId()).getUserSessions().isEmpty();
    } 
    
    static Predicate<UserRepresentation> filterUserByStatus(String status) {
        return u -> u.getAttributes().get(CommonString.getInstance().getStatus()).get(0).equals(status);
    } 
    
    static List<UserRepresentation> filterUsers(List<UserRepresentation> userRepresentations, Predicate<UserRepresentation> predicate) {
        return userRepresentations.stream().filter(predicate).collect(Collectors.toList());
    }
    
    static List<ClientRepresentation> filterClients(List<ClientRepresentation> clientRepresentations, Predicate<ClientRepresentation> predicate) {
        return clientRepresentations.stream().filter(predicate).collect(Collectors.toList());
    }
    
    static List<RoleRepresentation> filterRoles(List<RoleRepresentation> roleRepresentations, Predicate<RoleRepresentation> predicate) {
        
        System.out.println("test : " + roleRepresentations);
        
        return roleRepresentations.stream().filter( predicate ).collect(Collectors.toList());
    }
}
