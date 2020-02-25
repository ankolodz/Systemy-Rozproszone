import socket;

serverIP = "127.0.0.1"
serverPort = 11000
msg =  'P'

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(bytes(msg, 'UTF-8'), (serverIP, serverPort))
buff, addr = client.recvfrom(1024)
print(buff)




