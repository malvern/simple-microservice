package zw.co.malvern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ApiGratewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGratewayApplication.class, args);
    }

}
