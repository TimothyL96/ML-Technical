package com.ml;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.bridge.SLF4JBridgeHandler;

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

		Micronaut.run(Application.class, args);
	}
}
