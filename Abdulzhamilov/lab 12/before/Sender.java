import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Куддус on 22.10.2017.
 */
public class Sender extends Thread {
    private boolean stop ;
    BufferedReader in;
    public Sender(BufferedReader inp)
    {
        in = inp;
    }
        public void SenderStop()
        {
            stop = true;
        }
        public void run()
        {
            try {
                while (!stop) {
                    String str = in.readLine();
                    System.out.println(str);
                }
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }

}
