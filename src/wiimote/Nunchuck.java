package wiimote;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class Nunchuck extends Structure{

    public int c, z;
    public int roll, pitch, yaw, angle, magnitude;

    @Override
    protected List getFieldOrder() {
        return Arrays.asList("c", "z", "roll", "pitch", "yaw", "angle", "magnitude" );
    }

    public String toString(){
        return "C: "+c;
    }
}
