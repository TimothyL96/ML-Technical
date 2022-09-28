package com.ml.repository;

import com.ml.model.Feature;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository()
public interface FeatureRepository extends CrudRepository<Feature, Integer> {

	@Executable
	Optional<Feature> findByName(String name);
}
