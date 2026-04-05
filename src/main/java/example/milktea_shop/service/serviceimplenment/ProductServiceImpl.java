package example.milktea_shop.service.serviceimplenment;

import example.milktea_shop.dto.request.product.CreateProductRequest;
import example.milktea_shop.dto.request.product.UpdateProductRequest;
import example.milktea_shop.dto.response.product.ProductResponse;
import example.milktea_shop.entity.Product;
import example.milktea_shop.exception.ResourceNotFoundException;
import example.milktea_shop.repository.ProductRepository;
import example.milktea_shop.service.serviceinterface.ProductService;
import example.milktea_shop.util.MessageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toProductResponse)
                .toList();
    }

    @Override
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .size(request.getSize())
                .toppings(request.getToppings())
                .build();
        return toProductResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.RESOURCE_NOT_FOUND));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSize(request.getSize());
        product.setToppings(request.getToppings());

        return toProductResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.RESOURCE_NOT_FOUND));
        productRepository.delete(product);
    }

    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .size(product.getSize())
                .toppings(product.getToppings())
                .createdDate(product.getCreatedDate())
                .updatedDate(product.getUpdatedDate())
                .build();
    }
}
