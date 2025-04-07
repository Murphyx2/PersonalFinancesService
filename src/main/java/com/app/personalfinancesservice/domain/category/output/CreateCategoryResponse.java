package com.app.personalfinancesservice.domain.category.output;

import com.app.personalfinancesservice.domain.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateCategoryResponse {

	private Category category;

	public CreateCategoryResponse withCategory(Category category) {
		this.setCategory(category);
		return this;
	}
}
