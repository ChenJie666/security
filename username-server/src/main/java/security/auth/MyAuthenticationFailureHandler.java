package security.auth;

import com.cj.security.utils.CommonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: CJ
 * @Data: 2020/6/9 10:42
 */
@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${spring.security.loginType}")
    private String loginType;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMsg = "用户名或密码错误";
        //首先处理校验码部分抛出的异常
        if (exception instanceof SessionAuthenticationException) {
            errorMsg = exception.getMessage();
        }
        //再处理账号密码抛出的异常
        if ("JSON".equalsIgnoreCase(loginType)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(CommonResult.error().setMessage(errorMsg)));
        } else {
            super.onAuthenticationFailure(request,response,exception);
        }
    }
}
