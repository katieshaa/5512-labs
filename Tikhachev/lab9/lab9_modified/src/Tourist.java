import java.util.Random;

/**
 * Created by root on 24.10.17 with love.
 */
public class Tourist {
    private int number;
    private int timeUseComputer;

    public Tourist(int n) {
        number = n;
    }

    public int getNumber() {
        return number;
    }

    public int getTimeUseComputer() {
        return timeUseComputer;
    }

    public void setTimeUseComputer(int time) {
        timeUseComputer = time;
    }
}
