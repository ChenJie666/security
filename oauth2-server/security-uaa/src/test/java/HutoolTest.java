import cn.hutool.json.JSONObject;
import oauth2.entities.UserInfoDTO;
import org.junit.Test;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/7/21 16:32
 */
public class HutoolTest {

    @Test
    public void test(){

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId(1).setUsername("cj").setPhone("16888888").setEmail("cj@163.com");
        JSONObject jsonObject = new JSONObject(userInfoDTO);
        String string = jsonObject.toString();
        System.out.println(string);

    }

}
