package kshiroma0622.excel2html.fragment;

import kshiroma0622.excel2html.tokenize.ITokenMatrix;

import java.util.List;

public interface IUIFragment {

    public void render(WriterWrapper w);

    public IUIFragment getParent();

    public List<IUIFragment> getChildren();

    public List<IUIFragment> getSiblings();

    public ITokenMatrix getTokenMatrix();
}
