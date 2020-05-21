package pl.longhorn.cglibtests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class CglibTestsApplication {

    public static void main(String[] arguments) {
        SpringApplication.run(CglibTestsApplication.class, arguments);
    }
}
