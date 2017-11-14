import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by denis on 26.09.2017.
 */
public class inThread extends Thread
{
    private DatagramSocket socket;
    private DatagramPacket packet;
    private String Name;
    private String Path;
    inThread(DatagramSocket socket, String Path)
    {
        this.Path = Path;
        this.socket = socket;
	Name = "user";
        byte[] receiveData = new byte[1024];
        packet = new DatagramPacket(receiveData, receiveData.length);
    }

    public void run()
    {
        while(true) {
            try {
                byte[] receiveData = new byte[1024];
		        packet = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(packet);
                String newMassege = new String(packet.getData());
		        if(newMassege.contains("@name"))
                {
                    String[] tmp = newMassege.split(" ");
                    StringBuilder temp = new StringBuilder();
                    int i = 0;
                    while(tmp[1].charAt(i) >= 'a' && tmp[1].charAt(i) <= 'z')
                    {
                        temp.append(tmp[1].charAt(i));
                        i++;
                    }
                    Name = temp.toString();
                }
                else if(newMassege.contains("@quit"))
                {
                    System.out.println("client disconected");
                }
                else
                {
                            System.out.println(Name + ": " + newMassege);
                }
                } catch (IOException e) {

                }
            }
    }
    public InetAddress getIP()
    {
        return packet.getAddress();
    }
    public int getPort()
    {
        return packet.getPort();
    }
}