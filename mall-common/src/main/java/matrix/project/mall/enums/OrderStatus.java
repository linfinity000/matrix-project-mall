package matrix.project.mall.enums;

/**
 * @author wangcheng
 * @date 2020-03-02
 */
public enum OrderStatus {

    CANCEL_ORDER(10, "取消订单"),
    WAIT_PAYING(11, "待支付"),
    WAIT_SHIPPING(12, "等待发货(已支付)"),
    PART_SHIP(13, "部分发货"),
    SHIPPED(14, "已发货(待收货)"),
    RECEIPTED(15, "已收货"),
    ORDER_END(100, "订单结束");

    private Integer code;

    private String name;

    OrderStatus(Integer code, String name) {
        this.setCode(code);
        this.setName(name);
    }

    public static String getNameByCode(Integer code) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.code.equals(code)) {
                return orderStatus.getName();
            }
        }
        return null;
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
