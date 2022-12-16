package com.microfinanceBank.Customer.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microfinanceBank.Customer.Config.KeycloakConfiguration;
import com.microfinanceBank.Customer.Config.KeycloakProvider;
import com.microfinanceBank.Customer.Exceptions.CustomerAlreadyExists;
import com.microfinanceBank.Customer.dto.CustomerDto;
import com.microfinanceBank.Customer.dto.Register;
import com.microfinanceBank.Customer.entity.Customer;
import com.microfinanceBank.Customer.repository.CustomerRepository;
import com.microfinanceBank.Customer.service.AccountService;
import com.microfinanceBank.Customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

import static com.microfinanceBank.Customer.Config.RabbitMQDirectConfig.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountService accountService;
    private final ModelMapper modelMapper;
    private final AmqpTemplate amqpTemplate;
    private final DirectExchange directExchange;
    private  final KeycloakConfiguration keycloakData;
    private final  KeycloakProvider kcProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public CustomerDto createCustomer(Register register) {
        log.trace("Entering method create Customer");

        var requestJson=objectMapper.writeValueAsString(register);

        CustomerDto customer=register.getCustomer();

        log.debug("Creating customer with info {}",requestJson);

        Response keycloakUser=createKeycloakUser(customer);
        RealmResource realmResource = getRealmResource();
        RoleRepresentation customerRoleRepresentation = realmResource.roles().get("customer").toRepresentation();
        if (keycloakUser.getStatus() == 201) {
            log.info("Keycloak user created successfully for user: {}",requestJson);
//            String userId = keycloakUser.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            String userId =CreatedResponseUtil.getCreatedId(keycloakUser);

//            setting the customer with customer role at the client level
//            realmResource.users().get(userId).roles()
//                    .clientLevel(clientRepresentation().getId()).add(Arrays.asList(customerClientRoleRepresentation()));

//            setting the customer with customer role at the realm level
            realmResource.users().get(userId).roles().realmLevel().add(Arrays.asList(customerRoleRepresentation));

//            getKeycloakUserResource().get(userId)
//                    .executeActionsEmail("idm-client","http://localhost:8765/customer/api/getAllCustomers/",300,Arrays.asList("UPDATE_PASSWORD"));
//                    .executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));

            Customer customerDtoToEntity= convertCustomerDtoToEntity(customer);


            customerDtoToEntity.setKeycloakId(userId);
            customerDtoToEntity.setPassword(encodePassword(customer.getPassword()));

            var account=accountService.createAccount(register.getAccountType());
            customerDtoToEntity.addAccount(account);
            Customer saved = customerRepository.save(customerDtoToEntity);

            log.info("Successfully created customer with customer {}",requestJson);
            // send welcome email async
            amqpTemplate.convertAndSend(directExchange.getName(),WELCOME_EMAIL_QUEUE,customer);
            return convertCustomerEntityToDto(saved);

        }
        else if (keycloakUser.getStatus()==409) {
            log.error("Customer with email {} already exists",customer.getEmail());
            throw new CustomerAlreadyExists("Customer with email already exists");

        }
        else
            return null;

    }



    @Override
    public CustomerDto updateCustomerDetails(CustomerDto customerDto) {
        log.trace("Entering method updateCustomerDetails");
        log.debug("updating  customer with email address "+ customerDto.getEmail());

//        Customer customerEntity= customerRepository.findById(customerDto.getId()).get();
//        customer. .setAddress(customerDto.getAddress());
//        customer.setContactNumber(customerDto.getContactNumber());
//        customer.setName(customerDto.getName());
//        customer.setEmail(customerDto.getEmail());
        var customer=customerRepository.save(convertCustomerDtoToEntity(customerDto));
        log.info("Customer {} was updated successfully ",customerDto.getEmail());
        return convertCustomerEntityToDto(customer);
    }

    @Override
    public void logout(   ){
        getKeycloakUserResource().get(getCurrentUserLogin()).logout();
    }
    private Response createKeycloakUser(CustomerDto user) {
        log.trace("Entering method create createKeycloakUser");
        log.debug("Creating keycloak customer for "+user.getEmail());
        UsersResource usersResource = getKeycloakUserResource();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getEmail());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(user.getFirstName());
        kcUser.setLastName(user.getFirstName());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
//        kcUser.setEmailVerified(true);
        Response response= usersResource.create(kcUser);
        if (response.getStatus()!=201)
            log.info("error {} happened while creating keycloak user ",response.getStatus(),user.getEmail());
        return response;

    }

    @Override
    public List<CustomerDto> getAllCustomer() {
        log.trace("Entering method getAllCustomer");
        log.debug("Getting all Customers");

        return  customerRepository.findAll().stream().map(this::convertCustomerEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(long id, String keycloakId) {
        log.trace("entering deleteCustomer method");
        log.debug("deleting customer with id {}",id);

        customerRepository.deleteById(id);
        getKeycloakUserResource().get(keycloakId).remove();

        log.info("customer with id {}",id);
    }

    @Override
    public CustomerDto getCustomerByKeycloakId() {
        var id=getCurrentUserLogin();
        Customer customer= customerRepository.findByKeycloakId(id);
        return convertCustomerEntityToDto(customer);
    }




    public String getCurrentUserLogin() {

        var authentications = SecurityContextHolder.getContext().getAuthentication();
        var   principal1= (Jwt)authentications.getPrincipal();
        System.out.println("getClaims: "+principal1.getClaims());
        System.out.println("TokenValue: "+principal1.getTokenValue());
        return principal1.getClaimAsString("sub");
    }



    private UsersResource getKeycloakUserResource() {
        return getRealmResource().users();
    }

    private RealmResource getRealmResource() {
        return kcProvider.getInstance()
                .realm(keycloakData.getRealm());
    }

    private ClientRepresentation clientRepresentation() {
        ClientRepresentation customerClient = getRealmResource().clients() //
                .findByClientId("springboot-bank-customer").get(0);
        return customerClient;
    }

    private RoleRepresentation customerClientRoleRepresentation() {
        RoleRepresentation userClientRole = getRealmResource().clients().get(clientRepresentation().getId()) //
                .roles().get("customer").toRepresentation();
        return userClientRole;
    }


    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }



    private CustomerDto convertCustomerEntityToDto(Customer customer){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        CustomerDto customerDto =modelMapper.map(customer, CustomerDto.class);
        return customerDto;
    }

    private Customer convertCustomerDtoToEntity(CustomerDto customerDto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Customer customer= modelMapper.map(customerDto,Customer.class);
        return  customer;
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


}
