package com.tnf.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class HTConfig {

    @Autowired
    DataFields fields;

    @Bean
    public DataSource getDatasource() {
        DriverManagerDataSource dataSource = new
                DriverManagerDataSource(fields.getUrl(), fields.getUsername(), fields.getPassword());
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", fields.getDialect());
        properties.put("hibernate.hbm2ddl.auto", fields.getDbop());
        //properties.put("hibernate.show_sql", "true");
        //properties.put("hibernate.format_sql", "true");
        return properties;
    }

    @Bean
    public LocalSessionFactoryBean localSessionFactory() {
        LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
        factory.setDataSource(getDatasource());
        factory.setHibernateProperties(hibernateProperties());
        factory.setPackagesToScan(new String[]{"com.tnf.entity"});
        return factory;
    }

    @Bean
    public HibernateTemplate hibernateTemplate(SessionFactory factory) {
        return new HibernateTemplate(factory);
    }

    @Bean
    @Autowired
    public HibernateTransactionManager hibernateTransactionManager(SessionFactory factory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(factory);
        return transactionManager;
    }
}