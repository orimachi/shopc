package backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerBan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String reason;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime banDate;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime endBannedDate;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    Customer customer;
}
