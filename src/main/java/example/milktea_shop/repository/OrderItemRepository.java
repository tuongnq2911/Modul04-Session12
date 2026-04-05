package example.milktea_shop.repository;

import example.milktea_shop.entity.OrderItem;
import example.milktea_shop.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(oi) > 0 THEN TRUE ELSE FALSE END
            FROM OrderItem oi
            WHERE oi.order.user.id = :userId
              AND oi.product.id = :productId
              AND oi.order.status = :status
            """)
    boolean existsPurchasedProduct(@Param("userId") Long userId,
                                   @Param("productId") Long productId,
                                   @Param("status") OrderStatus status);
}
