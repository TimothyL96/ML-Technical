package com.ml.service;

import com.ml.dto.UserHasFeatureResponse;
import com.ml.exception.ResourceNotFound;
import com.ml.model.Feature;
import com.ml.model.User;
import com.ml.repository.FeatureRepository;
import com.ml.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.Optional;

@Singleton
public class FeatureService {

	private final FeatureRepository featureRepository;
	private final UserRepository userRepository;

	private final Logger LOG = LoggerFactory.getLogger(FeatureService.class);

	@Inject
	public FeatureService(FeatureRepository featureRepository, UserRepository userRepository) {
		this.featureRepository = featureRepository;
		this.userRepository = userRepository;
	}

	public UserHasFeatureResponse getUserFeatureFlag(String email, String featureName) {
		boolean userHasFeature = userRepository.existsByEmailAndFeaturesName(email, featureName);

		return new UserHasFeatureResponse(userHasFeature);
	}

	@Transactional
	public boolean updateUserFeatureFlag(String email, String featureName, Boolean featureEnable) throws Exception {
		boolean userHasFeature = userRepository.existsByEmailAndFeaturesName(email, featureName);

		if ((userHasFeature && featureEnable) || (!userHasFeature && !featureEnable))
			return false;

		// Get feature
		Optional<Feature> optionalFeature = featureRepository.findByName(featureName);
		if (optionalFeature.isEmpty()) {
			throw new ResourceNotFound("Feature not found");
		}
		Feature feature = optionalFeature.get();

		// Get user
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty()) {
			throw new ResourceNotFound("User not found");
		}
		User user = optionalUser.get();

		if (!featureEnable)
			// delete feature from user
			user.getFeatures().remove(feature);
		else
			// add feature to user
			user.getFeatures().add(feature);

		return true;
	}
}
