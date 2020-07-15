package oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/6/13 17:13
 */
@Configuration
public class TokenConfig {

    /**
     * 配置将令牌存储在内存中
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        //使用内存存储令牌
        return new InMemoryTokenStore();
    }

}
