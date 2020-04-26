package sr.ice.server;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Identity;

public class Server
{
	public void t1(String[] args)
	{
		int status = 0;
		Communicator communicator = null;

		try
		{
			communicator = Util.initialize(args);
			ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h localhost -p 10000:udp -h localhost -p 10000");

			// 3. Stworzenie serwanta/serwant�w
			Alarm alarm1 = new Alarm();
			Alarm alarm2 = new Alarm();
			Camera camera1 = new Camera();
			Camera camera2 = new Camera();
			Thermomete thermomete = new Thermomete();
			ServerHandler handler = new ServerHandler("alarm/01, alarm/02, camera/01, camera/02, tmp/01");
			
						    
			// 4. Dodanie wpis�w do tablicy ASM
			adapter.add(alarm1, new Identity("01", "alarm"));
	        adapter.add(alarm2, new Identity("02", "alarm"));
			adapter.add(camera1, new Identity("01", "camera"));
			adapter.add(camera2, new Identity("02", "camera"));
			adapter.add(thermomete,new Identity("01","tmp"));
			adapter.add(handler,new Identity("0","server"));

	        
			// 5. Aktywacja adaptera i przej�cie w p�tl� przetwarzania ��da�
			adapter.activate();
			
			System.out.println("Entering event processing loop...");
			
			communicator.waitForShutdown(); 		
			
		}
		catch (Exception e)
		{
			System.err.println(e);
			status = 1;
		}
		if (communicator != null)
		{
			// Clean up
			//
			try
			{
				communicator.destroy();
			}
			catch (Exception e)
			{
				System.err.println(e);
				status = 1;
			}
		}
		System.exit(status);
	}


	public static void main(String[] args)
	{
		Server app = new Server();
		app.t1(args);
	}
}
