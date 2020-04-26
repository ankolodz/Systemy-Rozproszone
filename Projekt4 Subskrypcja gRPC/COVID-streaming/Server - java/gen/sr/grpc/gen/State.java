// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: stream.proto

package sr.grpc.gen;

/**
 * Protobuf enum {@code streaming.State}
 */
public enum State
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>New_ill = 0;</code>
   */
  New_ill(0),
  /**
   * <code>New_dead = 1;</code>
   */
  New_dead(1),
  /**
   * <code>New_quarantine = 2;</code>
   */
  New_quarantine(2),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>New_ill = 0;</code>
   */
  public static final int New_ill_VALUE = 0;
  /**
   * <code>New_dead = 1;</code>
   */
  public static final int New_dead_VALUE = 1;
  /**
   * <code>New_quarantine = 2;</code>
   */
  public static final int New_quarantine_VALUE = 2;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static State valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static State forNumber(int value) {
    switch (value) {
      case 0: return New_ill;
      case 1: return New_dead;
      case 2: return New_quarantine;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<State>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      State> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<State>() {
          public State findValueByNumber(int number) {
            return State.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return sr.grpc.gen.StreamingCOVID.getDescriptor().getEnumTypes().get(1);
  }

  private static final State[] VALUES = values();

  public static State valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private State(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:streaming.State)
}

