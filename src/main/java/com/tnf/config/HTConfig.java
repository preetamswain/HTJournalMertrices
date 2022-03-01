package com.tnf.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@PropertySource(value = {"classpath:application.yaml"})
@Configuration
public class HTConfig {
    @Value("${spring.driver-class-name}")
    private String driverClass;
    @Value("${spring.dialect}")
    private String dialect;
    @Value("${spring.db-op}")
    private String op;

    @Value("${db0.localhost.url}")
    private String localUrl;
    @Value("${db0.localhost.username}")
    private String localUserName;
    @Value("${db0.localhost.password}")
    private String localPassword;

    @Value("${db1.security.url}")
    private String securityUrl;
    @Value("${db1.security.username}")
    private String securityUsername;
    @Value("${db1.security.password}")
    private String securityPassword;

    @Value("${db2.rest.url}")
    private String restUrl;
    @Value("${db2.rest.username}")
    private String restUsername;
    @Value("${db2.rest.password}")
    private String restPassword;

    @Bean
    public DataSource getDatasource() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nPlease Select On Which Table You want to make DB operation"
                + "\n Enter 1 for Localhost "
                + "\n Enter 2 for restapi "
                + "\n Enter 3 for security ");
        int ch = Integer.parseInt(br.readLine());
        if (ch == 1) {
            System.out.println("ON LOCALHOST");
            DriverManagerDataSource dataSource = new
                    DriverManagerDataSource(localUrl, localUserName, localPassword);
            dataSource.setDriverClassName(driverClass);
            return dataSource;
        } else if (ch == 2) {
            System.out.println("ON RESTAPI");
            DriverManagerDataSource dataSource = new
                    DriverManagerDataSource(restUrl, restUsername, restPassword);
            dataSource.setDriverClassName(driverClass);
            return dataSource;
        } else if (ch == 3) {
            System.out.println("ON SECURITY");
            DriverManagerDataSource dataSource = new
                    DriverManagerDataSource(securityUrl, securityUsername, securityPassword);
            dataSource.setDriverClassName(driverClass);
            return dataSource;
        } else return null;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.hbm2ddl.auto", op);
        //properties.put("hibernate.show_sql", "true");
        //properties.put("hibernate.format_sql", "true");
        return properties;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws IOException {
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
