package sr.ice.server;

import Demo.CameraModule.position;
import com.zeroc.Ice.Current;

public class Camera implements Demo.CameraModule.Camera {
    int zoom=1,x=50,y=50;
    @Override
    public boolean Zoom(int zoom, Current current) {
        if (zoom + this.zoom <= 4 && zoom + this.zoom > 1){
            this.zoom += zoom;
            return  true;
        }
            return false;
    }

    @Override
    public boolean Move(int x, int y, Current current) {
        if (x + this.x <= 180 && x + this.x > -180){
            this.x += x;
            return  true;
        }
        if (y + this.y <= 20 && y + this.y > -20){
            this.y += y;
            return  true;
        }
        return false;
    }

    @Override
    public int getZoom(Current current) {
        return zoom;
    }

    @Override
    public position getPosition(Current current) {
        return new position(x,y);
    }
}
