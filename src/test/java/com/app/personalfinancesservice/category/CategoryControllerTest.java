package com.app.personalfinancesservice.category;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.app.personalfinancesservice.controller.CategoryController;
import com.app.personalfinancesservice.converters.CategoryConverter;
import com.app.personalfinancesservice.domain.category.Category;
import com.app.personalfinancesservice.domain.category.input.CreateCategoryRequest;
import com.app.personalfinancesservice.domain.category.input.GetCategoriesRequest;
import com.app.personalfinancesservice.domain.category.output.CreateCategoryResponse;
import com.app.personalfinancesservice.domain.category.output.GetCategoriesResponse;
import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.domain.transaction.TransactionType;
import com.app.personalfinancesservice.exceptions.CreateNewItemException;
import com.app.personalfinancesservice.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

	private static final String REQUEST_BODY = """
			{
			    "name":"Telecommunications",
			    "transactionType": "EXPENSE"
			}
			""";
	private static final String CATEGORY_LABEL = "CATEGORY";
	@Autowired
	MockMvc mockMvc;
	@MockitoBean
	private CategoryService categoryService;

	@Test
	void CreateCategoryCategoryAlreadyExists() throws Exception {
		UUID userId = UUID.randomUUID();

		when(categoryService.createCategory(any(CreateCategoryRequest.class))) //
				.thenThrow(new CreateNewItemException("category", String.format("Category: %s already exists", "Test"), CATEGORY_LABEL));

		mockMvc.perform(post(HttpRoutes.API_ROOT + HttpRoutes.CATEGORY) //
						.contentType(MediaType.APPLICATION_JSON) //
						.header("X-User-id", userId.toString()).content(REQUEST_BODY) //
				).andExpect(status().isBadRequest()) //
				.andExpect(jsonPath("$.error").value(CATEGORY_LABEL)) //
				.andExpect(jsonPath("$.message").value(String.format("Error creating category, Category: %s already exists", "Test")));
	}

	@Test
	void CreateCategoryFailure() throws Exception {
		UUID userId = UUID.randomUUID();

		when(categoryService.createCategory(any(CreateCategoryRequest.class))) //
				.thenThrow(new CreateNewItemException("new category",  //
						String.format("Could not save %s", "Test") //
						, CATEGORY_LABEL));

		mockMvc.perform(post(HttpRoutes.API_ROOT + HttpRoutes.CATEGORY) //
						.contentType(MediaType.APPLICATION_JSON) //
						.header("X-User-id", userId.toString()).content(REQUEST_BODY) //
				).andExpect(status().isBadRequest()) //
				.andExpect(jsonPath("$.error").value(CATEGORY_LABEL)) //
				.andExpect(jsonPath("$.message").value(String.format("Error creating new category, Could not save %s", "Test")));
	}

	@Test
	void CreateCategorySuccess() throws Exception {
		UUID userId = UUID.randomUUID();
		CreateCategoryRequest request = new CreateCategoryRequest() //
				.withName("Telecommunications") //
				.withTransactionType(TransactionType.EXPENSE) //
				.withUserId(userId.toString());

		Category category = CategoryConverter.convert(request) //
				.withId(userId);

		CreateCategoryResponse response = new CreateCategoryResponse().withCategory(category);

		when(categoryService.createCategory(any(CreateCategoryRequest.class))).thenReturn(response);

		mockMvc.perform(post(HttpRoutes.API_ROOT + HttpRoutes.CATEGORY) //
						.contentType(MediaType.APPLICATION_JSON) //
						.header("X-User-id", userId.toString()).content(REQUEST_BODY) //
				).andExpect(status().isOk()) //
				.andExpect(jsonPath("$.category").exists()) //
				.andExpect(jsonPath("$.category.name").value(request.getName().toUpperCase())) //
				.andExpect(jsonPath("$.category.transactionType").value(request.getTransactionType().toString())) //
				.andExpect(jsonPath("$.category.userId").value(userId.toString())) //
				.andExpect(jsonPath("$.category.createdAt").exists()) //
		;
	}

	@Test
	void getCategoriesSuccess() throws Exception {
		UUID userId = UUID.randomUUID();
		UUID id = UUID.randomUUID();

		Category category = new Category() //
				.withId(id) //
				.withUserId(userId) //
				.withName("Telecommunications") //
				.withTransactionType(TransactionType.EXPENSE) //
				.withCreatedAt(LocalDateTime.now());
		Category category2 = new Category() //
				.withId(id) //
				.withUserId(userId) //
				.withName("Utilities") //
				.withTransactionType(TransactionType.EXPENSE) //
				.withCreatedAt(LocalDateTime.now());
		List<Category> categories = new ArrayList<>();
		categories.add(category);
		categories.add(category2);

		when(categoryService.getCategories(any(GetCategoriesRequest.class))) //
				.thenReturn(new GetCategoriesResponse().withCategories(categories));

		mockMvc.perform(get(HttpRoutes.API_ROOT + HttpRoutes.CATEGORY + "/{id}", id.toString()) //
						.contentType(MediaType.APPLICATION_JSON) //
						.header("X-User-id", userId.toString())) //
				.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.category").exists()) //
				.andExpect(jsonPath("$.category").isArray()) //
				.andExpect(jsonPath("$.category[0].name").value(category.getName())) //
				.andExpect(jsonPath("$.category[0].transactionType").value(category.getTransactionType().toString())) //
				.andExpect(jsonPath("$.category[0].createdAt").exists()) //
		;
	}

}

