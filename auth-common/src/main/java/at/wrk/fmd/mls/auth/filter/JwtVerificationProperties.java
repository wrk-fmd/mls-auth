package at.wrk.fmd.mls.auth.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.auth.verification")
@Getter
@Setter
public class JwtVerificationProperties {

    private Resource signingKey;
}
