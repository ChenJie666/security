package oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/6/13 17:01
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //token存储方式
    @Resource
    private TokenStore tokenStore;

    //客户端详情服务
    @Resource
    private ClientDetailsService clientDetailsService;

    //授权码服务
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    //认证管理器
    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 令牌管理服务
     *
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService); //客户端详情服务
        services.setSupportRefreshToken(true); //支持刷新令牌
        services.setTokenStore(tokenStore); //令牌的存储策略
        services.setAccessTokenValiditySeconds(7200); //令牌默认有效时间2小时
        services.setRefreshTokenValiditySeconds(259200); //刷新令牌默认有效期3天
        return services;
    }

    /**
     * 设置授权码模式的授权码如何存取，暂时采用内存方式
     *
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new InMemoryAuthorizationCodeServices();
    }


    /**
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients(); //表单认证（申请令牌）
    }

    /**
     * 客户端配置
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()//使用内存存储
                .withClient("c1") //客户端id
                .secret(bCryptPasswordEncoder.encode("abc123"))//设置密码
                .resourceIds("res1")//可访问的资源列表
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")//该client允许的授权类型
                .scopes("all")//允许的授权范围
                .autoApprove(false)//false跳转到授权页面，true不跳转
                .redirectUris("http://www.baidu.com");//设置回调地址
    }

    /**
     *
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)//认证管理器
                .authorizationCodeServices(authorizationCodeServices)//授权码服务
                .tokenServices(tokenServices()) //令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

}
