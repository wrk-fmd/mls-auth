package at.wrk.fmd.mls.auth.mapper;

import at.wrk.fmd.mls.auth.dto.concern.ConcernDto;
import at.wrk.fmd.mls.auth.entity.Concern;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConcernMapper {

    ConcernDto concernToDto(Concern concern);
}
