package kshiroma0622.excel2html.fragment.impl;

import kshiroma0622.excel2html.fragment.AbstractUIFragment;
import kshiroma0622.excel2html.fragment.IBlock;
import kshiroma0622.excel2html.fragment.IUIFragment;
import kshiroma0622.excel2html.fragment.WriterWrapper;
import kshiroma0622.excel2html.layout.LayoutData;
import kshiroma0622.excel2html.layout.LayoutManager;
import kshiroma0622.excel2html.layout.LayoutManager.IBreakLine;
import kshiroma0622.excel2html.layout.MatrixLayoutManager;
import kshiroma0622.excel2html.tokenize.IToken;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.tokenize.TokenizeUtil;
import kshiroma0622.excel2html.util.Pair;

public class Table extends AbstractUIFragment implements IBlock {

    private LayoutManager<ITableCell> layoutManager;

    public Table(ITokenMatrix matrix) {
        super(matrix);
    }

    @Override
    public void renderStartTag(WriterWrapper w) {
        compose();
        // layoutManager.getLayoutDataRenderOrder()
        // 幅の計算

        int width = TokenizeUtil.getWidth(getTokenMatrix().getRow(0));
        String widthS = "";
        if (width > 1)
            widthS = String.format(" width=\"%d\"", width);

        w.writeLine(String.format("<table %s class=\"all-border\">", widthS));
    }

    @Override
    public void renderBody(WriterWrapper w) {

        if (getChildren() == null) {
        }
        renderBody(layoutManager.getLayoutDataRenderOrder(), w);
    }

    protected void renderBody(Iterable<Pair<LayoutData, ITableCell>> cells, WriterWrapper w) {

        boolean lineBroke = true;
        for (Pair<LayoutData, ITableCell> pair : cells) {
            // このペアには同じものが入っている。
            LayoutData data = pair.getLeft();
            ITableCell child = pair.getRight();
            // Cell cell = pair.getRight();
            if (lineBroke) {
                w.writeLine("<tr>");
                lineBroke = false;
            }
            if (data instanceof IBreakLine) {
                w.writeLine("</tr>");
                lineBroke = true;
            }

            if (child != null && child instanceof ITableCell) {
                IUIFragment f = (IUIFragment) child;
                f.render(w);
            }
        }
        if (!lineBroke) {
            w.writeLine("</tr>");
            lineBroke = false;
        }
    }

    @Override
    public void renderEndTag(WriterWrapper w) {
        w.writeLine("</table>");
    }

    @Override
    public void addChild(IUIFragment fragment) {
        if (fragment == null) {
            return;
        }
        if (fragment instanceof ITableCell) {
            super.addChild(fragment);
        }
        return;
    }

    public void addChild(ITableCell fragment) {
        super.addChild((IUIFragment) fragment);
    }

    public static interface ITableCell extends LayoutData {

        public int getColspan();

        public void setColSpan(int colSpan);

        public int getRowspan();

        public void setRowSpan(int rowSpan);

    }

    /**
     * コンポーズする
     */
    protected void compose() {

        ITokenMatrix root = getTokenMatrix().getRootMatrix();
        ITokenMatrix m = getTokenMatrix();
        IToken origin = m.get(0, 0);
        int originRow = root.getRowIndex(origin);
        int originCol = root.getColIndex(origin);

        int maxCol = 0;
        int maxRow = 0;
        for (IUIFragment c : getChildren()) {
            if (c instanceof ITableCell) {
                TableCell cell = (TableCell) c;
                int row = cell.getRow();
                int col = cell.getCol();
                cell.setRow(row - originRow);
                cell.setCol(col - originCol);

                col = cell.getColspan() + cell.getCol();
                row = cell.getRowspan() + cell.getRow();
                maxCol = col > maxCol ? col : maxCol;
                maxRow = row > maxRow ? row : maxRow;
            }
        }
        // adjustCellSize(getMyChildren(), rowHeight, colWidth);
        layoutManager = new MatrixLayoutManager<ITableCell>(maxRow, maxCol);
        for (IUIFragment cell : getChildren()) {
            if (cell instanceof TableCell) {
                TableCell t = (TableCell) cell;
                int row = t.getRow();
                int col = t.getCol();
                layoutManager.addLayoutData((ITableCell) cell, (ITableCell) cell);
            }
        }

        layoutManager.compose();
    }

}
