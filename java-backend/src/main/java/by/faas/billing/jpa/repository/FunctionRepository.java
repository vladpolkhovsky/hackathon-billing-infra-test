package by.faas.billing.jpa.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import by.faas.billing.dto.BillingPeriod;
import by.faas.billing.jpa.entity.FunctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionRepository extends JpaRepository<FunctionEntity, UUID> {
    @Query(nativeQuery = true, value = "select * from billing.get_resource_consumption(:period) " +
        "where time_period between :from and :to and function_id = :functionId")
    List<ResourceConsumptionProjection> getResourceConsumption(String period, LocalDateTime from, LocalDateTime to, UUID functionId);

    @Query(nativeQuery = true, value = "select * from billing.get_resource_consumption('month') where " +
        "time_period = (select MAX(time_period) from billing.get_resource_consumption('month') where function_id in :functionIds)")
    List<ResourceConsumptionProjection> getResourceConsumptionByFunctions(List<UUID> functionIds);
}
