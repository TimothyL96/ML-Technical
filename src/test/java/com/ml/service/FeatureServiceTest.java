package com.ml.service;

import com.ml.dto.UserHasFeatureResponse;
import com.ml.exception.ResourceNotFound;
import com.ml.model.Feature;
import com.ml.model.User;
import com.ml.repository.FeatureRepository;
import com.ml.repository.UserRepository;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@MicronautTest
public class FeatureServiceTest {

	@Inject
	FeatureService featureService;

	@Inject
	UserRepository userRepository;

	@Inject
	FeatureRepository featureRepository;

	@ParameterizedTest
	@CsvSource({"email1@email.com,feature1,true", "email2@email.com,feature2,true", "email,feature1,false"})
	public void testGetUserFeatureFlag(String email, String featureName, Boolean exists) {
		UserHasFeatureResponse want = new UserHasFeatureResponse(exists);

		when(userRepository.existsByEmailAndFeaturesName(anyString(), anyString()))
				.then(it -> exists);

		UserHasFeatureResponse get = featureService.getUserFeatureFlag(email, featureName);

		Assertions.assertEquals(want, get);

		verify(userRepository).existsByEmailAndFeaturesName(anyString(), anyString());
	}

	@ParameterizedTest
	@MethodSource
	public void testUpdateUserFeatureFlag(String email, String featureName, Boolean featureEnable, Boolean exists, Boolean want) throws Exception {
		Optional<Feature> feature = Optional.of(mock(Feature.class));
		Optional<User> user = Optional.of(mock(User.class));

		when(userRepository.existsByEmailAndFeaturesName(anyString(), anyString()))
				.then(it -> exists);
		when(userRepository.findByEmail(anyString()))
				.then(it -> user);
		when(featureRepository.findByName(anyString()))
				.then(it -> feature);

		Boolean get = featureService.updateUserFeatureFlag(email, featureName, featureEnable);
		Assertions.assertEquals(want, get);
	}

	private static Stream<Arguments> testUpdateUserFeatureFlag() {
		return Stream.of(
				Arguments.of("my1@email.com", "myFeature1", true, true, false),
				Arguments.of("my2@email.com", "myFeature2", true, false, true),
				Arguments.of("my3@email.com", "myFeature3", false, false, false),
				Arguments.of("my4@email.com", "myFeature4", false, true, true)
		);
	}

	@ParameterizedTest
	@MethodSource
	public void testUpdateUserFeatureFlagException(ArgumentsAccessor argumentsAccessor) {
		Class<ResourceNotFound> ex = ResourceNotFound.class;
		boolean exists = false;

		when(userRepository.existsByEmailAndFeaturesName(anyString(), anyString()))
				.then(it -> exists);
		when(userRepository.findByEmail(anyString()))
				.then(it -> Optional.ofNullable(argumentsAccessor.get(3)));
		when(featureRepository.findByName(anyString()))
				.then(it -> Optional.ofNullable(argumentsAccessor.get(4)));

		Assertions.assertThrowsExactly(ex, () -> featureService.updateUserFeatureFlag(
				(String) argumentsAccessor.get(0),
				(String) argumentsAccessor.get(1),
				(Boolean) argumentsAccessor.get(2)
		));
	}

	private static Stream<Arguments> testUpdateUserFeatureFlagException() {
		return Stream.of(
				Arguments.of("my2@email.com", "myFeature2", true, null, mock(Feature.class)),
				Arguments.of("my3@email.com", "myFeature3", true, mock(User.class), null),
				Arguments.of("my4@email.com", "myFeature4", true, null, null)
		);
	}

	@MockBean(UserRepository.class)
	UserRepository userRepository() {
		return mock(UserRepository.class);
	}

	@MockBean(FeatureRepository.class)
	FeatureRepository featureRepository() {
		return mock(FeatureRepository.class);
	}
}
