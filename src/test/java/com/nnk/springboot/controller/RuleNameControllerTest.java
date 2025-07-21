package com.nnk.springboot.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RuleNameServiceImpl ruleNameService;

	private RuleName ruleName;

	@BeforeEach
	void setUp() {
		ruleName = new RuleName();
		ruleName.setId(1);
		ruleName.setName("Test Rule");
		ruleName.setDescription("Test Description");
		ruleName.setJson("json");
		ruleName.setTemplate("template");
		ruleName.setSqlStr("SELECT * FROM table");
		ruleName.setSqlPart("WHERE id = 1");
	}

	@WithMockUser(username = "user")
	@Test
	void testAddRuleName_Valid() throws Exception {
		mockMvc.perform(post("/ruleName/validate").param("name", "Test Rule").param("description", "Test Description")
				.param("json", "json").param("template", "template").param("sqlStr", "SELECT * FROM table")
				.param("sqlPart", "WHERE id = 1").with(csrf()).contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk()).andExpect(view().name("ruleName/list"));

		verify(ruleNameService, times(1)).saveRuleName(any(RuleName.class));
	}

	@WithMockUser(username = "user")
	@Test
	void testAddRuleName_Invalid() throws Exception {
		mockMvc.perform(post("/ruleName/validate").param("name", "").param("description", "").with(csrf())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk())
				.andExpect(view().name("ruleName/add"));

		verify(ruleNameService, never()).saveRuleName(any());
	}

	@WithMockUser(username = "user")
	@Test
	void testUpdateRuleName_Valid() throws Exception {
		mockMvc.perform(post("/ruleName/update/1").param("name", "Updated Rule").param("description", "Updated")
				.param("json", "json").param("template", "template").param("sqlStr", "SELECT *")
				.param("sqlPart", "WHERE").with(csrf()).contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/ruleName/list"));

		verify(ruleNameService, times(1)).saveRuleName(any(RuleName.class));
	}

	@WithMockUser(username = "user")
	@Test
	void testUpdateRuleName_Invalid() throws Exception {
		mockMvc.perform(post("/ruleName/update/1").param("name", "").param("description", "").with(csrf())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk())
				.andExpect(view().name("ruleName/update"));

		verify(ruleNameService, never()).saveRuleName(any());
	}

	@WithMockUser(username = "user")
	@Test
	void testDeleteRuleName_Success() throws Exception {
		when(ruleNameService.getRuleNameById(1)).thenReturn(ruleName);

		mockMvc.perform(get("/ruleName/delete/1").with(csrf())).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/ruleName/list"));

		verify(ruleNameService, times(1)).deleteRuleNameById(1);
	}
}
