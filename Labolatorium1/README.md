# Systemy Rozproszone
## Zadanie 1
Zaimplementować dwukierunkową komunikację przez UDP * Java-Java – Klient wysyła wiadomość i odczytuje  
* odpowiedź – Serwer otrzymuje wiadomość i wysyła 
* odpowiedź – Należy pobrać adres nadawcy z otrzymanego datagramu 

## Zadanie 2
Zaimplementować komunikację przez UDP pomiędzy językami Java i Python – JavaUdpServer + PythonUdpClient 
* Należy przesłać wiadomość tekstową:  ‘żółta gęś’ 

## Zadanie 3
Zaimplementować przesył wartości liczbowej w przypadku JavaUdpServer + PythonUdpClient 
* Symulujemy komunikację z platformą o innej kolejności bajtów: klient Python ma wysłać następujący ciąg bajtów: 
*msg_bytes = (300).to_bytes(4, byteorder='little')*
* Server Javy ma wypisać otrzymaną liczbę oraz odesłać liczbę zwiększoną o jeden 
### Zadanie 3 wskazówki 
* Zamiana bajty –> int –> bajty w Javie:  
*int nb = ByteBuffer.wrap(buff).getInt();* 
*buff = ByteBuffer.allocate(4).putInt(nb).array();* 
 
* Zamiana *bajty –> int  w Pythonie:  
int.from_bytes(buff, byteorder='little')*

## Zadanie 4
Zaimplementować serwer (Java lub Python) który rozpoznaje czy otrzymał wiadomość od klienta Java czy od klienta Python i wysyła im różne odpowiedzi (np. ‘Pong Java’, ‘Pong Python’) 