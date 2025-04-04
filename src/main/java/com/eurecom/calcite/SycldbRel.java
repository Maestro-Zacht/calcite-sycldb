package com.eurecom.calcite;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.runtime.PairList;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface SycldbRel extends RelNode {
    Convention SYCLDB = new Convention.Impl("SYCLDB", SycldbRel.class);

    void implement(Implementor implementor);

    class Implementor {
        final PairList<@Nullable String, String> list = PairList.of();
        final RexBuilder rexBuilder;

        @Nullable
        RelOptTable table;
        
        @Nullable
        SycldbTable sycldbTable;

        public Implementor(RexBuilder rexBuilder) {
            this.rexBuilder = rexBuilder;
        }

        public void add(@Nullable String findOp, String aggOp) {
            list.add(findOp, aggOp);
        }

        public void visitChild(int ordinal, RelNode input) {
            assert ordinal == 0;
            ((SycldbRel) input).implement(this);
        }
    }
}
