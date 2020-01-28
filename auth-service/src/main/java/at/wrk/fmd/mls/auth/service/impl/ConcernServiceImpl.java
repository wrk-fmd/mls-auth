package at.wrk.fmd.mls.auth.service.impl;

import static java.util.Objects.requireNonNull;

import at.wrk.fmd.mls.auth.dto.concern.ConcernDto;
import at.wrk.fmd.mls.auth.mapper.ConcernMapper;
import at.wrk.fmd.mls.auth.repository.ConcernRepository;
import at.wrk.fmd.mls.auth.service.ConcernService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
class ConcernServiceImpl implements ConcernService {

    private final ConcernRepository concernRepository;
    private final ConcernMapper concernMapper;

    @Autowired
    public ConcernServiceImpl(ConcernRepository concernRepository, ConcernMapper concernMapper) {
        this.concernRepository = requireNonNull(concernRepository, "ConcernRepository must not be null");
        this.concernMapper = requireNonNull(concernMapper, "ConcernMapper must not be null");
    }

    @Override
    public Collection<ConcernDto> getConcerns() {
        return concernRepository.findAll().stream()
                .map(concernMapper::concernToDto)
                .collect(Collectors.toList());
    }
}
