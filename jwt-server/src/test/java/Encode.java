import com.cj.security.MainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: CJ
 * @Data: 2020/6/11 15:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class Encode {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void encode(){
        String password = bCryptPasswordEncoder.encode("abc123");
        System.out.println(password);
    }

}
