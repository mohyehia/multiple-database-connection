package com.mohyehia.multipledatabaseconnection.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        basePackages = "com.mohyehia.multipledatabaseconnection.booking",
        entityManagerFactoryRef = "bookingManagerFactoryBean",
        transactionManagerRef = "bookingTransactionManager"
)
public class BookingDBConfig {
    private final Environment environment;

    @Autowired
    public BookingDBConfig(Environment environment) {
        this.environment = environment;
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.booking.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder
                .create()
                .build();
    }

    @Bean
    public PlatformTransactionManager bookingTransactionManager(){
        EntityManagerFactory managerFactory = bookingManagerFactoryBean().getObject();
        return new JpaTransactionManager(managerFactory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean bookingManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.mohyehia.multipledatabaseconnection.booking.entity");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpaProperties.put("hibernate.show-sql", environment.getProperty("spring.jpa.show-sql"));
        factoryBean.setJpaProperties(jpaProperties);
        return factoryBean;
    }
}
