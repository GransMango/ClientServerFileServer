package client;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main (String[] args) {
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            Client client = new Client();
            client.openSocket();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
