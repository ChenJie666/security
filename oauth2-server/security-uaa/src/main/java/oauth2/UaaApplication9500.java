package oauth2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/6/13 14:49
 */
@SpringBootApplication
@MapperScan(basePackages = "oauth2.dao")
public class UaaApplication9500 {
    public static void main(String[] args) {
        SpringApplication.run(UaaApplication9500.class, args);
    }
}