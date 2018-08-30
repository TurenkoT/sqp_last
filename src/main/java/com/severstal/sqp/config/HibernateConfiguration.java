package com.severstal.sqp.config;

/**
 * Hibernate configuration.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.config
 */

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.severstal.sqp.config"})
@PropertySource(value = {"classpath:application.properties"})
public class HibernateConfiguration {


    private static Logger logger = Logger.getLogger(HibernateConfiguration.class.getName());

    @Autowired
    private Environment environment;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[]{"com.severstal.sqp"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Primary
    @Bean(name = "iMySQL")
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(environment.getRequiredProperty("spring.datasource.int-mysql.driver-class-name"));
        ds.setUrl(environment.getRequiredProperty("spring.datasource.int-mysql.url"));
        ds.setUsername(environment.getRequiredProperty("spring.datasource.int-mysql.username"));
        ds.setPassword(environment.getRequiredProperty("spring.datasource.int-mysql.password"));

        return ds;
    }

    @Bean(name = "iMySQLJdbc")
    public JdbcTemplate mySQLJdbcTemplate(@Qualifier("iMySQL") DataSource tMySQL) {
        return new JdbcTemplate(tMySQL);
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }

}
