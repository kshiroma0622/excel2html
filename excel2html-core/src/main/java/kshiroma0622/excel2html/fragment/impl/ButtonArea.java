package kshiroma0622.excel2html.fragment.impl;

import kshiroma0622.excel2html.fragment.AbstractUIFragment;
import kshiroma0622.excel2html.fragment.IBlock;
import kshiroma0622.excel2html.fragment.IUIFragment;
import kshiroma0622.excel2html.fragment.WriterWrapper;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;

public class ButtonArea extends AbstractUIFragment implements IBlock {

    public ButtonArea(ITokenMatrix matrix) {
        super(matrix);
    }

    @Override
    public void renderStartTag(WriterWrapper w) {
        w.writeLine("<table width=\"980px\" class=\"all-border\">");
        w.writeLine("<tr>");
        w.writeLine("<td align=\"right\" colspan=\"1\" rowspan=\"1\">");
    }

    @Override
    public void renderBody(WriterWrapper w) {

        if (getChildren() == null) {
            return;
        }
        for (IUIFragment f : getChildren()) {
            f.render(w);
        }
    }

    @Override
    public void renderEndTag(WriterWrapper w) {
        w.writeLine("</td>");
        w.writeLine("</tr>");
        w.writeLine("</table>");
    }

}
