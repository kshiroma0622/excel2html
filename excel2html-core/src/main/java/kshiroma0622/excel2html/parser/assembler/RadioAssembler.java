package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.tokenize.IToken;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.IUIFragment;

public class RadioAssembler extends SimpleAssembler {

    public IUIFragment assemble(ITokenMatrix matrix) {
        Radio radio = new Radio(matrix);
        IToken t = matrix.get(0, 0);
        String text = t.getInnerText();
        radio.setText(text);
        return radio;
    }

    @Override
    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {
        if (isRadio(matrix)) {
            return matrix;// .getSubMatrix(0, 0, 0, 0);
        }
        return null;
    }

}
