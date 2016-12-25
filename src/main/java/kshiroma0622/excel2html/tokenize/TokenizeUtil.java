package kshiroma0622.excel2html.tokenize;

import kshiroma0622.excel2html.fragment.FragmentUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Iterator;

public class TokenizeUtil {

    public static final int MAX_COUNT = 1000;
    private static final short RED = 53;
    private static final short WHITE = 64;


    public static int getWidth(IToken... iterable) {
        if (iterable == null) {
            return 0;
        }
        int width = -1;
        for (IToken t : iterable) {
            if (t == null) {
                continue;
            }
            if (width == -1) {
                width = t.getWidth();
            } else {
                width += t.getWidth();
            }
        }
        return width;
    }

    public static int getHeight(IToken... iterable) {
        if (iterable == null) {
            return 0;
        }
        int h = -1;
        for (IToken t : iterable) {
            if (t == null) {
                continue;
            }
            if (h == -1) {
                h = t.getHeight();
            } else {
                h += t.getHeight();
            }
        }
        return h;
    }

    public static boolean isEmptyToken(IToken token) {
        String text = token.getInnerText();
        return StringUtils.isEmpty(text);
    }

    // 内部罫線
    public static boolean hasInnerBorder(ITokenMatrix matrix) {
        if (matrix == null) {
            return false;
        }

        // 自分自身だけ
        if (matrix.getRows() == 1 && matrix.getCols() == 1) {
            return false;
        }

        // /横一列
        if (matrix.getRows() == 1) {
            IToken[] rows = matrix.getRow(0);

            for (int i = 1; i < rows.length - 1; i++) {
                IToken token = rows[i];
                Border left = token.getLeftBorder();
                Border right = token.getRightBorder();
                if (left != null || right != null) {
                    return true;
                }
            }
            return false;
        }
        if (matrix.getCols() == 1) {
            IToken[] cols = matrix.getCol(0);

            for (int i = 1; i < cols.length - 1; i++) {
                IToken token = cols[i];
                Border top = token.getTopBorder();
                Border bottom = token.getBottomBorder();
                if (top != null || bottom != null) {
                    return true;
                }
            }
            return false;
        }


        // 縦一列
        int beginCol = 1;
        int endCol = matrix.getLastColIndex() - 1;
        int beginRow = 1;
        int endRow = matrix.getLastRowIndex() - 1;
        for (IToken token : tokenRolling(matrix, beginRow, endRow, beginCol, endCol)) {
            if (FragmentUtil.hasAnyBorder(token)) {
                return true;
            }
        }
        return false;
    }


    public static Iterable<IToken> tokenRolling(
            final ITokenMatrix matrix, //
            final int row1, final int row2,//
            final int col1, final int col2//
    ) {

        Iterable<IToken> rolling = new Iterable<IToken>() {

            public Iterator<IToken> iterator() {
                Iterator<IToken> itr = new Iterator<IToken>() {
                    private int row = row1;
                    private int col = col1;

                    public IToken next() {
                        IToken t = matrix.get(row, col);
                        if (col < col2) {
                            col++;
                        } else if (col == col2) {
                            col = col1;
                            row++;
                        }
                        return t;
                    }

                    public boolean hasNext() {
                        if (col <= col2 || row < +row2) {
                            return true;
                        }
                        return false;
                    }

                    public void remove() {
                    }
                };
                return itr;
            }
        };
        return rolling;
    }

    public static boolean isValidBorder(Border border) {
        return border != null;
    }


    public static boolean isBlackBorder(Border border) {
        return border != null && border.getColor() != RED;
    }


    public static boolean isLabelCell(IToken token) {
        return token != null && token.getColor() == RED;
    }

}
