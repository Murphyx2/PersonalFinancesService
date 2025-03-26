package com.app.personalfinancesservice.domain.transaction.transactiontype;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TransactionType {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String name;

	private String description;

	public TransactionType withId(UUID id) {
		this.setId(id);
		return this;
	}

	public TransactionType withName(String name) {
		this.setName(name);
		return this;
	}

	public TransactionType withDescription(String description) {
		this.setDescription(description);
		return this;
	}
}
