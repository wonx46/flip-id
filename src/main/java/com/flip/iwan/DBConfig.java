package com.flip.iwan;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		  entityManagerFactoryRef = "flipEntityManagerFactory",
		  transactionManagerRef = "flipTransactionManager",
		  basePackages = { "com.flip.iwan.repository" }
		)
public class DBConfig {
	
	  @Primary
	  @Bean(name = "flipdataSource")
	  @ConfigurationProperties(prefix = "spring.datasource.flip")
	  public DataSource dataSource() {
	    return DataSourceBuilder.create().build();
	  }
	  
	  @Primary
	  @Bean(name = "flipEntityManagerFactory")
	  public LocalContainerEntityManagerFactoryBean 
	  entityManagerFactory(
	    EntityManagerFactoryBuilder builder,
	    @Qualifier("flipdataSource") DataSource dataSource
	  ) {
	    return builder
	      .dataSource(dataSource)
	      .packages("com.flip.iwan.model")
	      .persistenceUnit("flip")
	      .build();
	  }
	    
	  @Primary
	  @Bean(name = "flipTransactionManager")
	  public PlatformTransactionManager transactionManager(
	    @Qualifier("flipEntityManagerFactory") EntityManagerFactory 
	    flipentityManagerFactory
	  ) {
	    return new JpaTransactionManager(flipentityManagerFactory);
	  }
}
