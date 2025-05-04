namespace java com.eurecom.calcite.thrift

exception InvalidParseRequest {
  1: i32 whatUp
  2: string whyUp
}

struct PlanResult {
    1: string plan_result
    // todo more fields
}

service CalciteServer {
    void ping()
    void shutdown()
    PlanResult parse(1: string sql) throws (1: InvalidParseRequest error)
}