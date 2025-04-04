package backend.mapper;

import backend.entity.Cart;
import backend.payload.request.CartRequest;
import backend.payload.response.CartResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper extends BaseMapper<Cart, CartRequest, CartResponse>{

    @Override
    @Mapping(source = "customer.id", target = "customerID")
    CartResponse toDTO(Cart entity);

    @Override
    @Mapping(target = "customer.id", ignore = true)
    Cart toEntity(CartRequest request);
}
