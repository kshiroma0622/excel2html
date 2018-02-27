package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.fragment.FragmentUtil;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.IUIFragment;

public class LabelAssembler extends SimpleAssembler {

    public IUIFragment assemble(ITokenMatrix matrix) {
        Label l = new Label(matrix);
        String text = FragmentUtil.getInnerText(matrix);
        l.setText(text);
        return l;
    }

    @Override
    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {
        if (isLabel(matrix)) {
            return matrix;// .getSubMatrix(0, 0, 0, 0);
        }
        return null;
    }

}
