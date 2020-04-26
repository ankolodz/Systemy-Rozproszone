package sr.ice.server;

import Demo.ThermometerModule.Thermometer;
import Demo.log;
import com.zeroc.Ice.Current;

import java.util.Random;

import static java.lang.Math.abs;

public class Thermomete implements Thermometer {
    private log mylog= new log("12.10.2020","High temp!");
    @Override
    public float getTemperature(Current current) {
        Random generator = new Random();
        return abs(generator.nextFloat()%30);
    }

    @Override
    public boolean setAlarm(int temperature, Current current) {
        return true;
    }

    @Override
    public log getLog(Current current) {
        return mylog;
    }
}
