package backend.payload.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {
    UUID customerID;

    String sessionID;

    String password;
}
