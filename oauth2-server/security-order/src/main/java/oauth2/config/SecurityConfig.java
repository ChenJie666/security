package oauth2.config;

import oauth2.filter.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/7/15 16:10
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterBefore(tokenAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/r/contents").hasAuthority("/contents/")
                .antMatchers("/r/users").hasAuthority("/users/")
                .antMatchers("/r/**").authenticated() //所有的/r/**的请求必须认证通过
//                .anyRequest().permitAll();//除了r/**，其他请求都可以随意访问
                .antMatchers("/list").hasAuthority("ROLE_admin");
    }

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}
