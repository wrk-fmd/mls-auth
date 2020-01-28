package at.wrk.fmd.mls.auth.config;

import at.wrk.fmd.mls.auth.entity.Concern;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Duration;

/**
 * Defines the Swagger configuration.
 * Swagger is required by the Front-end that builds the API while the AmbFlow server is running (upon terminal request during development).
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .alternateTypeRules(
                        AlternateTypeRules.newRule(Duration.class, Long.class),
                        AlternateTypeRules.newRule(Concern.class, Long.class)
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("at.wrk.fmd.mls.auth"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder()
                .title("MLS Auth Service")
                .description("API of the MLS Auth Service")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("3.0.0-SNAPSHOT")
                .build();
    }
}