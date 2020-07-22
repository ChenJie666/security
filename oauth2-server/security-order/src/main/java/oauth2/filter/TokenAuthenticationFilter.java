package oauth2.filter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("*****进入Order的Filter中");
        // 解析出header中的token
        String encodeToken = httpServletRequest.getHeader("json-token");
        if (encodeToken != null) {
            String token = Base64.decodeStr(encodeToken);
            JSONObject jsonObject = new JSONObject(token);
            System.out.println(jsonObject.toString());
            // 用户信息
            String principal = jsonObject.getString("principal");
            // 用户权限
            JSONArray authoritiesArray = jsonObject.getJSONArray("authorities");
            String authorities = authoritiesArray.join(",");

            System.out.println("*****UserInfo:" + principal + "--" + authorities);
            // 将用户信息和权限填充到UsernamePasswordAuthenticationToken对象中
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

            System.out.println("*****grantedAuthorities:" + grantedAuthorities);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, null, grantedAuthorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            // 将authenticationToken填充到安全上下文中
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // 继续执行下一个过滤器
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
