package kshiroma0622.excel2html.tokenize;

import kshiroma0622.excel2html.layout.Align;
import kshiroma0622.excel2html.layout.VAlign;

public interface IToken {

    public int getRowIndex();

    public int getColIndex();

    public Border getTopBorder();

    public Border getLeftBorder();

    public Border getRightBorder();

    public Border getBottomBorder();

    public int getWidth();

    public int getHeight();

    public short getColor();

    public String getInnerText();

    public Align getAlign();

    public VAlign getVAlign();

    // public void setMatrix(ITokenMatrix matrix);

}
