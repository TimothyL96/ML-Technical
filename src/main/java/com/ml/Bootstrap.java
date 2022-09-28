package com.ml;

import com.ml.model.Feature;
import com.ml.model.User;
import com.ml.repository.FeatureRepository;
import com.ml.repository.UserRepository;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.core.type.Argument;
import io.micronaut.json.JsonMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
@Singleton
public class Bootstrap implements ApplicationEventListener<StartupEvent> {

	private final JsonMapper jsonMapper;
	private final FeatureRepository featureRepository;
	private final UserRepository userRepository;

	private final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);
	private final String DATA_FILE_USERS = "data/users.json";
	private final String DATA_FILE_FEATURES = "data/features.json";

	@Inject
	public Bootstrap(JsonMapper jsonMapper, FeatureRepository featureRepository, UserRepository userRepository) {
		this.jsonMapper = jsonMapper;
		this.featureRepository = featureRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void onApplicationEvent(StartupEvent event) {

		// Populate users
		try (InputStream inputStream = new FileInputStream(DATA_FILE_USERS)) {
			List<User> user = jsonMapper.readValue(inputStream, Argument.listOf(User.class));
			userRepository.saveAll(user);
			LOG.info("Total {} users populated", userRepository.count());
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			throw new RuntimeException(ex);
		}

		// Populate features
		try (InputStream inputStream = new FileInputStream(DATA_FILE_FEATURES)) {
			List<Feature> features = jsonMapper.readValue(inputStream, Argument.listOf(Feature.class));
			featureRepository.saveAll(features);
			LOG.info("Total {} features populated", featureRepository.count());
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			throw new RuntimeException(ex);
		}
	}
}
