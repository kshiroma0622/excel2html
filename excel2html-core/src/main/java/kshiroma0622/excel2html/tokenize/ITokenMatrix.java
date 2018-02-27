package kshiroma0622.excel2html.tokenize;

public interface ITokenMatrix {

    public int getCols();

    public int getRows();

    public int getLastColIndex();

    public int getLastRowIndex();

    public IToken get(int row, int col);

    public IToken[] getRow(int rowIndex);

    public IToken[] getCol(int col);

    public ITokenMatrix getSubMatrix(int row1, int row2, int col1, int col2);

    public ITokenMatrix getTransposeMatrix();

    public int getColIndex(IToken token);

    public int getRowIndex(IToken token);

    public ITokenMatrix getRootMatrix();

    public ITokenMatrix getParentMatrix();
}
