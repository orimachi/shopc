package backend.payload.response;


import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    @Builder.Default
    private String type = "Bearer ";
    private UUID id;
    private String username;
    private List<String> roles;
    private String token;
    private String refreshToken;
}
