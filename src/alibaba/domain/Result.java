package alibaba.domain;

import java.util.List;

public class Result {
    //状态码
    private Integer code;
    //状态码的描述信息
    private String msg;
    //返回的数据
    private List<Trade> list;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Trade> getList() {
        return list;
    }

    public void setList(List<Trade> list) {
        this.list = list;
    }
}
