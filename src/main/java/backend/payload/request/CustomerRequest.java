package backend.payload.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {
    String username;

    String password;

    Set<String> role;

    String email;

    Date dob;

    String firstName;

    String lastName;

    String phone;

    boolean enabled = true;
}
