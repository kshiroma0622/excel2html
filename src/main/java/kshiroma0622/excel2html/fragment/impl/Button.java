package kshiroma0622.excel2html.fragment.impl;

import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.AbstractUIFragment;
import kshiroma0622.excel2html.fragment.IInline;
import kshiroma0622.excel2html.fragment.WriterWrapper;

public class Button extends AbstractUIFragment implements IInline {

    private String value;

    public Button(ITokenMatrix matrix) {
        super(matrix);
        setValue(value);
    }

    @Override
    public void renderBody(WriterWrapper w) {
        w.writeLine(String.format("<input type=\"button\" class=\"button\" value=\"%s\">", value));
    }

    public void setValue(String value) {
        this.value = value;
    }

}
