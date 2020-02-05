package com.mohyehia.multipledatabaseconnection.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.mohyehia.multipledatabaseconnection.user",
        entityManagerFactoryRef = "userEntityManagerFactoryBean",
        transactionManagerRef = "userTransactionManager"
)
public class UserDBConfig {

    private HibernateProperties hibernateProperties;

    @Autowired
    public UserDBConfig(HibernateProperties hibernateProperties) {
        this.hibernateProperties = hibernateProperties;
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.user.datasource")
    public DataSource userDataSource(){
        return DataSourceBuilder
                .create()
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager userTransactionManager(){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(userEntityManagerFactoryBean().getObject());
        return jpaTransactionManager;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean userEntityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(userDataSource());
        factoryBean.setPackagesToScan("com.mohyehia.multipledatabaseconnection.user");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties.getHibernateProperties());
        return factoryBean;
    }

}
