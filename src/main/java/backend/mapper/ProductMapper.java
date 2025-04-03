package backend.mapper;

import backend.entity.Product;
import backend.payload.request.ProductRequest;
import backend.payload.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product, ProductRequest, ProductResponse> {

    @Override
    @Mapping(source = "category.id",target = "categoryID")
    ProductResponse toDTO(Product entity);

    @Override
    Product toEntity(ProductRequest request);
}
