@echo off

:loop
time /t >> hellofile.txt
echo Hello world >> hellofile.txt
ping -n 5 127.0.0.1>NUL
goto loop