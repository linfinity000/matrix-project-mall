CREATE TABLE atoms_goods (
                             ATOMS_GOODS_ID    VARCHAR(255) NOT NULL COMMENT '原子商品ID',
                             ATOMS_GOODS_NAME  VARCHAR(255) NOT NULL COMMENT '原子商品名称',
                             SALE_POINTS       VARCHAR(255) COMMENT '商品卖点',
                             DESCRIPTION       TEXT COMMENT '商品描述',
                             ATOMS_GOODS_IMAGE VARCHAR(255) COMMENT '原子商品图片',
                             SHOP_ID           VARCHAR(255) NOT NULL COMMENT '店铺ID',
                             BRAND_ID          VARCHAR(255) NOT NULL COMMENT '品牌ID',
                             CATEGORY_ID       VARCHAR(255) NOT NULL COMMENT '分类ID',
                             HAS_LOGISTICS     INT(1)       NOT NULL DEFAULT 1 COMMENT '1:有物流, 2:无物流',
                             CREATE_TIME       DATETIME     NOT NULL COMMENT '创建时间',
                             UPDATE_TIME       DATETIME     NOT NULL COMMENT '更新时间',
                             STATUS            INT(1)       NOT NULL COMMENT '状态(1:启用,0:禁用,-1:删除)',
                             PRIMARY KEY (ATOMS_GOODS_ID)
);
INSERT INTO atoms_goods (ATOMS_GOODS_ID, ATOMS_GOODS_NAME, SALE_POINTS, DESCRIPTION, ATOMS_GOODS_IMAGE, SHOP_ID,
                         BRAND_ID, CATEGORY_ID, HAS_LOGISTICS, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('1', '测试手机', '此商品为测试商品', '<a href="http://www.baidu.com">百度</a>', '', '1', '1', '1', 1, NOW(), NOW(), 1);

CREATE TABLE atoms_goods_attr_label (
                                        ID             VARCHAR(255) NOT NULL COMMENT '属性标签ID',
                                        ATOMS_GOODS_ID VARCHAR(255) NOT NULL COMMENT '原子商品ID',
                                        ATTR_NAME      VARCHAR(255) NOT NULL COMMENT '属性标签名',
                                        ORDER_BY       INT(10)      NOT NULL DEFAULT 0 COMMENT '排序',
                                        CREATE_TIME    DATETIME     NOT NULL COMMENT '创建时间',
                                        UPDATE_TIME    DATETIME     NOT NULL COMMENT '更新时间',
                                        STATUS         INT(1)       NOT NULL COMMENT '状态(1:启用,0:禁用,-1:删除)',
                                        PRIMARY KEY (ID)
);
INSERT INTO atoms_goods_attr_label (ID, ATOMS_GOODS_ID, ATTR_NAME, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('1', '1', '处理器', NOW(), NOW(), 1);

CREATE TABLE atoms_goods_sku_label (
                                       ID             VARCHAR(255) NOT NULL COMMENT 'SKU标签ID',
                                       ATOMS_GOODS_ID VARCHAR(255) NOT NULL COMMENT '原子商品ID',
                                       SKU_NAME       VARCHAR(255) NOT NULL COMMENT 'SKU标签名',
                                       ORDER_BY       INT(10)      NOT NULL DEFAULT 0 COMMENT '排序',
                                       CREATE_TIME    DATETIME     NOT NULL COMMENT '创建时间',
                                       UPDATE_TIME    DATETIME     NOT NULL COMMENT '更新时间',
                                       STATUS         INT(1)       NOT NULL COMMENT '状态(1:启用,0:禁用,-1:删除)',
                                       PRIMARY KEY (ID)
);
INSERT INTO atoms_goods_sku_label (ID, ATOMS_GOODS_ID, SKU_NAME, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('1', '1', '内存', NOW(), NOW(), 1);

CREATE TABLE atoms_goods_banner (
   ID VARCHAR(255) NOT NULL COMMENT '图片ID',
   ATOMS_GOODS_ID VARCHAR(255) NOT NULL COMMENT '原子商品ID',
   IMAGE_URL VARCHAR(255) NOT NULL COMMENT '图片URL',
   ORDER_BY INT(32) NOT NULL DEFAULT 0 COMMENT '排序',
   CREATE_TIME DATETIME NOT NULL COMMENT '创建时间',
   UPDATE_TIME DATETIME NOT NULL COMMENT '更新时间',
   STATUS INT(1) NOT NULL COMMENT '状态(1:启用,0:禁用,-1:删除)',
   PRIMARY KEY (ID)
);
INSERT INTO atoms_goods_banner (ID, ATOMS_GOODS_ID, IMAGE_URL, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('1', '1', 'http://www.baidu.com1', 0, NOW(), NOW(), 1);
INSERT INTO atoms_goods_banner (ID, ATOMS_GOODS_ID, IMAGE_URL, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('2', '1', 'http://www.baidu.com2', 0, NOW(), NOW(), 1);