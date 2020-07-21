package com.cloud.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/7/21 9:57
 */
@Component
public class AuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @SneakyThrows
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        StringBuffer requestUrl = request.getRequestURL();
        System.out.println("*****requestUrl --> " + requestUrl);

        // 获取权限信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("*****AuthFilter  authentication:" + authentication);
        if (Objects.isNull(authentication) || !(authentication instanceof OAuth2Authentication)) {
            return null;
        }
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        // 获取用户身份
        String principal = userAuthentication.getName();
        // 获取用户权限
        Collection<? extends GrantedAuthority> authoritiesList = userAuthentication.getAuthorities();
        List<String> authorities = authoritiesList.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 获取用户请求中的参数列表
        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
        Map<String, String> requestParameters = oAuth2Request.getRequestParameters();

        // 封装信息并加密
        HashMap<String, Object> jsonToken = new HashMap<>(requestParameters);
        jsonToken.put("principal", principal);
        jsonToken.put("authorities", authorities);

        JSONObject jsonObject = new JSONObject(jsonToken);
        System.out.println("*****jsonObject：" + jsonObject);
        String encodeToken = cn.hutool.core.codec.Base64.encode(jsonObject.toString());
        // 将jsonToke放入到http的header中
        ctx.addZuulRequestHeader("json-token",encodeToken);

        return null;
    }
}
