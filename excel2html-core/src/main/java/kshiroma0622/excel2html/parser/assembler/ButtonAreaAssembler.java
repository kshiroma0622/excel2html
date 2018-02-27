package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.fragment.IUIFragment;
import kshiroma0622.excel2html.fragment.impl.ButtonArea;
import kshiroma0622.excel2html.parser.AssemblerSet;
import kshiroma0622.excel2html.tokenize.IToken;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.tokenize.Border;

public class ButtonAreaAssembler extends AbstractAssembler {

    private static final short BORDER_COLOR = 53;

    public IUIFragment assemble(ITokenMatrix matrix) {
        ButtonArea buttonArea = new ButtonArea(matrix);
        // でこの次に
        // だんだん小さくしていく。
        ButtonAssembler buttonAssembler = new ButtonAssembler();

        // for (int row = 0; row < matrix.getRows(); row++) {
        int row = 0;
        int col = 0;
        ITokenMatrix subMatrix = buttonAssembler.findFirstFragment(matrix.getSubMatrix(row, row, col, matrix.getCols() - 1));
        int count = 0;
        while (subMatrix != null && count++ < 10000) {
            IUIFragment fragment = buttonAssembler.assemble(subMatrix);

            buttonArea.addChild(fragment);
            IToken firstToken = subMatrix.get(0, 0);
            int colIndex = matrix.getColIndex(firstToken);
            col = colIndex + subMatrix.getCols();
            subMatrix = buttonAssembler.findFirstFragment(matrix.getSubMatrix(row, row, col, matrix.getCols() - 1));
        }
        // }

        return buttonArea;
    }

    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {

        boolean hit = false;
        int beginRow = 0;
        int beginCol = 0;
        int endRow = matrix.getLastRowIndex();
        int endCol = matrix.getLastColIndex();

        ITokenMatrix subMatrix = matrix.getSubMatrix(beginRow, endRow, beginCol, endCol);
        // 最初の検索
        // for (int row = beginRow; row <= endRow; row++) {
        int row = 0;
        for (int col = beginCol; col <= endCol; col++) {
            IToken t = subMatrix.get(row, col);
            Border top = t.getTopBorder();
            Border left = t.getLeftBorder();
            // Border right = t.getRightBorder();
            Border bottom = t.getBottomBorder();
            if (top != null && left != null && bottom != null) {

                boolean topColor = top.getColor() == BORDER_COLOR;
                boolean leftColor = left.getColor() == BORDER_COLOR;
                boolean bottomColor = bottom.getColor() == BORDER_COLOR;
                if (topColor && leftColor && bottomColor) {
                    beginRow = row;
                    endRow = beginRow;
                    beginCol = col;
                    hit = true;
                    break;
                }
            }
        }
        if (!hit) {
            return null;
        }
        hit = false;
        endRow = beginRow;
        // 最後の検索
        for (int col = beginCol; col <= endCol; col++) {
            IToken t = subMatrix.get(beginRow, col);
            Border top = t.getTopBorder();
            // Border left = t.getLeftBorder();
            Border right = t.getRightBorder();
            Border bottom = t.getBottomBorder();

            // hitすればここ
            if (right != null && top != null && bottom != null) {
                if (col == endCol) {
                    endCol = col;
                    hit = true;
                } else {
                    IToken token2 = subMatrix.get(endRow, col + 1);
                    Border nextRight = token2.getRightBorder();
                    Border nextTop = token2.getTopBorder();
                    Border nextBottom = token2.getBottomBorder();
                    if (nextRight == null && nextTop == null && nextBottom == null) {
                        endCol = col;
                        hit = true;
                    }
                }
                if (hit) {
                    break;
                }
            }
        }
        if (hit) {
            return matrix.getSubMatrix(beginRow, endRow, beginCol, endCol);
        }
        return null;

    }

    protected AssemblerSet getAssemblerSet() {
        AssemblerSet set = new AssemblerSet();
        set.append(new ButtonAssembler());
        return set;
    }

}
