package o.api.library.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI sdkOpenAPI() {
		Info info = new Info()
				.title("Library API")
				.version("v1")
				.description("Library API Collection");
		return new OpenAPI()
				.addServersItem(new Server().url("/library"))
				.components(new Components())
				.info(info);
	}
}
