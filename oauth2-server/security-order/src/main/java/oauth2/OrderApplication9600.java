package oauth2;

import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan(basePackages = "oauth2.dao")
public class OrderApplication9600 {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication9600.class, args);
    }
}
