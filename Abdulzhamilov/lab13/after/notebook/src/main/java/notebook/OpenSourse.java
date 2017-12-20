package notebook;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TimerTask;

/**
 * Created by Куддус on 20.11.2017.
 */
public class OpenSourse extends TimerTask{
    public OpenSourse(){}
    public void run()
    {
        try {
            URL url = new URL("http://localhost:8080/notebook/Handbook");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();

            if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Secsesful2");

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
