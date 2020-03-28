package matrix.project.mall.enums;

/**
 * @author wangcheng
 * @date 2020-03-28
 */
public enum PayChannel {

    ALI(0, "支付宝"),
    WE(1, "微信");

    private Integer code;

    private String name;

    PayChannel(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
