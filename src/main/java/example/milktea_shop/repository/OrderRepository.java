package example.milktea_shop.repository;

import example.milktea_shop.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.product"})
    List<Order> findByUserIdOrderByCreatedDateDesc(Long userId);

    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.product"})
    List<Order> findAllByOrderByCreatedDateDesc();

    @Override
    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.product"})
    Optional<Order> findById(Long id);
}
