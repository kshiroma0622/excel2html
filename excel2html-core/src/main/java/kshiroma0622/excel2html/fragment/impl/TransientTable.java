package kshiroma0622.excel2html.fragment.impl;

import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.WriterWrapper;

public class TransientTable extends Table {

    public TransientTable(ITokenMatrix matrix) {
        super(matrix);
    }

    public void renderStartTag(WriterWrapper w) {
        compose();
        w.writeLine("<table class=\"no-border\">");
    }

    @Override
    public void renderEndTag(WriterWrapper w) {
        w.writeLine("</table>");
    }
}
