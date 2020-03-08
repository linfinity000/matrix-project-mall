CREATE TABLE `order`
(
    ORDER_ID     VARCHAR(255)   NOT NULL COMMENT '订单ID',
    SHOP_ID      VARCHAR(255)   NOT NULL COMMENT '店铺ID',
    PRICE        DECIMAL(20, 2) NOT NULL COMMENT '订单金额',
    GOODS_COUNT  INT(10)        NOT NULL COMMENT '商品总数量',
    CREATE_TIME  DATETIME       NOT NULL COMMENT '创建时间',
    UPDATE_TIME  DATETIME       NOT NULL COMMENT '更新时间',
    ORDER_STATUS INT(3)         NOT NULL COMMENT '10:取消订单,11:待支付,12:等待发货(已支付),13:部分发货,14:已发货(待收货),15:已收货,100:订单结束',
    STATUS       INT(1)         NOT NULL COMMENT '1:正常(前端)，-1:删除(前端)',
    PRIMARY KEY (ORDER_ID)
);

CREATE TABLE order_ext
(
    ID            VARCHAR(255) NOT NULL COMMENT '订单扩展表ID',
    ORDER_ID      VARCHAR(255) NOT NULL COMMENT '订单ID',
    HAS_LOGISTICS INT(1)       NOT NULL DEFAULT 1 COMMENT '1:有物流, 2:无物流',
    PROVINCE_CODE INT(10) COMMENT '省份编码',
    PROVINCE_NAME VARCHAR(20) COMMENT '省份名称',
    CITY_CODE     INT(10) COMMENT '城市编码',
    CITY_NAME     VARCHAR(20) COMMENT '城市名称',
    AREA_CODE     INT(10) COMMENT '地区编码',
    AREA_NAME     VARCHAR(20) COMMENT '地区名称',
    ADDRESS       VARCHAR(255) COMMENT '收货地址',
    LINK_NAME     VARCHAR(20)  NOT NULL COMMENT '收货人',
    MOBILE        VARCHAR(20)  NOT NULL COMMENT '手机号',
    REMARK        TEXT COMMENT '订单备注',
    PRIMARY KEY (ID)
);

CREATE TABLE order_goods
(
    ID                     VARCHAR(255)   NOT NULL COMMENT '订单商品表ID',
    ORDER_ID               VARCHAR(255)   NOT NULL COMMENT '订单ID',
    HAS_LOGISTICS          INT(1)         NOT NULL DEFAULT 1 COMMENT '1:有物流, 2:无物流',
    GOODS_ID               VARCHAR(255)   NOT NULL COMMENT '商品ID',
    GOODS_COUNT            INT(10)        NOT NULL COMMENT '商品数量',
    GOODS_NAME             VARCHAR(255)   NOT NULL COMMENT '商品名称',
    GOODS_TOTAL_PRICE      DECIMAL(20, 2) NOT NULL COMMENT '商品总价格',
    LOGISTICS_COMPANY_ID   VARCHAR(255) COMMENT '物流公司ID',
    LOGISTICS_COMPANY_NAME VARCHAR(255) COMMENT '物流公司名称',
    LOGISTICS_NO           VARCHAR(255) COMMENT '物流单号',
    GOODS_SECRET           TEXT COMMENT '商品密钥，不需要收货订单可能需要此密钥',
    MIRROR                 TEXT           NOT NULL COMMENT '商品镜像(JSON)',
    PRIMARY KEY (ID)
);