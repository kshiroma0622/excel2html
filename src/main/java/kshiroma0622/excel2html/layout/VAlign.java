package kshiroma0622.excel2html.layout;

/**
 * 垂直方向 アライメント
 */
public enum VAlign {
    /**
     * top
     */
    TOP,

    /**
     * middel
     */
    MIDDLE,

    /**
     * bottom
     */
    BOTTOM;

    /**
     * 取得
     *
     * @param name 名称
     * @return インスタンス
     */
    public static VAlign get(String name) {
        return Utils.getEnumByNameIgnoreCase(VAlign.class, name);
    }

}
