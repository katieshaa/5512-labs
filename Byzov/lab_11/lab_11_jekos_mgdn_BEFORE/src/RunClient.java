import java.io.IOException;

public class RunClient {
    public RunClient() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            String ip = args[0];
            int port = Integer.parseInt(args[1]);
            Client client = new Client(ip, port);
            client.run();
        } else {
            System.out.println("Не те аргументы");
        }

    }
}