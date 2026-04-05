package example.milktea_shop.controller;

import example.milktea_shop.dto.request.review.CreateReviewRequest;
import example.milktea_shop.dto.response.ApiResponse;
import example.milktea_shop.dto.response.ResponseFactory;
import example.milktea_shop.dto.response.review.ReviewResponse;
import example.milktea_shop.security.user.CustomUserDetails;
import example.milktea_shop.service.serviceinterface.ReviewService;
import example.milktea_shop.util.MessageConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/reviews")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @Valid @RequestBody CreateReviewRequest request) {
        ReviewResponse response = reviewService.createReview(currentUser.getUser().getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseFactory.success(MessageConstant.CREATE_SUCCESS, response));
    }

    @GetMapping("/api/products/{id}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByProduct(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseFactory.success(MessageConstant.GET_SUCCESS, reviewService.getReviewsByProductId(id)));
    }
}
