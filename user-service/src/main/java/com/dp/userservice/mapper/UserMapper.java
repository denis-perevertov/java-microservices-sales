package com.dp.userservice.mapper;

import com.dp.userservice.model.user.*;
import com.dp.userservice.model.admin.AdminDTO;
import com.dp.userservice.model.admin.AdminSaveRequest;
import com.dp.userservice.persistence.entity.Admin;
import com.dp.userservice.persistence.entity.User;
import com.dp.userservice.persistence.entity.UserProfileDetails;
import com.dp.userservice.persistence.entity.UserTransaction;
import com.dp.userservice.persistence.repository.AdminRepository;
import com.dp.userservice.persistence.repository.UserRepository;
import com.dp.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public UserRepresentation registerRequestToRepresentation(UserRegisterRequest request) {
        UserRepresentation r = new UserRepresentation();
        r.setUsername(request.username());
        r.setFirstName(request.firstName());
        r.setLastName(request.lastName());
        r.setEmail(request.email());
        r.setEmailVerified(true);
        r.setEnabled(true);
        List<CredentialRepresentation> credentialRepresentations = request.credentials().stream().map(c -> {
            CredentialRepresentation cr = new CredentialRepresentation();
            cr.setTemporary(c.temporary());
            cr.setType(c.type());
            cr.setValue(c.value());
            return cr;
        }).toList();
        r.setCredentials(credentialRepresentations);
        return r;
    }

    public Admin registerRequestToAdmin(UserRegisterRequest request) {
        return new Admin()
                .setEmail(request.email())
                .setFirstName(request.firstName())
                .setLastName(request.lastName());
    }

    public Admin fromSaveRequestToAdmin(AdminSaveRequest request) {
        Admin admin = (request.id() != null && request.id() > 0)
                ? adminRepository.findById(request.id()).orElseThrow()
                : new Admin();
        admin.setId(request.id())
            .setEmail(request.email())
            .setFirstName(request.firstName())
            .setLastName(request.lastName());
        return admin;
    }

    public User registerRequestToUser(UserRegisterRequest request) {
        User user = new User()
                .setEmail(request.email());
        user.getProfileDetails()
                .setFirstName(request.firstName())
                .setLastName(request.lastName());
        return user;
    }

    public User fromSaveRequestToUser(UserSaveRequest request) {
        User user = (request.id() != null && request.id() > 0)
                ? userRepository.findById(request.id()).orElseThrow()
                : new User();
        user.setId(request.id())
            .setEmail(request.email());
        user.getProfileDetails()
            .setFirstName(request.firstName())
            .setLastName(request.lastName())
            .setBirthdate(request.birthdate())
            .setPhone(request.phone())
            .setGender(UserProfileDetails.Gender.valueOf(request.gender()));
        return user;
    }

    public AdminDTO adminToDTO(Admin admin) {
        return new AdminDTO(
                admin.getEmail(),
                admin.getFirstName(),
                admin.getLastName()
        );
    }

    public UserDTO userToDTO(User user) {
        return new UserDTO(
                userProfileToDTO(user),
                userToFinanceDTO(user, userService.getUserTransactions(user.getId()))
        );
    }

    public UserProfileDTO userProfileToDTO(User user) {
        UserProfileDetails profile = user.getProfileDetails();
        return new UserProfileDTO(
                profile.getFirstName(),
                profile.getLastName(),
                profile.getBirthdate(),
                profile.getAvatar(),
                user.getEmail(),
                profile.getPhone(),
                profile.getGender().name()
        );
    }

    public UserFinanceDTO userToFinanceDTO(User user, List<UserTransaction> transactions) {
        return new UserFinanceDTO(
                user.getFinanceDetails().getBalance(),
                user.getFinanceDetails().getAutomaticDebtDeduction(),
                transactionListToDTO(transactions)
        );
    }

    public UserTransactionDTO transactionToDTO(UserTransaction transaction) {
        return new UserTransactionDTO(
                transaction.getId(),
                transaction.getDatetime(),
                transaction.getSum(),
                transaction.getType().name(),
                transaction.getMethod().name()
        );
    }

    public List<UserTransactionDTO> transactionListToDTO(List<UserTransaction> list) {
        return list.stream().map(this::transactionToDTO).toList();
    }
}
