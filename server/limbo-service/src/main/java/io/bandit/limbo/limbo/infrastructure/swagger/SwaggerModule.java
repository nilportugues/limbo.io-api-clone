package io.bandit.limbo.limbo.infrastructure.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class SwaggerModule {

    @Value("${swagger.contact.name}")
    private String contactName;

    @Value("${swagger.contact.url}")
    private String contactUrl;

    @Value("${swagger.contact.email}")
    private String contactEmail;

    @Value("${swagger.api-info.title}")
    private String apiInfoTitle;

    @Value("${swagger.api-info.description}")
    private String apiInfoDescription;

    @Value("${swagger.api-info.version}")
    private String apiInfoVersion;

    @Value("${swagger.api-info.tos}")
    private String apiInfoTos;

    @Value("${swagger.api-info.license}")
    private String apiInfoLicense;

    @Value("${swagger.api-info.license-url}")
    private String apiInfoLicenseUrl;

    @Bean
    public Docket swaggerDocumentation() {

        final Set<String> protocols = new HashSet<>();
        protocols.add("http");
        protocols.add("https");

        final Contact contact = new Contact(contactName, contactUrl, contactEmail);

        final ApiInfo apiInfo = new ApiInfo(
                apiInfoTitle,
                apiInfoDescription,
                apiInfoVersion,
                apiInfoTos,
                contact,
                apiInfoLicense,
                apiInfoLicenseUrl
        );

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .protocols(protocols)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nilportugues"))
                .paths(PathSelectors.any())
                .build();
    }

}
