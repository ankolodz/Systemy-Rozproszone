// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: stream.proto

package sr.grpc.gen;

public final class StreamingCOVID {
  private StreamingCOVID() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_streaming_Task_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_streaming_Task_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_streaming_Report_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_streaming_Report_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014stream.proto\022\tstreaming\"-\n\004Task\022%\n\010dis" +
      "trict\030\001 \001(\0162\023.streaming.District\"_\n\006Repo" +
      "rt\022\037\n\005state\030\001 \001(\0162\020.streaming.State\022%\n\010d" +
      "istrict\030\002 \001(\0162\023.streaming.District\022\r\n\005va" +
      "lue\030\003 \001(\005*\254\002\n\010District\022\007\n\003All\020\000\022\020\n\014Dolno" +
      "slaskie\020\001\022\026\n\022Kujawsko_pomorskie\020\002\022\r\n\tLub" +
      "elskie\020\003\022\014\n\010Lubuskie\020\004\022\013\n\007Lodzkie\020\005\022\017\n\013M" +
      "alopolskie\020\006\022\017\n\013Mazowieckie\020\007\022\014\n\010Opolski" +
      "e\020\010\022\020\n\014Podkarpackie\020\t\022\r\n\tPodlaskie\020\n\022\r\n\t" +
      "Pomorskie\020\013\022\013\n\007Slaskie\020\014\022\022\n\016Swietokrzysk" +
      "ie\020\r\022\027\n\023Warminsko_mazurskie\020\016\022\021\n\rWielkop" +
      "olskie\020\017\022\026\n\022Zachodniopomorskie\020\020*6\n\005Stat" +
      "e\022\013\n\007New_ill\020\000\022\014\n\010New_dead\020\001\022\022\n\016New_quar" +
      "antine\020\0022\265\001\n\014StreamTester\0223\n\tGetNewIll\022\017" +
      ".streaming.Task\032\021.streaming.Report\"\0000\001\0224" +
      "\n\nGetNewDead\022\017.streaming.Task\032\021.streamin" +
      "g.Report\"\0000\001\022:\n\020GetNewQuarantine\022\017.strea" +
      "ming.Task\032\021.streaming.Report\"\0000\001B\037\n\013sr.g" +
      "rpc.genB\016StreamingCOVIDP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_streaming_Task_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_streaming_Task_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_streaming_Task_descriptor,
        new java.lang.String[] { "District", });
    internal_static_streaming_Report_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_streaming_Report_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_streaming_Report_descriptor,
        new java.lang.String[] { "State", "District", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
