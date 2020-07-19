package oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/6/13 14:49
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderApplication9600 {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication9600.class, args);
    }
}
