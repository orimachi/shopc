package backend.service.imp;

import backend.entity.Cart;
import backend.mapper.BaseMapper;
import backend.mapper.CartMapper;
import backend.payload.request.CartRequest;
import backend.payload.response.CartResponse;
import backend.repository.CartRepository;
import backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CartServiceImp extends BaseServiceImp<Cart, UUID,CartRequest, CartResponse> implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    @Override
    protected JpaRepository<Cart, UUID> getRepository() {
        return cartRepository;
    }

    @Override
    protected BaseMapper<Cart, CartRequest, CartResponse> getMapper() {
        return cartMapper;
    }
}
