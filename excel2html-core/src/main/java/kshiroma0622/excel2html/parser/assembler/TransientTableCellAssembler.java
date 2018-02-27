package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.IUIFragment;

public class TransientTableCellAssembler extends AbstractAssembler {

    public IUIFragment assemble(ITokenMatrix matrix) {
        // 内部には何をもってもよい。
        // ページと同じもの
        return null;
    }

    @Override
    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {
        // 何をみつけるの？
        return super.findFirstFragment(matrix);
    }
    // /あれとおなじだよな

}
