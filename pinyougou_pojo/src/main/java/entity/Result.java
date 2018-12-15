package entity;

import java.io.Serializable;

/**
 * Created by ZJE on 2018/12/4
 */
public class Result implements Serializable {

    //结果
    private boolean success;
    //得到结果返回的信息
    private String message;

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
