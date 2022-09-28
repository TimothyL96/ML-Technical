package com.ml.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Serdeable
public class Feature {

	@Id
	@GeneratedValue
	private Integer id;

	@NotBlank
	@Column(unique = true)
	private String name;

	@ManyToMany(mappedBy = "features")
	Set<User> users = new HashSet<>();

	public Feature() {
	}

	public Feature(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public Set<User> getUsers() {
		return users;
	}

	@JsonIgnore
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Feature{" +
				"id=" + id +
				", name='" + name + '\'' +
				", users=" + users.size() +
				'}';
	}
}
