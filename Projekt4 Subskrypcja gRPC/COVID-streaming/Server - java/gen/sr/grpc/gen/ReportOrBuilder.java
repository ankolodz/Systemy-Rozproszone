// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: stream.proto

package sr.grpc.gen;

public interface ReportOrBuilder extends
    // @@protoc_insertion_point(interface_extends:streaming.Report)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.streaming.District district = 1;</code>
   * @return The enum numeric value on the wire for district.
   */
  int getDistrictValue();
  /**
   * <code>.streaming.District district = 1;</code>
   * @return The district.
   */
  sr.grpc.gen.District getDistrict();

  /**
   * <code>int32 died = 2;</code>
   * @return The died.
   */
  int getDied();

  /**
   * <code>int32 ill = 3;</code>
   * @return The ill.
   */
  int getIll();

  /**
   * <code>int32 quarantine = 4;</code>
   * @return The quarantine.
   */
  int getQuarantine();
}