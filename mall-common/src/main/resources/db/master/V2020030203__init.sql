CREATE TABLE `order`
(
    ORDER_ID    VARCHAR(255)   NOT NULL COMMENT '订单ID',
    SHOP_ID     VARCHAR(255)   NOT NULL COMMENT '店铺ID',
    PRICE       DECIMAL(20, 2) NOT NULL COMMENT '订单金额',
    CREATE_TIME DATETIME       NOT NULL COMMENT '创建时间',
    UPDATE_TIME DATETIME       NOT NULL COMMENT '更新时间',
    STATUS      INT(1)         NOT NULL COMMENT '10:取消订单,11:等待支付,12:已支付,13:等待发货,14:已发货,15:已收货,100:订单结束',
    PRIMARY KEY (ORDER_ID)
);

CREATE TABLE order_ext
(
    ID                     VARCHAR(255) NOT NULL COMMENT '订单扩展表ID',
    ORDER_ID               VARCHAR(255) NOT NULL COMMENT '订单ID',
    LOGISTICS_COMPANY_ID   VARCHAR(255) COMMENT '物流公司ID',
    LOGISTICS_COMPANY_NAME VARCHAR(255) COMMENT '物流公司名称',
    LOGISTICS_NO           VARCHAR(255) COMMENT '物流单号',
    COUNTRY_CODE           INT(10) COMMENT '国家编码',
    COUNTRY_NAME           VARCHAR(20) COMMENT '国家名称',
    PROVINCE_CODE          INT(10) COMMENT '省份编码',
    PROVINCE_NAME          VARCHAR(20) COMMENT '省份名称',
    CITY_CODE              INT(10) COMMENT '城市编码',
    CITY_NAME              VARCHAR(20) COMMENT '城市名称',
    ADDRESS                VARCHAR(255) COMMENT '收货地址',
    REMARK                 TEXT COMMENT '订单备注',
    PRIMARY KEY (ID)
);

CREATE TABLE order_goods
(
    ID                VARCHAR(255)   NOT NULL COMMENT '订单商品表ID',
    ORDER_ID          VARCHAR(255)   NOT NULL COMMENT '订单ID',
    GOODS_ID          VARCHAR(255)   NOT NULL COMMENT '商品ID',
    GOODS_COUNT       INT(10)        NOT NULL COMMENT '商品数量',
    GOODS_TOTAL_PRICE DECIMAL(20, 2) NOT NULL COMMENT '商品总价格',
    GOODS_SECRET      TEXT COMMENT '商品密钥，不需要收货订单可能需要此密钥',
    MIRROR            TEXT           NOT NULL COMMENT '商品镜像(JSON)',
    PRIMARY KEY (ID)
);