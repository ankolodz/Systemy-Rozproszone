
#ifndef CALC_ICE
#define CALC_ICE

module Demo
{
  sequence<long> seqofNumbers;
  struct log{
    string date;
    string log;
  }
module CameraModule{
  struct position{
    int x;
    int y;  
}
  interface Camera
  {
    bool Zoom(int zoom);
    bool Move(int x, int y);
    int getZoom();
    position getPosition();    
  }
};
module ThermometerModule{
  interface Thermometer
  {
   float getTemperature();
   bool setAlarm(int temperature);
   log getLog();
  }
};
module AlarmModule{
  interface Alarm
  {
   bool isActive();
   bool on();
   bool off();
   log getLog();
   }
};
interface Server{
   string AllID();
   }

};

#endif
