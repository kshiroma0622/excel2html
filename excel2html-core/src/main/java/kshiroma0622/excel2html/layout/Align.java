package kshiroma0622.excel2html.layout;

/**
 * 水平方向のアライメント
 */
public enum Align {
    /**
     * right
     */
    RIGHT,
    /**
     * left
     */
    LEFT,
    /**
     * center
     */
    CENTER;

    /**
     * 取得
     */
    public static Align get(String name) {
        return Utils.getEnumByNameIgnoreCase(Align.class, name);
    }
}
