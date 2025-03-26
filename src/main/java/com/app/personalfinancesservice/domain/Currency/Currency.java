package com.app.personalfinancesservice.domain.Currency;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String name;
	private String shortName;
	private String code;

	public Currency withCode(String code) {
		this.setCode(code);
		return this;
	}

	public Currency withId(UUID id) {
		this.setId(id);
		return this;
	}

	public Currency withName(String name) {
		this.setName(name);
		return this;
	}

	public Currency withShortName(String shortName) {
		this.setShortName(shortName);
		return this;
	}
}
