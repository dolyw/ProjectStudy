package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置信息
 * https://www.jianshu.com/p/c79f6a14f6c9
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 14:47
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Swagger配置信息
     * 访问地址 http://localhost:8080/swagger-ui.html
     * @return
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        // 设置文档的标题
                        .title("MQ生产者")
                        // 设置文档的描述
                        .description("MQ生产者 API接口文档")
                        // 设置文档的版本信息
                        .version("1.0")
                        // 设置文档的License信息
                        .termsOfServiceUrl("http://www.baidu.com")
                        .build())
                .select()
                // 接口包扫描路径
                .apis(RequestHandlerSelectors.basePackage("com.example"))
                .paths(PathSelectors.any())
                .build();
    }

}
