package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.fragment.FragmentUtil;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.IUIFragment;

public class InputAssembler extends SimpleAssembler {

    public IUIFragment assemble(ITokenMatrix matrix) {
        Input l = new Input(matrix);
        String text = FragmentUtil.getInnerText(matrix);
        l.setText(text);
        return l;
    }

    @Override
    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {
        if (isInput(matrix)) {
            return matrix;// .getSubMatrix(0, 0, 0, 0);
        }
        return null;
    }

}
