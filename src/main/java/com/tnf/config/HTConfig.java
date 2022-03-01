package com.tnf.config;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Getter
@Setter
@ConfigurationProperties(prefix = "security")
@Configuration
public class HTConfig {
    private String dialect;
    private String dbop;
    private String name;
    private String url;
    private String username;
    private String password;

    @Bean
    public DataSource getDatasource() {
        DriverManagerDataSource dataSource = new
                DriverManagerDataSource(getUrl(), getUsername(), getPassword());
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", getDialect());
        properties.put("hibernate.hbm2ddl.auto", getDbop());
        //properties.put("hibernate.show_sql", "true");
        //properties.put("hibernate.format_sql", "true");
        return properties;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
        factory.setDataSource(getDatasource());
        factory.setHibernateProperties(hibernateProperties());
        factory.setPackagesToScan(new String[]{"com.tnf.entity"});
        return factory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory factory) {
        HibernateTransactionManager tx = new HibernateTransactionManager();
        tx.setSessionFactory(factory);
        return tx;
    }
}
