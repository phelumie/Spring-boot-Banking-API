//package com.microfinanceBank.Customer.keycloak.service;
//
//import java.util.List;
//
//import com.microfinanceBank.Customer.keycloak.CurrentUserProvider;
//import com.microfinanceBank.Customer.keycloak.KeycloakAdminClientConfig;
//import com.microfinanceBank.Customer.keycloak.KeycloakAdminClientUtils;
//import com.microfinanceBank.Customer.keycloak.KeycloakPropertyReader;
//import lombok.RequiredArgsConstructor;
//import org.keycloak.KeycloakPrincipal;
//import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.resource.RealmResource;
//import org.keycloak.admin.client.resource.UserResource;
//import org.keycloak.admin.client.resource.UsersResource;
//import org.keycloak.representations.idm.UserRepresentation;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class KeycloakAdminClientService {
//    @Value("${keycloak.resource}")
//    private String keycloakClient;
//    private CurrentUserProvider currentUserProvider;
//    private KeycloakPropertyReader keycloakPropertyReader;
//
//    public List<String> getCurrentUserRoles() {
//
//        return currentUserProvider.getCurrentUser().getRoles();
//    }
//
//    public Object getUserProfileOfLoggedUser() {
//
//        @SuppressWarnings("unchecked")
//        KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) SecurityContextHolder.getContext()
//                .getAuthentication().getPrincipal();
//        KeycloakAdminClientConfig keycloakAdminClientConfig = KeycloakAdminClientUtils.loadConfig(keycloakPropertyReader);
//        Keycloak keycloak = KeycloakAdminClientUtils.getKeycloakClient(principal.getKeycloakSecurityContext(), keycloakAdminClientConfig);
//
//        // Get realm
//        RealmResource realmResource = keycloak.realm(keycloakAdminClientConfig.getRealm());
//        UsersResource usersResource = realmResource.users();
//        UserResource userResource = usersResource.get(currentUserProvider.getCurrentUser().getUserId());
//        UserRepresentation userRepresentation = userResource.toRepresentation();
//
//        return userRepresentation;
//    }
//}
