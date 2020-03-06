package matrix.project.mall.enums;

/**
 * @author wangcheng
 * @date 2020-03-02
 */
public enum Logistics {

    HAS_LOGISTICS(1, "有物流"),
    HAS_NO_LOGISTICS(2, "无物流");

    private Integer code;

    private String name;

    Logistics(Integer code, String name) {
        this.setCode(code);
        this.setName(name);
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
