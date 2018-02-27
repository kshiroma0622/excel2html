package kshiroma0622.excel2html.tokenize;

public class TokenMatrix implements ITokenMatrix {

    private int width;
    private int height;

    private IToken[][] matrix;

    private TokenMatrix tMatrix;

    private ITokenMatrix root = this;

    private ITokenMatrix parent = null;

    public TokenMatrix(int width, int height) {
        this(width, height, true);
    }

    private TokenMatrix(int width, int height, boolean createTMatrix) {
        this.width = width;
        this.height = height;

        matrix = new IToken[height][width];
        if (createTMatrix) {
            tMatrix = new TransposeTokenMatrix(height, width, this);
        }

    }

    public void set(final IToken cell, int row, int col) {
        matrix[row][col] = cell;
        tMatrix.set(cell, col, row);
    }

    protected void setWithoutT(final IToken cell, int row, int col) {
        // System.out.println(getClass().getName() + ":" + row + " " + col);
        // if (row == 38) {
        // System.out.println(row);
        // }
        matrix[row][col] = cell;
    }

    public IToken get(int row, int col) {
        return matrix[row][col];
    }

    public int getRows() {
        return height;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    public int getCols() {
        return width;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    public int getLastColIndex() {
        return width - 1;
    }

    public int getLastRowIndex() {
        return height - 1;
    }

    public IToken[] getRow(int row) {
        // エラー回避
        return matrix[row];
    }

    public IToken[] getCol(int col) {
        return tMatrix.getRow(col);
    }

    public int getColIndex(IToken token) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (token == matrix[row][col]) {
                    return col;
                }
            }
        }
        return -1;
    }

    public int getRowIndex(IToken token) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (token == matrix[row][col]) {
                    return row;
                }
            }
        }
        return -1;
    }

    protected void setTmatrix(TokenMatrix matrix) {
        this.tMatrix = matrix;
    }

    /**
     * TokenMatrix
     */
    public ITokenMatrix getSubMatrix(int row1, int row2, int col1, int col2) {

        // エラー回避
        int width = col2 - col1 + 1;
        int height = row2 - row1 + 1;
        TokenMatrix matrix = new TokenMatrix(width, height);
        matrix.root = getRootMatrix();
        matrix.parent = this;
        for (int row = row1; row <= row2; row++) {
            for (int col = col1; col <= col2; col++) {
                IToken token = get(row, col);
                matrix.set(token, row - row1, col - col1);
            }
        }
        return matrix;
    }

    public ITokenMatrix getRootMatrix() {
        return root;
    }

    public ITokenMatrix getParentMatrix() {
        return parent;
    }

    public ITokenMatrix getTransposeMatrix() {
        return tMatrix;
    }

    private static class TransposeTokenMatrix extends TokenMatrix {
        private TransposeTokenMatrix(int width, int height, TokenMatrix t) {
            super(width, height, false);
            setTmatrix(t);
        }

        public void set(final IToken cell, int row, int col) {
            super.setWithoutT(cell, row, col);
        }
    }

}
