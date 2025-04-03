package backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    Double price;

    @Column(columnDefinition = "TINYINT", length = 1)
    boolean isActive;

    String description;

    String urlImage;

    @Lob
    byte[] data;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<ProductSize> productSize;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<CartDetail> cartDetail;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Comment> comment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Store> store;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Discount> discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    Category category;
}
