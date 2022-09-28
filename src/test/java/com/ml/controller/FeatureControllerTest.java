package com.ml.controller;

import com.ml.dto.UserHasFeatureResponse;
import com.ml.service.FeatureService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FeatureControllerTest {

	@Inject
	FeatureService featureService;

	@Inject
	FeatureController featureController;

	@Inject
	@Client("/")
	HttpClient featureClient;

	UserHasFeatureResponse userHasFeatureResponse;

	@BeforeAll
	void initialize() {
		this.userHasFeatureResponse = new UserHasFeatureResponse(true);
	}

	@ParameterizedTest
	@MethodSource
	public void testGetUserFeatureFlag(String email, String featureName, HttpStatus want) {
		when(featureService.getUserFeatureFlag(anyString(), anyString()))
				.then(it -> userHasFeatureResponse);

		URI uri = UriBuilder.of("/feature").queryParam("email", email).queryParam("featureName", featureName).build();

		HttpRequest<?> request = HttpRequest.GET(uri.getPath() + "?" + uri.getQuery());

		try {
			HttpResponse<?> response = featureClient.toBlocking().exchange(request);
			Assertions.assertEquals(want, response.getStatus());
		} catch (HttpClientResponseException ex) {
			Assertions.assertEquals(want, ex.getStatus());
		}

	}

	public static Stream<Arguments> testGetUserFeatureFlag() {
		return Stream.of(
				Arguments.of("email1@email.com", "feature1", HttpStatus.OK),
				Arguments.of("email2@email.com", "", HttpStatus.BAD_REQUEST),
				Arguments.of("email3@email.com", null, HttpStatus.BAD_REQUEST),
				Arguments.of("email4email.com", "feature2", HttpStatus.BAD_REQUEST),
				Arguments.of("email5@email", "feature3", HttpStatus.OK), // Javax email validator
				Arguments.of("", "feature4", HttpStatus.BAD_REQUEST),
				Arguments.of(null, "feature5", HttpStatus.BAD_REQUEST),
				Arguments.of("", "", HttpStatus.BAD_REQUEST),
				Arguments.of(null, null, HttpStatus.BAD_REQUEST)
		);
	}

	@ParameterizedTest
	@MethodSource
	public void testUpdateUserFeatureFlag(String email, String featureName, Boolean featureEnable, Boolean apiResponse, HttpStatus want) throws Exception {
		when(featureService.updateUserFeatureFlag(anyString(), anyString(), anyBoolean()))
				.then(it -> apiResponse);

		String body = "{";
		if (featureName != null)
			body = body.concat("\"featureName\": \"" + featureName + "\",");
		if (email != null)
			body = body.concat("\"email\": \"" + email + "\",");
		if (featureEnable != null)
			body = body.concat("\"enable\": " + featureEnable.toString() + ",");
		if (body.endsWith(","))
			body = body.substring(0, body.length() - 1);
		body = body.concat("}");

		HttpRequest<?> request = HttpRequest.POST("/feature", body);

		try {
			HttpResponse<?> response = featureClient.toBlocking().exchange(request);
			Assertions.assertEquals(want, response.getStatus());
		} catch (HttpClientResponseException ex) {
			Assertions.assertEquals(want, ex.getStatus());
		}

	}

	public static Stream<Arguments> testUpdateUserFeatureFlag() {
		return Stream.of(
				Arguments.of("email1@email.com", "feature1", true, true, HttpStatus.OK),
				Arguments.of("email2@email.com", "feature2", false, false, HttpStatus.NOT_MODIFIED),
				Arguments.of("email4@email.com", "", true, true, HttpStatus.BAD_REQUEST),
				Arguments.of("email5@email.com", null, false, false, HttpStatus.BAD_REQUEST),
				Arguments.of("email6@email", null, true, true, HttpStatus.BAD_REQUEST),
				Arguments.of("email7email", "feature3", false, false, HttpStatus.BAD_REQUEST),
				Arguments.of("email8email", "feature4", true, true, HttpStatus.BAD_REQUEST),
				Arguments.of("", "feature5", true, true, HttpStatus.BAD_REQUEST),
				Arguments.of(null, "feature6", true, true, HttpStatus.BAD_REQUEST),
				Arguments.of("", "", true, true, HttpStatus.BAD_REQUEST),
				Arguments.of(null, null, true, true, HttpStatus.BAD_REQUEST),
				Arguments.of("email9@email.com", "feature7", null, true, HttpStatus.BAD_REQUEST),
				Arguments.of("email10@email.com", "feature8", null, false, HttpStatus.BAD_REQUEST)
		);
	}

	@MockBean(FeatureService.class)
	FeatureService featureService() {
		return mock(FeatureService.class);
	}
}
