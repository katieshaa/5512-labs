package Simulate;

import Simulate.UserSimulate;
import notebook.OpenSourse;

import java.util.Timer;

/**
 * Created by Куддус on 20.11.2017.
 */
public class Main {
    public static void main(String[] arg)
    {
        UserSimulate sim = new UserSimulate();
        OpenSourse open = new OpenSourse();
        Timer time = new Timer();
        Timer time1 = new Timer();
        time1.schedule(open,5000,5000);
        time.schedule(sim,15000,15000);

    }
}
