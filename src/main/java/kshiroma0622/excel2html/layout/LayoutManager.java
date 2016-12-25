package kshiroma0622.excel2html.layout;

import kshiroma0622.excel2html.util.Pair;

/**
 * レイアウトマネージャ
 */
public interface LayoutManager<E> {

    /**
     * 水平方向
     */
    public static final int DIRECTION_HORIZONTAL = 0;

    /**
     * 垂直方向
     */
    public static final int DIRECTION_VERTICAL = 1;

    /**
     * レイアウト情報を追加する
     * @return 件数
     */
    public int addLayoutData(LayoutData layoutData, E component);

    // /**
    // * レイアウト情報を追加する
    // *
    // * @param layoutData
    // * レイアウト情報
    // * @param index
    // * index(0,-1,,,最初に追加。length以上)
    // * @return 件数
    // */
    // public int addLayoutData(LayoutData layoutData, int index);

    /**
     * レイアウトデータのサイズ
     * 
     * @return 件数
     */
    public int getLayoutDataSize();

    /**
     * レイアウトを確定する
     */
    public void compose();

    /**
     * レンダリング順にLayoutDataを出力
     * 
     * @return LayoutData
     */
    public Iterable<Pair<LayoutData, E>> getLayoutDataRenderOrder();

    /**
     * LayoutDataを定義順に出力
     * 
     * @return layoutData
     */
    public Iterable<Pair<LayoutData, E>> getLayoutDataDefinitionOrder();

    /**
     * BreakLine
     */
    public static interface IBreakLine {

    }
}
