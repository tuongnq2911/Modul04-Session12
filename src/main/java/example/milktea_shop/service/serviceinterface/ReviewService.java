package example.milktea_shop.service.serviceinterface;

import example.milktea_shop.dto.request.review.CreateReviewRequest;
import example.milktea_shop.dto.response.review.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse createReview(Long currentUserId, CreateReviewRequest request);

    List<ReviewResponse> getReviewsByProductId(Long productId);
}
