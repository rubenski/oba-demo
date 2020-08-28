package nl.codebasesoftware.obademo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


/**
 * This JDBC config is almost empty, because a PlatformTransactionManager and a NamedParameterJdbcTemplate
 * are already auto-configured by Spring
 * <p>
 * See JdbcTemplateAutoConfiguration for JDBC template
 * See DataSourceTransactionManagerAutoConfiguration for the transaction manager
 */
@Configuration
public class MysqlConfig {

    @Bean
    public DataSource dataSource(ObaDemoProperties props) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(props.getDbUrl() + "/" + props.getDbName());
        dataSource.setUsername(props.getMysqlUser());
        dataSource.setPassword(props.getMysqlPassword());
        return dataSource;
    }
}
