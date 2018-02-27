package kshiroma0622.excel2html.fragment;

import kshiroma0622.excel2html.layout.Utils;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;

import java.util.List;

public abstract class AbstractUIFragment implements IUIFragment {

    private final ITokenMatrix matrix;

    public AbstractUIFragment(ITokenMatrix matrix) {
        this.matrix = matrix;
    }

    private List<IUIFragment> children = Utils.newList();

    public List<IUIFragment> getChildren() {
        return children;
    }

    public void addChild(IUIFragment fragment) {
        children.add(fragment);
    }

    public IUIFragment getParent() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    public void render(WriterWrapper w) {
        renderStartTag(w);
        renderBody(w);
        renderEndTag(w);
    }

    public List<IUIFragment> getSiblings() {
        return null;
    }

    public void renderBody(WriterWrapper w) {
    }

    public void renderEndTag(WriterWrapper w) {
    }

    public void renderStartTag(WriterWrapper w) {
    }

    public ITokenMatrix getTokenMatrix() {
        return matrix;
    }

}
