package cubeledger;

import static org.springdoc.core.utils.Constants.ALL_PATTERN;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class CubeLedgerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CubeLedgerApplication.class, args);
	}

//	@Bean
////	@Profile("!prod")
//	public GroupedOpenApi actuatorApi(OpenApiCustomizer actuatorOpenApiCustomizer,
//																		OperationCustomizer actuatorCustomizer,
//																		WebEndpointProperties endpointProperties) {
//		return GroupedOpenApi.builder()
//				.group("Actuator")
//				.pathsToMatch(endpointProperties.getBasePath() + ALL_PATTERN)
//				.addOpenApiCustomizer(actuatorOpenApiCustomizer)
//				.addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Actuator API").version("1.0")))
//				.addOperationCustomizer(actuatorCustomizer)
//				.pathsToExclude("/health/*")
//				.build();
//	}

	@Bean
	public GroupedOpenApi usersGroup() {
		return GroupedOpenApi.builder().group("api")
				.addOperationCustomizer((operation, handlerMethod) -> {
					operation.addSecurityItem(new SecurityRequirement().addList("basicScheme"));
					return operation;
				})
				.addOpenApiCustomizer(openApi -> openApi.info(new Info().title("CubeLedger API").version("1.0")))
				.packagesToScan("cubeledger")
				.pathsToMatch("/**")
				.build();
	}
}
