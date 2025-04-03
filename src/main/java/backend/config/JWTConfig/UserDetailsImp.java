package backend.config.JWTConfig;

import backend.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserDetailsImp implements UserDetails {
    UUID id;

    String username;

    String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImp build(Customer customer) {
        List<GrantedAuthority> authorities = customer.getRoles().stream()
                .flatMap(role -> {
                List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority(role.getName().name()));
                List<GrantedAuthority> permissions = role.getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getName().name())).collect(Collectors.toList());
                return Stream.concat(roles.stream(),permissions.stream());
                }).toList();
        return new UserDetailsImp(
                customer.getId(),
                customer.getUsername(),
                customer.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true ;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true ;
    }

    @Override
    public boolean isEnabled() {
        return true ;
    }
}
