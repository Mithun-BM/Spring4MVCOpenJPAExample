package com.example.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class WebAppInitializer implements WebApplicationInitializer {
	public void onStartup(ServletContext servletContext) throws ServletException {  
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();  
		ctx.register(AppConfig.class);

		ctx.setServletContext(servletContext);    
		ServletRegistration.Dynamic dynamic = servletContext.addServlet("employee", new DispatcherServlet(ctx));  
		dynamic.addMapping("/Spring4MVCOpenJPAExample/*");  // this does the work of deployment descriptor (i.e work of web.xml, but web.xml is required for 
																 //deployment , leave web.xml contents empty)
		dynamic.setLoadOnStartup(1);  
	}  
}
