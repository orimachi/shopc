package backend.payload.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String name;

    Double price;

    @Builder.Default
    boolean isActive = true;

    String description;

    String urlImage;

    int storeID;

    int categoryID;
}
