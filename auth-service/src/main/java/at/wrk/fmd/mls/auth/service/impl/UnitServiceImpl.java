package at.wrk.fmd.mls.auth.service.impl;

import static java.util.Objects.requireNonNull;

import at.wrk.fmd.mls.auth.dto.unit.UnitTokenDto;
import at.wrk.fmd.mls.auth.entity.Concern;
import at.wrk.fmd.mls.auth.entity.Unit;
import at.wrk.fmd.mls.auth.mapper.UnitMapper;
import at.wrk.fmd.mls.auth.repository.UnitRepository;
import at.wrk.fmd.mls.auth.service.TokenService;
import at.wrk.fmd.mls.auth.service.UnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final TokenService tokenService;
    private final UnitMapper unitMapper;

    @Autowired
    public UnitServiceImpl(UnitRepository unitRepository, TokenService tokenService, UnitMapper unitMapper) {
        this.unitRepository = requireNonNull(unitRepository, "UnitRepository must not be null");
        this.tokenService = requireNonNull(tokenService, "TokenService must not be null");
        this.unitMapper = requireNonNull(unitMapper, "UnitMapper must not be null");
    }

    @Override
    public Collection<UnitTokenDto> getUnitTokens(Concern concern, Instant expiration) {
        return unitRepository.findByConcern(concern).stream()
                .map(unit -> getTokenDto(unit, expiration))
                .collect(Collectors.toList());
    }

    private UnitTokenDto getTokenDto(Unit unit, Instant expiration) {
        UnitTokenDto dto = unitMapper.unitToTokenDto(unit);
        dto.setToken(tokenService.generateUnitRenewalToken(unit, expiration));
        return dto;
    }
}
