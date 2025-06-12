package com.eurecom.calcite.thrift;

// generated code

import com.eurecom.calcite.*;
import org.apache.calcite.config.CalciteConnectionConfig;
import org.apache.calcite.config.CalciteConnectionConfigImpl;
import org.apache.calcite.config.CalciteConnectionProperty;
import org.apache.calcite.plan.*;
import org.apache.calcite.plan.volcano.VolcanoPlanner;
import org.apache.calcite.prepare.CalciteCatalogReader;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.rules.CoreRules;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.sql.SqlExplainFormat;
import org.apache.calcite.sql.SqlExplainLevel;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.validate.SqlValidator;
import org.apache.calcite.sql.validate.SqlValidatorUtil;
import org.apache.calcite.sql2rel.SqlToRelConverter;
import org.apache.calcite.sql2rel.StandardConvertletTable;
import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;

import java.util.Collections;
import java.util.Properties;

public class ServerHandler implements CalciteServer.Iface {
    private TServer server;

    public ServerHandler(TServer server) {
        super();
        this.server = server;
    }

    @Override
    public void ping() throws TException {
        System.out.println("ping()");
    }

    @Override
    public void shutdown() throws TException {
        System.out.println("shutdown()");
//        server.stop();
    }

    private static RelOptCluster newCluster(RelDataTypeFactory factory) {
        RelOptPlanner planner = new VolcanoPlanner();
        planner.addRelTraitDef(ConventionTraitDef.INSTANCE);
        return RelOptCluster.create(planner, new RexBuilder(factory));
    }

    private static final RelOptTable.ViewExpander NOOP_EXPANDER = (rowType, queryString, schemaPath
            , viewPath) -> null;

    @Override
    public PlanResult parse(String sql) {
        // need to trim the sql string as it seems it is not trimed prior to here
        sql = sql.trim();
        // remove last charcter if it is a ;
        if (sql.length() > 0 && sql.charAt(sql.length() - 1) == ';') {
            sql = sql.substring(0, sql.length() - 1);
        }

        SycldbSchema schema = new SycldbSchema();

        // Create an SQL parser
        SqlParser parser = SqlParser.create(sql);
        // Parse the query into an AST

        SqlNode sqlNode = null;
        try {
            sqlNode = parser.parseQuery();
        } catch (SqlParseException e) {
            return null;
        }

        // Configure and instantiate validator
        Properties props = new Properties();
        props.setProperty(CalciteConnectionProperty.CASE_SENSITIVE.camelName(), "false");
        CalciteConnectionConfig config = new CalciteConnectionConfigImpl(props);
        CalciteCatalogReader catalogReader = new CalciteCatalogReader(schema.getRootSchema(),
                Collections.singletonList(""),
                schema.getTypeFactory(), config);

        SqlValidator validator = SqlValidatorUtil.newValidator(SqlStdOperatorTable.instance(),
                catalogReader, schema.getTypeFactory(),
                SqlValidator.Config.DEFAULT);

        // Validate the initial AST
        SqlNode validNode = validator.validate(sqlNode);

        // Configure and instantiate the converter of the AST to Logical plan (requires opt cluster)
        RelOptCluster cluster = newCluster(schema.getTypeFactory());
        SqlToRelConverter relConverter = new SqlToRelConverter(
                NOOP_EXPANDER,
                validator,
                catalogReader,
                cluster,
                StandardConvertletTable.INSTANCE,
                SqlToRelConverter.config());

        // Convert the valid AST into a logical plan
        RelNode logPlan = relConverter.convertQuery(validNode, false, true).rel;

        RelOptPlanner planner = cluster.getPlanner();
        // Initialize optimizer/planner with the necessary rules
        planner.addRule(SycldbFilterRule.INSTANCE);
        planner.addRule(SycldbProjectRule.INSTANCE);
        planner.addRule(SycldbToEnumerableConverterRule.INSTANCE);
        planner.addRule(SycldbTableScanRule.INSTANCE);
        planner.addRule(SycldbJoinRule.INSTANCE);
        planner.addRule(SycldbAggregateRule.INSTANCE);

//        TODO: doesn't work
//        planner.addRule(ProjectTableScanRule.INSTANCE);

        planner.addRule(CoreRules.FILTER_INTO_JOIN);
        planner.addRule(CoreRules.AGGREGATE_MERGE);
        planner.addRule(CoreRules.AGGREGATE_PROJECT_PULL_UP_CONSTANTS);
        planner.addRule(CoreRules.AGGREGATE_PROJECT_MERGE);
//        planner.addRule(CoreRules.AGGREGATE_REMOVE);
//        planner.addRule(CoreRules.AGGREGATE_FILTER_TRANSPOSE);
//        planner.addRule(CoreRules.AGGREGATE_JOIN_JOIN_REMOVE);
//        planner.addRule(CoreRules.AGGREGATE_JOIN_REMOVE);
//        planner.addRule(CoreRules.AGGREGATE_JOIN_TRANSPOSE);
        planner.addRule(CoreRules.FILTER_MERGE);
        planner.addRule(CoreRules.FILTER_AGGREGATE_TRANSPOSE);
        planner.addRule(CoreRules.FILTER_SCAN);
        planner.addRule(CoreRules.PROJECT_AGGREGATE_MERGE);
//        planner.addRule(CoreRules.PROJECT_JOIN_JOIN_REMOVE);
//        planner.addRule(CoreRules.PROJECT_JOIN_REMOVE);
        planner.addRule(CoreRules.PROJECT_MERGE);
        planner.addRule(CoreRules.PROJECT_REMOVE);
        planner.addRule(CoreRules.PROJECT_TO_SEMI_JOIN);
        planner.addRule(CoreRules.PROJECT_JOIN_TRANSPOSE);
        planner.addRule(CoreRules.JOIN_CONDITION_PUSH);
        planner.addRule(CoreRules.JOIN_ADD_REDUNDANT_SEMI_JOIN);
        planner.addRule(CoreRules.JOIN_ON_UNIQUE_TO_SEMI_JOIN);
        planner.addRule(CoreRules.JOIN_TO_SEMI_JOIN);
//        planner.addRule(CoreRules.JOIN_COMMUTE);
//        planner.addRule(CoreRules.JOIN_PUSH_EXPRESSIONS);
//        planner.addRule(CoreRules.JOIN_PUSH_TRANSITIVE_PREDICATES);

        // Define the type of the output plan (in this case we want a physical plan in
        // BindableConvention)
        logPlan = planner.changeTraits(logPlan,
                cluster.traitSet().replace(SycldbRel.SYCLDB));
        planner.setRoot(logPlan);
        // Start the optimization process to obtain the most efficient physical plan based on the
        // provided rule set.
        RelNode phyPlan = planner.findBestExp();
        String json = RelOptUtil.dumpPlan("", phyPlan, SqlExplainFormat.JSON, SqlExplainLevel.NO_ATTRIBUTES);
//
        System.out.println(
                RelOptUtil.dumpPlan("[Physical plan]", phyPlan, SqlExplainFormat.TEXT,
                        SqlExplainLevel.NON_COST_ATTRIBUTES));


        SycldbJsonConverter converter = new SycldbJsonConverter(json);

        return new PlanResult(converter.getRels(), json);
    }
}
