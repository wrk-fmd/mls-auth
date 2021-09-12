package at.wrk.fmd.mls.auth.config;

import at.wrk.fmd.mls.auth.entity.Concern;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.util.PrimitiveType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SpringDocUtils;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.time.Duration;
import java.time.Instant;

@Configuration
class SpringdocConfiguration {

    @Bean
    public OpenAPI springDocOpenAPI(SpringDocConfigProperties springDocConfigProperties, BuildProperties buildProperties) {
        ModelResolver.enumsAsRef = true;

        springDocConfigProperties.setDefaultProducesMediaType(MediaType.APPLICATION_JSON_VALUE);
        springDocConfigProperties.setWriterWithOrderByKeys(true);

        SpringDocUtils.getConfig().replaceWithClass(
                org.springframework.data.domain.Pageable.class,
                org.springdoc.core.converters.models.Pageable.class
        );

        // Map java.time classes to primitives
        addCustomClass(Instant.class, PrimitiveType.DOUBLE);
        addCustomClass(Duration.class, PrimitiveType.LONG);

        // Map entity parameters from their ids
        addCustomClass(Concern.class, PrimitiveType.LONG);

        return new OpenAPI()
                .info(new Info()
                        .title("MLS Auth Service")
                        .description("API of the MLS Auth Service")
                        .license(new License().name("MIT").url(("https://opensource.org/licenses/MIT")))
                        .version(buildProperties.getVersion())
                )
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new Components().addSecuritySchemes("JWT", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                ))
                ;
    }

    private void addCustomClass(Class<?> clazz, PrimitiveType type) {
        PrimitiveType.customClasses().put(clazz.getName(), type);
    }
}
