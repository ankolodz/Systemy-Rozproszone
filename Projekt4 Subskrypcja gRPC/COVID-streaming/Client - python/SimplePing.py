import grpc

# import the generated classes
import stream_pb2
import stream_pb2_grpc

# open a gRPC channel
channel = grpc.insecure_channel('localhost:11011')

# create a stub (client)
stub = stream_pb2_grpc.StreamTesterStub(channel)



# create a valid request message
msg = stream_pb2.Task(district = stream_pb2.District.Name(1))

# make the call
response = stub.GetNewInfo(msg)
for f in response:
    print(f)
# et voilà
print("PONG")