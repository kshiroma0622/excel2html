import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import kshiroma0622.excel2html.layout.Utils;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import kshiroma0622.excel2html.parser.IParser;
import kshiroma0622.excel2html.parser.Parser;
import kshiroma0622.excel2html.parser.assembler.TableAssembler;
import kshiroma0622.excel2html.parser.assembler.TableCellAssembler;
import kshiroma0622.excel2html.tokenize.IToken;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.tokenize.excel.ExcelTokenizer;
import kshiroma0622.excel2html.util.SpreadSheetUtil;
import kshiroma0622.excel2html.fragment.IUIFragment;
import kshiroma0622.excel2html.fragment.WriterWrapper;
import kshiroma0622.excel2html.fragment.impl.Page;
import kshiroma0622.excel2html.fragment.impl.Table;

public class Test {

	public static void main(String args[]) throws Exception {
		// doBorderTest();
		doCustPage();
	}

	public static void doCustPage() {
		File file = new File("excels\\sample.xlsx");
		Workbook book = SpreadSheetUtil.createWorkbookWithClose(file);
		Sheet sheet = book.getSheetAt(2);
		ExcelTokenizer tokenizer = new ExcelTokenizer();
		tokenizer.setSheet(sheet);
		tokenizer.setStartPoint(4, "B");
		tokenizer.setEndPoint(41, "BD");

		ITokenMatrix matrix = tokenizer.tokenize();
		IParser parser = new Parser();
		IUIFragment page = parser.parse(matrix);
		File out = new File("output\\test.html");
		write(out, page);

	}

	/**
	 * コンバートHTML
	 * 
	 * @param args
	 */
	public static void doPageTest() {
		// エクセルの読み込み

		File file = new File("a.xls");
		Workbook book = SpreadSheetUtil.createWorkbookWithClose(file);
		Sheet sheet = book.getSheetAt(0);

		ExcelTokenizer tokenizer = new ExcelTokenizer();
		tokenizer.setSheet(sheet);

		tokenizer.setStartPoint(4, "B");
		tokenizer.setEndPoint(41, "BD");

		ITokenMatrix matrix = tokenizer.tokenize();

		IParser parser = new Parser();
		IUIFragment page = parser.parse(matrix);
		File out = new File("output\\convHtml.html");
		write(out, page);

	}

	public static void doMultiTableTest() throws Exception {

		File file = new File("a.xls");
		Workbook book = SpreadSheetUtil.createWorkbookWithClose(file);
		Sheet sheet = book.getSheet("Table");
		ExcelTokenizer tokenizer = new ExcelTokenizer();
		tokenizer.setSheet(sheet);

		tokenizer.setStartPoint(1, "A");
		tokenizer.setEndPoint(8, "AZ");

		ITokenMatrix matrix = tokenizer.tokenize();

		Parser parser = new Parser();
		IUIFragment page = parser.parse(matrix);
		File out = new File("output\\table.html");
		write(out, page);
	}

	public static void doTableTest() throws Exception {

		File file = new File("a.xls");
		Workbook book = SpreadSheetUtil.createWorkbookWithClose(file);
		Sheet sheet = book.getSheet("Table");
		ExcelTokenizer tokenizer = new ExcelTokenizer();
		tokenizer.setSheet(sheet);

		tokenizer.setStartPoint(1, "A");
		tokenizer.setEndPoint(4, "AR");

		ITokenMatrix matrix = tokenizer.tokenize();
		IToken token = matrix.get(0, 0);
		System.out.println(token.getWidth());

		IUIFragment page = new Page(matrix);

		List<IUIFragment> child = Utils.newList();
		TableAssembler assembler = new TableAssembler();
		IUIFragment t = assembler.assemble(matrix);
		child.add(t);
		page.getChildren().add(t);
		File out = new File("output\\table.html");
		write(out, page);
	}

	public static void doTableCellTest() throws Exception {

		File file = new File("a.xls");
		Workbook book = SpreadSheetUtil.createWorkbookWithClose(file);
		Sheet sheet = book.getSheet("Table");
		ExcelTokenizer tokenizer = new ExcelTokenizer();
		tokenizer.setSheet(sheet);

		tokenizer.setStartPoint(1, "A");
		tokenizer.setEndPoint(1, "B");

		ITokenMatrix matrix = tokenizer.tokenize();

		IUIFragment page = new Page(matrix);
		List<IUIFragment> child = Utils.newList();
		Table t = new Table(matrix);
		child.add(t);
		page.getChildren().add(t);

		TableCellAssembler assembler = new TableCellAssembler();
		IToken token = matrix.get(0, 0);
		System.out.println(token.getInnerText());
		IUIFragment cell = assembler.assemble(matrix);
		WriterWrapper w = new WriterWrapper(new BufferedWriter(
				new OutputStreamWriter(System.out)));
		cell.render(w);
		w.flush();

		t.addChild(cell);

		File out = new File("output\\table.html");
		write(out, page);

	}

	public static void write(File file, IUIFragment page) {
		BufferedWriter buf = null;
		try {
			buf = new BufferedWriter(new FileWriter(file));
			WriterWrapper w = new WriterWrapper(buf);
			page.render(w);
		} catch (IOException e) {
		} finally {
			try {
				buf.flush();
				buf.close();
			} catch (IOException e) {
			}
		}
	}

	public static void write(IUIFragment page) {
		OutputStreamWriter out = new OutputStreamWriter(System.out);
		BufferedWriter buf = null;
		try {
			buf = new BufferedWriter(out);
			WriterWrapper w = new WriterWrapper(buf);
			page.render(w);
		} finally {
			try {
				buf.flush();
				buf.close();
			} catch (IOException e) {
			}
		}
	}

	private static void doBorderTest() throws Exception {
		File file = new File("a.xls");
		Workbook book = SpreadSheetUtil.createWorkbookWithClose(file);
		Sheet sheet = book.getSheet("border");
		ExcelTokenizer tokenizer = new ExcelTokenizer();
		tokenizer.setSheet(sheet);

		tokenizer.setStartPoint(1, "A");
		tokenizer.setEndPoint(3, "C");

		ITokenMatrix matrix = tokenizer.tokenize();
		writeBorder(matrix.get(0, 0));
		writeBorder(matrix.get(0, 1));
		writeBorder(matrix.get(0, 2));

		writeBorder(matrix.get(1, 0));
		writeBorder(matrix.get(1, 1));
		writeBorder(matrix.get(1, 2));

		writeBorder(matrix.get(2, 0));
		writeBorder(matrix.get(2, 1));
		writeBorder(matrix.get(2, 2));

		Parser parser = new Parser();
		IUIFragment page = parser.parse(matrix);
		File out = new File("output\\table.html");
		write(out, page);
	}

	private static void writeBorder(IToken token) {
		System.out.println("TOP:" + token.getTopBorder());
		System.out.println("LEFT:" + token.getLeftBorder());
		System.out.println("RIGHT:" + token.getRightBorder());
		System.out.println("BOTTOM:" + token.getBottomBorder());
	}

}
