package com.app.personalfinancesservice.domain.category.output;

import java.util.List;

import com.app.personalfinancesservice.domain.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetCategoriesResponse {

	private List<Category> categories;

	public GetCategoriesResponse withCategories(List<Category> category) {
		this.setCategories(category);
		return this;
	}
}
