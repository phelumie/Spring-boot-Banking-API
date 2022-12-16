package com.microfinanceBank.Customer.Config;

import com.microfinanceBank.Customer.proxy.RefreshActuatorProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import static org.keycloak.admin.client.KeycloakBuilder.builder;


@Configuration
//@Getter
@Slf4j
@RequiredArgsConstructor
public class KeycloakProvider {

    @Autowired
    private KeycloakConfiguration keycloakData;

    private static Keycloak keycloak = null;

    public Keycloak getInstance() {
        if (keycloak == null) {
            return
                    builder()
                            .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                            .serverUrl(keycloakData.getServerURL())
                            .realm(keycloakData.getRealm())
                            .clientId("idm-client")
                            .clientSecret(keycloakData.getClientSecret())
                            .build();
        }
        return keycloak;
    }

//    public JsonNode refreshToken(String refreshToken) throws UnirestException {
//        String url = serverURL + "/realms/" + realm + "/protocol/openid-connect/token";
//        return Unirest.post(url)
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .field("client_id", clientID)
////                .field("client_secret", clientSecret)
//                .field("refresh_token", refreshToken)
//                .field("grant_type", "refresh_token")
//                .asJson().getBody();
//    }
}