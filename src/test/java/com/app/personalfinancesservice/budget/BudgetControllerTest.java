package com.app.personalfinancesservice.budget;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.app.personalfinancesservice.controller.BudgetController;
import com.app.personalfinancesservice.converters.BudgetConverter;
import com.app.personalfinancesservice.domain.budget.Budget;
import com.app.personalfinancesservice.domain.budget.input.CreateBudgetRequest;
import com.app.personalfinancesservice.domain.budget.input.GetBudgetsRequest;
import com.app.personalfinancesservice.domain.budget.input.UpdateBudgetRequest;
import com.app.personalfinancesservice.domain.budget.output.CreateBudgetResponse;
import com.app.personalfinancesservice.domain.budget.output.GetBudgetsResponse;
import com.app.personalfinancesservice.domain.budget.output.UpdateBudgetResponse;
import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.exceptions.BudgetNotFoundException;
import com.app.personalfinancesservice.exceptions.InvalidIdException;
import com.app.personalfinancesservice.exceptions.PortfolioNotFoundException;
import com.app.personalfinancesservice.service.BudgetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BudgetController.class)
@ExtendWith(MockitoExtension.class)
class BudgetControllerTest {

	private static final String REQUEST_BODY = """
			{
			    "portfolioId":"ba53ef5a-677f-4b0b-beb4-5a2b4cffe7f0",
			    "name":"Budget 3",
			    "description":"No Portfolio id test",
			    "startAt":"2025-04-01 00:00:00",
			    "endAt":"2025-05-30 00:00:00"
			}
			""";

	private static final String PORTFOLIO_LABEL = "PORTFOLIO";
	private static final String BUDGET_LABEL = "BUDGET";

	@Autowired
	private MockMvc mockMvc;
	@MockitoBean
	private BudgetService budgetService;

	@Test
	void createBudgetMissingPortfolioIdTest() throws Exception {

		when(budgetService.createBudget(any(CreateBudgetRequest.class))) //
				.thenThrow(new InvalidIdException(BUDGET_LABEL, "portfolioId", ""));

		mockMvc.perform(post(HttpRoutes.BUDGET) //
						.header("X-User-id", "ba53ef5a-677f-4b0b-beb4-5a2b4cffe7f0") //
						.contentType(MediaType.APPLICATION_JSON) //
						.content(REQUEST_BODY) //
				) //
				.andExpect(status().isBadRequest()) //
				.andExpect(jsonPath("$.error").value(BUDGET_LABEL)) //
				.andExpect(jsonPath("$.message").value(String.format("Invalid %s %s", "portfolioId", ""))) //
		;
	}

	@Test
	void createBudgetPortfolioNotFoundTest() throws Exception {

		when(budgetService.createBudget(any(CreateBudgetRequest.class))) //
				.thenThrow(new PortfolioNotFoundException(PORTFOLIO_LABEL, "portfolioId", "ba53ef5a-677f-4b0b-beb4-5a2b4cffe7f1"));

		mockMvc.perform(post(HttpRoutes.BUDGET) //
						.header("X-User-id", "ba53ef5a-677f-4b0b-beb4-5a2b4cffe7f0") //
						.contentType(MediaType.APPLICATION_JSON) //
						.content(REQUEST_BODY) //
				) //
				.andExpect(status().isBadRequest()) //
				.andExpect(jsonPath("$.error").value(PORTFOLIO_LABEL)) //
				.andExpect(jsonPath("$.message")//
						.value(String.format("Portfolio from %s %s not found", //
								"portfolioId",  //
								"ba53ef5a-677f-4b0b-beb4-5a2b4cffe7f1") //
						)) //
		;
	}

	@Test
	void createBudgetSuccess() throws Exception {

		Budget budget = new Budget() //
				.withId(UUID.randomUUID()) //
				.withUserId(UUID.randomUUID()) //
				.withPortfolioId(UUID.fromString("ba53ef5a-677f-4b0b-beb4-5a2b4cffe7f0")) //
				.withName("Test Budget") //
				.withDescription("Test Description") //
				.withCreatedAt(LocalDateTime.now()) //
				.withStartAt(LocalDateTime.now()) //
				.withEndAt(LocalDateTime.now());

		CreateBudgetResponse response = new CreateBudgetResponse().withBudget(budget);

		when(budgetService.createBudget(any(CreateBudgetRequest.class))).thenReturn(response);

		mockMvc.perform(post(HttpRoutes.BUDGET) //
						.header("X-User-id", budget.getUserId()) //
						.contentType(MediaType.APPLICATION_JSON) //
						.content(REQUEST_BODY) //
				) //
				.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.budget.id").value(budget.getId().toString())) //
				.andExpect(jsonPath("$.budget.userId").value(budget.getUserId().toString())) //
				.andExpect(jsonPath("$.budget.portfolioId").value(budget.getPortfolioId().toString())) //
				.andExpect(jsonPath("$.budget.name").value(budget.getName())) //
				.andExpect(jsonPath("$.budget.description").value(budget.getDescription())) //
				.andExpect(jsonPath("$.budget.createdAt") //
						.value(budget.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))) //
		;
	}

	@Test
	void getBudgetSuccess() throws Exception {

		UUID userId = UUID.randomUUID();

		Budget budget = new Budget() //
				.withId(UUID.randomUUID()) //
				.withUserId(userId) //
				.withPortfolioId(UUID.randomUUID()) //
				.withName("Test Budget") //
				.withDescription("Test Description") //
				;

		List<Budget> budgets = Collections.singletonList(budget);

		GetBudgetsResponse response = new GetBudgetsResponse().withBudgets(budgets);

		when(budgetService.getBudgets(any(GetBudgetsRequest.class))).thenReturn(response);

		mockMvc.perform(get(HttpRoutes.BUDGET + "/", budget.getId().toString()) //
						.header("X-User-id", userId.toString()) //
						.contentType(MediaType.APPLICATION_JSON) //
				).andExpect(status().isOk()) //
				.andExpect(jsonPath("$.budgets").exists()) //
				.andExpect(jsonPath("$.budgets").isArray()) //
				.andExpect(jsonPath("$.budgets.length()").value(1)) //
				.andExpect(jsonPath("$.budgets[0].id").value(budget.getId().toString())) //
				.andExpect(jsonPath("$.budgets[0].name").value(budget.getName())) //
				.andExpect(jsonPath("$.budgets[0].description").value(budget.getDescription())) //
				.andExpect(jsonPath("$.budgets[0].portfolioId").value(budget.getPortfolioId().toString())) //
		;
	}

	@Test
	void getBudgetsSuccess() throws Exception {

		UUID userId = UUID.randomUUID();

		Budget budget = new Budget() //
				.withId(UUID.randomUUID()) //
				.withUserId(userId) //
				.withPortfolioId(UUID.randomUUID()) //
				.withName("Test Budget") //
				.withDescription("Test Description") //
				;
		Budget budget2 = new Budget() //
				.withId(UUID.randomUUID()) //
				.withUserId(userId) //
				.withPortfolioId(UUID.randomUUID()) //
				.withName("Test Budget 2") //
				.withDescription("Test Description 2") //
				;

		List<Budget> budgets = new java.util.ArrayList<>(Collections.singletonList(budget));
		budgets.add(budget2);

		GetBudgetsResponse response = new GetBudgetsResponse().withBudgets(budgets);

		when(budgetService.getBudgets(any(GetBudgetsRequest.class))).thenReturn(response);

		mockMvc.perform(get(HttpRoutes.BUDGET + "/") //
						.header("X-User-id", userId.toString()) //
						.contentType(MediaType.APPLICATION_JSON) //
				).andExpect(status().isOk()) //
				.andExpect(jsonPath("$.budgets").exists()) //
				.andExpect(jsonPath("$.budgets").isArray()) //
				.andExpect(jsonPath("$.budgets.length()").value(2)) //
				.andExpect(jsonPath("$.budgets[1].id").value(budget2.getId().toString())) //
				.andExpect(jsonPath("$.budgets[1].name").value(budget2.getName())) //
				.andExpect(jsonPath("$.budgets[1].description").value(budget2.getDescription())) //
				.andExpect(jsonPath("$.budgets[1].portfolioId").value(budget2.getPortfolioId().toString())) //
		;
	}

	@Test
	void updateBudgetNotBudgetFound() throws Exception {

		UUID invalidBudgetId = UUID.randomUUID();

		when(budgetService.updateBudget(any(UpdateBudgetRequest.class))) //
				.thenThrow(new BudgetNotFoundException(BUDGET_LABEL, "budgetId", invalidBudgetId.toString()));

		mockMvc.perform(put(HttpRoutes.BUDGET) //
						.header("X-User-id", "ba53ef5a-677f-4b0b-beb4-5a2b4cffe7f0") //
						.contentType(MediaType.APPLICATION_JSON) //
						.content(REQUEST_BODY) //
				) //
				.andExpect(status().isBadRequest()) //
				.andExpect(jsonPath("$.error").value(BUDGET_LABEL)) //
				.andExpect(jsonPath("$.message")//
						.value(String.format("Budget from %s %s not found", //
								"budgetId",  //
								invalidBudgetId.toString()) //
						)) //
		;
	}

	@Test
	void updateBudgetSuccess() throws Exception {

		UUID budgetId = UUID.randomUUID();
		UUID userId = UUID.randomUUID();
		UUID portfolioId = UUID.randomUUID();

		UpdateBudgetRequest request = new UpdateBudgetRequest() //
				.withId(budgetId.toString()) //
				.withUserId(userId.toString()) //
				.withName("Updated Budget name") //
				.withDescription("Updated Test Description") //
				.withStartAt(LocalDateTime.parse("2025-04-01T00:00:00")) //
				.withEndAt(LocalDateTime.parse("2025-05-01T00:00:00")) //
				;

		Budget oldBudget = new Budget() //
				.withId(budgetId) //
				.withUserId(userId) //
				.withPortfolioId(portfolioId) //
				.withName("Test Budget") //
				.withDescription("Test Description") //
				.withCreatedAt(LocalDateTime.parse("2025-04-03T00:00:00")) //
				;

		Budget updateBudget = BudgetConverter.convert(request, oldBudget);

		when(budgetService.updateBudget(any(UpdateBudgetRequest.class))) //
				.thenReturn(new UpdateBudgetResponse().withBudget(updateBudget));

		mockMvc.perform(put(HttpRoutes.BUDGET) //
						.header("X-User-id", userId.toString()) //
						.contentType(MediaType.APPLICATION_JSON) //
						.content(REQUEST_BODY) //
				) //
				.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.budget.id").value(request.getId())) //
				.andExpect(jsonPath("$.budget.userId").value(request.getUserId())) //
				.andExpect(jsonPath("$.budget.portfolioId").value(portfolioId.toString())) //
				.andExpect(jsonPath("$.budget.name").value(request.getName())) //
				.andExpect(jsonPath("$.budget.description").value(request.getDescription())) //
				.andExpect(jsonPath("$.budget.createdAt") //
						.value(oldBudget.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))) //
				.andExpect(jsonPath("$.budget.startAt") //
						.value(request.getStartAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))) //
				.andExpect(jsonPath("$.budget.endAt") //
						.value(request.getEndAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))) //
		;
	}
}
