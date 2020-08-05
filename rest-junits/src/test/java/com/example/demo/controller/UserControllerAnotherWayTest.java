package com.example.demo.controller;

import com.example.demo.model.UserDTO;
import com.example.demo.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/***
 * This way should be follow when using @Validated and custom annotation in @RequestParam and @PathVariable
 */
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerAnotherWayTest {
	@MockBean
	private UserService userService;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

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

	@Test
	public void saveUser_shouldReturnUser() throws Exception {
		UserDTO user = UserDTO.builder()
				.username("Tom")
				.age(24)
				.occupation("Developer")
				.birthday(LocalDate.of(1995, 11, 24))
				.build();

		given(userService.save(any())).willReturn(user);

		this.mockMvc
				.perform(post("/users")
						.content(mappingJackson2HttpMessageConverter.getObjectMapper().writeValueAsBytes(user))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value(CoreMatchers.equalTo(user.getUsername())))
				.andExpect(jsonPath("$.occupation").value(CoreMatchers.equalTo(user.getOccupation())))
				.andExpect(jsonPath("$.age").value(CoreMatchers.equalTo(user.getAge())))
				.andExpect(content().string(CoreMatchers.notNullValue()));
	}

}
