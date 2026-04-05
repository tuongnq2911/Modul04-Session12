package example.milktea_shop.dto.response.order;

import example.milktea_shop.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private String userEmail;
    private LocalDateTime createdDate;
    private OrderStatus status;
    private BigDecimal totalMoney;
    private List<OrderItemResponse> items;
}
