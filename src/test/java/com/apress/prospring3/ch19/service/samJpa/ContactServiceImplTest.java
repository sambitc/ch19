package com.apress.prospring3.ch19.service.samJpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.apress.prospring3.ch19.domain.Contact;
import com.apress.prospring3.ch19.service.ContactService;

/**
 * @author Clarence
 *
 */
public class ContactServiceImplTest extends AbstractServiceImplTest {

	@Autowired
	ContactService customerService;

	@Test
	public void testFindAll() throws Exception {

		List<Contact> result = customerService.findAll();

		assertNotNull(result);
		assertEquals(12, result.size());
	}

	@Test
	public void testFindByFirstNameAndLastName_1() throws Exception {

		Contact result = customerService.findByFirstNameAndLastName("Clarence",
				"Ho");

		assertNotNull(result);
	}

	@Test
	public void testFindByFirstNameAndLastName_2() throws Exception {

		Contact result = customerService.findByFirstNameAndLastName("Peter",
				"Chan");

		assertNull(result);
	}

	@Test
	public void testAddContact() throws Exception {

		Contact contact = new Contact();
		contact.setFirstName("Rod");
		contact.setLastName("Johnson");

		contact = customerService.save(contact);
		em.flush();

		List<Contact> contacts = customerService.findAll();
		assertEquals(13, contacts.size());

	}

	@Test(expected = Exception.class)
	public void testAddContactWithJSR303Error() throws Exception {

		Contact contact = new Contact();

		contact = customerService.save(contact);
		// em.flush();
		//
		// List<Contact> contacts = customerService.findAll();
		// assertEquals(0, contacts.size());

	}

}