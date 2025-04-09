package com.app.personalfinancesservice.domain.category;

import java.time.LocalDateTime;
import java.util.UUID;

import com.app.personalfinancesservice.domain.transaction.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private UUID userId;

	@Column(nullable = false, unique = true)
	private String name;

	private TransactionType transactionType;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime createdAt;

	public Category withCreatedAt(LocalDateTime createdAt) {
		this.setCreatedAt(createdAt);
		return this;
	}

	public Category withId(UUID id) {
		this.setId(id);
		return this;
	}

	public Category withName(String name) {
		this.setName(name);
		return this;
	}

	public Category withTransactionType(TransactionType transactionType) {
		this.setTransactionType(transactionType);
		return this;
	}

	public Category withUserId(UUID userId) {
		this.setUserId(userId);
		return this;
	}
}
