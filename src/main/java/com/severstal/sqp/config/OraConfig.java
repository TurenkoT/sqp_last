package com.severstal.sqp.config;

/**
 * Upload dao implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.dao.impl
 */

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class OraConfig {

    @Bean(name = "iOracle")
    @ConfigurationProperties(prefix = "spring.datasource.int-oracle")
    public DataSource heOracleDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "iOracleJdbc")
    public JdbcTemplate jdbcTemplate(@Qualifier("iOracle") DataSource tOracle) {
        return new JdbcTemplate(tOracle);
    }
}
