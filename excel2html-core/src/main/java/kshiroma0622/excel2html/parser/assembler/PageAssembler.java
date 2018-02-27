package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.parser.AssemblerSet;
import kshiroma0622.excel2html.tokenize.IToken;
import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.IUIFragment;
import kshiroma0622.excel2html.fragment.impl.Page;

public class PageAssembler extends AbstractAssembler {

    public Page assemble(ITokenMatrix matrixS) {

        Page page = new Page(matrixS);

        int height = matrixS.getRows();
        // int width = matrixS.getCols();
        // int topRow = 0;
        int beginRow = 0;
        int endRow = matrixS.getLastRowIndex();
        int beginCol = 0;
        int endCol = matrixS.getLastColIndex();

        AssemblerSet assemblers = getAssemblerSet();
        int count = 0;//
        while (beginRow <= endRow && endRow < height && count < 1000) {

            ITokenMatrix subMatrix = matrixS.getSubMatrix(beginRow, endRow, beginCol, endCol);
            ITokenMatrix mat = assemblers.findFirstFragment(subMatrix);
            if (mat != null) {
                IUIFragment t = assemblers.assemble(mat);
                page.addChild(t);
                IToken token = mat.get(0, 0);
                // beginRow = endRow + 1;// matrixS.getRowIndex(token) +
                // if (t instanceof IInline) {
                // //
                // } else if (t instanceof IBlock) {
                // // block同士が並んだ場合にtransienttableをかます
                beginRow = matrixS.getRowIndex(token) + mat.getRows();
                // beginCol = 0;
                // endCol = matrixS.getLastColIndex();
                // }

                // mat.getRows();
                // endRow++;
            } else {
                // 見つからない場合。
                // endRow++;
                break;
                // if (endRow >= height) {
                // beginRow++;
                // endRow = beginRow;
                // }
            }
        }

        return page;
    }

    @Override
    public ITokenMatrix findFirstFragment(ITokenMatrix matrix) {
        return super.findFirstFragment(matrix);
    }

    protected AssemblerSet getAssemblerSet() {
        AssemblerSet set = new AssemblerSet();

        set.append(new BreakLineAssembler());
        set.append(new ButtonAreaAssembler());
        set.append(new TableAssembler());
        set.append(AssemblerSet.getSimpleAssemblerSet());
        return set;
    }
}
