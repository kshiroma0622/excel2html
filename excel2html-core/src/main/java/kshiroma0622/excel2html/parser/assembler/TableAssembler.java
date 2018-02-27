package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.fragment.IUIFragment;
import kshiroma0622.excel2html.fragment.impl.Table;
import kshiroma0622.excel2html.fragment.impl.Table.ITableCell;
import kshiroma0622.excel2html.fragment.impl.TableCell;
import kshiroma0622.excel2html.layout.LayoutManager;
import kshiroma0622.excel2html.matrix.Matrix;
import kshiroma0622.excel2html.matrix.MatrixRange;
import kshiroma0622.excel2html.tokenize.Border;
import kshiroma0622.excel2html.tokenize.IToken;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.tokenize.TokenizeUtil;
import org.apache.commons.lang.StringUtils;

public class TableAssembler extends AbstractAssembler {

    public IUIFragment assemble(ITokenMatrix matrix) {
        Table table = new Table(matrix);
        // /この中をさらにアセンブルする
        // 
        boolean mayBeExist = true;
        int beginCol = 0;
        int beginRow = 0;
        int endCol = matrix.getLastColIndex();
        int endRow = matrix.getLastRowIndex();

        Matrix<Boolean> flags = new Matrix<Boolean>(endRow + 1, endCol + 1);
        TableCellAssembler cellAssembler = new TableCellAssembler();
        int count = 0;
        while (mayBeExist && count++ < 1000) {
            ITokenMatrix m1 = matrix.getSubMatrix(beginRow, endRow, beginCol, endCol);
            // OutOfBoundExceptionが出たらどうしよう。
            ITokenMatrix m2 = cellAssembler.findFirstFragment(m1);

            if (m2 != null) {
                TableCell f = (TableCell) cellAssembler.assemble(m2);
                // m1に対する座標
                table.addChild((ITableCell) f);

                int rowSpan = m2.getRows();
                int colSpan = m2.getCols();
                MatrixRange r = new MatrixRange(beginRow, beginCol, rowSpan, colSpan);
                if (r == null) {
                    mayBeExist = false;
                    continue;
                }
                flags.assign(r, true);
                r = flags.nextRange(LayoutManager.DIRECTION_HORIZONTAL);
                if (r == null) {
                    mayBeExist = false;
                    continue;
                }
                beginCol = r.getCol();
                beginRow = r.getRow();
                // endCol = beginCol + r.getColspan() - 1;
                // endRow = beginRow + r.getRowspan() - 1;

                if (beginCol < 0 || beginRow < 0) {
                    mayBeExist = false;
                }
            } else {
                mayBeExist = false;
            }
        }

        //

        return table;
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
        boolean found = false;
        ITokenMatrix m = null;
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
                    found = true;
                    m = matrix.getSubMatrix(beginRow, endRow, beginCol, endCol);
                    break;
                }
            }
            if (found) {
                break;
            }
        }

        if (m == null) {
            return null;
        }
        // 内部罫線がなく、文字を持つ
        // ラベルの場合だけはよしとしよう
        String text = m.get(0, 0).getInnerText();
        if (!TokenizeUtil.hasInnerBorder(m) && StringUtils.isNotEmpty(text)) {
            return null;
        }

        return m;
    }

    private int getEndRowIndex(final ITokenMatrix matrix, final int row, final int col) {
        int endRow = -1;
        for (int row2 = row; row2 < matrix.getRows(); row2++) {
            IToken t2 = matrix.get(row2, col);
            Border current = t2.getBottomBorder();
            if (row2 < matrix.getRows() - 1) {
                int row3 = row2 + 1;
                IToken t3 = matrix.get(row3, col);

                Border next = t3.getLeftBorder();
                if (TokenizeUtil.isBlackBorder(current) && !TokenizeUtil.isBlackBorder(next)) {
                    endRow = row2;
                    return endRow;
                }
            } else {
                if (TokenizeUtil.isBlackBorder(current)) {
                    endRow = row2;
                    return endRow;
                }
            }
        }
        return endRow;
    }

    private int getEndColIndex(final ITokenMatrix matrix, final int row, final int col) {
        int endCol = -1;
        for (int col2 = col; col2 < matrix.getCols(); col2++) {
            IToken t2 = matrix.get(row, col2);
            Border current = t2.getRightBorder();
            if (col2 < matrix.getCols() - 1) {
                int col3 = col2 + 1;
                IToken t3 = matrix.get(row, col3);

                Border next = t3.getTopBorder();
                if (TokenizeUtil.isBlackBorder(current) && !TokenizeUtil.isBlackBorder(next)) {
                    endCol = col2;
                    return endCol;
                }
            } else {
                if (TokenizeUtil.isBlackBorder(current)) {
                    endCol = col2;
                    return endCol;
                }
            }
        }
        return endCol;
    }

}
