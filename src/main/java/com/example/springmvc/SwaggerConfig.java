package com.example.springmvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

	
	 Contact contact = new Contact(
	            "Emre A",
	            "http://localhost:8080", 
	            "test@test.com"
	    );
	    
	    @SuppressWarnings("rawtypes")
		List<VendorExtension> vendorExtensions = new ArrayList<>();
		
		ApiInfo apiInfo2 = new ApiInfo(
				"SpringWebService RESTful Web Service documentation",
				"This pages documents SpringWebService RESTful Web Service endpoints", 
				"1.0",
				"http://localhost:8080/service.html", 
				contact, 
				"Apache 2.0",
				"http://www.apache.org/licenses/LICENSE-2.0", 
				vendorExtensions);

	ApiInfo apiInfo = new ApiInfo("SpringWebService RESTful Web Service documentation",
			"This pages documents SpringWebService RESTful Web Service endpoints", "1.0",
			"http://localhost:8080", contact, "Apache 2.0",
			"http://www.apache.org/licenses/LICENSE-2.0", vendorExtensions);
	
	
	 @Bean
	 public Docket apiDocket() 
	 {
		 Docket docket = new Docket(DocumentationType.SWAGGER_2)
				 .protocols(new HashSet<>(Arrays.asList("HTTP","HTTPs")))
				 .apiInfo(apiInfo)
				 .select()
				 .apis(RequestHandlerSelectors.any())
				 .paths(PathSelectors.any())
				 .build();
		 
		 return docket;
		 
	 }
	 
	
	@Bean
	 public LinkDiscoverers discoverers() 
	 {
		 List<LinkDiscoverer> plugins = new ArrayList<>();
		 plugins.add(new CollectionJsonLinkDiscoverer());
		 return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
		 
	 }
	@Bean
    public WebMvcConfigurer webMvcConfigurer()
    {
        return new WebMvcConfigurer()
        {
            @Override
            public void addResourceHandlers( ResourceHandlerRegistry registry )
            {
                registry.addResourceHandler( "swagger-ui.html" ).addResourceLocations( "classpath:/META-INF/resources/" );
                registry.addResourceHandler( "/webjars/**" ).addResourceLocations( "classpath:/META-INF/resources/webjars/" );
            }
        };
    }
	
}
