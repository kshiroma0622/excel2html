package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.tokenize.IToken;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;

public abstract class AbstractAssembler implements IAssembler {

    /**
     * ITokenMatrix
     */
    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {
        return matrix;
    }

    public ITokenMatrix getSurrounded(ITokenMatrix matrix) {
        if (matrix == null) {
            return null;
        }
        IToken token = matrix.get(0, 0);

        if (token.getTopBorder() != null || token.getLeftBorder() != null) {
            return null;
        }
        int col = 0;
        int row = 0;

        int col1 = 0;
        int col2 = -1;
        int row1 = 0;
        int row2 = -1;

        for (row = 0; row < matrix.getRows(); row++) {
            IToken t = matrix.get(row, col);
            if (t.getLeftBorder() != null) {
                break;
            }
            if (t.getBottomBorder() != null) {
                row2 = row;
            }
        }

        if (row2 == -1) {
            return null;
        }
        for (col = col1; col < matrix.getCols(); col++) {
            // IToken t = matrix.get() ;
        }
        return null;
    }

}
