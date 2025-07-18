package com.digis01.DGarciaProgramacionNCapasMayo25Maven.Configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {
    
    @Bean // terminar de configurar, configuraciones personalizadas
    public DataSource dataSource(){
        
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        dataSource.setUsername("JSanchezProgramacionNCapasMarzo25");
        dataSource.setPassword("password1");
        
        return dataSource;
        
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
