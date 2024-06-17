package com.sparta.reviewspotproject;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        // 액세스 토큰 보안 스키마 설정
        SecurityScheme accessTokenScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // 리프레시 토큰 보안 스키마 설정
        SecurityScheme refreshTokenScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Refresh-Token");

        // 보안 요구 사항 설정
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("accessTokenAuth")
                .addList("refreshTokenAuth");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("accessTokenAuth", accessTokenScheme)
                        .addSecuritySchemes("refreshTokenAuth", refreshTokenScheme))
                .security(Arrays.asList(securityRequirement))
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("ReviewSpot API Test") // API의 제목
                .description("뉴스피드 프로젝트 - ReviewSpot API") // API에 대한 설명
                .version("0.0.1"); // API의 버전
    }
}