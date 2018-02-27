package kshiroma0622.excel2html.matrix;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractPrioritySet<T> implements Iterable<T> {
    private Set<T> set = new LinkedHashSet<T>();

    public Iterator<T> iterator() {
        return set.iterator();
    }

    public void append(T... assemblers) {
        for (T assembler : assemblers) {
            set.add(assembler);
        }
    }

    protected Set<T> getInnterLinkedSet() {
        return set;
    }

}
