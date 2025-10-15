package by.faas.billing.mappers;

import by.faas.billing.dto.CreateTariffRequest;
import by.faas.billing.dto.TariffDto;
import by.faas.billing.jpa.entity.TariffEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface TariffMapper {

    @Mapping(target = "name", source = "tariffName")
    TariffDto map(TariffEntity tariffEntity);

    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    TariffEntity mapToEntity(CreateTariffRequest request);
}
