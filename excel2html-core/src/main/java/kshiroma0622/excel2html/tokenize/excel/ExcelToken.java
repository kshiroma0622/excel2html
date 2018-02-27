package kshiroma0622.excel2html.tokenize.excel;

import java.awt.Color;

import kshiroma0622.excel2html.layout.Align;
import kshiroma0622.excel2html.layout.VAlign;
import kshiroma0622.excel2html.layout.Utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import kshiroma0622.excel2html.tokenize.IToken;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.tokenize.Border;
import kshiroma0622.excel2html.util.SpreadSheetUtil;

public class ExcelToken implements IToken {

	private static final int SCALE = 32;

	private final Cell cell;

	// private Align align;
	private int colIndex;
	private int rowIndex;
	// private int width;
	// private int height;
	private Color color;

	private ITokenMatrix matrix;

	public ExcelToken(Cell cell, Cell startCell) {
		this.cell = cell;
		rowIndex = cell.getRowIndex() - startCell.getRowIndex();
		colIndex = cell.getColumnIndex() - startCell.getColumnIndex();
	}

	// public static final short ALIGN_GENERAL = 0;
	// public static final short ALIGN_LEFT = 1;
	// public static final short ALIGN_CENTER = 2;
	// public static final short ALIGN_RIGHT = 3;
	// public static final short ALIGN_FILL = 4;
	// public static final short ALIGN_JUSTIFY = 5;
	// public static final short ALIGN_CENTER_SELECTION = 6;

	public Align getAlign() {
		short align = cell.getCellStyle().getAlignment();
		switch (align) {
		case CellStyle.ALIGN_GENERAL:
		case CellStyle.ALIGN_LEFT:
			return Align.LEFT;
		case CellStyle.ALIGN_RIGHT:
			return Align.RIGHT;
		case CellStyle.ALIGN_CENTER:
		case CellStyle.ALIGN_CENTER_SELECTION:
		case CellStyle.ALIGN_FILL:
		case CellStyle.ALIGN_JUSTIFY:
			return Align.CENTER;
		}

		return null;
	}

	public int getColIndex() {
		return colIndex;
	}

	public short getColor() {
		short color = cell.getCellStyle().getFillForegroundColor();
		return color;
	}

	public int getHeight() {
		int i = cell.getRowIndex();
		int h = cell.getSheet().getRow(i).getHeight();
		h = h / SCALE;
		return h;
	}

	public String getInnerText() {
		return SpreadSheetUtil.getValueAsString(cell);
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public VAlign getVAlign() {
		short align = cell.getCellStyle().getAlignment();
		switch (align) {
		case CellStyle.VERTICAL_TOP:
			return VAlign.TOP;
		case CellStyle.VERTICAL_CENTER:
			return VAlign.MIDDLE;
		case CellStyle.VERTICAL_BOTTOM:
			return VAlign.BOTTOM;
		case CellStyle.VERTICAL_JUSTIFY:
		}
		return null;
	}

	public int getWidth() {
		int i = cell.getColumnIndex();
		int width = cell.getSheet().getColumnWidth(i);
		width = width / SCALE;
		return width;
	}

	Object topBorder = Utils.IN_VALID;
	Object bottomBorder = Utils.IN_VALID;
	Object leftBorder = Utils.IN_VALID;
	Object rightBorder = Utils.IN_VALID;

	public Border getBottomBorder() {// 循環にならない工夫が必要。
		if (bottomBorder != Utils.IN_VALID) {
			if (bottomBorder == null) {
				return null;
			}
			return (Border) bottomBorder;
		}
		short border = cell.getCellStyle().getBorderBottom();
		bottomBorder = null;
		if (border != 0) {// ありのとき
			short color = cell.getCellStyle().getBottomBorderColor();
			int width = 0;
			Border b = new Border(color, width);
			bottomBorder = b;
			return b;
		} else {// なしのとき
			// 隣のものを取得
			IToken t = getBellow();
			Border b = null;
			if (t != null) {
				b = t.getTopBorder();
			}
			bottomBorder = b;
			return b;
		}
	}

	public Border getLeftBorder() {
		if (leftBorder != Utils.IN_VALID) {
			if (leftBorder == null) {
				return null;
			}
			return (Border) leftBorder;
		}
		short border = cell.getCellStyle().getBorderLeft();
		leftBorder = null;
		if (border != 0) {
			short color = cell.getCellStyle().getLeftBorderColor();
			int width = 0;
			Border b = new Border(color, width);
			leftBorder = b;
			return b;
		} else {
			// 隣のものを取得
			IToken t = getLeft();
			Border b = null;
			if (t != null) {
				b = t.getRightBorder();
			}
			leftBorder = b;
			return b;

		}
	}

	public Border getRightBorder() {
		if (rightBorder != Utils.IN_VALID) {
			if (rightBorder == null) {
				return null;
			}
			return (Border) rightBorder;
		}
		short border = cell.getCellStyle().getBorderRight();
		rightBorder = null;
		if (border != 0) {
			short color = cell.getCellStyle().getRightBorderColor();
			int width = 0;
			Border b = new Border(color, width);
			rightBorder = b;
			return b;
		} else {
			// 隣のものを取得
			IToken t = getRight();
			Border b = null;
			if (t != null) {
				b = t.getLeftBorder();
			}
			rightBorder = b;
			return b;
		}
	}

	public Border getTopBorder() {
		if (topBorder != Utils.IN_VALID) {
			if (topBorder == null) {
				return null;
			}
			return (Border) topBorder;
		}
		topBorder = null;

		short border = cell.getCellStyle().getBorderTop();
		if (border != 0) {
			short color = cell.getCellStyle().getTopBorderColor();
			int width = 0;
			Border b = new Border(color, width);
			topBorder = b;
			return b;
		} else {
			// 隣のものを取得
			IToken t = getUpper();
			Border b = null;
			if (t != null) {
				b = t.getBottomBorder();
			}
			topBorder = b;
			return b;
		}
	}

	public void setMatrix(ITokenMatrix matrix) {
		this.matrix = matrix;
	}

	@Override
	public String toString() {
		// StringBuilder sb = new StringBuilder();
		// sb.append("row ").append(cell.getRowIndex());
		// sb.append("col ").append(cell.getColumnIndex());
		// sb.append("\r\n");
		// sb.append("value").append(getInnerText());
		// sb.append("\r\n");
		return cell.toString();

	}

	private IToken getBellow() {
		if (matrix == null) {
			return null;
		}

		int row = getRowIndex() + 1;
		int col = getColIndex();

		if (row <= matrix.getLastRowIndex()) {
			return matrix.get(row, col);
		}
		return null;
	}

	public IToken getUpper() {
		if (matrix == null) {
			return null;
		}
		int row = getRowIndex() - 1;
		int col = getColIndex();

		if (row >= 0) {
			return matrix.get(row, col);
		}
		return null;
	}

	public IToken getRight() {
		if (matrix == null) {
			return null;
		}
		int row = getRowIndex();
		int col = getColIndex() + 1;

		if (col <= matrix.getLastColIndex()) {
			return matrix.get(row, col);
		}
		return null;
	}

	public IToken getLeft() {
		if (matrix == null) {
			return null;
		}
		int row = getRowIndex();
		int col = getColIndex() - 1;

		if (col >= 0) {
			return matrix.get(row, col);
		}
		return null;
	}

}
