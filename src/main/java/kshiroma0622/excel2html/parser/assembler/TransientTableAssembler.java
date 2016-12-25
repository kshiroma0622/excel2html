package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.fragment.IUIFragment;
import kshiroma0622.excel2html.fragment.impl.Table;
import kshiroma0622.excel2html.fragment.impl.Table.ITableCell;
import kshiroma0622.excel2html.fragment.impl.TableCell;
import kshiroma0622.excel2html.fragment.impl.TransientTable;
import kshiroma0622.excel2html.layout.LayoutManager;
import kshiroma0622.excel2html.layout.Utils;
import kshiroma0622.excel2html.matrix.Matrix;
import kshiroma0622.excel2html.matrix.MatrixRange;
import kshiroma0622.excel2html.parser.AssemblerSet;
import kshiroma0622.excel2html.tokenize.Border;
import kshiroma0622.excel2html.tokenize.IToken;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.tokenize.TokenizeUtil;

public class TransientTableAssembler extends AbstractAssembler {

    public IUIFragment assemble(ITokenMatrix matrix) {
        Table table = new TransientTable(matrix);
        int beginCol = 0;
        int beginRow = 0;
        int endCol = matrix.getLastColIndex();
        int endRow = matrix.getLastRowIndex();

        boolean mayBeExist = true;
        Matrix<Boolean> flags = new Matrix<Boolean>(endRow + 1, endCol + 1);

        IAssembler assembler = getAssembler();
        int count = 0;
        while (mayBeExist && count++ < 1000) {
            ITokenMatrix m1 = matrix.getSubMatrix(beginRow, endRow, beginCol, endCol);// 全領域
            ITokenMatrix m2 = assembler.findFirstFragment(m1);

            if (m2 != null) {
                TableCell f = (TableCell) assembler.assemble(m2);
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

                if (beginCol < 0 || beginRow < 0) {
                    mayBeExist = false;
                }
            } else {
                mayBeExist = false;
            }
        }
        return null;
    }

    private IAssembler getAssembler() {
        AssemblerSet set = new AssemblerSet();
        // 大きなものから小さなものへと
        set.append(new TableAssembler());
        set.append(new ButtonAreaAssembler());

        set.append(AssemblerSet.getSimpleAssemblerSet());

        return set;

    }

    @Override
    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {
        // trimingを行うだけ
        matrix = trim(matrix);// トリミング
        if (false) {
            int beginRow = 0;
            int endRow = matrix.getLastRowIndex();
            int beginCol = 0;
            int endCol = matrix.getLastColIndex();
        }

        return matrix;
    }

    private ITokenMatrix trim(ITokenMatrix matrix) {
        int beginRow = 0;
        int endRow = matrix.getLastRowIndex();
        int beginCol = 0;
        int endCol = matrix.getLastColIndex();

        {// 上
            int count = 0;
            while (beginRow <= endRow && count++ < TokenizeUtil.MAX_COUNT) {
                IToken[] tokens = matrix.getRow(beginRow);
                if (isEmptyAsTop(tokens)) {
                    beginRow++;
                } else {
                    break;
                }
            }
        }

        {// 下
            int count = 0;
            while (beginRow <= endRow && count++ < TokenizeUtil.MAX_COUNT) {
                IToken[] tokens = matrix.getRow(endRow);
                if (isEmptyAsBottom(tokens)) {
                    endRow--;
                } else {
                    break;
                }
            }
        }

        {// 左
            int count = 0;
            while (beginCol <= endCol && count++ < TokenizeUtil.MAX_COUNT) {
                IToken[] tokens = matrix.getCol(beginCol);
                if (isEmptyAsLeft(tokens)) {
                    beginCol++;
                } else {
                    break;
                }
            }
        }

        {// 右
            int count = 0;
            while (beginCol <= endCol && count++ < TokenizeUtil.MAX_COUNT) {
                IToken[] tokens = matrix.getCol(endCol);
                if (isEmptyAsRight(tokens)) {
                    endCol--;
                } else {
                    break;
                }
            }
        }
        if (beginRow > endRow || beginCol > endCol) {
            return null;
        }
        return matrix.getSubMatrix(beginRow, endRow, beginCol, endCol);
    }

    private boolean isEmptyAsTop(IToken[] tokens) {
        if (Utils.isEmpty(tokens)) {
            return true;
        }
        for (IToken token : tokens) {
            Border top = token.getTopBorder();// 上
            Border left = token.getLeftBorder();// 左
            Border right = token.getRightBorder();// 右
            if (TokenizeUtil.isValidBorder(top) || TokenizeUtil.isValidBorder(left) && TokenizeUtil.isValidBorder(right)) {
                return false;
            }
            // プラスして、空ではない
            if (!TokenizeUtil.isEmptyToken(token)) {
                return false;
            }
        }
        return true;
    }

    private boolean isEmptyAsBottom(IToken[] tokens) {
        if (Utils.isEmpty(tokens)) {
            return true;
        }
        for (IToken token : tokens) {
            // Border top = token.getTopBorder();// 上
            Border left = token.getLeftBorder();// 左
            Border right = token.getRightBorder();// 右
            Border bottom = token.getBottomBorder();// 下
            if (TokenizeUtil.isValidBorder(bottom) || TokenizeUtil.isValidBorder(left) && TokenizeUtil.isValidBorder(right)) {
                return false;
            }
            // プラスして、空ではない
            if (!TokenizeUtil.isEmptyToken(token)) {
                return false;
            }
        }
        return true;
    }

    private boolean isEmptyAsRight(IToken[] tokens) {
        if (Utils.isEmpty(tokens)) {
            return true;
        }
        for (IToken token : tokens) {
            Border top = token.getTopBorder();// 上
            // Border left = token.getLeftBorder();// 左
            Border right = token.getRightBorder();// 右
            Border bottom = token.getBottomBorder();// 下
            if (TokenizeUtil.isValidBorder(bottom) || TokenizeUtil.isValidBorder(top) && TokenizeUtil.isValidBorder(right)) {
                return false;
            }
            // 空ではない
            if (!TokenizeUtil.isEmptyToken(token)) {
                return false;
            }
        }
        return true;
    }

    private boolean isEmptyAsLeft(IToken[] tokens) {
        if (Utils.isEmpty(tokens)) {
            return true;
        }
        for (IToken token : tokens) {
            Border top = token.getTopBorder();// 上
            Border left = token.getLeftBorder();// 左
            // Border right = token.getRightBorder();// 右
            Border bottom = token.getBottomBorder();// 下
            if (TokenizeUtil.isValidBorder(bottom) || TokenizeUtil.isValidBorder(top) && TokenizeUtil.isValidBorder(left)) {
                return false;
            }
            // 空ではない
            if (!TokenizeUtil.isEmptyToken(token)) {
                return false;
            }
        }
        return true;
    }

}
