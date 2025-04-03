package backend.controller;

import backend.enums.EMessage;
import backend.payload.request.ProductRequest;
import backend.payload.response.APIResponse;
import backend.payload.response.ProductResponse;
import backend.service.ProductService;
import backend.utils.app.MethodUtils;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    @GetMapping("/allProduct")
    public APIResponse<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> result = productService.getAll();
        return APIResponse.<List<ProductResponse>>builder().result(result).build();
    }

    @GetMapping("/getOne/{id}")
    public APIResponse<Optional<ProductResponse>> getOneByID(@PathVariable("id")  @NotBlank Integer id) {
        Optional<ProductResponse> result = productService.getOneByID(id);
        return APIResponse.<Optional<ProductResponse>>builder().result(result).build();
    }

    @PostMapping("/createProduct")
    public APIResponse<ProductResponse> createProduct(@RequestBody ProductRequest request) {

        ProductResponse result = productService.save(request);
        return APIResponse.<ProductResponse>builder().message(MethodUtils.formatMessage(EMessage.SAVE_PRODUCT_SUCCESS)).result(result).build();
    }

    @PutMapping("/updateProduct/{id}")
    public APIResponse<ProductResponse> updateProduct(@PathVariable("id") Integer id , @RequestBody ProductRequest request) {
       Optional<ProductResponse> isExist = productService.getOneByID(id);
       if (isExist.isPresent()){
           BeanUtils.copyProperties(request, isExist, "id");
       }
        ProductResponse result = productService.save(request);
        return APIResponse.<ProductResponse>builder().message(MethodUtils.formatMessage(EMessage.UPDATE_PRODUCT_SUCCESS,result.getId())).result(result).build();
    }

    @DeleteMapping("/deleteProduct/{id}")
    public APIResponse<ProductResponse> deleteProduct(@PathVariable("id") @NotBlank Integer id) {
        productService.deleteByID(id);
        return APIResponse.<ProductResponse>builder().message(MethodUtils.formatMessage(EMessage.DELETE_PRODUCT_SUCCESS,id)).build();
    }
}
