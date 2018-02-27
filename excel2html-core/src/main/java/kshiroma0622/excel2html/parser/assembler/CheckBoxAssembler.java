package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.tokenize.IToken;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.IUIFragment;

public class CheckBoxAssembler extends SimpleAssembler {

    public IUIFragment assemble(ITokenMatrix matrix) {
        IToken token = matrix.get(0, 0);
        CheckBox box = new CheckBox(matrix);
        String text = token.getInnerText();
        box.setText(text);
        return box;
    }

    @Override
    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {
        if (isCheck(matrix)) {
            // /囲まれた範囲を返すのがよいかもね。
            return matrix.getSubMatrix(0, 0, 0, 0);
        }
        return null;
    }
}
