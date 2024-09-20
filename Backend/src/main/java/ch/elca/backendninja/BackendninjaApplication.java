package ch.elca.backendninja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class BackendninjaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendninjaApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {

		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/*/*"))
				.apis(RequestHandlerSelectors.basePackage("ch.elca.backendninja"))
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {

		return new ApiInfo(
				"Final Backend Practice",
				"API for Final Practice",
				"1.0",
				"Free to use",
				new Contact("David Martinez Diaz", "http://google.com", "test@gmail.com"),
				"API LICENSE",
				"http://google.com",
				Collections.emptyList()
		);
	}
}
