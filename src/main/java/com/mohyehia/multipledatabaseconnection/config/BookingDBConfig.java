package com.mohyehia.multipledatabaseconnection.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.mohyehia.multipledatabaseconnection.booking",
        entityManagerFactoryRef = "bookingEntityManagerFactoryBean",
        transactionManagerRef = "bookingTransactionManager"
)
public class BookingDBConfig {
    private HibernateProperties hibernateProperties;

    @Autowired
    public BookingDBConfig(HibernateProperties hibernateProperties) {
        this.hibernateProperties = hibernateProperties;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.booking.datasource")
    public DataSource bookingDataSource(){
        return DataSourceBuilder
                .create()
                .build();
    }

    @Bean
    public PlatformTransactionManager bookingTransactionManager(){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(bookingEntityManagerFactoryBean().getObject());
        return jpaTransactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean bookingEntityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(bookingDataSource());
        factoryBean.setPackagesToScan("com.mohyehia.multipledatabaseconnection.booking");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties.getHibernateProperties());
        return factoryBean;
    }

}
