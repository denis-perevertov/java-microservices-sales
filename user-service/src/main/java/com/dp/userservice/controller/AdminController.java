package com.dp.userservice.controller;

import com.dp.userservice.config.KeycloakProperties;
import com.dp.userservice.mapper.UserMapper;
import com.dp.userservice.model.keycloak.KeycloakLoginResponse;
import com.dp.userservice.model.user.UserLoginRequest;
import com.dp.userservice.model.user.UserRegisterRequest;
import com.dp.userservice.model.admin.AdminPageRequest;
import com.dp.userservice.model.admin.AdminSaveRequest;
import com.dp.userservice.persistence.entity.Admin;
import com.dp.userservice.persistence.specification.AdminListSpecification;
import com.dp.userservice.service.AdminService;
import com.dp.userservice.validator.AdminValidator;
import com.dp.utils.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import javax.ws.rs.core.Response;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final AdminValidator adminValidator;
    private final UserMapper userMapper;
    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;

    // *****  LOGIN  *****

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody UserLoginRequest request) {
        if(!adminValidator.adminExists(request.username())) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of(
                            "error", "Bad credentials"
                    ));
        }
        LinkedMultiValueMap<String, Object> loginRequest = new LinkedMultiValueMap<>();
        loginRequest.set("client_id", keycloakProperties.getClientId());
        loginRequest.set("client_secret", keycloakProperties.getClientSecret());
        loginRequest.set("username", request.username());
        loginRequest.set("password", request.password());
        loginRequest.set("grant_type", keycloakProperties.getGrantType());
        RestClient restClient = RestClient.builder().build();
        KeycloakLoginResponse response = restClient.post()
                .uri(String.format(
                        "%s/realms/%s/protocol/openid-connect/token",
                        keycloakProperties.getServer(),
                        keycloakProperties.getRealm()
                ))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(loginRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new RuntimeException();
                })
                .toEntity(KeycloakLoginResponse.class)
                .getBody();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody UserRegisterRequest request) {
        if(adminValidator.adminExists(request.username())) {
            return ResponseEntity
                    .status(409)
                    .body(Map.of(
                            "error", "Admin with this email already exists!"
                    ));
        }
        else {
            Response response = keycloak.realm(keycloakProperties.getRealm()).users().create(userMapper.registerRequestToRepresentation(request));
            log.info("response: {}", response);
            Admin admin;
            String authId = "";

            if(response.getStatus() == 409) {
                UserRepresentation user = keycloak.realm(keycloakProperties.getRealm()).users().searchByUsername(request.username(), true).get(0);
                log.info("found existing user in keycloak");
                authId = user.getId();
            }
            else {
                String location = (String) response.getHeaders().getFirst("Location");
                if(location != null && !location.isEmpty()) {
                    authId = location.substring(location.lastIndexOf("/") + 1);
                    log.info("AUTH ID: {}", authId);
                }
            }
            admin = userMapper.registerRequestToAdmin(request).setAuthId(authId);
            adminService.saveAdmin(admin);
            return ResponseEntity
                    .status(response.getStatus())
                    .body(Map.of(
                            "auth_id", authId,
                            "username", request.username()
                    ));
        }

    }

    // *******************

    @GetMapping
    public ResponseEntity<?> getAdmins(AdminPageRequest request) {
        Page<Admin> admins = adminService.getAdminsPage(
                new AdminListSpecification(request),
                PageRequest.of(request.page(), request.size())
        );
        return ResponseEntity.ok(admins.map(userMapper::adminToDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdmin(@PathVariable Long id) {
        if(!adminValidator.adminExists(id)) {
            return ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.ok(userMapper.adminToDTO(adminService.getAdmin(id)));
        }
    }

    @PostMapping
    public ResponseEntity<?> saveAdmin(@RequestBody @Valid AdminSaveRequest request,
                                       BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(bindingResult));
        }
        Admin admin = userMapper.fromSaveRequestToAdmin(request);
        admin = adminService.saveAdmin(admin);
        return ResponseEntity.status(201).body(userMapper.adminToDTO(admin));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editAdmin(@PathVariable Long id,
                                       @RequestBody @Valid AdminSaveRequest request,
                                       BindingResult bindingResult) {
        if(!adminValidator.adminExists(id)) {
            return ResponseEntity.badRequest().build();
        }
        else if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(bindingResult));
        }
        else {
            Admin admin = userMapper.fromSaveRequestToAdmin(request);
            admin = adminService.saveAdmin(admin);
            return ResponseEntity.ok(userMapper.adminToDTO(admin));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        if(!adminValidator.adminExists(id)) {
            return ResponseEntity.badRequest().build();
        }
        else {
            adminService.deleteAdmin(id);
            return ResponseEntity.ok().build();
        }
    }
}
