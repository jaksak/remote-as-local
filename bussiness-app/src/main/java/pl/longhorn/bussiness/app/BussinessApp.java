package pl.longhorn.bussiness.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class BussinessApp {

    public static void main(String[] arguments) {
        SpringApplication.run(BussinessApp.class, arguments);
    }
}
