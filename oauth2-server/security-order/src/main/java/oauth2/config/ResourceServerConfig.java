package oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/7/15 14:57
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "res1";    //与授权中心客户端配置一致

    @Resource
    private TokenStore tokenStore;

    /**
     * 对token进行远程校验
     *
     * @return
     */
//    @Bean
//    public ResourceServerTokenServices tokenServices() {
//        RemoteTokenServices service = new RemoteTokenServices();
//        service.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
//        service.setClientId("c1");
//        service.setClientSecret("abc123");
//
//        return service;
//    }


    /**
     * 验证令牌的服务
     *
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID) //资源id
//                .tokenServices(tokenServices()) //到验证服务器验证令牌有效性
                .tokenStore(tokenStore) //对JWT令牌进行本地验证
                .stateless(true); //只允许基于令牌的身份验证
    }

    /**
     * 验证令牌的权限
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('all')")  //用户有"all"授权，可以访问所有接口
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
