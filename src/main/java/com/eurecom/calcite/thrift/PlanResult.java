/**
 * Autogenerated by Thrift Compiler (0.21.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.eurecom.calcite.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.21.0)", date = "2025-05-09")
public class PlanResult implements org.apache.thrift.TBase<PlanResult, PlanResult._Fields>, java.io.Serializable, Cloneable, Comparable<PlanResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PlanResult");

  private static final org.apache.thrift.protocol.TField RELS_FIELD_DESC = new org.apache.thrift.protocol.TField("rels", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField OLD_JSON_FIELD_DESC = new org.apache.thrift.protocol.TField("oldJson", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new PlanResultStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new PlanResultTupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable java.util.List<RelNode> rels; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String oldJson; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    RELS((short)1, "rels"),
    OLD_JSON((short)2, "oldJson");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // RELS
          return RELS;
        case 2: // OLD_JSON
          return OLD_JSON;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    @Override
    public short getThriftFieldId() {
      return _thriftId;
    }

    @Override
    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RELS, new org.apache.thrift.meta_data.FieldMetaData("rels", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, RelNode.class))));
    tmpMap.put(_Fields.OLD_JSON, new org.apache.thrift.meta_data.FieldMetaData("oldJson", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PlanResult.class, metaDataMap);
  }

  public PlanResult() {
  }

  public PlanResult(
    java.util.List<RelNode> rels,
    java.lang.String oldJson)
  {
    this();
    this.rels = rels;
    this.oldJson = oldJson;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PlanResult(PlanResult other) {
    if (other.isSetRels()) {
      java.util.List<RelNode> __this__rels = new java.util.ArrayList<RelNode>(other.rels.size());
      for (RelNode other_element : other.rels) {
        __this__rels.add(new RelNode(other_element));
      }
      this.rels = __this__rels;
    }
    if (other.isSetOldJson()) {
      this.oldJson = other.oldJson;
    }
  }

  @Override
  public PlanResult deepCopy() {
    return new PlanResult(this);
  }

  @Override
  public void clear() {
    this.rels = null;
    this.oldJson = null;
  }

  public int getRelsSize() {
    return (this.rels == null) ? 0 : this.rels.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<RelNode> getRelsIterator() {
    return (this.rels == null) ? null : this.rels.iterator();
  }

  public void addToRels(RelNode elem) {
    if (this.rels == null) {
      this.rels = new java.util.ArrayList<RelNode>();
    }
    this.rels.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<RelNode> getRels() {
    return this.rels;
  }

  public PlanResult setRels(@org.apache.thrift.annotation.Nullable java.util.List<RelNode> rels) {
    this.rels = rels;
    return this;
  }

  public void unsetRels() {
    this.rels = null;
  }

  /** Returns true if field rels is set (has been assigned a value) and false otherwise */
  public boolean isSetRels() {
    return this.rels != null;
  }

  public void setRelsIsSet(boolean value) {
    if (!value) {
      this.rels = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getOldJson() {
    return this.oldJson;
  }

  public PlanResult setOldJson(@org.apache.thrift.annotation.Nullable java.lang.String oldJson) {
    this.oldJson = oldJson;
    return this;
  }

  public void unsetOldJson() {
    this.oldJson = null;
  }

  /** Returns true if field oldJson is set (has been assigned a value) and false otherwise */
  public boolean isSetOldJson() {
    return this.oldJson != null;
  }

  public void setOldJsonIsSet(boolean value) {
    if (!value) {
      this.oldJson = null;
    }
  }

  @Override
  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case RELS:
      if (value == null) {
        unsetRels();
      } else {
        setRels((java.util.List<RelNode>)value);
      }
      break;

    case OLD_JSON:
      if (value == null) {
        unsetOldJson();
      } else {
        setOldJson((java.lang.String)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  @Override
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case RELS:
      return getRels();

    case OLD_JSON:
      return getOldJson();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  @Override
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case RELS:
      return isSetRels();
    case OLD_JSON:
      return isSetOldJson();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof PlanResult)
      return this.equals((PlanResult)that);
    return false;
  }

  public boolean equals(PlanResult that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_rels = true && this.isSetRels();
    boolean that_present_rels = true && that.isSetRels();
    if (this_present_rels || that_present_rels) {
      if (!(this_present_rels && that_present_rels))
        return false;
      if (!this.rels.equals(that.rels))
        return false;
    }

    boolean this_present_oldJson = true && this.isSetOldJson();
    boolean that_present_oldJson = true && that.isSetOldJson();
    if (this_present_oldJson || that_present_oldJson) {
      if (!(this_present_oldJson && that_present_oldJson))
        return false;
      if (!this.oldJson.equals(that.oldJson))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetRels()) ? 131071 : 524287);
    if (isSetRels())
      hashCode = hashCode * 8191 + rels.hashCode();

    hashCode = hashCode * 8191 + ((isSetOldJson()) ? 131071 : 524287);
    if (isSetOldJson())
      hashCode = hashCode * 8191 + oldJson.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(PlanResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetRels(), other.isSetRels());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRels()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.rels, other.rels);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetOldJson(), other.isSetOldJson());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOldJson()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.oldJson, other.oldJson);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  @Override
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  @Override
  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  @Override
  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("PlanResult(");
    boolean first = true;

    sb.append("rels:");
    if (this.rels == null) {
      sb.append("null");
    } else {
      sb.append(this.rels);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("oldJson:");
    if (this.oldJson == null) {
      sb.append("null");
    } else {
      sb.append(this.oldJson);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PlanResultStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    @Override
    public PlanResultStandardScheme getScheme() {
      return new PlanResultStandardScheme();
    }
  }

  private static class PlanResultStandardScheme extends org.apache.thrift.scheme.StandardScheme<PlanResult> {

    @Override
    public void read(org.apache.thrift.protocol.TProtocol iprot, PlanResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RELS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list80 = iprot.readListBegin();
                struct.rels = new java.util.ArrayList<RelNode>(_list80.size);
                @org.apache.thrift.annotation.Nullable RelNode _elem81;
                for (int _i82 = 0; _i82 < _list80.size; ++_i82)
                {
                  _elem81 = new RelNode();
                  _elem81.read(iprot);
                  struct.rels.add(_elem81);
                }
                iprot.readListEnd();
              }
              struct.setRelsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // OLD_JSON
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.oldJson = iprot.readString();
              struct.setOldJsonIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    @Override
    public void write(org.apache.thrift.protocol.TProtocol oprot, PlanResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.rels != null) {
        oprot.writeFieldBegin(RELS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.rels.size()));
          for (RelNode _iter83 : struct.rels)
          {
            _iter83.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.oldJson != null) {
        oprot.writeFieldBegin(OLD_JSON_FIELD_DESC);
        oprot.writeString(struct.oldJson);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PlanResultTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    @Override
    public PlanResultTupleScheme getScheme() {
      return new PlanResultTupleScheme();
    }
  }

  private static class PlanResultTupleScheme extends org.apache.thrift.scheme.TupleScheme<PlanResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PlanResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetRels()) {
        optionals.set(0);
      }
      if (struct.isSetOldJson()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetRels()) {
        {
          oprot.writeI32(struct.rels.size());
          for (RelNode _iter84 : struct.rels)
          {
            _iter84.write(oprot);
          }
        }
      }
      if (struct.isSetOldJson()) {
        oprot.writeString(struct.oldJson);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PlanResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list85 = iprot.readListBegin(org.apache.thrift.protocol.TType.STRUCT);
          struct.rels = new java.util.ArrayList<RelNode>(_list85.size);
          @org.apache.thrift.annotation.Nullable RelNode _elem86;
          for (int _i87 = 0; _i87 < _list85.size; ++_i87)
          {
            _elem86 = new RelNode();
            _elem86.read(iprot);
            struct.rels.add(_elem86);
          }
        }
        struct.setRelsIsSet(true);
      }
      if (incoming.get(1)) {
        struct.oldJson = iprot.readString();
        struct.setOldJsonIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

