package example.milktea_shop.service.serviceimplenment;

import example.milktea_shop.dto.request.review.CreateReviewRequest;
import example.milktea_shop.dto.response.review.ReviewResponse;
import example.milktea_shop.entity.Product;
import example.milktea_shop.entity.Review;
import example.milktea_shop.entity.User;
import example.milktea_shop.enums.OrderStatus;
import example.milktea_shop.exception.BusinessException;
import example.milktea_shop.exception.ResourceNotFoundException;
import example.milktea_shop.repository.OrderItemRepository;
import example.milktea_shop.repository.ProductRepository;
import example.milktea_shop.repository.ReviewRepository;
import example.milktea_shop.repository.UserRepository;
import example.milktea_shop.service.serviceinterface.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public ReviewResponse createReview(Long currentUserId, CreateReviewRequest request) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm"));

        if (reviewRepository.existsByUserIdAndProductId(currentUserId, request.getProductId())) {
            throw new BusinessException("Bạn đã đánh giá sản phẩm này rồi");
        }

        boolean purchasedProduct = orderItemRepository.existsPurchasedProduct(
                currentUserId,
                request.getProductId(),
                OrderStatus.COMPLETED
        );

        if (!purchasedProduct) {
            throw new BusinessException("Chỉ được đánh giá sản phẩm đã mua và đã hoàn thành đơn hàng");
        }

        Review review = Review.builder()
                .user(user)
                .product(product)
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        return toReviewResponse(reviewRepository.save(review));
    }

    @Override
    public List<ReviewResponse> getReviewsByProductId(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Không tìm thấy sản phẩm");
        }
        return reviewRepository.findByProductIdOrderByCreatedDateDesc(productId)
                .stream()
                .map(this::toReviewResponse)
                .toList();
    }

    private ReviewResponse toReviewResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .userEmail(review.getUser().getEmail())
                .productId(review.getProduct().getId())
                .productName(review.getProduct().getName())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdDate(review.getCreatedDate())
                .build();
    }
}
