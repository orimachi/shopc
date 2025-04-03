package backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`order`")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String address;

    String note;

    Double totalPrice;

    Double deliveryPrice;

    @Temporal(TemporalType.DATE)
    Date oderDate;

    @Column(columnDefinition = "TINYINT", length = 1)
    boolean isDiscount;

    @Column(columnDefinition = "TINYINT", length = 1)
    boolean isVoucher;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id",referencedColumnName = "id")
    Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_detail_id",referencedColumnName = "id")
    CartDetail cartDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id",referencedColumnName = "id")
    Voucher voucher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id",referencedColumnName = "id")
    Discount discount;
}
