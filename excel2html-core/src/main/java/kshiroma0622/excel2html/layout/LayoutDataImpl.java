package kshiroma0622.excel2html.layout;


/**
 * レイアウトデータ
 */
public class LayoutDataImpl implements LayoutData {

	/**
	 * 幅
	 */
	private int width;

	/**
	 * 高さ
	 */
	private int height;

	/**
	 * 行
	 */
	private int col = -1;
	/**
	 * 列
	 */
	private int row = -1;

	/**
	 * colspan
	 */
	private int colspan;

	/**
	 * rowspan
	 */
	private int rowspan;

	/**
	 * Getter width
	 * 
	 * @return width を戻します。
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Setter width
	 * 
	 * @param width
	 *            設定する width。
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Getter height
	 * 
	 * @return height を戻します。
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Setter height
	 * 
	 * @param height
	 *            設定する height。
	 */
	public void setHeight(int height) {
		this.height = height;
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

}
