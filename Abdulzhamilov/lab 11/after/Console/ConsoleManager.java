package Console;

import javax.swing.*;
import java.io.*;
import java.net.DatagramPacket;

/**
 * Created by Куддус on 02.10.2017.
 */
public class ConsoleManager implements Closeable
{
    private JTextArea jTextArea;
    private BufferedReader buf ;
    public ConsoleManager(JTextArea j,File buffer)throws  Exception
    {
        jTextArea = j;
        buf = new BufferedReader(new FileReader(buffer));
    }
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
        jTextArea.append("SYSTEM: " + text + "\n");

        if(exception != null) {
            System.out.println(exception);
        }
    }


    public synchronized void write(String message) {
        jTextArea.append(message + "\n");


    }

}
