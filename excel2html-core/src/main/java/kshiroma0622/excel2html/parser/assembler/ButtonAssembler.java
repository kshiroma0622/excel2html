package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.parser.AssemblerSet;
import kshiroma0622.excel2html.tokenize.IToken;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.tokenize.Border;
import kshiroma0622.excel2html.fragment.IUIFragment;
import kshiroma0622.excel2html.fragment.impl.Button;

public class ButtonAssembler extends AbstractAssembler {

    public IUIFragment assemble(ITokenMatrix matrix) {
        Button button = new Button(matrix);
        // でこの次に
        IToken token = matrix.get(0, 0);
        String value = token.getInnerText().replace("b", "");
        button.setValue(value);
        return button;
    }

    protected AssemblerSet getAssemblerSet() {
        AssemblerSet set = new AssemblerSet();
        set.append(new ButtonAssembler());
        return set;
    }

    @Override
    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {
        // 囲まれていて、その中の文字の最初がbで始まる。
        // Rootを取得するのもいいかもしれない。
        int maxRow = matrix.getRows() - 1;
        int maxCol = matrix.getCols() - 1;

        // ボタンは一行だけ
        boolean hit = false;
        int beginRow = -1;
        int beginCol = -1;

        for (int row = 0; row <= maxRow; row++) {
            for (int col = 0; col <= maxCol; col++) {
                IToken t = matrix.get(row, col);
                String text = t.getInnerText();
                if (text != null && text.startsWith("b")) {
                    hit = true;
                    beginRow = row;
                    beginCol = col;
                    break;
                }
            }
            if (hit) {
                break;
            }
        }

        int endCol = -1;
        if (hit) {
            for (int col = beginCol; col <= maxCol; col++) {
                IToken t = matrix.get(beginRow, col);
                Border right = t.getRightBorder();
                if (right != null) {
                    endCol = col;
                    break;
                }
            }
            if (endCol != -1 && beginRow != -1 && beginCol != -1) {
                return matrix.getSubMatrix(beginRow, beginRow, beginCol, endCol);
            }
        }
        return null;
    }

}
