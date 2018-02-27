package kshiroma0622.excel2html.parser;

import kshiroma0622.excel2html.parser.assembler.IAssembler;
import kshiroma0622.excel2html.parser.assembler.PageAssembler;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.IUIFragment;

public class Parser implements IParser {

    public IUIFragment parse(ITokenMatrix matrix) {
        IAssembler assembler = new PageAssembler();

        return assembler.assemble(matrix);
    }
}
