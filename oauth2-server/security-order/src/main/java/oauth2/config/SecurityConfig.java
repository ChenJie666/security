package oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/7/15 16:10
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers("/r/contents").hasAuthority("/contents/")
//                .antMatchers("/r/users").hasAuthority("/users/")
                .antMatchers("/r/**").authenticated()//所有的/r/**的请求必须认证通过
                .anyRequest().permitAll();//除了r/**，其他请求都可以随意访问
    }

}
