/**
 * Created on Jan 4, 2012
 */
package com.apress.prospring3.ch19.web.samController;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.apress.prospring3.ch19.test.config.ControllerTestConfig;

/**
 * @author Clarence
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/datasource-tx-jpa.xml", "/jpa-app-context.xml", "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@ActiveProfiles("test")
public class AbstractControllerTest {

}
