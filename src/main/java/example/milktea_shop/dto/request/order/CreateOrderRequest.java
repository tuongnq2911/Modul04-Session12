package example.milktea_shop.dto.request.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    @Valid
    @NotEmpty(message = "Danh sách sản phẩm không được để trống")
    private List<CreateOrderItemRequest> items;
}
