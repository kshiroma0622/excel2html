package kshiroma0622.excel2html.fragment.impl;

import kshiroma0622.excel2html.layout.Align;
import kshiroma0622.excel2html.layout.VAlign;

import org.apache.commons.lang.StringUtils;

import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.AbstractUIFragment;
import kshiroma0622.excel2html.fragment.IUIFragment;
import kshiroma0622.excel2html.fragment.WriterWrapper;
import kshiroma0622.excel2html.fragment.impl.Table.ITableCell;

public class TableCell extends AbstractUIFragment implements ITableCell {

    public TableCell(ITokenMatrix matrix) {
        super(matrix);
    }

    public void setAlign(Align align) {
        this.align = align;
    }

    public void setVAlign(VAlign align) {
        vAlign = align;
    }

    public int getColspan() {
        return colSpan;
    }

    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }

    public int getRowspan() {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getHeight() {
        return height;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public void renderStartTag(WriterWrapper w) {

        String rendered;
        rendered = String.format("<td %s%s%s%s%s%s%s >",//
                getAlign(),//
                getVAlign(),//
                getColSpan(),//
                getRowSpan(),//
                getStyleClass(),//
                getWidthAttr(),//
                getHeightAttr()//
                );
        w.writeLine(rendered);
    }

    private String getAlign() {
        if (align == null) {
            return "";
        }
        return String.format(" align=\"%s\"", align.toString().toLowerCase());
    }

    private String getVAlign() {
        if (align == null) {
            return "";
        }
        return String.format("valign=\"%s\"", vAlign.toString().toLowerCase());
    }

    private String getColSpan() {
        if (colSpan < 1) {
            return "";
        }
        return String.format(" colspan=\"%d\"", colSpan);
    }

    private String getRowSpan() {
        if (rowSpan < 1) {
            return "";
        }
        return String.format(" rowspan=\"%d\"", rowSpan);
    }

    private String getStyleClass() {
        if (StringUtils.isEmpty(styleClass)) {
            return "";
        }
        return String.format(" class=\"%s\"", styleClass);
    }

    private String getWidthAttr() {
        if (width < 1) {
            return "";
        }
        return String.format(" width=\"%d\"", width);
    }

    private String getHeightAttr() {
        if (height < 1) {
            return "";
        }
        return String.format(" height=\"%d\"", height);
    }

    @Override
    public void renderBody(WriterWrapper w) {
        if (getChildren() == null) {
            // 子がない場合は&nbsp;を出力する
            w.writeLine("&nbsp;");
            return;
        }
        for (IUIFragment f : getChildren()) {
            f.render(w);
        }
    }

    @Override
    public void renderEndTag(WriterWrapper w) {
        w.writeLine("</td>");
    }

    private Align align;
    private VAlign vAlign;

    private int colSpan = -1;
    private int rowSpan = -1;

    private String styleClass;

    private int width = 0;
    private int height = 0;
    private int col;
    private int row;
}
