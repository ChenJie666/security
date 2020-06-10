package security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import security.auth.MyAuthenticationFailureHandler;
import security.auth.MyAuthenticationSuccessHandler;
import security.auth.MyExpiredSessionStrategy;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Author: CJ
 * @Data: 2020/6/8 16:58
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 采用httpbasic方式进行认证
     * @param http
     * @throws Exception
     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic()//开启httpbasic认证
//        .and()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated();//所有请求都需要登录认证
//    }

    @Resource
    private MyAuthenticationSuccessHandler mySuthenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    /**
     * 采用formLogin方式进行认证
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //退出登录
                .logout()
                .logoutUrl("/logout") //退出登录的请求接口
                .logoutSuccessUrl("/login.html") //退出登录后跳转的路径
                .deleteCookies("JSESSIONID") //退出时删除浏览器中的cookie
                .and()
                //自动登录
                .rememberMe()
                .rememberMeParameter("remember-me-new")
                .rememberMeCookieName("remember-me-cookie")
                .tokenValiditySeconds(60*60*24*2)
                .tokenRepository(persistentTokenRepository())
                .and()
                //禁用csrf攻击防御
                .csrf().disable()
                //authorizeRequests配置端
                .authorizeRequests()
                .antMatchers("/login.html", "/login","/kaptcha").permitAll() //不需要验证即可访问
                .antMatchers("/biz1", "/biz2").hasAnyAuthority("ROLE_user", "ROLE_admin")//user和admin权限可以访问的路径，等同于hasAnyRole("user","admin")
//                .antMatchers("/syslog","/sysuser").hasAnyRole("admin")//admin角色可以访问的路径
                .antMatchers("/syslog").hasAuthority("sys:log")//权限id，有该id的用户可以访问
                .antMatchers("/sysuser").hasAuthority("sys:user")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/login.html")
                .sessionFixation().migrateSession()
                .maximumSessions(1) //最大登录数为1
                .maxSessionsPreventsLogin(false)//false表示允许再次登录但会踢出之前的登陆；true表示不允许再次登录
                .expiredSessionStrategy(new MyExpiredSessionStrategy());//会话过期后进行的自定义操作
    }

    /**
     * 将角色信息存储在内存中
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(bCryptPasswordEncoder().encode("abc123"))
                .roles("user")
                .and()
                .withUser("admin")
                .password(bCryptPasswordEncoder().encode("abc123"))
//                .authorities("sys:log","sys:user")
                .roles("admin")
                .and()
                .passwordEncoder(bCryptPasswordEncoder());//配置BCrypt加密
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 静态资源访问不需要鉴权
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/fonts/**", "img/**", "js/**");
    }


    @Resource
    private DataSource dataSource;

    /**
     * 将数据库连接封装到框架中
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);

        return tokenRepository;
    }

}
