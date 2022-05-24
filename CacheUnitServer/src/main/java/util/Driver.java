package util;

import server.Server;

public class Driver {

    public static void main(String[] args) {
        CLI cli = new CLI(System.in, System.out);
        Server server = new Server(34567);
        cli.add(server);
        new Thread(cli).start();
    }
}
