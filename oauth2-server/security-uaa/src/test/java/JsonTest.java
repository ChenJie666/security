import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;

public class JsonTest {

    @Test
    public void test() throws JSONException {
        HashMap<Object, Object> jsonToken = new HashMap<>();
        jsonToken.put("a", "1");
        jsonToken.put("b", "2");
        JSONObject jsonObject = new JSONObject(jsonToken);
        String s = jsonObject.toString();
        System.out.println(s);

        JSONObject json = new JSONObject(s);
        System.out.println(json);

    }

}
