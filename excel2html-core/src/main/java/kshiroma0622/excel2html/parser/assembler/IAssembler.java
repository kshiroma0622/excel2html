package kshiroma0622.excel2html.parser.assembler;

import kshiroma0622.excel2html.tokenize.ITokenMatrix;
import kshiroma0622.excel2html.fragment.IUIFragment;

///内部の構造を組み立てるのでassmblerになる。
public interface IAssembler<T extends IUIFragment> {

    /**
     * 内部構造を含めて組み立てる。
     */
    public IUIFragment assemble(ITokenMatrix matrix);

    public ITokenMatrix findFirstFragment(ITokenMatrix matrix);

}
