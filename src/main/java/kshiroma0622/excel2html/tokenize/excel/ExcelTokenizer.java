package kshiroma0622.excel2html.tokenize.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.tokenize.ITokenizer;
import kshiroma0622.excel2html.tokenize.TokenMatrix;
import kshiroma0622.excel2html.util.SpreadSheetUtil;
import kshiroma0622.excel2html.util.SpreadSheetUtil.CellProcess;
import kshiroma0622.excel2html.util.SpreadSheetUtil.Direction;

public class ExcelTokenizer implements ITokenizer<Sheet> {

	private Cell startCell;
	private Cell endCell;

	private int startCellRowIndex;
	private int startCellColIndex;

	private int endCellRowIndex;
	private int endCellColIndex;

	private Sheet sheet;

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public void setStartPoint(int rowIndex, int colIndex) {
		this.startCellColIndex = colIndex;
		this.startCellRowIndex = rowIndex;
	}

	public void setStartPoint(int row, String colAddress) {
		int colIndex = SpreadSheetUtil.getCellIndex(colAddress);
		int rowIndex = row - 1;
		setStartPoint(rowIndex, colIndex);
	}

	public void setEndPoint(int rowIndex, int colIndex) {
		this.endCellColIndex = colIndex;
		this.endCellRowIndex = rowIndex;
	}

	public void setEndPoint(int row, String colAddress) {
		int colIndex = SpreadSheetUtil.getCellIndex(colAddress);
		int rowIndex = row - 1;
		setEndPoint(rowIndex, colIndex);
	}

	public ITokenMatrix tokenize() {

		startCell = SpreadSheetUtil.getCellNullAsBlank(sheet, startCellRowIndex, startCellColIndex);
		endCell = SpreadSheetUtil.getCellNullAsBlank(sheet, endCellRowIndex, endCellColIndex);

		int width = endCellColIndex - startCellColIndex + 1;
		int height = endCellRowIndex - startCellRowIndex + 1;

		final TokenMatrix matrix = new TokenMatrix(width, height);

		CellProcess p = new CellProcess() {
			public void process(Cell cell) {
				int rowIndex = cell.getRowIndex() - startCellRowIndex;
				int colIndex = cell.getColumnIndex() - startCellColIndex;
				ExcelToken token = new ExcelToken(cell, startCell);
				token.setMatrix(matrix);
				matrix.set(token, rowIndex, colIndex);
			}
		};

		SpreadSheetUtil.sheetRun(startCell, endCell, Direction.Vertical, p);

		return matrix;
	}

}
