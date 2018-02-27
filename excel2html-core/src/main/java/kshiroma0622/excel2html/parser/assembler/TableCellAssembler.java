package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.fragment.IUIFragment;
import kshiroma0622.excel2html.fragment.impl.TableCell;
import kshiroma0622.excel2html.layout.Align;
import kshiroma0622.excel2html.matrix.Matrix;
import kshiroma0622.excel2html.parser.AssemblerSet;
import kshiroma0622.excel2html.tokenize.Border;
import kshiroma0622.excel2html.tokenize.IToken;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.tokenize.TokenizeUtil;

public class TableCellAssembler extends AbstractAssembler {

    public IUIFragment assemble(ITokenMatrix matrix) {
        TableCell tableCell = new TableCell(matrix);

        IToken t = matrix.get(0, 0);

        Align a = t.getAlign();
        String alignStr = "";
        if (a == Align.CENTER) {
            alignStr = "-center";
        } else if (a == Align.LEFT) {
            alignStr = "-left";
        } else if (a == Align.RIGHT) {
            alignStr = "-right";
        }
        if (TokenizeUtil.isLabelCell(matrix.get(0, 0))) {
            tableCell.setStyleClass("title" + alignStr);
        } else {
            tableCell.setStyleClass("data" + alignStr);
        }

        tableCell.setColSpan(matrix.getCols());
        tableCell.setRowSpan(matrix.getRows());
        ITokenMatrix parent = matrix.getRootMatrix();
        if (parent == null) {
            tableCell.setCol(0);
            tableCell.setRow(0);
        } else {
            int col;
            int row;
            col = parent.getColIndex(t);
            row = parent.getRowIndex(t);

            tableCell.setCol(col);
            tableCell.setRow(row);

        }

        int width = TokenizeUtil.getWidth(matrix.getRow(0));
        int height = TokenizeUtil.getHeight(matrix.getCol(0));
        tableCell.setWidth(width);
        tableCell.setHeight(height);
        // tableCell.setHeight(-1);

        // なくなるまで探す
        boolean mayBeExist = true;
        int beginCol = 0;
        int begigRow = 0;
        int endCol = matrix.getLastColIndex();
        int endRow = matrix.getLastRowIndex();

        Matrix<Boolean> flags = new Matrix<Boolean>(endRow + 1, endCol + 1);
        IAssembler assembler = getAssemblerSet();
        ITokenMatrix matrix2 = assembler.findFirstFragment(matrix);
        // とりあえずひとつだけ。
        // while (mayBeExist) {
        if (mayBeExist) {
            if (matrix2 != null) {
                IUIFragment f = assembler.assemble(matrix2);
                tableCell.addChild(f);
                // matrixの更新
                for (int row = begigRow; row < endRow; row++) {
                    for (int col = beginCol; col < endCol; col++) {
                        flags.assign(row, col, true);
                    }
                }
                // 次のendとcolはどこだ？
            } else {
                mayBeExist = false;
            }
        }

        // / ラベル
        // ラベルかどうか

        // / 普通のもの
        // / 入力用テキスト
        // / pullDown

        return tableCell;
    }

    @Override
    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {
        // /罫線で囲まれた領域
        // どうやってDetectすればよいか

        // 調べていって
        int beginRow = -1;
        int beginCol = -1;
        int endRow = -1;
        int endCol = -1;
        for (int row = 0; row < matrix.getRows(); row++) {
            for (int col = 0; col < matrix.getCols(); col++) {
                IToken t = matrix.get(row, col);
                Border top = t.getTopBorder();
                Border left = t.getLeftBorder();

                if (TokenizeUtil.isBlackBorder(top) && TokenizeUtil.isBlackBorder(left)) {
                    beginRow = row;
                    beginCol = col;
                } else {
                    continue;
                }
                // とりあえず、完全に方形で囲まれていることを前提にする。
                endRow = getEndRowIndex(matrix, row, col);
                // colIndexは転置させて計算する
                endCol = getEndColIndex(matrix, row, col);
                if (endRow > -1 && endCol > -1) {
                    return matrix.getSubMatrix(beginRow, endRow, beginCol, endCol);
                }
            }
        }

        return super.findFirstFragment(matrix);
    }

    private int getEndRowIndex(final ITokenMatrix matrix, final int row, final int col) {
        int endRow = -1;
        for (int row2 = row; row2 < matrix.getRows(); row2++) {
            IToken t2 = matrix.get(row2, col);
            Border current = t2.getBottomBorder();
            // カレントに線が惹かれていたらそれでよいのではないか？
            if (TokenizeUtil.isBlackBorder(current)) {
                endRow = row2;
                return endRow;
            }
        }
        return endRow;
    }

    private int getEndColIndex(final ITokenMatrix matrix, final int row, final int col) {
        int endCol = -1;
        for (int col2 = col; col2 < matrix.getCols(); col2++) {
            IToken t2 = matrix.get(row, col2);
            Border current = t2.getRightBorder();
            // カレントに線が惹かれていたらそれでよいのではないか？
            if (TokenizeUtil.isBlackBorder(current)) {
                endCol = col2;
                return endCol;
            }
        }
        return endCol;
    }

    private AssemblerSet getAssemblerSet() {
        return AssemblerSet.getTableCellInnerAssemblerSet();
    }

}
