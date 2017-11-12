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
                else if(newMassege.contains("@pwd"))
                {
                    System.out.println("pwd command");
                    byte[] sendData = new byte[1024];
                    sendData = System.getProperty("user.dir").getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, packet.getAddress(), packet.getPort());
                    socket.send(sendPacket);
                }
                else if(newMassege.contains("@ls"))
                {
                    File dir = new File(Path);
                    for(File f : dir.listFiles())
                    {
                        byte[] sendData = new byte[1024];
                        sendData = f.getName().getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, packet.getAddress(), packet.getPort());
                        socket.send(sendPacket);

                    }
                }
                else if(newMassege.contains("@cd"))
                {
                    String[] tmp = newMassege.split(" ");
                    StringBuilder temp = new StringBuilder();
                    int i = 0;
                    while((int)tmp[1].charAt(i) != 0)
                    {
                        temp.append(tmp[1].charAt(i));
                        i++;
                    }
                    File dir;
                    dir = new File(temp.toString());
                    Path = dir.getCanonicalPath();
                    System.setProperty("user.dir", Path);
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