package com.app.personalfinancesservice.domain.category.output;

import com.app.personalfinancesservice.domain.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCategoryResponse {

	private Category category;

	public UpdateCategoryResponse withCategory(Category category) {
		this.setCategory(category);
		return this;
	}
}
