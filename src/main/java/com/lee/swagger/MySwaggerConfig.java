package com.lee.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class MySwaggerConfig {

    //http://localhost:8080/swagger-ui.html#/department-controller

    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.lee.controller"))
                .paths(PathSelectors.any()) // 对所有路径进行监听?
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("Lee", "", "873793992@qq.com");
        return new ApiInfoBuilder()
                .title("SSM_App")
                .contact(contact)
                .version("1.0")
                .license("主页")
                .licenseUrl("http://localhost:8080")
                .build();
    }

}