package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.IUIFragment;

public class InputWithSearchIconAssembler extends SimpleAssembler {

    public IUIFragment assemble(ITokenMatrix matrix) {
        InputWithSearchIcon l = new InputWithSearchIcon(matrix);
        return l;
    }

    @Override
    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {
        if (isInputWithSearch(matrix)) {
            return matrix;// .getSubMatrix(0, 0, 0, 0);
        }
        return null;
    }
}
