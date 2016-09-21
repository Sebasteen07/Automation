package com.medfusion.mdvip.objects;

import java.io.FileNotFoundException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan(basePackages = "com.medfusion.mdvip")

public class PropertyLoader {
	@Bean
	public static PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() throws FileNotFoundException {

		String propName = "local.properties";
		PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
		Resource[] resourceLocations = new Resource[] {new ClassPathResource(propName)};
		p.setLocations(resourceLocations);
		return p;
	}
}
