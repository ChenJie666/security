package security.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: CJ
 * @Data: 2020/6/9 10:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CommonResult<T> {

    private Integer code;
    private String message;
    private T data;

    private CommonResult(Integer code,String message){
        this(code, message, null);
    }

    public static CommonResult success(){
        return new CommonResult(200, "success");
    }

    public static CommonResult error(){
        return new CommonResult(500, "error");
    }

}
