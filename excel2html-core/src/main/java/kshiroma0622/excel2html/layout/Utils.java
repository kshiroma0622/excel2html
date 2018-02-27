package kshiroma0622.excel2html.layout;

import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * ユーティリティクラス
 */
public class Utils {

    /**
     * 無用なオブジェクト
     */
    public static final Object IN_VALID = new Object();

    /**
     * コンストラクタ
     */
    private Utils() {
    }


    /**
     * 列挙のインスタンスを取得する
     *
     * @return 列挙のインスタンス
     */
    public static <T extends Enum> T getEnumByNameIgnoreCase(//
                                                             Class<T> enumType,//
                                                             String name//
    ) {
        if (enumType == null) {
            return null;
        }
        for (T t : enumType.getEnumConstants()) {
            if (StringUtils.equalsIgnoreCase(t.toString(), name)) {
                return t;
            }
        }
        return null;
    }


    /**
     * 繰り返しが空である
     *
     * @param collection collection
     * @return true/false
     */
    public static boolean isEmpty(Collection collection) {
        if (collection == null) {
            return true;
        }
        return collection.isEmpty();
    }

    /**
     * 繰り返しが空である
     *
     */
    public static boolean isEmpty(Object[] collection) {
        if (collection == null) {
            return true;
        }
        return collection.length < 1;
    }


    /**
     * マップを生成
     * @return マップ
     */
    public static <K, V> Map<K, V> newMap() {
        return new HashMap<K, V>();
    }

    /**
     * リストを生成
     *
     * @param <V> 型
     * @return リスト
     */
    public static <V> List<V> newList() {
        return new ArrayList<V>();
    }

    /**
     * Setを生成
     *
     * @param <V> Value
     * @return マップ
     */
    public static <V> Set<V> newSet() {
        return new HashSet<V>();
    }


    /**
     * リストから値を取得する
     *
     * @param <V>   値の型
     * @param list  リスト
     * @param index インデックス
     * @return 値
     */
    public static <V> V get(List<V> list, int index) {
        if (withinRange(list, index)) {
            return list.get(index);
        }
        return null;

    }

    /**
     * 配列から値を取得する
     *
     * @param <V>   値の型
     * @param list  リスト
     * @param index インデックス
     * @return 値
     */
    public static <V> V get(V[] list, int index) {
        if (withinRange(list, index)) {
            return list[index];
        }
        return null;
    }

    /**
     * indexがリストに収まるかを検査する<BR>
     * outOfRangeだったらtrueを返します
     *
     * @param list  list
     * @param index index
     * @return true/false
     */
    public static boolean outOfRange(List list, int index) {
        if (list == null) {
            return true;
        }
        if (index < 0) {
            return true;
        }
        if (index >= list.size()) {
            return true;
        }
        return false;
    }

    /**
     * indexがリストに収まるかを検査する
     *
     * @param list  list
     * @param index index
     * @return true/false
     */
    public static boolean withinRange(List list, int index) {
        return !outOfRange(list, index);
    }

    /**
     * indexがリストに収まるかを検査する
     *
     * @param list  list
     * @param index index
     * @return true/false
     */
    public static boolean outOfRange(Object[] list, int index) {
        if (list == null) {
            return true;
        }
        if (index < 0) {
            return true;
        }
        if (index >= list.length) {
            return true;
        }
        return false;
    }


    /**
     * indexが配列に収まっているかを検査する
     *
     * @param list  配列
     * @param index index
     * @return true/fasel
     */
    public static boolean withinRange(Object[] list, int index) {
        return !outOfRange(list, index);
    }


    /**
     * widthに入るラベルのサイズ
     *
     * @param width 幅
     * @return サイズ
     */
    public static int getSizeOfSimpleText(int width) {
        if (width <= 13) {
            return 0;
        }
        return (width - 13) / 6;
    }


    // /**
    // * NumberExpressionのテスト
    // *
    // * @param args
    // * args
    // */
    // public static void main(String... args) {
    //
    // testNumberExpression("", false);
    // testNumberExpression("a", false);
    // testNumberExpression(",1", false);
    // testNumberExpression("1,", false);
    // testNumberExpression("111,,11", false);
    // testNumberExpression("111a11", false);
    // testNumberExpression("111-1", false);
    // testNumberExpression("-11.11.1", false);
    // testNumberExpression("-1111.1", true);
    // testNumberExpression("01", false);
    // testNumberExpression("0,1", false);
    // testNumberExpression("0.1", true);
    // testNumberExpression("1", true);
    // testNumberExpression("111,111,111,111.00", true);
    // testNumberExpression("-111,111,111,111.00", true);
    // testNumberExpression("-111,111,111,111.00", true);
    // testNumberExpression("111.0,0", false);
    //
    // testNumberExpression("-01,00", false);
    // testNumberExpression("-0,100", false);
    // testNumberExpression("01,00", false);
    // testNumberExpression("011,11", false);
    // }
    //
    // /**
    // * isNumberExpressionのテスト
    // *
    // * @param value
    // * value
    // * @param result
    // * result
    // */
    // private static void testNumberExpression(String value, boolean result) {
    // if (isNumberExpression(value) != result) {
    // System.out.println(value);
    // }
    // }

    // /**
    // * replaceのテスト
    // * @param args
    // */
    // public static void main(String... args) {
    // List<String> list = newList();
    // list.add("a");
    // list.add("b");
    // list.add("C");
    // String str = replace(list, "aho", 1);
    //
    // System.out.println(str);
    // for (String a : list) {
    // System.out.println(a);
    // }
    // }
}
