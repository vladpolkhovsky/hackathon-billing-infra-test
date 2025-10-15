package by.faas.billing.service;

import java.util.UUID;
import by.faas.billing.dto.CreateTariffRequest;
import by.faas.billing.dto.TariffDto;
import by.faas.billing.dto.UserDto;
import by.faas.billing.exception.JpaExceptionCreator;
import by.faas.billing.jpa.entity.TariffEntity;
import by.faas.billing.jpa.entity.UserEntity;
import by.faas.billing.jpa.repository.TariffRepository;
import by.faas.billing.jpa.repository.UserRepository;
import by.faas.billing.mappers.TariffMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TariffService {

    private final TariffRepository tariffRepository;
    private final TariffMapper tariffMapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<TariffDto> list(Pageable pageable) {
        return tariffRepository.findAllByActualIsTrue(pageable)
            .map(tariffMapper::map);
    }

    @Transactional
    public TariffDto createTariff(CreateTariffRequest request, UserDto creator) {
        TariffEntity tariff = tariffMapper.mapToEntity(request);
        UserEntity user = userRepository.findById(creator.getId())
            .orElseThrow(JpaExceptionCreator.notFound("user", creator.getId()));
        tariff.setCreatedBy(user);
        return tariffMapper.map(tariffRepository.save(tariff));
    }

    @Transactional
    public void deleteTariff(UUID tariffId) {
        tariffRepository.findByIdAndActualIsTrue(tariffId).ifPresent(tariff -> {
            tariff.setTariffName(tariff.getTariffName() + " (Удалён)");
            tariff.setActual(false);
        });
    }
}
