package blitznihar.org.spring_boot_camel_rest_sql.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import blitznihar.org.spring_boot_camel_rest_sql.constants.ApplicationConstants;
import blitznihar.org.spring_boot_camel_rest_sql.constants.ProfileConstants;
import blitznihar.org.spring_boot_camel_rest_sql.constants.RouteConstants;
@ActiveProfiles(ProfileConstants.MOCK)
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductRouteTest extends CamelTestSupport {

	@Autowired
	CamelContext camelContext;
	
	@Autowired
	Environment environment;

	@Autowired
	ProducerTemplate producerTemplate;

	protected CamelContext createCamelContext() {
		return camelContext;
	}
	
	@Test
	@DirtiesContext
	public void testMocksAreValid() throws Exception {
		MockEndpoint mockEndpoint = getMockEndpoint(environment.getProperty(RouteConstants.PRODUCTTO));
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedBodiesReceived(ApplicationConstants.MESSAGE);
        producerTemplate.sendBodyAndHeader(environment.getProperty(RouteConstants.PRODUCTFROM),ApplicationConstants.MESSAGESENT,ProfileConstants.ENVIRONMENT,environment.getProperty(ProfileConstants.ACTIVEPROFILE));
        assertMockEndpointsSatisfied();
	}
}
