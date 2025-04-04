package com.eurecom.calcite;

import com.google.common.collect.ImmutableList;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.TranslatableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeName;

public class SycldbTable extends AbstractTable implements TranslatableTable {
    // TODO: real fields
    private final String tableName;

    SycldbTable(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "SycldbTable {" + tableName + "}";
    }

    @Override
    public RelNode toRel(RelOptTable.ToRelContext context, RelOptTable relOptTable) {
        final RelOptCluster cluster = context.getCluster();
        return new SycldbTableScan(
                cluster,
                cluster.traitSetOf(SycldbRel.SYCLDB),
                ImmutableList.of(),
                relOptTable,
                this
        );
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        final RelDataType mapType =
                typeFactory.createMapType(
                        typeFactory.createSqlType(SqlTypeName.VARCHAR),
                        typeFactory.createTypeWithNullability(
                                typeFactory.createSqlType(SqlTypeName.ANY), true));
        return typeFactory.builder().add("_MAP", mapType).build();
    }
}
