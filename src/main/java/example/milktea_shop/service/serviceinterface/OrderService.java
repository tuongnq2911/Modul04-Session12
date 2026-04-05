package example.milktea_shop.service.serviceinterface;

import example.milktea_shop.dto.request.order.CreateOrderRequest;
import example.milktea_shop.dto.request.order.UpdateOrderStatusRequest;
import example.milktea_shop.dto.response.order.OrderResponse;
import example.milktea_shop.dto.response.order.OrderSummaryResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(Long currentUserId, CreateOrderRequest request);

    List<OrderSummaryResponse> getMyOrders(Long currentUserId);

    List<OrderSummaryResponse> getAllOrders();

    OrderResponse updateOrderStatus(Long id, UpdateOrderStatusRequest request);
}
