package backend.entity;

import backend.enums.ERole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role implements Serializable {
    @Id
    @Enumerated(EnumType.STRING)
    ERole name;

    String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<Customer> customers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "permission_role", joinColumns = @JoinColumn(name = "role_name",referencedColumnName = "name"),inverseJoinColumns = @JoinColumn(name = "permission_name",referencedColumnName = "name"))
    private Set<Permission> permissions;
}
