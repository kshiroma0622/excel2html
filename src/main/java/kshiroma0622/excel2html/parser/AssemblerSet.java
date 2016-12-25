package kshiroma0622.excel2html.parser;

import kshiroma0622.excel2html.fragment.IUIFragment;
import kshiroma0622.excel2html.matrix.AbstractPrioritySet;
import kshiroma0622.excel2html.parser.assembler.*;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;

public class AssemblerSet extends AbstractPrioritySet<IAssembler> implements IAssembler {

    private IAssembler current = null;

    public IUIFragment assemble(ITokenMatrix matrix) {
        if (current != null) {
            return current.assemble(matrix);
        }
        return null;
    }

    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {
        for (IAssembler assembler : this) {
            ITokenMatrix sub = assembler.findFirstFragment(matrix);
            if (sub != null) {
                current = assembler;
                return sub;
            } else {
                continue;
            }
        }
        current = null;
        return null;
    }

    // シンプルアセンブラーズ
    public static AssemblerSet getTableCellInnerAssemblerSet() {
        AssemblerSet set = new AssemblerSet();

        set.append(new LabelAssembler());// /ラベル
        set.append(new InputWithSearchIconAssembler());// 入力テキストとアイコン
        set.append(new InputAssembler());// 入力テキスト
        set.append(new SearchIconAssembler()); //
        set.append(new CheckBoxAssembler());// チェックボックす
        set.append(new RadioAssembler());// ラジオ
        set.append(new ButtonAssembler());// ボタン
        set.append(new PulldownAssembler());// プルダウン
        set.append(new LiteralAssembler());// リテラル
        return set;
    }

    public static AssemblerSet getSimpleAssemblerSet() {
        AssemblerSet set = new AssemblerSet();

        set.append(new LabelAssembler());// /ラベル
        set.append(new InputWithSearchIconAssembler());// 入力テキストとアイコン
        set.append(new InputAssembler());// 入力テキスト
        set.append(new SearchIconAssembler()); //
        set.append(new CheckBoxAssembler());// チェックボックす
        set.append(new RadioAssembler());// ラジオ
        set.append(new ButtonAssembler());// ボタン
        set.append(new LiteralAssembler());// リテラル
        return set;
    }

}
