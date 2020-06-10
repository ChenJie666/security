package security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: CJ
 * @Data: 2020/6/8 16:30
 */
@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class})*/
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
