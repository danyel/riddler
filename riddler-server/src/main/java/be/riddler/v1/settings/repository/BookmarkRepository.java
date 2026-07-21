package be.riddler.v1.settings.repository;

import be.riddler.v1.settings.entity.BookmarkEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * BookmarkRepository
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, UUID> {
    void deleteById(@NonNull UUID id);
}
