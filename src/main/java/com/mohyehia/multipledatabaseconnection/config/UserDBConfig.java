package com.mohyehia.multipledatabaseconnection.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.mohyehia.multipledatabaseconnection.user",
        entityManagerFactoryRef = "",
        transactionManagerRef = ""
)
public class UserDBConfig {
    private Environment environment;

    @Autowired
    public UserDBConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.user.datasource")
    public DataSource userDataSource(){
        return DataSourceBuilder
                .create()
                .build();
    }

    @Bean
    public PlatformTransactionManager userTransactionManager(){
        EntityManagerFactory managerFactory = userManagerFactoryBean().getObject();
        return new JpaTransactionManager(managerFactory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean userManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(userDataSource());
        factoryBean.setPackagesToScan("com.mohyehia.multipledatabaseconnection.user.entity");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpaProperties.put("hibernate.show-sql", environment.getProperty("spring.jpa.show-sql"));
        factoryBean.setJpaProperties(jpaProperties);
        return factoryBean;
    }
}
