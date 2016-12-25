package kshiroma0622.excel2html.matrix;

import kshiroma0622.excel2html.layout.LayoutManager;

/**
 * 行列
 * 
 * @param <E>
 *            各要素の型
 */
public class Matrix<E> {

    /**
     * 無効な型
     */
    public static final Object INVALID = new Object() {
        public String toString() {
            return "INVALID";
        }
    };

    /**
     * 列数
     */
    private final int columns;

    /**
     * 行数
     */
    private final int rows;

    /**
     * matrix
     */
    private Object[][] matrix;

    /**
     * コンストラタク
     * 
     * @param rows
     *            行数
     * @param columns
     *            列数
     */
    public Matrix(int rows, int columns) {
        if (columns < 1) {
            this.columns = 1;
        } else {
            this.columns = columns;

        }
        if (rows < 1) {
            this.rows = 1;
        } else {
            this.rows = rows;
        }

        matrix = new Object[this.rows][this.columns];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                matrix[r][c] = INVALID;
            }
        }
    }

    /**
     * 列数
     * 
     * @return 列数
     */
    public int getColumns() {
        return columns;
    }

    /**
     * 行数
     * 
     * @return 行数
     */
    public int getRows() {
        return rows;
    }

    /**
     * 行列
     * 
     * @return 行列
     */
    public E[][] getMatrix() {
        return (E[][]) matrix;
    }

    /**
     * 取得
     * 
     * @param row
     *            行
     * @param column
     *            列
     * @return 取得
     */
    public E getInvalidAsNull(int row, int column) {
        Object o = matrix[row][column];
        if (o == null) {
            return null;
        }
        if (o == INVALID) {
            return null;
        }
        return (E) o;
    }

    /**
     * 取得
     * 
     * @param row
     *            行
     * @param column
     *            列
     * @return 取得
     */
    public Object getObject(int row, int column) {
        return matrix[row][column];
    }

    /**
     * 割り当て
     * 
     * @param row
     *            行
     * @param column
     *            列
     * @param element
     *            割り当て要素
     */
    public void assign(int row, int column, final E element) {
        if (!withinRange(row, column)) {
            return;
        }

        matrix[row][column] = element;
    }

    /**
     * 割り当て
     * 
     * @param range
     *            範囲
     * @param element
     *            要素
     */
    public void assign(MatrixRange range, E element) {
        if (range == null) {
            return;
        }
        for (int row = range.getRow(); row < range.getRow() + range.getRowspan(); row++) {
            if (row > rows) {
                break;
            }
            for (int col = range.getCol(); col < range.getCol() + range.getColspan(); col++) {
                if (col > columns) {
                    break;
                }
                assign(row, col, element);
            }
        }
    }

    /**
     * クリアする
     * 
     * @param row
     *            行
     * @param column
     *            列
     */
    public void clear(int row, int column) {
        if (!withinRange(row, column)) {
            return;
        }
        matrix[row][column] = INVALID;
    }

    /**
     * 割り当て可能（割り当てずみ->false,outOfRange->false）
     * 
     * @param row
     *            rowIndex
     * @param column
     *            columnIndex
     * @return true/false
     */
    public boolean isAssignable(int row, int column) {
        if (!withinRange(row, column)) {
            return false;
        }
        Object o = getObject(row, column);
        return o == INVALID;
    }

    /**
     * 各indexが範囲内である
     * 
     * @param row
     *            row
     * @param column
     *            column
     * @return true/fale
     */
    public boolean withinRange(int row, int column) {
        if (row < 0 || row >= rows) {
            return false;
        }
        if (column < 0 || column >= columns) {
            return false;
        }
        return true;
    }

    /**
     * 次のあいている箇所を取得する
     * 
     * @param range
     *            範囲
     * @param direction
     *            方向
     * @return nextRange
     */
    public MatrixRange nextRange(//
            final int direction//
    ) {
        int startCol = 0;// range.getCol();
        int startRow = 0;// range.getRow();
        if (direction == LayoutManager.DIRECTION_VERTICAL) {
            for (int col = startCol; col < getColumns(); col++) {
                for (int row = startRow; row < getRows(); row++) {
                    if (isAssignable(row, col)) {
                        return new MatrixRange(row, col);
                    }
                }
            }
            return null;
        }
        if (direction == LayoutManager.DIRECTION_HORIZONTAL) {
            for (int row = startRow; row < getRows(); row++) {
                for (int col = startCol; col < getColumns(); col++) {
                    if (isAssignable(row, col)) {
                        return new MatrixRange(row, col);
                    }
                }
            }
            return null;
        }
        throw new IllegalArgumentException();
    }

    /**
     * その地点がオリジンであるかどうか
     * 
     * @param row
     *            行
     * @param col
     *            列
     * @return true/false
     */
    public boolean isOrigin(int row, int col) {
        if (!withinRange(row, col)) {
            return false;
        }
        Object t = getObject(row, col);
        if (t == null) {
            return true;// たぶんあの場合だと思う。LineBreakをいろいろやってみる。
        }
        if (t == INVALID) {
            return false;
        }
        if (row != 0) {
            Object upon = getObject(row - 1, col);
            if (t.equals(upon)) {
                return false;
            }
        }
        if (col != 0) {
            Object left = getObject(row, col - 1);
            if (t.equals(left)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 作成
     * 
     * @param <T>
     *            型
     * @param rows
     *            行数
     * @param columns
     *            列数
     * @return Matrix
     */
    protected static <T> Matrix<T> newMatrix(int rows, int columns) {
        return new Matrix<T>(rows, columns);
    }

}
