package com.eurecom.calcite;

import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.jdbc.JavaTypeFactoryImpl;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.apache.calcite.sql.type.SqlTypeName;

import java.util.HashMap;
import java.util.Map;

public class SycldbSchema extends AbstractSchema {
    private final Map<String, Table> tables;
    private RelDataTypeFactory typeFactory;
    private CalciteSchema rootSchema;


    public SycldbSchema() {
        tables = new HashMap<String, Table>();

        typeFactory = new JavaTypeFactoryImpl();

        RelDataTypeFactory.Builder lineorderType = new RelDataTypeFactory.Builder(typeFactory);
        lineorderType.add("lo_orderkey", SqlTypeName.INTEGER);
        lineorderType.add("lo_linenumber", SqlTypeName.INTEGER);
        lineorderType.add("lo_custkey", SqlTypeName.INTEGER);
        lineorderType.add("lo_partkey", SqlTypeName.INTEGER);
        lineorderType.add("lo_suppkey", SqlTypeName.INTEGER);
        lineorderType.add("lo_orderdate", SqlTypeName.INTEGER);
        lineorderType.add("lo_orderpriority", SqlTypeName.VARCHAR);
        lineorderType.add("lo_shippriority", SqlTypeName.VARCHAR);
        lineorderType.add("lo_quantity", SqlTypeName.INTEGER);

//        lineorderType.add("lo_extendedprice", SqlTypeName.FLOAT);
//        lineorderType.add("lo_ordtotalprice", SqlTypeName.FLOAT);
//        lineorderType.add("lo_discount", SqlTypeName.FLOAT);
//        lineorderType.add("lo_revenue", SqlTypeName.FLOAT);
//        lineorderType.add("lo_supplycost", SqlTypeName.FLOAT);
        lineorderType.add("lo_extendedprice", SqlTypeName.INTEGER);
        lineorderType.add("lo_ordtotalprice", SqlTypeName.INTEGER);
        lineorderType.add("lo_discount", SqlTypeName.INTEGER);
        lineorderType.add("lo_revenue", SqlTypeName.INTEGER);
        lineorderType.add("lo_supplycost", SqlTypeName.INTEGER);

        lineorderType.add("lo_tax", SqlTypeName.INTEGER);
        lineorderType.add("lo_commitdate", SqlTypeName.INTEGER);
//        lineorderType.add("lo_shopmode", SqlTypeName.VARCHAR);
        lineorderType.add("lo_shopmode", SqlTypeName.INTEGER);

        SycldbTable lineorderTable = new SycldbTable("lineorder", lineorderType.build(), 119994746);
        tables.put("lineorder", lineorderTable);

        RelDataTypeFactory.Builder partType = new RelDataTypeFactory.Builder(typeFactory);
        partType.add("p_partkey", SqlTypeName.INTEGER);
//        partType.add("p_name", SqlTypeName.VARCHAR);
//        partType.add("p_mfgr", SqlTypeName.VARCHAR);
//        partType.add("p_category", SqlTypeName.VARCHAR);
//        partType.add("p_brand1", SqlTypeName.VARCHAR);
//        partType.add("p_color", SqlTypeName.VARCHAR);
//        partType.add("p_type", SqlTypeName.VARCHAR);
        partType.add("p_name", SqlTypeName.INTEGER);
        partType.add("p_mfgr", SqlTypeName.INTEGER);
        partType.add("p_category", SqlTypeName.INTEGER);
        partType.add("p_brand1", SqlTypeName.INTEGER);
        partType.add("p_color", SqlTypeName.INTEGER);
        partType.add("p_type", SqlTypeName.INTEGER);

        partType.add("p_size", SqlTypeName.INTEGER);
//        partType.add("p_container", SqlTypeName.VARCHAR);
        partType.add("p_container", SqlTypeName.INTEGER);

        SycldbTable partTable = new SycldbTable("part", partType.build(), 1000000);
        tables.put("part", partTable);

        RelDataTypeFactory.Builder supplierType = new RelDataTypeFactory.Builder(typeFactory);
        supplierType.add("s_suppkey", SqlTypeName.INTEGER);
//        supplierType.add("s_name", SqlTypeName.VARCHAR);
//        supplierType.add("s_address", SqlTypeName.VARCHAR);
//        supplierType.add("s_city", SqlTypeName.VARCHAR);
//        supplierType.add("s_nation", SqlTypeName.VARCHAR);
//        supplierType.add("s_region", SqlTypeName.VARCHAR);
//        supplierType.add("s_phone", SqlTypeName.VARCHAR);
        supplierType.add("s_name", SqlTypeName.INTEGER);
        supplierType.add("s_address", SqlTypeName.INTEGER);
        supplierType.add("s_city", SqlTypeName.INTEGER);
        supplierType.add("s_nation", SqlTypeName.INTEGER);
        supplierType.add("s_region", SqlTypeName.INTEGER);
        supplierType.add("s_phone", SqlTypeName.INTEGER);

        SycldbTable supplierTable = new SycldbTable("supplier", supplierType.build(), 40000);
        tables.put("supplier", supplierTable);

        RelDataTypeFactory.Builder customerType = new RelDataTypeFactory.Builder(typeFactory);
        customerType.add("c_custkey", SqlTypeName.INTEGER);
//        customerType.add("c_name", SqlTypeName.VARCHAR);
//        customerType.add("c_address", SqlTypeName.VARCHAR);
//        customerType.add("c_city", SqlTypeName.VARCHAR);
//        customerType.add("c_nation", SqlTypeName.VARCHAR);
//        customerType.add("c_region", SqlTypeName.VARCHAR);
//        customerType.add("c_phone", SqlTypeName.VARCHAR);
//        customerType.add("c_mktsegment", SqlTypeName.VARCHAR);
        customerType.add("c_name", SqlTypeName.INTEGER);
        customerType.add("c_address", SqlTypeName.INTEGER);
        customerType.add("c_city", SqlTypeName.INTEGER);
        customerType.add("c_nation", SqlTypeName.INTEGER);
        customerType.add("c_region", SqlTypeName.INTEGER);
        customerType.add("c_phone", SqlTypeName.INTEGER);
        customerType.add("c_mktsegment", SqlTypeName.INTEGER);

        SycldbTable customerTable = new SycldbTable("customer", customerType.build(), 600000);
        tables.put("customer", customerTable);

        RelDataTypeFactory.Builder ddateType = new RelDataTypeFactory.Builder(typeFactory);
        ddateType.add("d_datekey", SqlTypeName.INTEGER);
//        ddateType.add("d_date", SqlTypeName.VARCHAR);
//        ddateType.add("d_dayofweek", SqlTypeName.VARCHAR);
//        ddateType.add("d_month", SqlTypeName.VARCHAR);
        ddateType.add("d_date", SqlTypeName.INTEGER);
        ddateType.add("d_dayofweek", SqlTypeName.INTEGER);
        ddateType.add("d_month", SqlTypeName.INTEGER);

        ddateType.add("d_year", SqlTypeName.INTEGER);
        ddateType.add("d_yearmonthnum", SqlTypeName.INTEGER);
//        ddateType.add("d_yearmonth", SqlTypeName.VARCHAR);
        ddateType.add("d_yearmonth", SqlTypeName.INTEGER);

        ddateType.add("d_daynuminweek", SqlTypeName.INTEGER);
        ddateType.add("d_daynuminmonth", SqlTypeName.INTEGER);
        ddateType.add("d_daynuminyear", SqlTypeName.INTEGER);
        ddateType.add("d_monthnuminyear", SqlTypeName.INTEGER);
        ddateType.add("d_weeknuminyear", SqlTypeName.INTEGER);
//        ddateType.add("d_sellingseasin", SqlTypeName.VARCHAR);
        ddateType.add("d_sellingseasin", SqlTypeName.INTEGER);

        ddateType.add("d_lastdayinweekfl", SqlTypeName.INTEGER);
        ddateType.add("d_lastdayinmonthfl", SqlTypeName.INTEGER);
        ddateType.add("d_holidayfl", SqlTypeName.INTEGER);
        ddateType.add("d_weekdayfl", SqlTypeName.INTEGER);

        SycldbTable ddateTable = new SycldbTable("ddate", ddateType.build(), 2556);
        tables.put("ddate", ddateTable);

        rootSchema = CalciteSchema.createRootSchema(true);

        tables.forEach((s, table) -> {
            rootSchema.add(s, table);
        });
    }

    public RelDataTypeFactory getTypeFactory() {
        return typeFactory;
    }

    public CalciteSchema getRootSchema() {
        return rootSchema;
    }

    @Override
    protected Map<String, Table> getTableMap() {
        return tables;
    }
}
