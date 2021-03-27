package com.zoran.common.constant;

/**
 * @author ：zoran
 * @date ：Created in 2021/3/27 16:21
 * @description：
 * @modified By：
 * @version:
 */
public class ProductConstant {
    /**
     * @author ：zoran
     * @date ：Created in 2021/3/27 16:21
     * @description：
     * @modified By：
     * @version:
     */
    public enum AttrEnum {
        ATTR_TYPE_BASE(1,"基本属性"),ATTR_TYPE_SALE(2,"销售属性");

        private int code;
        private String msg;

        AttrEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }


        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
