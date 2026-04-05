package example.milktea_shop.controller;

import example.milktea_shop.dto.request.order.CreateOrderRequest;
import example.milktea_shop.dto.request.order.UpdateOrderStatusRequest;
import example.milktea_shop.dto.response.ApiResponse;
import example.milktea_shop.dto.response.ResponseFactory;
import example.milktea_shop.dto.response.order.OrderResponse;
import example.milktea_shop.dto.response.order.OrderSummaryResponse;
import example.milktea_shop.security.user.CustomUserDetails;
import example.milktea_shop.service.serviceinterface.OrderService;
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
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @Valid @RequestBody CreateOrderRequest request) {
        OrderResponse response = orderService.createOrder(currentUser.getUser().getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseFactory.success(MessageConstant.CREATE_SUCCESS, response));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<List<OrderSummaryResponse>>> getMyOrders(
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        List<OrderSummaryResponse> response = orderService.getMyOrders(currentUser.getUser().getId());
        return ResponseEntity.ok(ResponseFactory.success(MessageConstant.GET_SUCCESS, response));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<ApiResponse<List<OrderSummaryResponse>>> getAllOrders() {
        return ResponseEntity.ok(ResponseFactory.success(MessageConstant.GET_SUCCESS, orderService.getAllOrders()));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        OrderResponse response = orderService.updateOrderStatus(id, request);
        return ResponseEntity.ok(ResponseFactory.success(MessageConstant.UPDATE_SUCCESS, response));
    }
}
