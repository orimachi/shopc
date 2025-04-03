package backend.service.imp;

import backend.entity.Product;
import backend.mapper.BaseMapper;
import backend.mapper.ProductMapper;
import backend.payload.request.ProductRequest;
import backend.payload.response.ProductResponse;
import backend.repository.ProductRepository;
import backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImp extends BaseServiceImp<Product,Integer,ProductRequest,ProductResponse> implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Override
    protected JpaRepository<Product, Integer> getRepository() {
        return productRepository;
    }

    @Override
    protected BaseMapper<Product, ProductRequest,ProductResponse> getMapper() {
        return productMapper;
    }

}
