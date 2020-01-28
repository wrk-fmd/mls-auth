package at.wrk.fmd.mls.auth.mapper;

import at.wrk.fmd.mls.auth.dto.unit.UnitTokenDto;
import at.wrk.fmd.mls.auth.entity.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    @Mapping(target = "token", ignore = true)
    UnitTokenDto unitToTokenDto(Unit unit);
}
