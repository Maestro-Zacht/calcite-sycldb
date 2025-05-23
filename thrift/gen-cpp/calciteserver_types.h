/**
 * Autogenerated by Thrift Compiler (0.21.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#ifndef calciteserver_TYPES_H
#define calciteserver_TYPES_H

#include <iosfwd>

#include <thrift/Thrift.h>
#include <thrift/TApplicationException.h>
#include <thrift/TBase.h>
#include <thrift/protocol/TProtocol.h>
#include <thrift/transport/TTransport.h>

#include <functional>
#include <memory>




struct RelNodeType {
  enum type {
    TABLE_SCAN = 0,
    FILTER = 1,
    JOIN = 2,
    PROJECT = 3,
    AGGREGATE = 4
  };
};

extern const std::map<int, const char*> _RelNodeType_VALUES_TO_NAMES;

std::ostream& operator<<(std::ostream& out, const RelNodeType::type& val);

std::string to_string(const RelNodeType::type& val);

struct ExprOption {
  enum type {
    LITERAL = 0,
    COLUMN = 1,
    EXPR = 2
  };
};

extern const std::map<int, const char*> _ExprOption_VALUES_TO_NAMES;

std::ostream& operator<<(std::ostream& out, const ExprOption::type& val);

std::string to_string(const ExprOption::type& val);

struct LiteralOption {
  enum type {
    LITERAL = 0,
    RANGE = 1
  };
};

extern const std::map<int, const char*> _LiteralOption_VALUES_TO_NAMES;

std::ostream& operator<<(std::ostream& out, const LiteralOption::type& val);

std::string to_string(const LiteralOption::type& val);

class AggType;

class LiteralType;

class ExprType;

class RelNode;

class PlanResult;

typedef struct _AggType__isset {
  _AggType__isset() : agg(false), operands(false), name(false), type(false), distinct(false) {}
  bool agg :1;
  bool operands :1;
  bool name :1;
  bool type :1;
  bool distinct :1;
} _AggType__isset;

class AggType : public virtual ::apache::thrift::TBase {
 public:

  AggType(const AggType&);
  AggType& operator=(const AggType&);
  AggType() noexcept;

  virtual ~AggType() noexcept;
  std::string agg;
  std::vector<int64_t>  operands;
  std::string name;
  std::string type;
  bool distinct;

  _AggType__isset __isset;

  void __set_agg(const std::string& val);

  void __set_operands(const std::vector<int64_t> & val);

  void __set_name(const std::string& val);

  void __set_type(const std::string& val);

  void __set_distinct(const bool val);

  bool operator == (const AggType & rhs) const;
  bool operator != (const AggType &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const AggType & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot) override;
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const override;

  virtual void printTo(std::ostream& out) const;
};

void swap(AggType &a, AggType &b);

std::ostream& operator<<(std::ostream& out, const AggType& obj);

typedef struct _LiteralType__isset {
  _LiteralType__isset() : literalOption(false), value(false), rangeSet(false) {}
  bool literalOption :1;
  bool value :1;
  bool rangeSet :1;
} _LiteralType__isset;

class LiteralType : public virtual ::apache::thrift::TBase {
 public:

  LiteralType(const LiteralType&);
  LiteralType& operator=(const LiteralType&);
  LiteralType() noexcept;

  virtual ~LiteralType() noexcept;
  /**
   * 
   * @see LiteralOption
   */
  LiteralOption::type literalOption;
  int64_t value;
  std::vector<std::vector<std::string> >  rangeSet;

  _LiteralType__isset __isset;

  void __set_literalOption(const LiteralOption::type val);

  void __set_value(const int64_t val);

  void __set_rangeSet(const std::vector<std::vector<std::string> > & val);

  bool operator == (const LiteralType & rhs) const;
  bool operator != (const LiteralType &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const LiteralType & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot) override;
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const override;

  virtual void printTo(std::ostream& out) const;
};

void swap(LiteralType &a, LiteralType &b);

std::ostream& operator<<(std::ostream& out, const LiteralType& obj);

typedef struct _ExprType__isset {
  _ExprType__isset() : exprType(false), input(false), name(false), op(false), operands(false), literal(false), type(false) {}
  bool exprType :1;
  bool input :1;
  bool name :1;
  bool op :1;
  bool operands :1;
  bool literal :1;
  bool type :1;
} _ExprType__isset;

class ExprType : public virtual ::apache::thrift::TBase {
 public:

  ExprType(const ExprType&);
  ExprType& operator=(const ExprType&);
  ExprType() noexcept;

  virtual ~ExprType() noexcept;
  /**
   * 
   * @see ExprOption
   */
  ExprOption::type exprType;
  int64_t input;
  std::string name;
  std::string op;
  std::vector<ExprType>  operands;
  LiteralType literal;
  std::string type;

  _ExprType__isset __isset;

  void __set_exprType(const ExprOption::type val);

  void __set_input(const int64_t val);

  void __set_name(const std::string& val);

  void __set_op(const std::string& val);

  void __set_operands(const std::vector<ExprType> & val);

  void __set_literal(const LiteralType& val);

  void __set_type(const std::string& val);

  bool operator == (const ExprType & rhs) const;
  bool operator != (const ExprType &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const ExprType & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot) override;
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const override;

  virtual void printTo(std::ostream& out) const;
};

void swap(ExprType &a, ExprType &b);

std::ostream& operator<<(std::ostream& out, const ExprType& obj);

typedef struct _RelNode__isset {
  _RelNode__isset() : id(false), relOp(false), tables(false), inputs(false), condition(false), joinType(false), fields(false), exprs(false), group(false), aggs(false) {}
  bool id :1;
  bool relOp :1;
  bool tables :1;
  bool inputs :1;
  bool condition :1;
  bool joinType :1;
  bool fields :1;
  bool exprs :1;
  bool group :1;
  bool aggs :1;
} _RelNode__isset;

class RelNode : public virtual ::apache::thrift::TBase {
 public:

  RelNode(const RelNode&);
  RelNode& operator=(const RelNode&);
  RelNode() noexcept;

  virtual ~RelNode() noexcept;
  int64_t id;
  /**
   * 
   * @see RelNodeType
   */
  RelNodeType::type relOp;
  std::vector<std::string>  tables;
  std::vector<int64_t>  inputs;
  ExprType condition;
  std::string joinType;
  std::vector<std::string>  fields;
  std::vector<ExprType>  exprs;
  std::vector<int64_t>  group;
  std::vector<AggType>  aggs;

  _RelNode__isset __isset;

  void __set_id(const int64_t val);

  void __set_relOp(const RelNodeType::type val);

  void __set_tables(const std::vector<std::string> & val);

  void __set_inputs(const std::vector<int64_t> & val);

  void __set_condition(const ExprType& val);

  void __set_joinType(const std::string& val);

  void __set_fields(const std::vector<std::string> & val);

  void __set_exprs(const std::vector<ExprType> & val);

  void __set_group(const std::vector<int64_t> & val);

  void __set_aggs(const std::vector<AggType> & val);

  bool operator == (const RelNode & rhs) const;
  bool operator != (const RelNode &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const RelNode & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot) override;
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const override;

  virtual void printTo(std::ostream& out) const;
};

void swap(RelNode &a, RelNode &b);

std::ostream& operator<<(std::ostream& out, const RelNode& obj);

typedef struct _PlanResult__isset {
  _PlanResult__isset() : rels(false), oldJson(false) {}
  bool rels :1;
  bool oldJson :1;
} _PlanResult__isset;

class PlanResult : public virtual ::apache::thrift::TBase {
 public:

  PlanResult(const PlanResult&);
  PlanResult& operator=(const PlanResult&);
  PlanResult() noexcept;

  virtual ~PlanResult() noexcept;
  std::vector<RelNode>  rels;
  std::string oldJson;

  _PlanResult__isset __isset;

  void __set_rels(const std::vector<RelNode> & val);

  void __set_oldJson(const std::string& val);

  bool operator == (const PlanResult & rhs) const;
  bool operator != (const PlanResult &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const PlanResult & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot) override;
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const override;

  virtual void printTo(std::ostream& out) const;
};

void swap(PlanResult &a, PlanResult &b);

std::ostream& operator<<(std::ostream& out, const PlanResult& obj);



#endif
