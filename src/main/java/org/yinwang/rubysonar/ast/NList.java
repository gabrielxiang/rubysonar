package org.yinwang.rubysonar.ast;

import org.jetbrains.annotations.NotNull;
import org.yinwang.rubysonar.State;
import org.yinwang.rubysonar.types.ListType;
import org.yinwang.rubysonar.types.Type;

import java.util.List;


public class NList extends Sequence {

    public NList(@NotNull List<Node> elts, int start, int end) {
        super(elts, start, end);
    }


    @NotNull
    @Override
    public Type transform(State s) {
        if (elts.size() == 0) {
            return new ListType();  // list<unknown>
        }

        ListType listType = new ListType();
        for (Node elt : elts) {
            listType.add(transformExpr(elt, s));
            if (elt instanceof Str) {
                listType.addValue(((Str) elt).value);
            }
        }

        return listType;
    }


    @NotNull
    @Override
    public String toString() {
        return "<List:" + start + ":" + elts + ">";
    }


    @Override
    public void visit(@NotNull NodeVisitor v) {
        if (v.visit(this)) {
            visitNodes(elts, v);
        }
    }
}