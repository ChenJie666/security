package security.auth;

import com.cj.security.utils.CommonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @Author: CJ
 * @Data: 2020/6/9 12:51
 */
@Component
public class MyExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    /**
     * session超时后该方法会被回调
     * @param event
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        CommonResult commonResult = CommonResult.error().setMessage("其他设别登录，当前设备已下线");
        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write(new ObjectMapper().writeValueAsString(commonResult));
    }
}
