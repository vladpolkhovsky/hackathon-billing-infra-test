package by.faas.billing.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "tariffs", schema = "billing")
public class TariffEntity extends BaseEntity<UUID> {

    @Column(name = "tariff_name", nullable = false)
    private String tariffName;

    @Column(name = "call_price", nullable = false)
    private Double callPrice;

    @Column(name = "cpu_price", nullable = false)
    private Double cpuPrice;

    @Column(name = "memory_price", nullable = false)
    private Double memoryPrice;

    @Column(name = "actual", nullable = false)
    private Boolean actual = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;
}