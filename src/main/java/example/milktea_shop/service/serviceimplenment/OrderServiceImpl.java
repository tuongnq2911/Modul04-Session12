package example.milktea_shop.service.serviceimplenment;

import example.milktea_shop.dto.request.order.CreateOrderItemRequest;
import example.milktea_shop.dto.request.order.CreateOrderRequest;
import example.milktea_shop.dto.request.order.UpdateOrderStatusRequest;
import example.milktea_shop.dto.response.order.OrderItemResponse;
import example.milktea_shop.dto.response.order.OrderResponse;
import example.milktea_shop.dto.response.order.OrderSummaryResponse;
import example.milktea_shop.entity.Order;
import example.milktea_shop.entity.OrderItem;
import example.milktea_shop.entity.Product;
import example.milktea_shop.entity.User;
import example.milktea_shop.enums.OrderStatus;
import example.milktea_shop.exception.BusinessException;
import example.milktea_shop.exception.ResourceNotFoundException;
import example.milktea_shop.repository.OrderRepository;
import example.milktea_shop.repository.ProductRepository;
import example.milktea_shop.repository.UserRepository;
import example.milktea_shop.service.serviceinterface.OrderService;
import example.milktea_shop.util.MessageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(Long currentUserId, CreateOrderRequest request) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .totalMoney(BigDecimal.ZERO)
                .build();

        BigDecimal totalMoney = BigDecimal.ZERO;

        for (CreateOrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với id = " + itemRequest.getProductId()));

            if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BusinessException("Giá sản phẩm không hợp lệ");
            }

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .priceBuy(product.getPrice())
                    .build();

            order.addOrderItem(orderItem);
            totalMoney = totalMoney.add(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        }

        order.setTotalMoney(totalMoney);
        Order savedOrder = orderRepository.save(order);
        Order orderWithDetails = orderRepository.findById(savedOrder.getId()).orElse(savedOrder);
        return toOrderResponse(orderWithDetails);
    }

    @Override
    public List<OrderSummaryResponse> getMyOrders(Long currentUserId) {
        return orderRepository.findByUserIdOrderByCreatedDateDesc(currentUserId)
                .stream()
                .map(this::toOrderSummaryResponse)
                .toList();
    }

    @Override
    public List<OrderSummaryResponse> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedDateDesc()
                .stream()
                .map(this::toOrderSummaryResponse)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(Long id, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.RESOURCE_NOT_FOUND));

        if (order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.CANCELLED) {
            throw new BusinessException("Đơn hàng đã kết thúc, không thể cập nhật trạng thái");
        }

        order.setStatus(request.getStatus());
        return toOrderResponse(orderRepository.save(order));
    }

    private OrderResponse toOrderResponse(Order order) {
        List<OrderItemResponse> items = new ArrayList<>();
        if (order.getOrderItems() != null) {
            for (OrderItem orderItem : order.getOrderItems()) {
                BigDecimal subTotal = orderItem.getPriceBuy().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                items.add(OrderItemResponse.builder()
                        .productId(orderItem.getProduct().getId())
                        .productName(orderItem.getProduct().getName())
                        .quantity(orderItem.getQuantity())
                        .priceBuy(orderItem.getPriceBuy())
                        .subTotal(subTotal)
                        .build());
            }
        }

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .userEmail(order.getUser().getEmail())
                .createdDate(order.getCreatedDate())
                .status(order.getStatus())
                .totalMoney(order.getTotalMoney())
                .items(items)
                .build();
    }

    private OrderSummaryResponse toOrderSummaryResponse(Order order) {
        return OrderSummaryResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .userEmail(order.getUser().getEmail())
                .createdDate(order.getCreatedDate())
                .status(order.getStatus())
                .totalMoney(order.getTotalMoney())
                .build();
    }
}
