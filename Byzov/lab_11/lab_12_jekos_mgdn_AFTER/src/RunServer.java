import java.io.IOException;

public class RunServer {
    public RunServer() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 1) {
            int port = Integer.parseInt(args[0]);
            Server server = new Server(port);
            server.run();
        } else {
            System.out.println("Не те аргументы");
        }

    }
}
