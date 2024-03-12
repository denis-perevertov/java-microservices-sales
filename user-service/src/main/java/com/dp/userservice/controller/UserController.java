package com.dp.userservice.controller;

import com.dp.userservice.config.KeycloakProperties;
import com.dp.userservice.mapper.UserMapper;
import com.dp.userservice.model.keycloak.KeycloakLoginResponse;
import com.dp.userservice.model.user.*;
import com.dp.userservice.persistence.entity.Admin;
import com.dp.userservice.persistence.entity.User;
import com.dp.userservice.persistence.specification.UserListSpecification;
import com.dp.userservice.service.UserService;
import com.dp.userservice.validator.UserValidator;
import com.dp.utils.ValidationUtils;
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
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final KeycloakProperties keycloakProperties;
    private final Keycloak keycloak;

    // *****  LOGIN  *****

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request) {
        if(!userValidator.userExists(request.username())) {
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
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest request) {

        if(userValidator.userExists(request.username())) {
            return ResponseEntity
                    .status(409)
                    .body(Map.of(
                            "error", "User with this email already exists!"
                    ));
        }
        else {
            Response response = keycloak.realm(keycloakProperties.getRealm()).users().create(userMapper.registerRequestToRepresentation(request));
            log.info("response: {}", response);
            User user;
            String authId = "";

            if(response.getStatus() == 409) {
                UserRepresentation userRepresentation = keycloak.realm(keycloakProperties.getRealm()).users().searchByUsername(request.username(), true).get(0);
                log.info("found existing user in keycloak");
                authId = userRepresentation.getId();
            }
            else {
                String location = (String) response.getHeaders().getFirst("Location");
                if(location != null && !location.isEmpty()) {
                    authId = location.substring(location.lastIndexOf("/") + 1);
                    log.info("AUTH ID: {}", authId);
                }
            }
            user = userMapper.registerRequestToUser(request).setAuthId(authId);
            userService.saveUser(user);
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
    public ResponseEntity<?> getUsers(UserPageRequest request) {
        Page<User> users = userService.getUsersPage(new UserListSpecification(request), PageRequest.of(request.page(), request.size()));
        return ResponseEntity.ok(users.map(userMapper::userToDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        if(!userValidator.userExists(id)) {
            return ResponseEntity.badRequest().build();
        }
        User user = userService.getUser(id);
        return ResponseEntity.ok(userMapper.userToDTO(user));
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody UserSaveRequest request,
                                      BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(bindingResult));
        }
        User user = userMapper.fromSaveRequestToUser(request);
        user = userService.saveUser(user);
        return ResponseEntity.ok(userMapper.userToDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id,
                                      @RequestBody UserSaveRequest request,
                                      BindingResult bindingResult) {
        if(!userValidator.userExists(id)) {
            return ResponseEntity.badRequest().build();
        }
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(bindingResult));
        }
        User user = userMapper.fromSaveRequestToUser(request);
        user = userService.saveUser(user);
        return ResponseEntity.ok(userMapper.userToDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if(!userValidator.userExists(id)) {
            return ResponseEntity.badRequest().build();
        }
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
