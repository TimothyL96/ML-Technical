package com.ml;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.h2.tools.Server;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.sql.SQLException;

@OpenAPIDefinition(
		info = @Info(
				title = "ML-Technical",
				version = "0.1"
		)
)

public class Application {
	public static void main(String[] args) {
		// Bridge JUL to Slf4j
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		// H2 - Start embedded web server
		try {
			// Access through web browser: http://localhost:8082/
			Server.createWebServer().start();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		Micronaut.run(Application.class, args);
	}
}
