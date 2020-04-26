import grpc
import stream_pb2
import stream_pb2_grpc
import grpc
import sys
import time
# from grpc._channel import _Rendezvous
sys.path.insert(1, './gen')

channel = None
stub = None
streaming = None
is_conecting = False

def initialize():
    global channel, stub, is_conecting
    channel = grpc.insecure_channel('localhost:11011')
    stub = stream_pb2_grpc.StreamTesterStub(channel)
    
    setStream()
    print('Waiting for server...')
    wait_for_connection()
    print('Connected!')
    startSubscribe()

def setStream():
    global streaming
    while streaming is None:
        newStream()

def newStream():
    global streaming, streamingFunction

    print(
        'Select district 1-17\n'+
        'All = 17\n'+
        'Dolnoslaskie = 1\n'+
        'Kujawsko_pomorskie = 2\n'+
        'Lubelskie = 3\n'+
        'Lubuskie = 4\n'+
        'Lodzkie = 5\n'+
        'Malopolskie = 6\n'+
        'Mazowieckie = 7\n'+
        'Opolskie = 8\n'+
        'Podkarpackie = 9\n'+
        'Podlaskie = 10\n'+
        'Pomorskie = 11\n'+
        'Slaskie = 12\n'+
        'Swietokrzyskie = 13\n'+
        'Warminsko_mazurskie = 14\n'+
        'Wielkopolskie = 15\n'+
        'Zachodniopomorskie = 16\n'
    )
    directoryID = int(sys.stdin.readline()[:-1])

    if directoryID in range(18):
        streaming = stream_pb2.District.Name(directoryID)
    

def wait_for_connection():
    global is_conecting
    while not is_conecting:
        try:
            print('ping')
            time.sleep(0.1)
            msg = stream_pb2.Ping_msg()
            response = stub.Ping(msg)
        except:
            continue
        is_conecting = True
        print('Connected')


def reconnect():
    wait_for_connection()
    startSubscribe()


def startSubscribe():
    global stub, streaming, is_conecting
    request = stream_pb2.Task(district=streaming)
    print(request)
    try:
        stream = stub.GetNewInfo(request)
            
        print("Waiting for first message!")
        for f in stream:
            print(f)
    except grpc._channel._Rendezvous:
        is_conecting = False
        print('Cannot reach server. Enter reconnecting...')
        reconnect()
    except KeyboardInterrupt:
        print('End')


#main
initialize()


