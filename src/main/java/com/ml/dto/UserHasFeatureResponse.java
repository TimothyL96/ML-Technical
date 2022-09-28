package com.ml.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record UserHasFeatureResponse(
		Boolean canAccess
) {
}
