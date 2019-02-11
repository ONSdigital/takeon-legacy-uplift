package uk.gov.ons.collection;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

//import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("uk.gov.ons.collection.controller"))
                .paths(regex("/doodles.*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Collection Persistence API",
                "The API uses JPA2/Hibernate to do CRUD operations on the database for Collection systems",
                "0.1",
                "Terms of service",
                new Contact("Collection Team", "https://www.ons.gov.uk", "collection.support@ons.gov.uk"),
                "Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
    }
}