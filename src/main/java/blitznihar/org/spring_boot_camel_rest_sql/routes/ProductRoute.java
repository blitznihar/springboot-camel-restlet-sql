package blitznihar.org.spring_boot_camel_rest_sql.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import blitznihar.org.spring_boot_camel_rest_sql.constants.ApplicationConstants;
import blitznihar.org.spring_boot_camel_rest_sql.constants.RouteConstants;

@Component
public class ProductRoute extends RouteBuilder {

    @Autowired
    Environment environment;
    
	
	@Override
	public void configure() throws Exception {
		from("{{product.route.from}}").routeId(RouteConstants.PRODUCTROUTENAME)
			.transform().simple("ref:myBean")
			.to("{{product.route.to}}");
	}
	@Bean
	String myBean() {
		return ApplicationConstants.MESSAGE;
	}
}
