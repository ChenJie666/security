package com.cj.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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


    /**
     * 采用formLogin方式进行认证
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //禁用csrf攻击防御
                //1.formLogin配置段
                .formLogin()
                .loginPage("/login.html")//用户访问资源时先跳转到该登录页面
                .loginProcessingUrl("/login")//登录表单中的action的地址，在该接口中进行认证
                .usernameParameter("username")//登录表单form中的用户名输入框input的name名，缺省是username
                .passwordParameter("password")//登录表单form中的密码输入框input的name名，缺省是username
                .defaultSuccessUrl("/index.html")//登录成功后默认跳转的路径
                .and()
                //2.authorizeRequests配置端
                .authorizeRequests()
                .antMatchers("/login.html","/login").permitAll() //不需要验证即可访问
                .antMatchers("/biz1","/biz2").hasAnyAuthority("ROLE_user","ROLE_admin")//user和admin权限可以访问的路径，等同于hasAnyRole("user","admin")
                .antMatchers("/syslog","/sysuser").hasAnyRole("admin")//admin角色可以访问的路径
//                .antMatchers("/syslog").hasAuthority("sys:log")//权限id，有该id的用户可以访问
//                .antMatchers("/sysuser").hasAuthority("sys:user")
                .anyRequest().authenticated();
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
}
