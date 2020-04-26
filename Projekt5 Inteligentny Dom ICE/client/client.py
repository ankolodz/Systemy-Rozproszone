import sys
import Ice

Ice.loadSlice('home.ice')
import Demo 
choice = None
direct = "camera"
def getItems():
    global choice
    items = ['camera','tmp','alarm']
    print("Wybierz urządzenie\n"+
        '1. Camera\n2.Termometr\n3.Alarm\n')
    choise = items[int(sys.stdin.readline()[:-1])]
    print("Podaj id urządzenia: ")
    itemsId = string(sys.stdin.readline()[:-1])
    return choice+'/'+id


with Ice.initialize(sys.argv) as comunicator:
    # while(True):
    itemsId = getItems()
    server = Demo.HelloPrx.checkedCast(communicator.stringToProxy(itemsId+":default -h localhost -p 10000"))
    server.getZoom()
        # while direct not "EXIT":
        #     print()