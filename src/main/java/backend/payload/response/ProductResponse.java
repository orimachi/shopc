package backend.payload.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    int id;

    String name;

    Double price;

    boolean isActive;

    String description;

    String urlImage;

    int categoryID;
}
