package com.app.personalfinancesservice.domain.portfolio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.app.personalfinancesservice.domain.budget.Budget;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Portfolio {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private UUID userId;

	private String name;

	private String description;

	@OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
	private List<Budget> budgets;

	private LocalDateTime created;

	private LocalDateTime updated;
}
