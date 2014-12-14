package com.apress.prospring3.ch19.web.samController;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.WebApplicationContext;

import com.apress.prospring3.ch19.domain.Contact;
import com.apress.prospring3.ch19.service.ContactService;
import com.apress.prospring3.ch19.web.form.Message;

/**
 * @author Clarence
 *
 */
@WebAppConfiguration
public class ContactControllerTest extends AbstractControllerTest {

	private final List<Contact> contacts = new ArrayList<Contact>();

	private ContactService contactService;

	private MessageSource messageSource;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void initContacts() {

		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

		// Initialize list of contacts for mocked ContactService
		Contact contact = new Contact();
		contact.setId(1l);
		contact.setFirstName("Clarence");
		contact.setLastName("Ho");
		contacts.add(contact);
	}

	@Test
	public void testList() throws Exception {

		ResultActions actions = this.mockMvc.perform(get("/contacts"));
		actions.andExpect(view().name("contacts/list"));
		actions.andExpect(model().attributeExists("contacts"));
		List<Contact> allContacts = (List<Contact>)actions.andReturn().getModelAndView().getModel().get("contacts");
		
		assertEquals(12, allContacts.size());
	} 

	@Test
	public void testCreate() throws Exception { 
		ResultActions actions = this.mockMvc.perform(post("/contacts")
				.contentType(MediaType.MULTIPART_FORM_DATA)				
				.param("firstName", "sambit") 
				.param("lastName", "choudhury") 
				); 

		// Test 1
		ResultActions actions1 = this.mockMvc.perform(get("/contacts"));
		List<Contact> allContacts = (List<Contact>)actions1.andReturn().getModelAndView().getModel().get("contacts");
		assertEquals(13, allContacts.size());
	}
	
	
	@Test
	public void testCreate1() throws Exception {
		
		ResultActions actions = this.mockMvc.perform(post("/contacts")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				);
		actions.andExpect(view().name("contacts/create"));
		assertEquals("Failed saving contact", 
				((Message)actions.andReturn().getModelAndView().getModel().get("message")).getMessage());
		
	}
	
	@Test
	public void testGetUpdate() throws Exception {
		ResultActions actions = this.mockMvc.perform(get("/contacts/1?form=21"));
		actions.andExpect(view().name("contacts/update"));
	}
	
	@Test
	public void testPostUpdate() throws Exception { 
		
		ResultActions actions = this.mockMvc.perform(post("/contacts/1?form=21")
				.contentType(MediaType.MULTIPART_FORM_DATA)		
				.param("id", "1")
				.param("firstName", "sambitup") 
				.param("lastName", "choudhuryup") 
				); 

		// Test 1
		actions.andExpect(redirectedUrl("/contacts/1"));
	}
	
	@Test
	public void testPostUpdate1() throws Exception {
		
		ResultActions actions = this.mockMvc.perform(post("/contacts/1?form=21")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				);
		actions.andExpect(view().name("contacts/update")); 
		assertEquals("Failed saving contact", 
				((Message)actions.andReturn().getModelAndView().getModel().get("message")).getMessage());
		
	}
	
	@Test
	public void testDownloadPhoto() throws Exception { 
		ResultActions actions = this.mockMvc.perform(get("/photo/1")
				); 
		assertEquals("", actions.andReturn().getResponse().getContentAsString());
	}
	
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	private String getFilePathFromResources(String fileName) {
		String result = "";
		ClassLoader classLoader = getClass().getClassLoader();
		result = classLoader.getResource(fileName).getPath();
		return result;
	}
}