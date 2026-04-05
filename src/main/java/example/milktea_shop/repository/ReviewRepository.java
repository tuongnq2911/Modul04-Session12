package example.milktea_shop.repository;

import example.milktea_shop.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"user", "product"})
    List<Review> findByProductIdOrderByCreatedDateDesc(Long productId);

    boolean existsByUserIdAndProductId(Long userId, Long productId);
}
