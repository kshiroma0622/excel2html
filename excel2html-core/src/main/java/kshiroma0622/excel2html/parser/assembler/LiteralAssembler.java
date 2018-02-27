package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.fragment.FragmentUtil;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.IUIFragment;

public class LiteralAssembler extends SimpleAssembler {

    @Override
    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {
        if (isLiteral(matrix)) {
            return matrix.getSubMatrix(0, 0, 0, 0);
        }
        return null;
    }

    public IUIFragment assemble(ITokenMatrix matrix) {
        Literal l = new Literal(matrix);
        String text = FragmentUtil.getInnerText(matrix);
        l.setText(text);
        return l;
    }

}
