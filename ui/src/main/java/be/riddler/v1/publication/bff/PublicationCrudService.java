package be.riddler.v1.publication.bff;

import be.riddler.v1.publication.entity.PublicationEntity;
import be.riddler.v1.publication.repository.PublicationRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.crud.CrudService;
import com.vaadin.hilla.crud.filter.Filter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;

/**
 * PublicationCrudService
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@AnonymousAllowed
@BrowserCallable
@RequiredArgsConstructor(access = PACKAGE)
public class PublicationCrudService implements CrudService<PublicationEntity, PublicationRepository> {
    private final PublicationRepository publicationRepository;

    @Override
    public @Nullable PublicationEntity save(@NonNull PublicationEntity value) {
        return publicationRepository.save(value);
    }

    @Override
    public void delete(PublicationRepository publicationRepository) {

    }

    @Override
    public @NonNull List<PublicationEntity> list(Pageable pageable, @Nullable Filter filter) {
        return List.of();
    }
}
