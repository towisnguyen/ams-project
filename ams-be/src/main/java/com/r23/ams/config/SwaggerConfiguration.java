package com.r23.ams.config;

import com.fasterxml.classmate.TypeResolver;
import com.r23.ams.models.ResponseDto;
import com.r23.ams.models.dto.*;
import com.r23.ams.models.dto.asset.AssetDetailDto;
import com.r23.ams.models.dto.asset.AssetDto;
import com.r23.ams.models.dto.assetSupplier.AssetSupplierDto;
import com.r23.ams.models.dto.project.ProjectCreateDto;
import com.r23.ams.models.dto.project.ProjectDetailDto;
import com.r23.ams.models.dto.request.RequestDto;
import com.r23.ams.models.dto.request.RequestSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .additionalModels(
                        typeResolver.resolve(RequestDto.class),
                        typeResolver.resolve(RequestSearchDto.class),
                        typeResolver.resolve(AssetDto.class),
                        typeResolver.resolve(AssetDetailDto.class),
                        typeResolver.resolve(AppUserDto.class),
                        typeResolver.resolve(RoleDto.class),
                        typeResolver.resolve(ProjectDto.class),
                        typeResolver.resolve(ResponseDto.class),
                        typeResolver.resolve(ProjectCreateDto.class),
                        typeResolver.resolve(ProjectDetailDto.class),
                        typeResolver.resolve(AssetDetailDto.class),
                        typeResolver.resolve(AssetSupplierDto.class)
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.r23.ams.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Demo REST API",
                "User Management API.",
                "API v1.0.0",
                "https://tma.com.vn/",
                new Contact("Toi Nguyen", "https://tma.com.vn/", "nhtoi@tma.com.vn"),
                "License of API",
                "https://tma.com.vn/",
                Collections.emptyList());
    }
    @Autowired
    private TypeResolver typeResolver;
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}
