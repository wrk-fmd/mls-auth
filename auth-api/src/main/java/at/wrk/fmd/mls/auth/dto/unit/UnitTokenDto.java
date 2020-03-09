package at.wrk.fmd.mls.auth.dto.unit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnitTokenDto {

    private Long id;
    private String name;
    private String token;
}
