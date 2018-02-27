package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.tokenize.IToken;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.IUIFragment;

public class PulldownAssembler extends SimpleAssembler {
    public IUIFragment assemble(ITokenMatrix matrix) {
        Pulldown radio = new Pulldown(matrix);
        IToken t = matrix.get(0, 0);
        String text = t.getInnerText();
        radio.setText(text);
        return radio;
    }

    @Override
    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {
        if (isPullDown(matrix)) {
            return matrix;
        }
        return null;
    }
}
