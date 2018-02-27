package kshiroma0622.excel2html.layout;

/**
 * レイアウト情報
 */
public interface LayoutData {

    /**
     * 幅
     * 
     * @return 幅
     */
    public int getWidth();

    /**
     * 高さ
     * 
     * @return 高さ
     */
    public int getHeight();

    /**
     * 列Index
     * 
     * @return 列Index
     */
    public int getCol();

    /**
     * 行index
     * 
     * @return index
     */
    public int getRow();

    /**
     * 列またがり
     * 
     * @return 列またがり
     */
    public int getColspan();

    /**
     * 行またがり
     * 
     * @return 行またがり
     */
    public int getRowspan();

}
