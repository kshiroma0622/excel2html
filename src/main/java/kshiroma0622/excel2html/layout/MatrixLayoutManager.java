package kshiroma0622.excel2html.layout;

import java.util.ArrayList;
import java.util.List;

import kshiroma0622.excel2html.util.Pair;

/**
 * 行列レイアウト
 * 
 * @param <E>
 */
public class MatrixLayoutManager<E> extends AbstractLayoutManager<E> {

    // /**
    // * 方向
    // */
    // private static final int PRIRORITY_DIRECTION = DIRECTION_VERTICAL;

    /**
     * 計算された結果
     */
    private List<Pair<LayoutData, E>> composedList;

    /** 右側 */
    private List<Pair<LayoutData, E>> rightList;

    /**
     * 最大列数0以下のとき自動延長する
     */
    private final int colSize;

    /**
     * 最大行数0以下のとき自動延長する
     */
    private final int rowSize;

    /**
     * 固定列
     */
    private final int fixedColumnPos;

    /**
     * コンストラクタ
     * 
     * @param rowSize
     *            列サイズ
     * @param colSize
     *            行サイズ
     * @param fixedColumnPos
     *            固定列
     */
    public MatrixLayoutManager(int rowSize, int colSize, int fixedColumnPos) {
        if (rowSize > 0) {
            this.rowSize = rowSize;
        } else {
            this.rowSize = 0;
        }
        if (colSize > 0) {
            this.colSize = colSize;
        } else {
            this.colSize = 0;
        }
        if (fixedColumnPos < 0) {
            this.fixedColumnPos = -1;

        } else {
            this.fixedColumnPos = fixedColumnPos;
        }
    }

    /**
     * コンストラタク
     * 
     * @param rowSize
     *            列サイズ
     * @param colSize
     *            行サイズ
     */
    public MatrixLayoutManager(int rowSize, int colSize) {
        this(rowSize, colSize, -1);
    }

    /**
     * 列数
     * 
     * @return 列数
     */
    protected int getColSize() {
        return colSize;
    }

    /**
     * 行数
     * 
     * @return 行数
     */
    protected int getRowSize() {
        return this.rowSize;
    }

    /**
     * composedList
     * 
     * @return composedList
     */
    protected List<Pair<LayoutData, E>> getComposedList() {
        if (composedList == null) {
            composedList = new ArrayList<Pair<LayoutData, E>>();
        }
        return composedList;
    }

    /**
     * 左側の出力
     * 
     * @return 左側の出力
     */
    public Iterable<Pair<LayoutData, E>> getLeftDataRenderOrder() {
        return getComposedList();
    }

    /**
     * 右側の出力
     * 
     * @return 右側の出力
     */
    public Iterable<Pair<LayoutData, E>> getRightDataRenderOrder() {
        if (rightList == null) {
            rightList = new ArrayList<Pair<LayoutData, E>>();
        }
        return rightList;
    }

    /**
     * レイアウトを確定する
     */
    public void compose() {
        if (fixedColumnPos < 0) {
            composedList = compose(getLayoutDataDefinitionOrder(), rowSize, colSize, 0);
            return;
        } else {
            List<Pair<LayoutData, E>> left = Utils.newList();
            List<Pair<LayoutData, E>> right = Utils.newList();
            for (Pair<LayoutData, E> d : getLayoutDataDefinitionOrder()) {
                LayoutData layout = d.getLeft();
                int col = layout.getCol();
                if (col <= fixedColumnPos) {
                    left.add(d);
                } else {
                    right.add(d);
                }
            }
            composedList = compose(left, rowSize, fixedColumnPos + 1, 0);
            rightList = compose(right, rowSize, colSize - fixedColumnPos + 1, fixedColumnPos + 1);
        }
    }

    /**
     * コンポーズ
     * 
     * @param rowSize
     *            rowSize
     * @param colSize
     *            colSize
     * @param layoutDataList
     *            リスト
     * @param offset
     *            offset
     * @return レイアウトリスト
     */
    private List<Pair<LayoutData, E>> compose(Iterable<Pair<LayoutData, E>> layoutDataList,//
                                              int rowSize,//
                                              int colSize, //
                                              int offset//
    ) {
        List<Pair<LayoutData, E>> composedList = new ArrayList<Pair<LayoutData, E>>();

        // マトリクスを作る。
        Matrix<Pair<LayoutData, E>> matrix;
        matrix = Matrix.newMatrix(rowSize, colSize);

        for (Pair<LayoutData, E> d : layoutDataList) {
            LayoutData layout = d.getLeft();

            MatrixRange range = new MatrixRange();

            range.setCol(layout.getCol() - offset);
            range.setRow(layout.getRow());
            range.setColspan(layout.getColspan());
            range.setRowspan(layout.getRowspan());

            matrix.assign(range, d);
        }

        // //正規化を入れたほうがいいかもな。

        for (int row = 0; row < matrix.getRows(); row++) {
            for (int col = 0; col < matrix.getColumns(); col++) {
                // スタート地点かどうか
                if (matrix.isOrigin(row, col)) {
                    Object o = matrix.getObject(row, col);
                    if (o instanceof Pair) {
                        composedList.add((Pair) o);
                    }
                }
            }
            Pair<LayoutData, E> br = Pair.newPair((LayoutData) new BreakLine(), null);
            composedList.add(br);
        }
        return composedList;
    }

    /**
     * レンダリング順にLayoutDataを出力
     * 
     * @return LayoutData
     */
    public Iterable<Pair<LayoutData, E>> getLayoutDataRenderOrder() {
        if (composedList == null) {
            throw new RuntimeException("LayoutManager has not been composed.");
        }
        return composedList;
    }

    /**
     * 行列
     * 
     * @param <E>
     *            各要素の型
     */
    protected static class Matrix<E> {

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
                final MatrixRange range,//
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
                return true;
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

    /**
     * マトリックスの範囲(正方)
     */
    protected static class MatrixRange {

        /**
         * 行
         */
        private int row;

        /**
         * 列
         */
        private int col;

        /**
         * 行またがり
         */
        private int rowspan;

        /**
         * 列またがり
         */
        private int colspan;

        /**
         * 行列範囲
         */
        protected MatrixRange() {
            this(0, 0, 1, 1);
        }

        /**
         * マトリクス範囲
         * 
         * @param row
         *            行
         * @param col
         *            列
         * @param rowspan
         *            行
         * @param colspan
         *            列
         */
        protected MatrixRange(//
                int row,//
                int col,//
                int rowspan,//
                int colspan//
        ) {
            this.row = row;
            this.col = col;
            this.rowspan = rowspan;
            this.colspan = colspan;
        }

        /**
         * 行列
         * 
         * @param row
         *            行
         * @param col
         *            列
         */
        protected MatrixRange(//
                int row,//
                int col//
        ) {
            this(row, col, 1, 1);
        }

        /**
         * Getter row
         * 
         * @return row を戻します。
         */
        public int getRow() {
            return row;
        }

        /**
         * Setter row
         * 
         * @param row
         *            設定する row。
         */
        public void setRow(int row) {
            this.row = row;
        }

        /**
         * Getter col
         * 
         * @return col を戻します。
         */
        public int getCol() {
            return col;
        }

        /**
         * Setter col
         * 
         * @param col
         *            設定する col。
         */
        public void setCol(int col) {
            this.col = col;
        }

        /**
         * Getter colspan
         * 
         * @return colspan を戻します。
         */
        public int getColspan() {
            return colspan;
        }

        /**
         * Setter colspan
         * 
         * @param colspan
         *            設定する colspan。
         */
        public void setColspan(int colspan) {
            this.colspan = colspan;
        }

        /**
         * Getter rowspan
         * 
         * @return rowspan を戻します。
         */
        public int getRowspan() {
            return rowspan;
        }

        /**
         * Setter rowspan
         * 
         * @param rowspan
         *            設定する rowspan。
         */
        public void setRowspan(int rowspan) {
            this.rowspan = rowspan;
        }

        /**
         * 文字列化
         * 
         * @return 幅,高さ,rowspan,colspan
         */
        public String toString() {
            String str = String.format("{%d,%d,%d,%d}", row, col, rowspan, colspan);
            return str;
        }

    }
}
