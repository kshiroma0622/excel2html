package kshiroma0622.excel2html.matrix;

/**
 * マトリックスの範囲(正方)
 */
public class MatrixRange {

    /**
     * 行
     */
    private int row;

    /**
     * 列
     */
    private int col;

    /**
     * 行またがり
     */
    private int rowspan;

    /**
     * 列またがり
     */
    private int colspan;

    /**
     * 行列範囲
     */
    protected MatrixRange() {
        this(0, 0, 1, 1);
    }

    /**
     * マトリクス範囲
     * 
     * @param row
     *            行
     * @param col
     *            列
     * @param rowspan
     *            行
     * @param colspan
     *            列
     */
    public MatrixRange(//
            int row,// 
            int col,//
            int rowspan,//
            int colspan//
    ) {
        this.row = row;
        this.col = col;
        this.rowspan = rowspan;
        this.colspan = colspan;
    }

    /**
     * 行列
     * 
     * @param row
     *            行
     * @param col
     *            列
     */
    public MatrixRange(//
            int row,// 
            int col//
    ) {
        this(row, col, 1, 1);
    }

    /**
     * Getter row
     * 
     * @return row を戻します。
     */
    public int getRow() {
        return row;
    }

    /**
     * Setter row
     * 
     * @param row
     *            設定する row。
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Getter col
     * 
     * @return col を戻します。
     */
    public int getCol() {
        return col;
    }

    /**
     * Setter col
     * 
     * @param col
     *            設定する col。
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Getter colspan
     * 
     * @return colspan を戻します。
     */
    public int getColspan() {
        return colspan;
    }

    /**
     * Setter colspan
     * 
     * @param colspan
     *            設定する colspan。
     */
    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    /**
     * Getter rowspan
     * 
     * @return rowspan を戻します。
     */
    public int getRowspan() {
        return rowspan;
    }

    /**
     * Setter rowspan
     * 
     * @param rowspan
     *            設定する rowspan。
     */
    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    /**
     * 文字列化
     * 
     * @return 幅,高さ,rowspan,colspan
     */
    public String toString() {
        String str = String.format("{%d,%d,%d,%d}", row, col, rowspan, colspan);
        return str;
    }

}
