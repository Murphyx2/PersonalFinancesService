package com.app.personalfinancesservice.domain.category.output;

import java.util.List;

import com.app.personalfinancesservice.domain.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetCategoryResponse {

	private List<Category> category;

	public GetCategoryResponse withCategory(List<Category> category) {
		this.setCategory(category);
		return this;
	}
}
