CREATE TABLE menu
(
    MENU_ID     VARCHAR(255) NOT NULL COMMENT '菜单ID',
    MENU_NAME   VARCHAR(255) NOT NULL COMMENT '菜单名称',
    IS_DEFAULT  INT(1)       NOT NULL DEFAULT 0 COMMENT '默认菜单项(不允许删除)',
    URL         VARCHAR(255) COMMENT '菜单链接',
    PARENT_ID   VARCHAR(255) NOT NULL DEFAULT '0' COMMENT '父级菜单ID(0为无父级)',
    TYPE        VARCHAR(20)  NOT NULL DEFAULT 'Operation' COMMENT '菜单类型(Admin: 管理员菜单, Operation: 运营人员菜单)',
    ORDER_BY    INT(10)      NOT NULL DEFAULT 0 COMMENT '',
    CREATE_TIME DATETIME     NOT NULL COMMENT '创建时间',
    UPDATE_TIME DATETIME     NOT NULL COMMENT '更新时间',
    STATUS      INT(1)       NOT NULL COMMENT '状态(1:启用,0:禁用,-1:删除)',
    PRIMARY KEY (MENU_ID)
);
INSERT INTO menu (MENU_ID, MENU_NAME, IS_DEFAULT, URL, PARENT_ID, TYPE, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('1', '系统设置', 1, '', '0', 'Admin', '10000', NOW(), NOW(), 1);
INSERT INTO menu (MENU_ID, MENU_NAME, IS_DEFAULT, URL, PARENT_ID, TYPE, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('10', '菜单管理', 1, 'Menu', '1', 'Admin', '1', NOW(), NOW(), 1);
INSERT INTO menu (MENU_ID, MENU_NAME, IS_DEFAULT, URL, PARENT_ID, TYPE, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)

VALUES ('2', '用户管理', 1, '', '0', 'Operation', '9999', NOW(), NOW(), 1);
INSERT INTO menu (MENU_ID, MENU_NAME, IS_DEFAULT, URL, PARENT_ID, TYPE, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('20', '运营人员列表', 1, 'OperationUser', '2', 'Admin', '1', NOW(), NOW(), 1);
INSERT INTO menu (MENU_ID, MENU_NAME, IS_DEFAULT, URL, PARENT_ID, TYPE, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('21', '用户列表', 1, 'User', '2', 'Admin', '2', NOW(), NOW(), 1);
INSERT INTO menu (MENU_ID, MENU_NAME, IS_DEFAULT, URL, PARENT_ID, TYPE, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('22', '当前用户信息管理', 1, 'OperationUserInfo', '2', 'Operation', '3', NOW(), NOW(), 1);

INSERT INTO menu (MENU_ID, MENU_NAME, IS_DEFAULT, URL, PARENT_ID, TYPE, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('3', '店铺管理', 1, '', '0', 'Operation', '9998', NOW(), NOW(), 1);
INSERT INTO menu (MENU_ID, MENU_NAME, IS_DEFAULT, URL, PARENT_ID, TYPE, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('30', '店铺列表', 1, 'Shop', '3', 'Admin', '1', NOW(), NOW(), 1);
INSERT INTO menu (MENU_ID, MENU_NAME, IS_DEFAULT, URL, PARENT_ID, TYPE, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('31', '当前店铺信息管理', 1, 'ShopInfo', '3', 'Operation', '2', NOW(), NOW(), 1);

INSERT INTO menu (MENU_ID, MENU_NAME, IS_DEFAULT, URL, PARENT_ID, TYPE, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('4', '订单管理', 1, '', '0', 'Operation', '9997', NOW(), NOW(), 1);

INSERT INTO menu (MENU_ID, MENU_NAME, IS_DEFAULT, URL, PARENT_ID, TYPE, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('5', '商品管理', 1, '', '0', 'Operation', '9996', NOW(), NOW(), 1);
INSERT INTO menu (MENU_ID, MENU_NAME, IS_DEFAULT, URL, PARENT_ID, TYPE, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('50', '分类管理', 1, 'Category', '5', 'Operation', '1', NOW(), NOW(), 1);
INSERT INTO menu (MENU_ID, MENU_NAME, IS_DEFAULT, URL, PARENT_ID, TYPE, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('51', '品牌管理', 1, 'Brand', '5', 'Operation', '2', NOW(), NOW(), 1);
INSERT INTO menu (MENU_ID, MENU_NAME, IS_DEFAULT, URL, PARENT_ID, TYPE, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('52', '原子商品维护', 1, 'AtomsGoods', '5', 'Operation', '3', NOW(), NOW(), 1);
INSERT INTO menu (MENU_ID, MENU_NAME, IS_DEFAULT, URL, PARENT_ID, TYPE, ORDER_BY, CREATE_TIME, UPDATE_TIME, STATUS)
VALUES ('53', '商品维护', 1, 'Goods', '5', 'Operation', '3', NOW(), NOW(), 1);