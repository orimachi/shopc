package backend.entity;

import backend.enums.EVoucher;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Voucher implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String name;

    String description;

    @Column(columnDefinition = "TINYINT", length = 1)
    boolean type;

    Double voucherValue;

    @Enumerated(EnumType.STRING)
    EVoucher status;

    @OneToMany(mappedBy = "voucher")
    private List<Order> order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    Customer customer;
}
