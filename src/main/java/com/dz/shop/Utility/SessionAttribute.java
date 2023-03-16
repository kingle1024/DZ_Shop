package com.dz.shop.Utility;

public enum SessionAttribute {
    name("sessionName")
    ,userid("sessionUserId")
    ,admin("sessionIsAdmin")
    ,chat("sessionChat")
    ,isLogin("isLogin")
    ;

    private final String value;

    SessionAttribute(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
