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
public class CurrencyType {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String name;
	private String shortName;

	public CurrencyType withId(UUID id) {
		this.setId(id);
		return this;
	}

	public CurrencyType withName(String name) {
		this.setName(name);
		return this;
	}

	public CurrencyType withShortName(String shortName) {
		this.setShortName(shortName);
		return this;
	}
}
