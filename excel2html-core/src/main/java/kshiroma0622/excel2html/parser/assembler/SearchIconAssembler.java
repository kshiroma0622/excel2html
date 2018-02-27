package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.IUIFragment;

public class SearchIconAssembler extends SimpleAssembler {

    public IUIFragment assemble(ITokenMatrix matrix) {
        SearchIcon l = new SearchIcon(matrix);
        return l;
    }

    @Override
    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {
        if (isSearchIcon(matrix)) {
            return matrix.getSubMatrix(0, 0, 0, 0);
        }
        return null;
    }
}
