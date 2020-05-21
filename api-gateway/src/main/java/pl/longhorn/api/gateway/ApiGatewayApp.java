package pl.longhorn.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class ApiGatewayApp {

    public static void main(String[] arguments) {
        SpringApplication.run(ApiGatewayApp.class, arguments);
    }
}
