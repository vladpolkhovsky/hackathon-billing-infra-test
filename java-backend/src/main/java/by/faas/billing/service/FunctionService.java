package by.faas.billing.service;

import by.faas.billing.dto.FunctionDto;
import by.faas.billing.jpa.repository.FunctionRepository;
import by.faas.billing.mappers.FunctionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FunctionService {

    private final FunctionRepository functionRepository;
    private final FunctionMapper functionMapper;

    @Transactional(readOnly = true)
    public Page<FunctionDto> list(Pageable pageable) {
        return functionRepository.findAll(pageable)
            .map(functionMapper::map);
    }
}
