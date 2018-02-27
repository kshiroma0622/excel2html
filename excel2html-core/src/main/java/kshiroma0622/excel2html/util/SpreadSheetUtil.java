package kshiroma0622.excel2html.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;

public final class SpreadSheetUtil {

    private SpreadSheetUtil() {
    }


    public static String getValueAsString(final Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {
        }
        return null;
    }

    public static Workbook createWorkbook(byte[] data) {
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(data);
            return createWorkBookWithClose(in);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }


    /**
     * ファイルからworkbookを生成
     */
    public static Workbook createWorkbookWithClose(File file) {
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            return createWorkBookWithClose(in);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }


    /**
     * workbookの作成
     */
    private static Workbook createWorkBookWithClose(final InputStream in) {
        Workbook workbook = null;
        try {

            workbook = WorkbookFactory.create(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return workbook;
    }


    /**
     * スプレッドシートの列名をindexに変換する
     */
    public static int getCellIndex(String str) {
        int index = -1;
        if (!StringUtils.isAlpha(str)) {
            return index;
        }
        // index = 0;
        char[] chars = StringUtils.reverse(str).toUpperCase().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            // System.out.println(c);
            int tmp = (int) c - 'A' + 1;
            // System.out.println(tmp);
            tmp *= (int) Math.pow(26, i);
            // System.out.println(tmp);
            index += tmp;
            // System.out.println(index);
        }
        return index;
    }


    /**
     * Cellの取得<br>
     * nullの場合、Blankセルとしてcreateする
     */
    public static Cell getCellNullAsBlank(final Sheet sheet, int rowIndex,
                                          int colIndex) {

        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        return row.getCell(colIndex, Row.CREATE_NULL_AS_BLANK);
    }


    /**
     * rowからcellを取得する
     *
     * @param row   行
     * @param index index
     * @return cell
     */
    public static Cell getCell(final Row row, int index) {
        if (row == null) {
            return null;
        }
        return row.getCell(index, Row.CREATE_NULL_AS_BLANK);
    }


    /**
     * sheetを走査する
     */
    public static final void sheetRun(//
                                      Cell startCell,//
                                      Cell endCell, //
                                      Direction direction, //
                                      CellProcess process//
    ) {
        RunningProcess running = new RunningProcess(startCell, endCell,
                direction);
        running.run(process);
    }


    /**
     * 走査の方向
     */
    public enum Direction {
        /**
         * 垂直方向
         */
        Vertical,
        /**
         * 水平方向
         */
        Horizontal;

        /**
         * デフォルト値
         *
         * @return デフォルト値
         */
        public Direction getDefault() {
            return Horizontal;
        }

        /**
         * もう一方を返す
         *
         * @return もう一方
         */
        public Direction inverse() {
            if (this == Vertical) {
                return Horizontal;
            } else {
                return Vertical;
            }
        }

    }

    /**
     * CellProcess
     */
    public static interface CellProcess {

        /**
         * セルプロセス
         *
         * @param cell cell
         */
        public void process(final Cell cell);
    }

    /**
     * シート走査
     */
    private static class RunningProcess {

        /**
         * 開始セル
         */
        private final Cell startCell;
        /**
         * 終了セル
         */
        private final Cell endCell;

        /**
         * 走査の方向
         */
        private final Direction direction;

        /**
         * カレントセル
         */
        private Cell currentCell;

        /**
         * コンストラクタ
         */
        public RunningProcess(//
                              Cell startCell, //
                              Cell endCell, //
                              Direction direction//
        ) {
            this.startCell = startCell;
            this.endCell = endCell;
            this.direction = direction;
        }

        /**
         * 走りはじめる
         *
         * @param cellProcess 各セルで行う操作
         */
        public void run(CellProcess cellProcess) {
            currentCell = startCell;
            do {
                cellProcess.process(currentCell);
                currentCell = nextCell();
            } while (!isEnd(currentCell));
        }

        /**
         * next
         *
         * @return cell
         */
        private Cell nextCell() {
            int rowIndex = currentCell.getRowIndex();
            int colIndex = currentCell.getColumnIndex();
            boolean turn = isTurn(currentCell);

            if (direction == Direction.Vertical) {
                if (!turn) {
                    rowIndex++;

                } else {
                    colIndex++;
                    rowIndex = startCell.getRowIndex();
                }
            } else {
                if (!turn) {
                    colIndex++;
                } else {
                    colIndex = startCell.getColumnIndex();
                    rowIndex++;
                }
            }
            Row nextRow = currentCell.getSheet().getRow(rowIndex);
            if (nextRow == null) {
                nextRow = currentCell.getSheet().createRow(rowIndex);
            }
            return getCell(nextRow, colIndex);
        }

        /**
         * 現在のセルを返す
         *
         * @return cell
         */
        public final Cell getCurrentCell() {
            return currentCell;
        }

        /**
         * 走査のターン
         *
         * @param cell Cell
         * @return true/false
         */
        public boolean isTurn(final Cell cell) {
            if (direction == Direction.Vertical) {
                int endRowIndex = endCell.getRowIndex();
                int rowIndex = cell.getRowIndex();
                if (endRowIndex == rowIndex) {
                    return true;
                }
            } else {// Horizontal
                int endColIndex = endCell.getColumnIndex();
                int colIndex = cell.getColumnIndex();
                if (endColIndex == colIndex) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 走査の終了
         *
         * @param cell Cell
         * @return true/false
         */
        public boolean isEnd(final Cell cell) {
            // if (cell.equals(endCell)) {//
            // return true;
            // }
            // 指しているものが同じだという保障がいまのところないので、cell.equals(endCell)はしない。
            int endRowIndex = endCell.getRowIndex();
            int endColIndex = endCell.getColumnIndex();
            int rowIndex = cell.getRowIndex();
            int colIndex = cell.getColumnIndex();
            return endRowIndex < rowIndex || endColIndex < colIndex;
        }
    }


}
