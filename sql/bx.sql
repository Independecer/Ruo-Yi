DROP TABLE IF EXISTS ` bx_customer_info `;
CREATE TABLE bx_customer_info
(`id`              int         NOT NULL AUTO_INCREMENT COMMENT 'id',
    `company_name` VARCHAR(100) NOT NULL COMMENT '公司名称',
    `short_name` VARCHAR(50) COMMENT '简称',
    `customer_type` VARCHAR(50) COMMENT '客户类别',
    `manager` VARCHAR(50) COMMENT '负责人',
    `salesperson` VARCHAR(50) COMMENT '业务员',
    `product_category` VARCHAR(50) COMMENT '产品类别',
    `region` VARCHAR(50) COMMENT '所属区域',
    `delivery_method` VARCHAR(50) COMMENT '送货方式',
    `contact_person` VARCHAR(50) COMMENT '联系人',
    `mobile_number` VARCHAR(20) COMMENT '手机号码',
    `delivery_address` VARCHAR(200) COMMENT '收货地址',
    `notes` TEXT COMMENT '注意事项',
    `department` VARCHAR(50) COMMENT '科室',
    `quantity` INT COMMENT '数量'
    `create_by`         varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT '' COMMENT '创建者',
    `create_time`       datetime                                                 NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`         varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT '' COMMENT '更新者',
    `update_time`       datetime                                                 NULL DEFAULT NULL COMMENT '更新时间',
    `remark`            varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '备注',
);
