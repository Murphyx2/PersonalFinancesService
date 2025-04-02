package com.app.personalfinancesservice.portfolio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.app.personalfinancesservice.controller.PortfolioController;
import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.domain.portfolio.Portfolio;
import com.app.personalfinancesservice.domain.portfolio.input.CreatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.GetPortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.output.CreatePortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.GetPortfolioResponse;
import com.app.personalfinancesservice.exceptions.InvalidIdException;
import com.app.personalfinancesservice.service.PortfolioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PortfolioController.class)
@ExtendWith(MockitoExtension.class)
class PortfolioControllerTest {

	private static final String REQUEST_BODY = """
			{
			    "name":"New Portfolio",
			    "description":"This is the description of the portfolio"   \s
			}
			""";
	@Autowired
	private MockMvc mockMvc;
	@MockitoBean
	private PortfolioService portfolioServiceMock;

	@Test
	void CreatePortfolioSuccess() throws Exception {

		// Arrange
		String validUserId = "550e8400-e29b-41d4-a716-446655440000";
		Portfolio portfolio = new Portfolio().withId(UUID.randomUUID()) //
				.withName("New Portfolio") //
				.withDescription("This is the description of the portfolio") //
				.withCreated(LocalDateTime.now());


		CreatePortfolioResponse response = new CreatePortfolioResponse() //
				.withPortfolio(portfolio);
		// Configure the mock to return a success response
		when(portfolioServiceMock.createPortfolio(eq(validUserId), any(CreatePortfolioRequest.class))) //
				.thenReturn(response);

		mockMvc.perform(post(HttpRoutes.PORTFOLIO) //
						.header("X-User-id", validUserId) //
						.contentType(MediaType.APPLICATION_JSON) //
						.content(REQUEST_BODY)) //
				.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.portfolio.id").value(response.getPortfolio().getId().toString())) //
				.andExpect(jsonPath("$.portfolio.name").value(response.getPortfolio().getName())) //
				.andExpect(jsonPath("$.portfolio.description").value(response.getPortfolio().getDescription())) //
				.andExpect(jsonPath("$.portfolio.created") //
						.value(response.getPortfolio().getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
	}

	@Test
	void createPortfolioInvalidUserId() throws Exception {

		//Arrange
		String exceptionLabel = "PORTFOLIO";
		String invalidUserId = "no-uuid";

		// Configuration the mock to throw an exception for an Invalid ID
		when(portfolioServiceMock.createPortfolio(any(String.class) //
				, any(CreatePortfolioRequest.class))) //
				.thenThrow(new InvalidIdException(exceptionLabel, "userId", invalidUserId));

		mockMvc.perform(post(HttpRoutes.PORTFOLIO) //
						.header("X-User-id", invalidUserId) //
						.contentType(MediaType.APPLICATION_JSON) //
						.content(REQUEST_BODY)) //
				.andExpect(status().isBadRequest()) //
				.andExpect(jsonPath("$.error").value(exceptionLabel)) //
				.andExpect(jsonPath("$.message").value(String.format("Invalid %s %s","userId", invalidUserId)))//
		;
	}

	@Test
	void createPortfolioMissingUserId() throws Exception {

		mockMvc.perform(post(HttpRoutes.PORTFOLIO) //
						.contentType(MediaType.APPLICATION_JSON) //
						.content(REQUEST_BODY)) //
				.andExpect(status().isBadRequest()) //
				.andExpect(jsonPath("$.error") //
						.value("GENERAL_ERROR")) //
				.andExpect(jsonPath("$.message") //
						.value("Required request header 'X-User-id' for method parameter type String is not present"));
	}

	@Test
	void getPortfolioInvalidUserId() throws Exception {

		//Arrange
		String exceptionLabel = "PORTFOLIO";
		UUID validUserId = UUID.randomUUID();
		String invalidPortfolioId = "no-uuid";

		// Configuration the mock to throw an exception for an Invalid ID
		when(portfolioServiceMock.getPortfolio(any(GetPortfolioRequest.class))) //
				.thenThrow(new InvalidIdException(exceptionLabel,"portfolioId", invalidPortfolioId));

		mockMvc.perform(get(HttpRoutes.PORTFOLIO + "/{portfolioId}", invalidPortfolioId) //
						.contentType(MediaType.APPLICATION_JSON) //
						.header("X-User-id", validUserId.toString())) //
				.andExpect(status().isBadRequest()) //
				.andExpect(jsonPath("$.error") //
						.value(exceptionLabel)) //
				.andExpect(jsonPath("$.message") //
						.value(String.format("Invalid portfolioId %s",invalidPortfolioId)));

	}

	@Test
	void getPortfolioMissingUserId() throws Exception {

		mockMvc.perform(get(HttpRoutes.PORTFOLIO) //
						.contentType(MediaType.APPLICATION_JSON)) //
				.andExpect(status().isBadRequest()) //
				.andExpect(jsonPath("$.error") //
						.value("GENERAL_ERROR")) //
				.andExpect(jsonPath("$.message") //
						.value("Required request header 'X-User-id' for method parameter type String is not present"));
	}

	@Test
	void getPortfolioSuccess() throws Exception {

		UUID validUserId = UUID.randomUUID();
		UUID validPortfolioId = UUID.randomUUID();
		Portfolio portfolio = new Portfolio() //
				.withId(validPortfolioId) //
				.withUserId(validUserId) //
				.withBudgets(new ArrayList<>()) //
				.withName("New Portfolio") //
				.withDescription("This is the description of the portfolio");

		GetPortfolioResponse response = new GetPortfolioResponse() //
				.withPortfolio(portfolio);

		when(portfolioServiceMock.getPortfolio(any(GetPortfolioRequest.class))) //
				.thenReturn(response);

		mockMvc.perform(get(HttpRoutes.PORTFOLIO + "/{portfolioId}", validPortfolioId.toString()) //
						.contentType(MediaType.APPLICATION_JSON) //
						.header("X-User-id", validUserId.toString()) //
				) //
				.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.portfolio").exists()) //
				.andExpect(jsonPath("$.portfolio.id").value(portfolio.getId().toString())) //
				.andExpect(jsonPath("$.portfolio.userId").value(portfolio.getUserId().toString())) //
				.andExpect(jsonPath("$.portfolio.name").value(portfolio.getName())) //
				.andExpect(jsonPath("$.portfolio.description").value(portfolio.getDescription())) //
		;
	}
}
