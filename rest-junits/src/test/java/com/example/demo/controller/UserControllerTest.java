package com.example.demo.controller;

import com.example.demo.handler.CustomGlobalExceptionHandler;
import com.example.demo.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Locale;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
class UserControllerTest {
	@Mock
	private UserService userService;
	private MockMvc mockMvc;
	@Autowired
	private MessageSource messageSource;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		UserController userController = new UserController(userService);
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
				.setControllerAdvice(new CustomGlobalExceptionHandler())
				.build();
	}

	@Test
	void findByIdTest() throws Exception{
		this.mockMvc
				.perform(get("/users/getById/{id}", 23))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.statusCode").value(CoreMatchers.equalTo(HttpStatus.BAD_REQUEST.value())))
				.andExpect(jsonPath("$.details").value(CoreMatchers
						.hasItem(this.messageSource.getMessage("user.id.not.exist", new Integer[]{23}, Locale.ENGLISH))));
	}

}
