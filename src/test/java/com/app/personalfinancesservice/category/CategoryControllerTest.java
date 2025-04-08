package com.app.personalfinancesservice.category;

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
import com.app.personalfinancesservice.domain.category.output.CreateCategoryResponse;
import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.domain.transaction.TransactionType;
import com.app.personalfinancesservice.exceptions.CreateNewItemException;
import com.app.personalfinancesservice.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
	@Autowired
	MockMvc mockMvc;
	@MockitoBean
	private CategoryService categoryService;

	private static final String CATEGORY_LABEL = "CATEGORY";

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

		mockMvc.perform(post(HttpRoutes.CATEGORY) //
						.contentType(MediaType.APPLICATION_JSON) //
						.header("X-User-id", userId.toString()).content(REQUEST_BODY) //
				).andExpect(status().isOk()) //
				.andExpect(jsonPath("$.category").exists()) //
				.andExpect(jsonPath("$.category.name").value(request.getName())) //
				.andExpect(jsonPath("$.category.transactionType").value(request.getTransactionType().toString())) //
				.andExpect(jsonPath("$.category.userId").value(userId.toString())) //
				.andExpect(jsonPath("$.category.createdAt").exists()) //
		;
	}

	@Test
	void CreateCategoryFailure() throws Exception {
		UUID userId = UUID.randomUUID();

		when(categoryService.createCategory(any(CreateCategoryRequest.class))) //
				.thenThrow(new CreateNewItemException("CREATE_NEW", "", CATEGORY_LABEL));

		mockMvc.perform(post(HttpRoutes.CATEGORY) //
						.contentType(MediaType.APPLICATION_JSON) //
						.header("X-User-id", userId.toString()).content(REQUEST_BODY) //
				).andExpect(status().isBadRequest()) //
				.andExpect(jsonPath("$.error").value("CREATE_NEW")) //
				.andExpect(jsonPath("$.message").value(String.format("Error creating %s", CATEGORY_LABEL)))
		;
	}



}
