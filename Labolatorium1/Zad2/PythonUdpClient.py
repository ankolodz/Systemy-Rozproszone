import socket;
#-*- coding: utf-8 -*-
serverIP = "127.0.0.1"
serverPort = 11000
msg = "żółta gęś!"

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(bytes(msg, 'UTF-8'), (serverIP, serverPort))




