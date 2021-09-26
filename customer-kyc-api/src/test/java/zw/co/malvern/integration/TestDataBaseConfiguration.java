package zw.co.malvern.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;


public class TestDataBaseConfiguration {
 public static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:8.0.18")
            .withUsername("root")
            .withPassword("d3v3l0p3r@2021")
            .withReuse(true);



    @DynamicPropertySource
   public static void setMySQLContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",mySQLContainer ::getJdbcUrl);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
    }

}
