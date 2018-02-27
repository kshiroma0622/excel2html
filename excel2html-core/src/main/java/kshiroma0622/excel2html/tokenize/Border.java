package kshiroma0622.excel2html.tokenize;

public class Border {
    public short color;
    public int width;

    public Border(short color, int width) {
        this.color = color;
        this.width = width;
    }

    public short getColor() {
        return color;
    }

    public int getWidth() {
        return width;
    }

    public String toString() {
        return "color:" + color + "  width:" + width;
    }

}
