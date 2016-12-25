import kshiroma0622.excel2html.fragment.IUIFragment;
import kshiroma0622.excel2html.fragment.WriterWrapper;
import kshiroma0622.excel2html.parser.IParser;
import kshiroma0622.excel2html.parser.Parser;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.tokenize.excel.ExcelTokenizer;
import kshiroma0622.excel2html.util.SpreadSheetUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;

public class Excel2Html {

    public static void main(String[] args) {
        // エクセルの読み込み

        File dir = new File("excels");
        File[] files = dir.listFiles();
        for (File file : files) {
            System.out.println(file.getName() + "を読み込んでいます。");
            File dest = convert(file);
            if (dest != null) {
                System.out.println(dest.getName() + "を出力しました");
            }
            System.out.println("");
        }
    }


    private static File convert(File file) {
        Workbook book = SpreadSheetUtil.createWorkbookWithClose(file);
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
        String name = file.getName();
        name = name.replaceAll("(?i).xlsx", ".html");
        name = name.replaceAll("(?i).xls", ".html");
        File out = new File("output\\" + name);
        write(out, page);
        return out;
    }

    public static void write(File file, IUIFragment page) {
        BufferedWriter buf = null;
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "UTF8");
            buf = new BufferedWriter(outputStreamWriter);
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
}
