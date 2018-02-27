package kshiroma0622.excel2html.parser.assembler;


import kshiroma0622.excel2html.fragment.AbstractUIFragment;
import kshiroma0622.excel2html.fragment.FragmentUtil;
import kshiroma0622.excel2html.fragment.IInline;
import kshiroma0622.excel2html.fragment.WriterWrapper;
import kshiroma0622.excel2html.layout.Utils;
import kshiroma0622.excel2html.tokenize.IToken;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.tokenize.TokenizeUtil;
import org.apache.commons.lang.StringUtils;

public abstract class SimpleAssembler extends AbstractAssembler implements IInline {

    protected boolean isLiteral(ITokenMatrix matrix) {
        // 何もない
        IToken t = matrix.get(0, 0);
        String text = t.getInnerText();
        return !StringUtils.isEmpty(text);
    }

    protected boolean isLabel(ITokenMatrix matrix) {
        if (isLiteral(matrix)) {
            // かつ
            return TokenizeUtil.isLabelCell(matrix.get(0, 0));
        }
        return false;
    }

    protected boolean isInput(ITokenMatrix matrix) {
        IToken top = matrix.get(0, 0);
        String prefix = top.getInnerText();
        if (prefix != null && prefix.startsWith("i")) {
            return true;
        }
        return false;
    }

    protected boolean isInputWithSearch(ITokenMatrix matrix) {
        if (isInput(matrix) && isSearchIcon(matrix)) {
            return true;
        }
        return false;
    }

    protected boolean isSearchIcon(ITokenMatrix matrix) {
        // 最後が?
        int last = matrix.getLastColIndex();
        IToken t = matrix.get(0, last);
        String suffix = t.getInnerText();
        return suffix != null && (suffix.endsWith("?") || suffix.endsWith("？"));
    }

    protected boolean isRadio(ITokenMatrix matrix) {
        // ○か◎を含む
        String str = matrix.get(0, 0).getInnerText();
        return StringUtils.contains(str, "○") || StringUtils.contains(str, "◎");
    }

    protected boolean isCheck(ITokenMatrix matrix) {
        // □を含む
        String str = matrix.get(0, 0).getInnerText();
        return StringUtils.contains(str, "□") || StringUtils.contains(str, "■");
    }

    protected boolean isPullDown(ITokenMatrix matrix) {
        // 最後が▼で
        int last = matrix.getLastColIndex();
        IToken t = matrix.get(0, last);
        String suffix = t.getInnerText();
        return suffix != null && (suffix.endsWith("▼"));
    }

}

class Label extends AbstractUIFragment {
    private String text;

    public Label(ITokenMatrix matrix) {
        super(matrix);
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void render(WriterWrapper w) {

        // 親が合ったら親を使う
        ITokenMatrix matrix = getTokenMatrix().getParentMatrix();
        if (matrix == null) {
            matrix = getTokenMatrix();
        }

        // 幅の計算
        int width = 0;
        for (IToken t : matrix.getRow(0)) {
            width += t.getWidth();
        }

        FragmentUtil.renderLabelInput(w, text, width);
    }
}

class Literal extends AbstractUIFragment {

    private String text;

    public Literal(ITokenMatrix matrix) {
        super(matrix);
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void render(WriterWrapper w) {
        if (text != null && text.startsWith("l")) {
            String literal = text.replaceFirst("l", "");
            w.write("<a href=\"javascript:void(0)\">");
            w.write(literal);
            w.writeLine("</a>");
        } else {
            w.writeLine(text);
        }
    }
}

class Input extends AbstractUIFragment {

    private String text;

    public Input(ITokenMatrix matrix) {
        super(matrix);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    protected int getSize() {
        ITokenMatrix matrix = getTokenMatrix();
        if (matrix == null) {
            matrix = getTokenMatrix();
        }
        int width = 0;
        for (IToken t : matrix.getRow(0)) {
            width += t.getWidth();
        }
        int size = Utils.getSizeOfSimpleText(width);
        return size;
    }

    public void render(WriterWrapper w) {
        // 親が合ったら親を使う
        ITokenMatrix matrix = getTokenMatrix().getParentMatrix();
        if (matrix == null) {
            matrix = getTokenMatrix();
        }

        // 幅の計算
        String sizeS = "";
        int size = getSize();
        if (size > 0) {
            sizeS = String.format("size=\"%d\"", size);
        }
        String str = String.format("<input type=\"text\" %s/>", sizeS);
        w.writeLine(str);

    }

}

class SearchIcon extends AbstractUIFragment {

    public SearchIcon(ITokenMatrix matrix) {
        super(matrix);
    }

    @Override
    public void render(WriterWrapper w) {
        String iconPath = FragmentUtil.getIconPath();
        w.writeLine(String.format("<img src=\"%s\" class=\"linkimg\"/>", iconPath));
    }

}

class InputWithSearchIcon extends Input {

    public InputWithSearchIcon(ITokenMatrix matrix) {
        super(matrix);
    }

    @Override
    public void render(WriterWrapper w) {
        super.render(w);
        String iconPath = FragmentUtil.getIconPath();
        w.writeLine(String.format("<img src=\"%s\" class=\"linkimg\"/>", iconPath));
    }

    @Override
    protected int getSize() {
        ITokenMatrix matrix = getTokenMatrix();
        if (matrix == null) {
            matrix = getTokenMatrix();
        }
        int width = 0;
        for (IToken t : matrix.getRow(0)) {
            width += t.getWidth();
        }
        int size = Utils.getSizeOfSimpleText(width - 30);
        return size;
    }

}

class CheckBox extends Input {

    private static final String CHECKED = "<input type=\"checkbox\" checked/>";
    private static final String UNCHECKED = "<input type=\"checkbox\"/>";

    public CheckBox(ITokenMatrix matrix) {
        super(matrix);
    }

    @Override
    public void render(WriterWrapper w) {
        String text = getText();
        text = text.replaceAll("□", UNCHECKED);
        text = text.replaceAll("■", CHECKED);
        w.writeLine(text);
    }
}

class Radio extends Input {

    private static final String CHECKED = "<input type=\"radio\" checked/>";
    private static final String UNCHECKED = "<input type=\"radio\"/>";

    public Radio(ITokenMatrix matrix) {
        super(matrix);
    }

    @Override
    public void render(WriterWrapper w) {
        // null
        String text = getText();
        text = text.replaceAll("○", UNCHECKED);
        text = text.replaceAll("◎", CHECKED);
        w.writeLine(text);
    }
}

class Pulldown extends Input {

    public Pulldown(ITokenMatrix matrix) {
        super(matrix);
    }

    @Override
    public void render(WriterWrapper w) {
        // null
        String text = getText();
        // コロンで区切る
        String[] labels = StringUtils.split(text, ":");
        w.writeLine("<select>");
        if (labels != null) {
            for (String label : labels) {
                w.write("<option>");
                w.write(label);
                w.write("</option>");
            }
        }
        w.writeLine("</select>");
    }

}