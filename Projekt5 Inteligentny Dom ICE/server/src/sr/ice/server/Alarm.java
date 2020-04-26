package sr.ice.server;

import Demo.log;
import com.zeroc.Ice.Current;

public class Alarm implements Demo.AlarmModule.Alarm {
    private boolean isOn;
    private log mylog= new log("12.12.2019","Burglary detected");
    @Override
    public boolean isActive(Current current) {
        return isOn;
    }

    @Override
    public boolean on(Current current) {
        isOn = true;
        return true;
    }

    @Override
    public boolean off(Current current) {
        isOn = false;
        return true;
    }

    @Override
    public log getLog(Current current) {
        return mylog;
    }
}
