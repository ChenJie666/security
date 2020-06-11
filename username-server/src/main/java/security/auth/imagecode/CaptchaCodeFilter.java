package security.auth.imagecode;

import com.cj.security.auth.MyAuthenticationFailureHandler;
import com.cj.security.utils.MyConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: CJ
 * @Data: 2020/6/10 10:56
 */
@Component
public class CaptchaCodeFilter extends OncePerRequestFilter {

    @Resource
    MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals("/login", request.getRequestURI()) && StringUtils.equalsIgnoreCase("post", request.getMethod())) {
            try {
                validate(new ServletWebRequest(request));
            } catch (AuthenticationException e) {
                myAuthenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {

        HttpSession session = request.getRequest().getSession();

        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "captchaCode");


        if (StringUtils.isEmpty(codeInRequest)) {
            throw new SessionAuthenticationException("验证码不能为空");
        }

        CaptchaImageVO codeInSession = (CaptchaImageVO)session.getAttribute(MyConstants.CAPTCHA_SESSION_KEY);
        if (Objects.isNull(codeInSession)) {
            throw new SessionAuthenticationException("验证码不存在");
        }

        if (codeInSession.isExpired()) {
            session.removeAttribute(MyConstants.CAPTCHA_SESSION_KEY);
            throw new SessionAuthenticationException("验证码已过期");
        }

        if (!codeInRequest.equals(codeInSession.getCode())) {
            throw new SessionAuthenticationException("验证码不匹配");
        }

    }

}
