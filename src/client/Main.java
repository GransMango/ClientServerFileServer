package client;
public class Main {
    public static void main (String[] args) {
        try {
            Client client = new Client();
            client.openSocket();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
