package backend.service;

import backend.entity.Product;
import backend.payload.request.ProductRequest;
import backend.payload.response.ProductResponse;

public interface ProductService extends BaseService<Product, Integer, ProductRequest,ProductResponse>{
}
