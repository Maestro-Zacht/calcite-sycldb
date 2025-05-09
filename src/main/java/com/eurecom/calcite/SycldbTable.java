package com.eurecom.calcite;

import org.apache.calcite.DataContext;
import org.apache.calcite.adapter.java.AbstractQueryableTable;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.linq4j.QueryProvider;
import org.apache.calcite.linq4j.Queryable;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.impl.AbstractTableQueryable;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

public class SycldbTable extends AbstractQueryableTable implements ScannableTable {
    // TODO: real fields
    private final String tableName;
    private final RelDataType dataType;

    SycldbTable(String tableName, RelDataType dataType) {
        super(Object[].class);
        this.tableName = tableName;
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "SycldbTable {" + tableName + "}";
    }

//    @Override
//    public RelNode toRel(RelOptTable.ToRelContext context, RelOptTable relOptTable) {
//        final RelOptCluster cluster = context.getCluster();
//        return new SycldbTableScan(
//                cluster,
//                cluster.traitSetOf(SycldbRel.SYCLDB),
//                ImmutableList.of(),
//                relOptTable,
//                this
//        );
//    }

    // TODO: actually get row type
    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        return typeFactory.copyType(dataType);
    }

    public List<String> getColumnNames() {
        return this.dataType.getFieldNames();
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public <T> Queryable<T> asQueryable(QueryProvider queryProvider, SchemaPlus schema, String tableName) {
        return new ElasticsearchQueryable<>(queryProvider, schema, this, tableName);
    }

    @Override
    public Enumerable<@Nullable Object[]> scan(DataContext root) {
        return null;
    }

    public static class ElasticsearchQueryable<T> extends AbstractTableQueryable<T> {

        protected ElasticsearchQueryable(QueryProvider queryProvider, SchemaPlus schema, SycldbTable table, String tableName) {
            super(queryProvider, schema, table, tableName);
        }

        @Override
        public Enumerator<T> enumerator() {
            throw new UnsupportedOperationException("enumerator");
        }
    }
}
