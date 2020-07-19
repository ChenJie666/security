package com.iotmars.filter;

import cn.hutool.core.codec.Base64;
import org.json.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AuthFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取权限信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("*****AuthFilter  authentication:" + authentication);
        if (Objects.isNull(authentication) || !(authentication instanceof OAuth2Authentication)) {
            return chain.filter(exchange);
        }
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        // 获取用户身份
        String principal = userAuthentication.getName();
        // 获取用户权限
        Collection<? extends GrantedAuthority> authoritiesList = userAuthentication.getAuthorities();
        List<String> authorities = authoritiesList.stream().map(authority -> {
            return ((GrantedAuthority) authority).getAuthority();
        }).collect(Collectors.toList());
        // 获取用户请求中的参数列表
        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
        Map<String, String> requestParameters = oAuth2Request.getRequestParameters();

        // 封装信息并加密
        HashMap<String, Object> jsonToken = new HashMap<>(requestParameters);
        jsonToken.put("principal", principal);
        jsonToken.put("authoritiest", authorities);

        JSONObject jsonObject = new JSONObject(jsonToken);
        System.out.println("*****jsonObject：" + jsonObject);
        String encodeToken = Base64.encode(jsonObject.toString());
        // 将jsonToke放入到http的header中
        ServerHttpRequest request = exchange.getRequest().mutate().header("json-token", encodeToken).build();
        ServerWebExchange newExchange = exchange.mutate().request(request).build();

        return chain.filter(newExchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
