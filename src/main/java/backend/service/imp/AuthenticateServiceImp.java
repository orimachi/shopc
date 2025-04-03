package backend.service.imp;

import backend.config.JWTConfig.JwtUtils;
import backend.config.JWTConfig.UserDetailsImp;
import backend.payload.request.LoginRequest;
import backend.payload.response.JwtResponse;
import backend.service.AuthenticateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticateServiceImp implements AuthenticateService {
    public static final String TYPE_TOKEN = "Bearer ";

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;


    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        String refreshJwt = jwtUtils.generateRefreshTokenFromUsername(authentication);

        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return JwtResponse.builder().refreshToken(refreshJwt).token(jwt).type(TYPE_TOKEN).id(userDetails.getId()).username(userDetails.getUsername()).roles(roles).build();
    }
}
