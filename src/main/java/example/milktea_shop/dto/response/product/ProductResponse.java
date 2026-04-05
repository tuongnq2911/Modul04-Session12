package example.milktea_shop.dto.response.product;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String size;
    private String toppings;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
