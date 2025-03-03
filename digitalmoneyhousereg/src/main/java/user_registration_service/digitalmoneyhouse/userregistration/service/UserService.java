package user_registration_service.digitalmoneyhouse.userregistration.service;


import user_registration_service.digitalmoneyhouse.userregistration.dto.UserRegistrationRequest;
import user_registration_service.digitalmoneyhouse.userregistration.dto.UserRegistrationResponse;

public interface UserService {
    UserRegistrationResponse registerUser(UserRegistrationRequest request);
}

