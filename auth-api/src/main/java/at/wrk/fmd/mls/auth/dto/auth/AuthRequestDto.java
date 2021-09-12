package at.wrk.fmd.mls.auth.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AuthRequestDto {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
