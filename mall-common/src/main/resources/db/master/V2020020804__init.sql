CREATE TABLE goods
(
    GOODS_ID       VARCHAR(255) NOT NULL COMMENT '商品ID',
    ATOMS_GOODS_ID VARCHAR(255) NOT NULL COMMENT '原子商品ID',
    ORIGIN_PRICE   DECIMAL(20, 2) COMMENT '原价',
    SALE_PRICE     DECIMAL(20, 2) COMMENT '销售价',
    STOCK          INT(10)      NOT NULL DEFAULT 0 COMMENT '库存',
    IMAGE_URL      VARCHAR(255) COMMENT '图片地址',
    CREATE_TIME    DATETIME     NOT NULL COMMENT '创建时间',
    UPDATE_TIME    DATETIME     NOT NULL COMMENT '更新时间',
    STATUS         INT(1)       NOT NULL COMMENT '状态(1:上架,0:下架,-1:删除)',
    PRIMARY KEY (GOODS_ID)
);
INSERT INTO goods (GOODS_ID, ATOMS_GOODS_ID, ORIGIN_PRICE, SALE_PRICE, STOCK, IMAGE_URL, CREATE_TIME, UPDATE_TIME,
                   STATUS)
VALUES ('1', '1', 100, 99, 100, 'http://www.baidu.com', NOW(), NOW(), 1);
INSERT INTO goods (GOODS_ID, ATOMS_GOODS_ID, ORIGIN_PRICE, SALE_PRICE, STOCK, IMAGE_URL, CREATE_TIME, UPDATE_TIME,
                   STATUS)
VALUES ('2', '1', 100, 98, 100, 'http://www.baidu.com', NOW(), NOW(), 1);

CREATE TABLE goods_attr
(
    ID                        VARCHAR(255) NOT NULL COMMENT '商品属性ID',
    GOODS_ID                  VARCHAR(255) NOT NULL COMMENT '商品ID',
    ATOMS_GOODS_ATTR_LABEL_ID varchar(255) NOT NULL COMMENT '商品属性标签ID',
    ATTR_VALUE                VARCHAR(255) NOT NULL DEFAULT '无' COMMENT '商品属性值',
    CREATE_TIME               DATETIME     NOT NULL COMMENT '创建时间',
    UPDATE_TIME               DATETIME     NOT NULL COMMENT '更新时间',
    STATUS                    INT(1)       NOT NULL COMMENT '状态(1:启用,0:禁用,-1:删除)',
    PRIMARY KEY (ID)
);
INSERT INTO goods_attr (ID, GOODS_ID, ATOMS_GOODS_ATTR_LABEL_ID, ATTR_VALUE, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('1', '1', '1', '骁龙850', NOW(), NOW(), 1);
INSERT INTO goods_attr (ID, GOODS_ID, ATOMS_GOODS_ATTR_LABEL_ID, ATTR_VALUE, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('2', '2', '1', '联发科555', NOW(), NOW(), 1);

CREATE TABLE goods_sku
(
    ID                       VARCHAR(255) NOT NULL COMMENT '商品SKUID',
    GOODS_ID                 VARCHAR(255) NOT NULL COMMENT '商品ID',
    ATOMS_GOODS_SKU_LABEL_ID varchar(255) NOT NULL COMMENT '商品SKU标签ID',
    SKU_VALUE                VARCHAR(255) NOT NULL DEFAULT '无' COMMENT '商品SKU值',
    CREATE_TIME              DATETIME     NOT NULL COMMENT '创建时间',
    UPDATE_TIME              DATETIME     NOT NULL COMMENT '更新时间',
    STATUS                   INT(1)       NOT NULL COMMENT '状态(1:启用,0:禁用,-1:删除)',
    PRIMARY KEY (ID)
);
INSERT INTO goods_sku (ID, GOODS_ID, ATOMS_GOODS_SKU_LABEL_ID, SKU_VALUE, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('1', '1', '1', '16G', NOW(), NOW(), 1);
INSERT INTO goods_sku (ID, GOODS_ID, ATOMS_GOODS_SKU_LABEL_ID, SKU_VALUE, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('2', '2', '1', '32G', NOW(), NOW(), 1);