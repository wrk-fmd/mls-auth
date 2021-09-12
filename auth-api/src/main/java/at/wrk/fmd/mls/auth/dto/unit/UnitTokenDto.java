package at.wrk.fmd.mls.auth.dto.unit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnitTokenDto {

    @Schema(required = true)
    private Long id;

    @Schema(required = true)
    private String name;

    @Schema(required = true)
    private String token;
}
