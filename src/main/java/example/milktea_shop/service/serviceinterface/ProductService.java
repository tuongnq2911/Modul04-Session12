package example.milktea_shop.service.serviceinterface;

import example.milktea_shop.dto.request.product.CreateProductRequest;
import example.milktea_shop.dto.request.product.UpdateProductRequest;
import example.milktea_shop.dto.response.product.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts();

    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse updateProduct(Long id, UpdateProductRequest request);

    void deleteProduct(Long id);
}
