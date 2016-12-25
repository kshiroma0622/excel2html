package kshiroma0622.excel2html.fragment.impl;

import kshiroma0622.excel2html.layout.Utils;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.AbstractUIFragment;
import kshiroma0622.excel2html.fragment.IUIFragment;
import kshiroma0622.excel2html.fragment.WriterWrapper;

public class Page extends AbstractUIFragment {

    public Page(ITokenMatrix matrix) {
        super(matrix);
    }

    public void renderStartTag(WriterWrapper w) {
        w.writeLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        w.writeLine("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ja\" lang=\"ja\">");
        w.writeLine("<head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/><meta http-equiv=\"Content-Style-Type\" content=\"text/css\" />");
        w.writeLine("<title></title>");
        w.writeLine("<link href=\"css/global.css\" rel=\"stylesheet\" type=\"text/css\" />");
        w.writeLine("</head>");
        w.writeLine("<body>");
        w.writeLine("<!-- メインエリア -->");
        w.writeLine("<div id=\"mainArea\">");
    }

    public void renderBody(WriterWrapper w) {
        renderChild(w);
    }

    public final void renderChild(WriterWrapper w) {
        if (Utils.isEmpty(getChildren())) {
            return;
        }
        for (IUIFragment ui : getChildren()) {
            if (ui != null) {
                ui.render(w);
            }

        }
    }

    public void renderEndTag(WriterWrapper w) {
        w.writeLine("</div>");
        w.writeLine("</body>");
        w.writeLine("</html>");
    }

}
