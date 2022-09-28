package com.ml.repository;

import com.ml.model.User;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository()
public interface UserRepository extends CrudRepository<User, Integer> {

	@Executable
	Optional<User> findByEmail(String email);

	@Executable
	boolean existsByEmailAndFeaturesName(String email, String featureName);
}
