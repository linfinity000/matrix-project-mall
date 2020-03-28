CREATE TABLE region
(
    CODE        INT(10)     NOT NULL COMMENT '地区ID',
    NAME        VARCHAR(20) NOT NULL COMMENT '地区名称',
    PARENT_CODE INT(10)     NOT NULL DEFAULT 0 COMMENT '父级CODE',
    STATUS      INT(1)      NOT NULL COMMENT '1:禁用,-1:删除',
    PRIMARY KEY (CODE)
);
INSERT INTO region (CODE, NAME, PARENT_CODE, STATUS)
VALUES (10, '江苏', 0, 1);
INSERT INTO region (CODE, NAME, PARENT_CODE, STATUS)
VALUES (1010, '苏州', 10, 1);
INSERT INTO region (CODE, NAME, PARENT_CODE, STATUS)
VALUES (101010, '吴中区', 1010, 1);
CREATE TABLE address
(
    ADDRESS_ID    VARCHAR(255) NOT NULL COMMENT '地址ID',
    ADDRESS       TEXT         NOT NULL COMMENT '地址信息',
    LINK_NAME     VARCHAR(20)  NOT NULL COMMENT '收货人',
    MOBILE        VARCHAR(20)  NOT NULL COMMENT '手机号',
    PROVINCE_CODE INT(10)      NOT NULL COMMENT '省份编码',
    PROVINCE_NAME VARCHAR(20)  NOT NULL COMMENT '省份名称',
    CITY_CODE     INT(10)      NOT NULL COMMENT '城市编码',
    CITY_NAME     VARCHAR(20)  NOT NULL COMMENT '城市名称',
    AREA_CODE     INT(10)      NOT NULL COMMENT '地区编码',
    AREA_NAME     VARCHAR(20)  NOT NULL COMMENT '地区名称',
    USER_ID       VARCHAR(255) NOT NULL COMMENT '用户ID',
    IS_DEFAULT    INT(1)       NOT NULL DEFAULT 0 COMMENT '1:默认地址,0:普通地址',
    CREATE_TIME   DATETIME     NOT NULL COMMENT '创建时间',
    UPDATE_TIME   DATETIME     NOT NULL COMMENT '更新时间',
    STATUS        INT(1)       NOT NULL COMMENT '状态',
    PRIMARY KEY (ADDRESS_ID)
);
CREATE TABLE logistics
(
    LOGISTICS_ID   VARCHAR(255) NOT NULL COMMENT '快递公司ID',
    LOGISTICS_NAME VARCHAR(255) NOT NULL COMMENT '快递公司名称',
    CREATE_TIME    DATETIME     NOT NULL COMMENT '创建时间',
    UPDATE_TIME    DATETIME     NOT NULL COMMENT '更新时间',
    STATUS         INT(1)       NOT NULL COMMENT '状态(1:启用，-1:删除)',
    PRIMARY KEY (LOGISTICS_ID)
);
INSERT INTO logistics (LOGISTICS_ID, LOGISTICS_NAME, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('1', '顺丰快递', NOW(), NOW(), 1)