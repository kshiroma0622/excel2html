package kshiroma0622.excel2html;

import kshiroma0622.excel2html.fragment.IUIFragment;
import kshiroma0622.excel2html.fragment.WriterWrapper;
import kshiroma0622.excel2html.parser.IParser;
import kshiroma0622.excel2html.parser.Parser;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.tokenize.excel.ExcelTokenizer;
import kshiroma0622.excel2html.util.SpreadSheetUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class Excel2HtmlUtil {

    public static void toHtml(byte[] data, OutputStream os) {
        Workbook book = SpreadSheetUtil.createWorkbook(data);
        Sheet sheet = book.getSheet("input");
        if (sheet == null) {
            return;
        }
        ExcelTokenizer tokenizer = new ExcelTokenizer();
        tokenizer.setSheet(sheet);
        tokenizer.setStartPoint(4, "B");
        tokenizer.setEndPoint(41, "BD");
        ITokenMatrix matrix = tokenizer.tokenize();
        IParser parser = new Parser();
        IUIFragment page = parser.parse(matrix);
        WriterWrapper w = new WriterWrapper(new OutputStreamWriter(os));
        page.render(w);
        w.flush();
    }

    public static String toHtml(byte[] data) {
        Workbook book = SpreadSheetUtil.createWorkbook(data);
        Sheet sheet = book.getSheet("input");
        if (sheet == null) {
            System.out.println("エクセルファイルにinputシートがありません。");
            return null;
        }
        ExcelTokenizer tokenizer = new ExcelTokenizer();
        tokenizer.setSheet(sheet);
        tokenizer.setStartPoint(4, "B");
        tokenizer.setEndPoint(41, "BD");
        ITokenMatrix matrix = tokenizer.tokenize();
        IParser parser = new Parser();
        IUIFragment page = parser.parse(matrix);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        WriterWrapper w = new WriterWrapper(new BufferedWriter(new OutputStreamWriter(os, Charset.forName("UTF-8"))));
        page.render(w);
        w.flush();
        return os.toString();
    }

}
