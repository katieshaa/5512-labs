package Simulate;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TimerTask;

/**
 * Created by Куддус on 20.11.2017.
 */
public class UserSimulate extends TimerTask {
    public UserSimulate()
    {}
    public void run()
    {
        int random_number = 1 + (int) (Math.random() * 256);
        int random_number1 = 1000000000 + (int) (Math.random() * 800000000 );
        try {
            URL url = new URL("http://localhost:8080/notebook/Handbook/addUser?name=example"+random_number+"&phones="+random_number1);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();

            if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Secsesful1");

            }
        }
        catch (MalformedURLException e)
        {}
        catch (IOException e)
        {
            System.out.println("Sec" );
        }
    }


}
