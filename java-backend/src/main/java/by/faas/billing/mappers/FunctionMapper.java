package by.faas.billing.mappers;

import by.faas.billing.dto.FunctionDto;
import by.faas.billing.jpa.entity.FunctionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FunctionMapper {

    FunctionDto map(FunctionEntity function);
}
