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
public class Token implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String tokens;

    String refreshToken;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime expiredDateToken;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime expiredDateRefreshToken;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    boolean enabled;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    boolean refreshTokenEnabled;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    boolean revokedToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    Customer customer;
}
