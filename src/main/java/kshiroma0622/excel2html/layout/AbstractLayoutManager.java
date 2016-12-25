package kshiroma0622.excel2html.layout;

import java.util.ArrayList;
import java.util.List;

import kshiroma0622.excel2html.util.Pair;

/**
 * レイアウトマネージャ
 */
abstract class AbstractLayoutManager<E> implements LayoutManager<E> {

    /**
     * レイアウト情報
     */
    private List<Pair<LayoutData, E>> dataList = new ArrayList<Pair<LayoutData, E>>();

    /**
     * レイアウト情報を追加する
     * @return 件数
     */
    public int addLayoutData(LayoutData layoutData, E component) {
        int size = dataList.size();
        if (layoutData == null) {
            return size;
        }
        Pair<LayoutData, E> pair = Pair.newPair(layoutData, component);
        dataList.add(pair);
        return size + 1;
    }

    /**
     * LayoutDataを定義順に出力
     * 
     * @return layoutData
     */
    public Iterable<Pair<LayoutData, E>> getLayoutDataDefinitionOrder() {
        return dataList;
    }

    /**
     * レイアウトデータのサイズ
     * 
     * @return 件数
     */
    public int getLayoutDataSize() {
        return dataList.size();
    }

}
