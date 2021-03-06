CREATE TABLE order_pay_group
(
    ID          VARCHAR(255)   NOT NULL COMMENT '合并支付ID',
    ORDER_IDS   TEXT           NOT NULL COMMENT '订单集合（,号分隔）',
    SHOP_IDS    TEXT           NOT NULL COMMENT '店铺集合(,号分隔)',
    PRICE       DECIMAL(20, 2) NOT NULL COMMENT '订单总价',
    PAY_MODE    VARCHAR(20)    NOT NULL COMMENT '支付方式',
    PAY_CHANNEL INT(10)        NOT NULL COMMENT '支付渠道(0:支付宝,1:微信)',
    USER_ID     VARCHAR(255)   NOT NULL COMMENT '用户ID',
    CREATE_TIME DATETIME       NOT NULL COMMENT '创建时间',
    UPDATE_TIME DATETIME       NOT NULL COMMENT '更新时间',
    STATUS      INT(1)         NOT NULL COMMENT '状态(1:支付成功,0:等待支付,-1:取消支付)',
    PRIMARY KEY (ID)
)