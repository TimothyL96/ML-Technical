package com.ml.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.serde.annotation.Serdeable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Serdeable
public class User {

	@Id
	@GeneratedValue
	private Integer id;

	@NotBlank
	@JsonProperty("first_name")
	private String firstName;

	@NotBlank
	@JsonProperty("last_name")
	private String lastName;

	@NotBlank
	@Column(unique = true)
	private String email;

	@DateCreated
	@Column(name = "date_created")
	private Date dateCreated;

	@ManyToMany()
	@JoinTable(joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "feature_id"))
	private Set<Feature> features = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Set<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(Set<Feature> features) {
		this.features = features;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", dateCreated=" + dateCreated +
				", features=" + features +
				'}';
	}
}
