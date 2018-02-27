package kshiroma0622.excel2html.fragment;

import kshiroma0622.excel2html.tokenize.IToken;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Set;

public class FragmentUtil {


    private static final String CLASS_ATTR = "class";
    private static final String READONLY_ATTR = "readonly";
    private static final String TABINDEX_ATTR = "tabindex";
    private static final String VALUE_ATTR = "value";
    private static final String SIZE_ATTR = "size";

    /**
     * attributeをレンダリングする
     *
     * @param w         ライター
     * @param attrName  属性名
     * @param attrValue 属性値
     * @throws IOException IOException
     */
    public static void renderAttribute(
            WriterWrapper w, //
            String attrName,//
            Object attrValue//
    ) {
        w.write(" ");
        w.write(attrName);
        w.write("=");
        w.write("\"");
        if (attrValue instanceof Set) {
            Set set = (Set) attrValue;
            for (Object o : set) {
                if (o == null) {
                    continue;
                }
                if (o instanceof String) {
                    w.write(o.toString());
                    w.write(" ");
                }
            }
        } else {
            if (attrValue != null) {
                w.write(String.valueOf(attrValue));
            }
        }

        w.write("\"");
        w.write(" ");
    }

    /**
     * ラベルをInputで出力
     *
     * @param writer writer
     * @param value  value
     * @param width  幅
     * @throws IOException IOException
     */
    public static void renderLabelInput(//
                                        final WriterWrapper writer,//
                                        final String value,//
                                        final int width //
    ) {
        writer.write("<");
        writer.write("input");
        renderAttribute(writer, CLASS_ATTR, "label");
        renderAttribute(writer, READONLY_ATTR, READONLY_ATTR);
        renderAttribute(writer, TABINDEX_ATTR, ("-1"));
        renderAttribute(writer, VALUE_ATTR, StringUtils.defaultString(value));
        int size1 = getByteLength(value);
        int size2 = getSizeOfLabel(width);
        int size = size1 < size2 ? size1 : size2;
        if (size < 1) {
            size = 1;
        }
        // 幅を超えるようならサイズを小さくする
        renderAttribute(writer, SIZE_ATTR, String.valueOf(size));
        writer.writeLine("/>");

    }


    /**
     * ByteLength
     *
     * @param value value
     * @return byteLength
     */
    private static int getByteLength(String value) {
        return value != null ? byteLength(value) - 1 : 0;
    }


    /**
     * 文字列のバイト数取得
     */
    public static int byteLength(String value) {
        // 引数チェック
        if (value == null || value.length() == 0) {
            return 0;
        }

        int bytes = 0;
        try {
            bytes = value.getBytes("Windows-31J").length;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }


    /**
     * widthに入るラベルのサイズ
     *
     * @param width 幅
     * @return サイズ
     */
    public static int getSizeOfLabel(int width) {
        if (width <= 9) {
            return 0;
        }
        return (width - 9) / 7;
    }

    public static boolean hasAnyBorder(IToken token) {
        return token.getTopBorder() != null || token.getBottomBorder() != null || token.getLeftBorder() != null || token.getRightBorder() != null;
    }

    public static String getIconPath() {
        return "img/help.png";
    }

    public static String getInnerText(ITokenMatrix matrix) {
        if (matrix == null) {
            return "";
        }
        IToken t = matrix.get(0, 0);
        if (t == null) {
            return "";
        }

        String text = t.getInnerText();
        if (text == null) {
            return "";
        }
        return text;
    }
}
