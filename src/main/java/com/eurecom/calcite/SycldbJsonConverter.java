package com.eurecom.calcite;

import com.eurecom.calcite.thrift.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;

public class SycldbJsonConverter {
    private List<RelNode> rels;

    public SycldbJsonConverter(String json) {
        JSONObject obj;
        try {
            obj = (JSONObject) new JSONParser().parse(json);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        this.rels = ((List<JSONObject>) obj.get("rels"))
                .stream()
                .map(this::parseRel)
                .toList();
    }

    public List<RelNode> getRels() {
        return rels;
    }

    private RelNode parseRel(JSONObject relObj) {

        long id = Long.parseLong((String) relObj.get("id"));
        String relOp = (String) relObj.get("relOp");
        RelNodeType relOpType = switch (relOp) {
            case "com.eurecom.calcite.SycldbTableScan" -> RelNodeType.TABLE_SCAN;
            case "com.eurecom.calcite.SycldbFilter" -> RelNodeType.FILTER;
            case "com.eurecom.calcite.SycldbProject" -> RelNodeType.PROJECT;
            case "com.eurecom.calcite.SycldbAggregate" -> RelNodeType.AGGREGATE;
            case "com.eurecom.calcite.SycldbJoin" -> RelNodeType.JOIN;
            default -> throw new RuntimeException("Unknown RelNodeType: " + relOp);
        };

        RelNode relNode = new RelNode(id, relOpType);

        if (relOpType == RelNodeType.TABLE_SCAN) {
            List<String> tables = (List<String>) relObj.get("table");
            relNode.tables = tables;
        }

        if (relOpType == RelNodeType.TABLE_SCAN || relOpType == RelNodeType.JOIN) {
            List<Long> inputs = ((List<String>) relObj.get("inputs"))
                    .stream()
                    .map(Long::valueOf)
                    .toList();
            relNode.inputs = inputs;
        }

        if (relOpType == RelNodeType.FILTER || relOpType == RelNodeType.JOIN) {
            relNode.condition = parseExpr((JSONObject) relObj.get("condition"));
        }

        if (relOpType == RelNodeType.JOIN) {
            relNode.joinType = (String) relObj.get("joinType");
        }

        if (relOpType == RelNodeType.PROJECT) {
            relNode.fields = ((List<String>) relObj.get("fields"));
            relNode.exprs = ((List<JSONObject>) relObj.get("exprs"))
                    .stream()
                    .map(this::parseExpr)
                    .toList();
        }

        if (relOpType == RelNodeType.AGGREGATE) {
            relNode.group = (List<Long>) relObj.get("group");

            relNode.aggs = ((List<JSONObject>) relObj.get("aggs"))
                    .stream()
                    .map(this::parseAgg)
                    .toList();
        }

        return relNode;
    }

    private AggType parseAgg(JSONObject obj) {
        return new AggType(
                (String) ((JSONObject) obj.get("agg")).get("name"),
                (List<Long>) obj.get("operands"),
                (String) obj.get("name"),
                (String) ((JSONObject) obj.get("type")).get("type"),
                (boolean) obj.get("distinct")
        );
    }

    private ExprType parseExpr(JSONObject obj) {
        ExprType exprType;

        // column
        if (obj.containsKey("input")) {
            exprType = new ExprType(ExprOption.COLUMN);

            exprType.name = (String) obj.get("name");
            exprType.input = (Long) obj.get("input");
        }
        // expr
        else if (obj.containsKey("op")) {
            exprType = new ExprType(ExprOption.COLUMN);

            exprType.op = (String) ((JSONObject) obj.get("op")).get("name");
            exprType.operands = ((List<JSONObject>) obj.get("operands"))
                    .stream()
                    .map(this::parseExpr)
                    .toList();
        }
        // literal
        else {
            exprType = new ExprType(ExprOption.LITERAL);

            Object literal = obj.get("literal");
            if (literal instanceof Long literalValue) {
                LiteralType literalType = new LiteralType(LiteralOption.LITERAL);
                literalType.value = literalValue;
                exprType.literal = literalType;
            } else {
                List<List<String>> rangeSet = (List<List<String>>) ((JSONObject) literal).get("rangeSet");
                LiteralType literalType = new LiteralType(LiteralOption.RANGE);
                literalType.rangeSet = rangeSet;
                exprType.literal = literalType;
            }
        }

        if (obj.containsKey("type")) {
            exprType.type = (String) ((JSONObject) obj.get("type")).get("type");
        }

        return exprType;
    }
}
