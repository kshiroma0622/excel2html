package kshiroma0622.excel2html.parser;

import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.IUIFragment;

public interface IParser {

    public IUIFragment parse(ITokenMatrix matrix);

}
