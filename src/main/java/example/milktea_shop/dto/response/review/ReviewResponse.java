package example.milktea_shop.dto.response.review;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewResponse {
    private Long id;
    private Long userId;
    private String userEmail;
    private Long productId;
    private String productName;
    private Integer rating;
    private String comment;
    private LocalDateTime createdDate;
}
