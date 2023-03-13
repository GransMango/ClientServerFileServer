package server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.openSocket();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}