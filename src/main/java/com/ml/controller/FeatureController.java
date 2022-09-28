package com.ml.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.ml.dto.UserHasFeatureResponse;
import com.ml.exception.ResourceNotFound;
import com.ml.service.FeatureService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.web.router.exceptions.UnsatisfiedRouteException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Controller("/feature")
public class FeatureController {

	private final Logger LOG = LoggerFactory.getLogger(FeatureController.class);

	private final FeatureService featureService;

	@Inject
	public FeatureController(FeatureService featureService) {
		this.featureService = featureService;
	}

	@ApiResponses({@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Malformed payload"),
	})
	@Get(value = "/", produces = MediaType.APPLICATION_JSON)
	HttpResponse<UserHasFeatureResponse> getUserFeatureFlag(@Email String email, @NotBlank String featureName) {
		UserHasFeatureResponse response = featureService.getUserFeatureFlag(email, featureName);

		return HttpResponse.ok(response);
	}

	@ApiResponses({@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Malformed payload"),
			@ApiResponse(responseCode = "304", description = "Not modified"),
	})
	@Post(value = "/")
	HttpResponse<String> updateUserFeatureFlag(@Email String email, @NotBlank String featureName, Boolean enable) throws Exception {
		boolean updated = featureService.updateUserFeatureFlag(email, featureName, enable);

		return updated ? HttpResponse.ok() : HttpResponse.notModified();
	}

	/**
	 * Handle exceptions
	 *
	 * @param request Injected request
	 * @param ex      Injected exception
	 * @return Process exception and return status code
	 */
	@Error(exception = Exception.class, global = false)
	HttpResponse<String> exceptionHandler(HttpRequest<?> request, Exception ex) {
		Class<? extends Exception> exceptionType = ex.getClass();
		List<Class<? extends Exception>> badRequestExceptions = new ArrayList<>() {
			{
				add(UnsatisfiedRouteException.class);
				add(ConstraintViolationException.class);
				add(ResourceNotFound.class);
				add(JsonParseException.class);
			}
		};

		if (badRequestExceptions.stream().anyMatch((it) -> it == exceptionType)) {
			LOG.warn(ex.getMessage());
			return HttpResponse.badRequest(ex.getMessage());
		}

		LOG.error(ex.getMessage());
		return HttpResponse.serverError();
	}
}
