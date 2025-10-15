package by.faas.billing.jpa.repository;

import java.util.Optional;
import java.util.UUID;
import by.faas.billing.jpa.entity.TariffEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<TariffEntity, UUID> {
    Page<TariffEntity> findAllByActualIsTrue(Pageable pageable);

    Optional<TariffEntity> findByIdAndActualIsTrue(UUID id);
}
