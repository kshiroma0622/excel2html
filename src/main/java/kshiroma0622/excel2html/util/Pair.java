package kshiroma0622.excel2html.util;

/**
 * ペア<br>
 * コンストラクタで渡した2つのオブジェクトを格納するコンテナです。<br>
 * コンストラタクの第1引数はgetLeftで、第2引数はgetRightで取得できます。<br>
 */
public class Pair<T1, T2> {

    /**
     * 値1
     */
    private final T1 left;

    /**
     * 値1
     */
    private final T2 right;

    /**
     * コンストラタク
     *
     * @param left  left
     * @param right right
     */
    public Pair(T1 left, T2 right) {
        this.left = left;
        this.right = right;
    }

    /**
     * コンストラクタの第一引数に対応します。
     *
     * @return コンストラクタの第一引数
     */
    public T1 getLeft() {
        return left;
    }

    /**
     * コンストラタクの第二引数に対応します。
     *
     * @return コンストラタクの第二引数
     */
    public T2 getRight() {
        return right;
    }


    /**
     * ペアを生成
     *
     * @param <T1>  型１
     * @param <T2>  型２
     * @param left  値１
     * @param right 値２
     * @return ペア
     */
    public static <T1, T2> Pair<T1, T2> newPair(T1 left, T2 right) {
        return new Pair<>(left, right);
    }
}
