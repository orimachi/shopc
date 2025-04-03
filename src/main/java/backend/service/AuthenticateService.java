package backend.service;

import backend.payload.request.LoginRequest;
import backend.payload.response.JwtResponse;

public interface AuthenticateService {
    JwtResponse login(LoginRequest loginRequest);
}
