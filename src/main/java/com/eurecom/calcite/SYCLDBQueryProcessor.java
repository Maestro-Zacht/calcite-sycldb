package com.eurecom.calcite;

import org.apache.calcite.DataContext;
import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.config.CalciteConnectionConfig;
import org.apache.calcite.config.CalciteConnectionConfigImpl;
import org.apache.calcite.config.CalciteConnectionProperty;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.jdbc.JavaTypeFactoryImpl;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Linq4j;
import org.apache.calcite.linq4j.QueryProvider;
import org.apache.calcite.plan.*;
import org.apache.calcite.plan.volcano.VolcanoPlanner;
import org.apache.calcite.prepare.CalciteCatalogReader;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.rules.CoreRules;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.SqlExplainFormat;
import org.apache.calcite.sql.SqlExplainLevel;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.sql.validate.SqlValidator;
import org.apache.calcite.sql.validate.SqlValidatorUtil;
import org.apache.calcite.sql2rel.SqlToRelConverter;
import org.apache.calcite.sql2rel.StandardConvertletTable;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class SYCLDBQueryProcessor {

    public static void main(String[] args) throws Exception {
        // Instantiate a type factory for creating types (e.g., VARCHAR, NUMERIC, etc.)
        RelDataTypeFactory typeFactory = new JavaTypeFactoryImpl();
        // Create the root schema describing the data model
        CalciteSchema schema = CalciteSchema.createRootSchema(false);
        // Define type for authors table
        RelDataTypeFactory.Builder authorType = new RelDataTypeFactory.Builder(typeFactory);
        authorType.add("id", SqlTypeName.INTEGER);
        authorType.add("fname", SqlTypeName.VARCHAR);
        authorType.add("lname", SqlTypeName.VARCHAR);


        SycldbTable authorTable = new SycldbTable("author", authorType.build());
        schema.add("author", authorTable);

        // Define type for books table
        RelDataTypeFactory.Builder bookType = new RelDataTypeFactory.Builder(typeFactory);
        bookType.add("id", SqlTypeName.INTEGER);
        bookType.add("title", SqlTypeName.VARCHAR);
        bookType.add("year", SqlTypeName.INTEGER);
        bookType.add("author", SqlTypeName.INTEGER);

        SycldbTable bookTable = new SycldbTable("book", bookType.build());
        schema.add("book", bookTable);

        // Create an SQL parser
        SqlParser parser = SqlParser.create(
                "SELECT b.id, b.title, b.\"year\", a.fname || ' ' || a.lname \n"
                        + "FROM Book b\n"
                        + "LEFT OUTER JOIN Author a ON b.author=a.id\n"
                        + "WHERE b.\"year\" > 1830\n"
//                        + "ORDER BY b.id\n"
//                        + "LIMIT 5"
        );
        // Parse the query into an AST
        SqlNode sqlNode = parser.parseQuery();

        // Configure and instantiate validator
        Properties props = new Properties();
        props.setProperty(CalciteConnectionProperty.CASE_SENSITIVE.camelName(), "false");
        CalciteConnectionConfig config = new CalciteConnectionConfigImpl(props);
        CalciteCatalogReader catalogReader = new CalciteCatalogReader(schema,
                Collections.singletonList(""),
                typeFactory, config);

        SqlValidator validator = SqlValidatorUtil.newValidator(SqlStdOperatorTable.instance(),
                catalogReader, typeFactory,
                SqlValidator.Config.DEFAULT);

        // Validate the initial AST
        SqlNode validNode = validator.validate(sqlNode);

        // Configure and instantiate the converter of the AST to Logical plan (requires opt cluster)
        RelOptCluster cluster = newCluster(typeFactory);
        SqlToRelConverter relConverter = new SqlToRelConverter(
                NOOP_EXPANDER,
                validator,
                catalogReader,
                cluster,
                StandardConvertletTable.INSTANCE,
                SqlToRelConverter.config());

        // Convert the valid AST into a logical plan
        RelNode logPlan = relConverter.convertQuery(validNode, false, true).rel;

        // Display the logical plan
        System.out.println(
                RelOptUtil.dumpPlan("[Logical plan]", logPlan, SqlExplainFormat.TEXT,
                        SqlExplainLevel.EXPPLAN_ATTRIBUTES));
//        System.out.println(
//                RelOptUtil.dumpPlan(
//                        "[Logical plan 2]",
//                        logPlan,
//                        SqlExplainFormat.JSON,
//                        SqlExplainLevel.NO_ATTRIBUTES
//                )
//        );

        RelOptPlanner planner = cluster.getPlanner();

        // Initialize optimizer/planner with the necessary rules
        planner.addRule(SycldbFilterRule.INSTANCE);
        planner.addRule(SycldbProjectRule.INSTANCE);
        planner.addRule(SycldbToEnumerableConverterRule.INSTANCE);
        planner.addRule(SycldbTableScanRule.INSTANCE);
        planner.addRule(SycldbJoinRule.INSTANCE);

        planner.addRule(CoreRules.FILTER_INTO_JOIN);

//        planner.addRule(EnumerableRules.ENUMERABLE_TABLE_SCAN_RULE);
//        planner.addRule(CoreRules.PROJECT_TO_CALC);
//        planner.addRule(CoreRules.FILTER_TO_CALC);
//        planner.addRule(EnumerableRules.ENUMERABLE_CALC_RULE);
//        planner.addRule(EnumerableRules.ENUMERABLE_JOIN_RULE);
//        planner.addRule(EnumerableRules.ENUMERABLE_SORT_RULE);
//        planner.addRule(EnumerableRules.ENUMERABLE_LIMIT_RULE);
//        planner.addRule(EnumerableRules.ENUMERABLE_AGGREGATE_RULE);
//        planner.addRule(EnumerableRules.ENUMERABLE_VALUES_RULE);
//        planner.addRule(EnumerableRules.ENUMERABLE_UNION_RULE);
//        planner.addRule(EnumerableRules.ENUMERABLE_MINUS_RULE);
//        planner.addRule(EnumerableRules.ENUMERABLE_INTERSECT_RULE);
//        planner.addRule(EnumerableRules.ENUMERABLE_MATCH_RULE);
//        planner.addRule(EnumerableRules.ENUMERABLE_WINDOW_RULE);


        // Define the type of the output plan (in this case we want a physical plan in
        // BindableConvention)
        logPlan = planner.changeTraits(logPlan,
                cluster.traitSet().replace(SycldbRel.SYCLDB));
        planner.setRoot(logPlan);
        // Start the optimization process to obtain the most efficient physical plan based on the
        // provided rule set.
        RelNode phyPlan = planner.findBestExp();

        // Display the physical plan
        System.out.println(
                RelOptUtil.dumpPlan("[Physical plan]", phyPlan, SqlExplainFormat.TEXT,
                        SqlExplainLevel.NON_COST_ATTRIBUTES));

        // get executable plan
//        Bindable<Object[]> executablePlan = EnumerableInterpretable.toBindable(
//                new HashMap<>(),
//                null,
//                phyPlan,
//                EnumerableRel.Prefer.ARRAY
//        );
//
//        // Run the executable plan using a context simply providing access to the schema
//        for (Object[] row : executablePlan.bind(new SchemaOnlyDataContext(schema))) {
//            System.out.println(Arrays.toString(row));
//        }
    }

    /**
     * A simple table based on a list.
     */
    private static class ListTable extends AbstractTable implements ScannableTable {
        private final RelDataType rowType;
        private final List<Object[]> data;

        ListTable(RelDataType rowType, List<Object[]> data) {
            this.rowType = rowType;
            this.data = data;
        }

        @Override
        public Enumerable<Object[]> scan(final DataContext root) {
            return Linq4j.asEnumerable(data);
        }

        @Override
        public RelDataType getRowType(final RelDataTypeFactory typeFactory) {
            return rowType;
        }
    }

    private static RelOptCluster newCluster(RelDataTypeFactory factory) {
        RelOptPlanner planner = new VolcanoPlanner();
        planner.addRelTraitDef(ConventionTraitDef.INSTANCE);
        return RelOptCluster.create(planner, new RexBuilder(factory));
    }

    private static final RelOptTable.ViewExpander NOOP_EXPANDER = (rowType, queryString, schemaPath
            , viewPath) -> null;

    /**
     * A simple data context only with schema information.
     */
    private static final class SchemaOnlyDataContext implements DataContext {
        private final SchemaPlus schema;

        SchemaOnlyDataContext(CalciteSchema calciteSchema) {
            this.schema = calciteSchema.plus();
        }

        @Override
        public SchemaPlus getRootSchema() {
            return schema;
        }

        @Override
        public JavaTypeFactory getTypeFactory() {
            return new JavaTypeFactoryImpl();
        }

        @Override
        public QueryProvider getQueryProvider() {
            return null;
        }

        @Override
        public Object get(final String name) {
            return null;
        }
    }
}