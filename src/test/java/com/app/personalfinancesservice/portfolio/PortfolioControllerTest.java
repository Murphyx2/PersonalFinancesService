package com.app.personalfinancesservice.portfolio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.app.personalfinancesservice.controller.PortfolioController;
import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.domain.portfolio.input.CreatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.output.CreatePortfolioResponse;
import com.app.personalfinancesservice.exceptions.InvalidUserIdException;
import com.app.personalfinancesservice.service.PortfolioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
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
		CreatePortfolioResponse response = new CreatePortfolioResponse() //
				.withId(UUID.randomUUID()) //
				.withName("New Portfolio") //
				.withDescription("This is the description of the portfolio") //
				.withCreated(LocalDateTime.now()) //
				;
		// Configure the mock to return a success response
		when(portfolioServiceMock.createPortfolio(eq(validUserId), any(CreatePortfolioRequest.class))) //
				.thenReturn(response);

		mockMvc.perform(post(HttpRoutes.PORTFOLIO) //
						.header("X-User-id", validUserId) //
						.contentType(MediaType.APPLICATION_JSON) //
						.content(REQUEST_BODY)) //
				.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.id").value(response.getId().toString())) //
				.andExpect(jsonPath("$.name").value(response.getName())) //
				.andExpect(jsonPath("$.description").value(response.getDescription())) //
				.andExpect(jsonPath("$.created") //
						.value(response.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
	}

	@Test
	void createPortfolioInvalidUserId() throws Exception {

		//Arrange
		String exceptionLabel = "CREATE_PORTFOLIO";
		String invalidUserId = "no-uuid";

		// Configuration the mock to throw an exception for an Invalid ID
		when(portfolioServiceMock.createPortfolio(any(String.class) //
				, any(CreatePortfolioRequest.class))) //
				.thenThrow(new InvalidUserIdException(exceptionLabel, invalidUserId));

		mockMvc.perform(post(HttpRoutes.PORTFOLIO) //
						.header("X-User-id", invalidUserId) //
						.contentType(MediaType.APPLICATION_JSON) //
						.content(REQUEST_BODY)) //
				.andExpect(status().isBadRequest()) //
				.andExpect(jsonPath("$.error").value(exceptionLabel)) //
				.andExpect(jsonPath("$.message").value("Invalid User ID"))//
		;
	}

	@Test
	void createPortfolioMissingUserId() throws Exception {

		mockMvc.perform(post(HttpRoutes.PORTFOLIO) //
						.contentType(MediaType.APPLICATION_JSON) //
						.content(REQUEST_BODY)) //
				.andExpect(status().isBadRequest()) //
				.andExpect(jsonPath("$.error").value("GENERAL_ERROR")).andExpect(jsonPath("$.message").value("Required request header 'X-User-id' for method parameter type String is not present"));
	}
}
