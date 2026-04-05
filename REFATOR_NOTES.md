# Ghi chú refactor bám sát file zip gốc

## Các phần đã giữ nguyên theo zip gốc
- Package gốc: `example.milktea_shop`
- Các entity: `User`, `Product`, `Order`, `OrderItem`, `Review`
- Các enum: `RoleName`, `OrderStatus`, `ReportType`
- Mẫu response: `ApiResponse`, `SuccessResponse`, `ErrorResponse`, `ResponseFactory`
- Cấu trúc service hiện tại: `service/serviceinterface`, `service/serviceimplenment`

## Các phần đã sửa để project hoàn chỉnh theo 5 bài
1. **build.gradle**
   - Đổi về dependency hợp lệ, dễ chạy với Spring Boot 3.2.5.
   - Bỏ các test starter không hợp lệ trong zip gốc.

2. **Security**
   - Thêm `SecurityConfig`
   - Thêm `JwtTokenProvider`
   - Thêm `JwtAuthenticationFilter`
   - Thêm `JwtAuthenticationEntryPoint`
   - Thêm `JwtAccessDeniedHandler`
   - Thêm `CustomUserDetailsService`

3. **DTO request**
   - Thêm toàn bộ request DTO cho auth, product, order, review, user.

4. **Controller**
   - Thêm full controller: `AuthController`, `ProductController`, `OrderController`, `ReviewController`, `UserController`, `ReportController`

5. **Service**
   - Thêm full interface + implementation cho: Auth, Product, Order, Review, User, Report

6. **Repository**
   - Sửa query `OrderItemRepository` để có `@Param`
   - Thêm `EntityGraph` trong `OrderRepository`, `ReviewRepository` để giảm lỗi lazy load khi map response

7. **GlobalExceptionHandler**
   - Sửa import `AccessDeniedException` sang package Spring Security
   - Đồng bộ message trả về theo response wrapper hiện có

## Những chỗ bạn nên cân nhắc đổi tên khi refactor tiếp
- `service/serviceimplenment` -> `service/impl`
- `service/serviceinterface` -> `service/interfaceService`

Mình chưa đổi 2 package này trong bản hoàn chỉnh để tránh làm lệch quá mạnh so với zip gốc của bạn.
