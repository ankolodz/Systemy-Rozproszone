import socket;

serverIP = "127.0.0.1"
serverPort = 11000
msg =  (300).to_bytes(4, byteorder='little')

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(msg, (serverIP, serverPort))
buff, addr = client.recvfrom(4)
print(int.from_bytes(buff,byteorder='big'))




