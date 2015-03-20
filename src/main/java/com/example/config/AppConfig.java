package com.example.config;  

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.dao.EmployeeDAO;
import com.example.dao.impl.EmployeeDaoImpl;


@Configuration 
@EnableWebMvc //Enables to use Spring's annotations in the code//This annotation is optional   
@ComponentScan("com.example.controller")//This is equivalent to <context:component-scan base-package="com.example.controller" />
@EnableTransactionManagement//Enables Spring's annotation-driven transaction management capability. This is equivalent to <tx:annotation-driven /> 
public class AppConfig {  

	@Bean(name = "employeeDAO") 
	public EmployeeDAO getEmployeeDao() {  
		return new EmployeeDaoImpl();  
	}

	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/Spring4MVCOpenJPAdb");
		dataSource.setUsername("postgres");
		dataSource.setPassword("pass@123");
		return dataSource;
	}

	//EntityManagerFactory that brings together the persistence unit, datasource, and JPA Vendor
	@Bean(name = "emf")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		//em.setPackagesToScan(new String[] { "com.example.entity.Employee" }); //Usually JPA defines a persistence unit through the META-INF/persistence.xml file. 
		//Starting with Spring 3.1, the persistence.xml is no longer necessary – the LocalContainerEntityManagerFactoryBean now supports a ‘packagesToScan’ property
		//where the packages to scan for @Entity classes can be specified.
		em.setPersistenceXmlLocation("/WEB-INF/persistence.xml"); //i.e this line is not required
		em.setPersistenceUnitName("Spring4MVCOpenJPAdb");

		JpaVendorAdapter vendorAdapter = new OpenJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em;
	}

	//JPA Transaction Management  
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("openjpa.jdbc.SynchronizeMappings", "buildSchema(ForeignKeys=true)");
		properties.setProperty("openjpa.Log", "SQL=TRACE");
		properties.setProperty("openjpa.RuntimeUnenhancedClasses", "supported");
		//properties.setProperty("hibernate.", "");
		return properties;
	}
}  
