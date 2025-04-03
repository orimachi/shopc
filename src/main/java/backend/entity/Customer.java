package backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String username;

    String password;

    String email;

    @Temporal(TemporalType.DATE)
    Date dob;

    String firstName;

    String lastName;

    String phone;

    @Column(columnDefinition = "TINYINT", length = 1)
    boolean enabled;

    @OneToMany(mappedBy = "customer")
    private List<Notification> notification;

    @OneToMany(mappedBy = "customer")
    private List<Token> token;

    @OneToMany(mappedBy = "customer")
    private List<CustomerBan> customerBan;

    @OneToMany(mappedBy = "customer")
    private List<Cart> cart;

    @OneToMany(mappedBy = "customer")
    private List<Comment> comment;

    @OneToMany(mappedBy = "customer")
    private List<Shop> shop;

    @OneToMany(mappedBy = "customer")
    private List<Voucher> voucher;

    @OneToMany(mappedBy = "customer")
    private List<Order> order;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_name"))
    private Set<Role> roles;

}
