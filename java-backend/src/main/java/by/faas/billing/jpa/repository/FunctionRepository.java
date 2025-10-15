package by.faas.billing.jpa.repository;

import java.util.UUID;
import by.faas.billing.jpa.entity.FunctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionRepository extends JpaRepository<FunctionEntity, UUID> {
}
