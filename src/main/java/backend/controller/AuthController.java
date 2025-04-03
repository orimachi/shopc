package backend.controller;

import backend.payload.request.LoginRequest;
import backend.payload.request.CustomerRequest;
import backend.payload.response.APIResponse;
import backend.payload.response.JwtResponse;
import backend.service.AuthenticateService;
import backend.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticateService authenticateService;

    private final CustomerService customerService;

    @PostMapping("/login")
    public APIResponse<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse result = authenticateService.login(loginRequest);
        return APIResponse.<JwtResponse>builder().result(result).build();
    }

    @PostMapping("/signup")
    public APIResponse<String> registerUser(@Valid @RequestBody CustomerRequest registerRequest) {
        String result = customerService.createNewCustomer(registerRequest);
        return APIResponse.<String>builder().message(result).build();
    }
}
