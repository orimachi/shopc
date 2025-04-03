package backend.payload.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    String sessionID;

    UUID customerID;

    String password;
}
