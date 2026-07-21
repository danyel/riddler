package be.riddler.v1.category.repository;

import be.riddler.v1.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * CategoryRepository
 *
 * @author dnoulet
 * @version 1.0.0 16/05/2026
 */
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    Optional<CategoryEntity> findByNameIgnoreCase(String categoryName);

    @EntityGraph(attributePaths = {"keywords"})
    @Query("SELECT c FROM CategoryEntity c")
    List<CategoryEntity> findAllWithKeywords();
}
