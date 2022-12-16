package com.microfinanceBank.Employee.service.Impl;

import com.microfinanceBank.Employee.Config.KeycloakConfiguration;
import com.microfinanceBank.Employee.dto.*;
import com.microfinanceBank.Employee.enums.Role;
import com.microfinanceBank.Employee.exceptions.NoEmployeeExceptions;
import com.microfinanceBank.Employee.exceptions.EmployeeAlreadyExists;
import com.microfinanceBank.Employee.entity.Employee;
import com.microfinanceBank.Employee.repository.BranchRepository;
import com.microfinanceBank.Employee.repository.EmployeeRepository;
import com.microfinanceBank.Employee.service.Emailservice;
import com.microfinanceBank.Employee.service.KeycloakProvider;
import com.microfinanceBank.Employee.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final ModelMapper modelMapper;
    private final KeycloakConfiguration keycloakData;
    private final KeycloakProvider kcProvider;
    private final Emailservice emailservice;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private HashMap<Role, String> roles;

    public EmployeeServiceImpl(ModelMapper modelMapper, KeycloakConfiguration keycloakData, KeycloakProvider kcProvider, Emailservice emailservice, BCryptPasswordEncoder passwordEncoder, EmployeeRepository employeeRepository, BranchRepository branchRepository) {
        this.modelMapper = modelMapper;
        this.keycloakData = keycloakData;
        this.kcProvider = kcProvider;
        this.emailservice = emailservice;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;

        if (roles == null) {
            roles = new HashMap<>(5);
        }
        roles.put(Role.ADMIN, "admin");
        roles.put(Role.MANAGER, "manager");
        roles.put(Role.COO, "coo");
        roles.put(Role.HR, "hr");
        roles.put(Role.EMPLOYEE, "employee");
    }

    @Override
    public RegisterResponse registerEmployee(RegisterDto user) {
        RegisterResponse registerResponse = new RegisterResponse();
        String name = user.getFirstName() + " " + user.getLastName();
        log.trace("Entering method register employee");
        log.debug("Creating employee with email {}", user.getEmail());

        Response keycloakUser = createKeycloakUser(user);
        RealmResource realmResource = getRealmResource();
        Role role = user.getRole();

        RoleRepresentation customerRoleRepresentation = realmResource.roles().get(roles.get(role)).toRepresentation();
//        GroupRepresentation group=getRealmResource().getGroupByPath()
        log.info("keycloakUser " + keycloakUser.getStatus());

        if (keycloakUser.getStatus() == 201) {
            log.info("Keycloak user created successfully for user {}", user.getEmail());
            String userId = keycloakUser.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            realmResource.users().get(userId).roles().realmLevel().add(Arrays.asList(customerRoleRepresentation));

            Employee employeeEntity = convertRegisterDtoToEntity(user);
            employeeEntity.setActive(true);
            employeeEntity.setRole(role);
            employeeEntity.setNotLocked(true);
            employeeEntity.setKeycloakId(userId);

            var branch = branchRepository.findById(user.getBranch())
                    .orElseThrow();

            var saved=employeeRepository.save(employeeEntity);
            employeeEntity.addBranch(branch);
            registerResponse.setId(saved.getId());
            registerResponse.setName(name);
            registerResponse.setEmail(user.getEmail());
            registerResponse.setMessage("Employee successfully created");
            registerResponse.setKeycloakId(employeeEntity.getKeycloakId());
        } else if (keycloakUser.getStatus() == 409)
            throw new EmployeeAlreadyExists("Employee already exists");
        return registerResponse;
    }

    @Override
    public Date getEmployeeLastLoginDate(String keycloakId){
        return getKeycloakEvents().stream()
                .filter(f->f.getUserId().equals(keycloakId))
                .map(x->x.getTime())
                .map(Date::new)
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public List<Date> getAllEmployeeLoginSessions(String keycloakId){
        return getKeycloakEvents()
                .stream()
                .filter(users->users.getUserId().equals(keycloakId))
                .map(x->x.getTime())
                .map(Date::new)
                .collect(Collectors.toList());
    }

    private List<EventRepresentation> getKeycloakEvents() {
        return getRealmResource().getEvents();
    }

    @Override
    public List<EmployeeDto> allActiveSessions() {
        var clientRepresentation= frontEndClientRepresentation();
        var clientResource= getRealmResource().clients().get(clientRepresentation.getId());
       return clientResource.getUserSessions(0,1000).stream()
                .map(x->x.getUserId())
                .map(employeeRepository::findByKeycloakId)
                .map(this::convertEmployeeEntityToDto)
                .collect(Collectors.toList());
    }



    @Override
    public void deleteEmployee(Long id,String keycloakId) {
        log.trace("Entering method delete employee");
        log.debug("deleting employee id {}",id);
        getKeycloakUserResource().get(keycloakId).remove();
        employeeRepository.deleteById(id);
        log.info("deleted employee id {}",id);
    }



    //    @Override
//    public void logout(){
//        System.out.println(getCurrentUserLogin());
//        getKeycloakUserResource().get(getCurrentUserLogin()).logout();
//    }
    @Override
    public RegisterResponse makeAdmin(Long id) {
        log.trace("Entering method make admin");
        String message = "Congratulations!! You have been made an Admin!!";
        Employee employee = employeeRepository.getById(id);

        log.debug("making admin employee id {} ",employee.getId());
        makeAdminRepresentation(employee.getKeycloakId(),employee.getRole());
        employee.setRole(Role.ADMIN);
        employeeRepository.save(employee);
        log.info("successfully made admin employee id {}",employee.getId());
        var response = roleResponse(employee, message);


        return response;
    }


    @Override
    public RegisterResponse demoteAdminToEmployee(Long id) {
        log.trace("entering method demoteAdminToEmployee");
        String message = "Successfully demoted to Employee";

        Employee employee = employeeRepository.getById(id);
        log.debug("oereparing to demote admin to employee {}",employee.getId());
        String admin_role = roles.get(Role.ADMIN);
        String employee_role = roles.get(Role.EMPLOYEE);

        RealmResource realmResource = getRealmResource();

        RoleRepresentation adminRoleRepresentation = realmResource.roles().get(admin_role).toRepresentation();
        RoleRepresentation empRoleRepresentation = realmResource.roles().get(employee_role).toRepresentation();

//      remove admin role
        realmResource.users().get(employee.getKeycloakId()).roles().realmLevel()
                .remove(Arrays.asList(adminRoleRepresentation));
//      adding  Employee role
        realmResource.users().get(employee.getKeycloakId()).roles().realmLevel()
                .add(Arrays.asList(empRoleRepresentation));


        employee.setRole(Role.EMPLOYEE);
        employeeRepository.save(employee);

        return roleResponse(employee, message);

    }

    @Override
    @Transactional(readOnly = true)
    public Flux<EmployeeDto> getAllUsersFlux() {
        log.trace("Entering method getAllUsersFlux");
        log.debug("Preparing to get all users by flux stream");
        long start = System.currentTimeMillis();
        var result= Flux.fromIterable(employeeRepository.findAll())
                .map(this::convertEmployeeEntityToDto);

        long end = System.currentTimeMillis();
        System.out.println("Time taken Flux: " + (end - start));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployeeList() {
        log.trace("Entering method getAllUsersFlux");
        log.debug("Preparing to get all users by flux stream");
        long start = System.currentTimeMillis();
        List<EmployeeDto> collect = employeeRepository.findAll().stream()
                .map(this::convertEmployeeEntityToDto)
                .collect(Collectors.toList());
        long end = System.currentTimeMillis();
        System.out.println("Time taken List: " + (end - start));
        return collect;
    }

    private Response createKeycloakUser(RegisterDto user) {
        log.trace("Entering method create createKeycloakUser");
        log.debug("Creating keycloak user for " + user.getEmail());

        UsersResource usersResource = getKeycloakUserResource();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getEmail());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(user.getFirstName());
        kcUser.setLastName(user.getLastName());
        kcUser.setEmail(user.getEmail());

        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);

        Response response = usersResource.create(kcUser);
        return response;

    }

    private UsersResource getKeycloakUserResource() {
        return getRealmResource().users();
    }

    private RealmResource getRealmResource() {
        log.debug("getting keycloak realm instance");
        return kcProvider.getInstance()
                .realm(keycloakData.getRealm());
    }

    public Employee getEmployeeByEmail(String email) {
        log.debug("getting employees by email {}",email);
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new NoEmployeeExceptions("No user with the email "));
        return employee;
    }

    private RegisterResponse roleResponse(Employee employee, String message) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setEmail(employee.getEmail());
        registerResponse.setName(employee.getFirstName() + " " + employee.getLastName());
        registerResponse.setMessage(message);
        registerResponse.setId(employee.getId());
        return registerResponse;
    }


    private EmployeeDto convertEmployeeEntityToDto(Employee employee) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        var employeeDto = new EmployeeDto();
        employeeDto = modelMapper.map(employee, EmployeeDto.class);
        return employeeDto;
    }

    private void makeAdminRepresentation(String keycloakId,Role role) {
        RealmResource realmResource = getRealmResource();
        var adminRole=roles.get(Role.ADMIN);
        var prevRole=roles.get(role);
        RoleRepresentation adminRoleRepresentation = realmResource.roles().get(adminRole).toRepresentation();
        RoleRepresentation prevRoleRepresentation = realmResource.roles().get(prevRole).toRepresentation();
        realmResource.users().get(keycloakId).roles().realmLevel()
                .remove(Arrays.asList(prevRoleRepresentation));

        realmResource.users().get(keycloakId).roles().realmLevel().add(Arrays.asList(adminRoleRepresentation));
    }
    private ClientRepresentation frontEndClientRepresentation() {
        ClientRepresentation customerClient = getRealmResource().clients() //
                .findByClientId("front-end").get(0);
        return customerClient;
    }
    private Employee convertRegisterDtoToEntity(RegisterDto registerDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Employee employee = modelMapper.map(registerDto, Employee.class);
        return employee;
    }

    public String getCurrentUserLogin() {

        var authentications = SecurityContextHolder.getContext().getAuthentication();
        var principal = (Jwt) authentications.getPrincipal();
//        System.out.println("getClaims: "+principal.getClaims());
//        System.out.println("TokenValue: "+principal.getTokenValue());
        return principal.getClaimAsString("sub");
    }


    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String getKeycloakId() {
        var authentications = SecurityContextHolder.getContext().getAuthentication();
        var principal = (Jwt) authentications.getPrincipal();
        return principal.getClaimAsString("sub");
    }


}