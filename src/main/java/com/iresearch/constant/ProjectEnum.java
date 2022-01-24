package com.iresearch.constant;

/**
 * @author lengqie
 */
public enum ProjectEnum {

    /**
     * ProjectEnum
     */
    DELETED(-1,"删除"),
    CREATED(0, "创建成功"),
    APPLYING(1, "申报中"),
    APPLY(2, "申报成功"),
    UNAPPLY(-2, "申报失败"),
    ENDING(3, "结课中"),
    END(4, "结课成功"),
    UNEND(-4, "结课失败"),
    ;

    private final Integer value;
    private final String explain;

    ProjectEnum(Integer value, String explain) {
        this.value = value;
        this.explain = explain;
    }

    public Integer value() {
        return this.value;
    }
    public String explain() {
        return this.explain;
    }

}
