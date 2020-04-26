package sr.ice.client;


import Demo.*;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

import Demo.AlarmModule.AlarmPrx;
import Demo.CameraModule.CameraPrx;
import Demo.CameraModule.position;
import Demo.ThermometerModule.ThermometerPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.LocalException;

public class Client 
{
	public static void main(String[] args) 
	{
		int status = 0;
		Communicator communicator = null;

		try {

			communicator = Util.initialize(args);
			Scanner scan = new Scanner(System.in);
			while (true){
				System.out.println("1. All ID");
				System.out.println("2. Camera");
				System.out.println("3. Thermometre");
				System.out.println("4. Alarm");
				switch(scan.nextInt()){
					case 1:
						allID(communicator);
						break;
					case 2:
						camera(communicator);
						break;
					case 3:
						thermomete(communicator);
						break;
					case 4:
						alarm(communicator);
						break;
				}


			}




		} catch (LocalException e) {
			e.printStackTrace();
			status = 1;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			status = 1;
		}
		if (communicator != null) {
			// Clean up
			//
			try {
				communicator.destroy();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				status = 1;
			}
		}
		System.exit(status);
	}

	private static void alarm(Communicator communicator) {
		System.out.println("PodajID: ");
		Scanner scan = new Scanner(System.in);
		String id = scan.next();
		ObjectPrx base = communicator.stringToProxy("alarm/" + id + ":tcp -h localhost -p 10000");
		AlarmPrx alarm = AlarmPrx.checkedCast(base);
		int prompt;
		do{
			System.out.println("    0. Exit\n" +
					"	 1. IsActive\n" +
					"    2. ON\n" +
					"    3. OFF\n" +
					"    4. Get log");
			prompt = scan.nextInt();
			switch(prompt){
				case 1:
					System.out.println(alarm.isActive());
					break;
				case 2:
					System.out.println(alarm.on());
					break;
				case 3:
					System.out.println(alarm.off());
					break;
				case 4:
					log myLog = alarm.getLog();
					System.out.println(myLog.date +" "+ myLog.log);
			}
		}while(prompt != 0);
	}

	private static void thermomete(Communicator communicator) {
		System.out.println("PodajID: ");
		Scanner scan = new Scanner(System.in);
		String id = scan.next();
		ObjectPrx base = communicator.stringToProxy("tmp/" + id + ":tcp -h localhost -p 10000");
		ThermometerPrx tmp = ThermometerPrx.checkedCast(base);
		int prompt;
		do{
			System.out.println("    0. Exit\n" +
					"	 1. Get temperature\n" +
					"    2.  Set alarm\n" +
					"    3.  getLog\n" );
			prompt = scan.nextInt();
			switch(prompt){
				case 1:
					System.out.println(tmp.getTemperature());
					break;
				case 2:
					System.out.println("Podaj wartość dla alarmu:");
					System.out.println(tmp.setAlarm(scan.nextInt()));
					break;
				case 3:
					log mylog = tmp.getLog();
					System.out.println(mylog.date + " "+ mylog.log);
					break;
			}
		}while(prompt != 0);
	}

	private static void camera(Communicator communicator) {
		System.out.println("PodajID: ");
		Scanner scan = new Scanner(System.in);
		String id = scan.next();
		ObjectPrx base = communicator.stringToProxy("camera/" + id + ":tcp -h localhost -p 10000");
		CameraPrx camera = CameraPrx.checkedCast(base);
		int prompt;
		do{
			System.out.println("    0. Exit\n" +
					"	 1. Zoom(int zoom)\n" +
					"    2.  Move(int x, int y)\n" +
					"    3.  getZoom()\n" +
					"    4.  getPosition() ");
			prompt = scan.nextInt();
			switch(prompt){
				case 1:
					System.out.println("Podaj o ile zwiększyć zoom");
					System.out.println(camera.Zoom(scan.nextInt()));
					break;
				case 2:
					System.out.println("Podaj o ile zwiększyć x: y:");
					System.out.println(camera.Move(scan.nextInt(),scan.nextInt()));
					break;
				case 3:
					System.out.println(camera.getZoom());
					break;
				case 4:
					position param = camera.getPosition();
					System.out.println(param.x + " " + param.y);
					break;
			}
		}while(prompt != 0);
	}

	private static void allID(Communicator communicator) {
		ObjectPrx base = communicator.stringToProxy("server/0:tcp -h localhost -p 10000");
		ServerPrx obj = ServerPrx.checkedCast(base);
		System.out.println(obj.AllID());
	}

}