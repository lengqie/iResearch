package com.iresearch.constant;

/**
 * @author lengqie
 */
public enum RoleEnum {

    /**
     * role
     */
    ADMIN("admin"),
    USER("user")
    ;

    private final String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
