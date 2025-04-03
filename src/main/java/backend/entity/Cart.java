package backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String sessionID;

    String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    LocalDateTime date = LocalDateTime.now();

    @OneToMany(mappedBy = "cart",cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    private List<CartDetail> cartDetail;

    @OneToMany(mappedBy = "cart")
    private List<Order> order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    Customer customer;
}
