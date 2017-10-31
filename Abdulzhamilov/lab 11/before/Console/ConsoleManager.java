package Console;

import java.io.*;
import java.net.DatagramPacket;

/**
 * Created by Куддус on 02.10.2017.
 */
public class ConsoleManager implements Closeable
{
    private BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
    public ConsoleManager()
    {}
    public void close() throws IOException {
        buf.close();
    }
    public String getMessage(DatagramPacket packet) {
        String message = new String(packet.getData());
        message = message.trim();

        return message;
    }


    public String read() throws IOException {
        String message = buf.readLine();

        return message;
    }

    public synchronized void printSystemMessage(String text, Throwable exception) {
        System.out.println("SYSTEM: " + text);
        if(exception != null) {
            System.out.println(exception);
        }
    }


    public synchronized void write(String message) {
        System.out.println(message);
    }

}
