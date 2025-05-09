package com.eurecom.calcite;

import org.apache.calcite.plan.*;
import org.apache.calcite.rel.core.TableScan;
import org.apache.calcite.rel.hint.RelHint;
import org.apache.calcite.rel.metadata.RelMetadataQuery;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

import static java.util.Objects.requireNonNull;

public final class SycldbTableScan extends TableScan implements SycldbRel {
    SycldbTable sycldbTable;

    SycldbTableScan(RelOptCluster cluster, RelTraitSet traitSet, List<RelHint> hints, RelOptTable table, SycldbTable sycldbTable) {
        super(cluster, traitSet, hints, table);
        this.sycldbTable = sycldbTable;
    }

    @Override
    public void implement(Implementor implementor) {
        implementor.table = table;
        implementor.sycldbTable = sycldbTable;
    }

//    @Override
//    public int convertPlan(TreeConverter converter) {
//        return converter.addTableColumns(sycldbTable.getColumnNames(), sycldbTable.getTableName());
//    }


    @Override
    public @Nullable RelOptCost computeSelfCost(RelOptPlanner planner, RelMetadataQuery mq) {
        return requireNonNull(
                super.computeSelfCost(planner, mq),
                "cost"
        ).multiplyBy(0.1);
    }
}
