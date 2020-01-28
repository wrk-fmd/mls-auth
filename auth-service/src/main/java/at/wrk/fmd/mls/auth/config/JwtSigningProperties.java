package at.wrk.fmd.mls.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application.auth.signing")
public class JwtSigningProperties {

    private Resource signingKey;

    @DurationUnit(ChronoUnit.MINUTES)
    private Duration renewalValidity;

    @DurationUnit(ChronoUnit.MINUTES)
    private Duration requestValidity;
}
