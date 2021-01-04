package com.nab.bank.prepaid.configuration;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by khai.d.ho
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  @Bean
  public Docket api() {
    SecurityReference securityReference = SecurityReference.builder()
        .reference("basicAuth")
        .scopes(new AuthorizationScope[0])
        .build();

    ArrayList<SecurityReference> reference = new ArrayList<>(1);
    reference.add(securityReference);

    ArrayList<SecurityContext> securityContexts = new ArrayList<>(1);
    securityContexts.add(SecurityContext.builder().securityReferences(reference).build());

    ArrayList<SecurityScheme> auth = new ArrayList<>(1);
    auth.add(new BasicAuth("basicAuth"));

    return new Docket(DocumentationType.SWAGGER_2)
        .securitySchemes(auth)
        .securityContexts(securityContexts)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.regex("/.*")).build()
        .apiInfo(apiEndPointsInfo());
  }

  private ApiInfo apiEndPointsInfo() {
    return new ApiInfoBuilder()
        .title("Bank ABC")
        .description("This is all document for purchase prepaid data")
        .license("Apache 2.0")
        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
        .version("1.0.0")
        .build();
  }
}
