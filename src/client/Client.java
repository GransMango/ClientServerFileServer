package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket clientSocket;
    private DataInputStream input;
    private DataOutputStream output;
    private Scanner scanner;
    private final String address = "127.0.0.1";
    private final int port = 2350;
    public Client() throws IOException  {
        clientSocket = new Socket(InetAddress.getByName("127.0.0.1"), port);
        scanner = new Scanner(System.in);
        System.out.println("Client started!");
        input = new DataInputStream(clientSocket.getInputStream());
        output = new DataOutputStream(clientSocket.getOutputStream());
    }

    public void openSocket() throws IOException{
        int choice = askUserChoice();
        if (choice != 0) {
            processResponse(choice);
        }
        input.close();
        output.close();
        clientSocket.close();
    }

    private int askUserChoice() throws IOException {
        System.out.print("Enter action (1 - get a file, 2 - create a file, 3 - delete a file): ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> {
                sendGetRequest();
                return 1;
            }
            case "2" -> {
                sendPutRequest();
                return 2;
            }
            case "3" -> {
                sendDeleteRequest();
                return 3;
            }
            case "exit" -> {
                output.writeUTF("exit");
                System.out.println("The request was sent");
                return 0;
            }
        }
        return -1;
    }

    private void sendGetRequest() throws IOException {
        System.out.print("Enter filename: ");
        String filename = scanner.next();
        output.writeUTF("GET");
        output.writeUTF(filename);
    }

    private void sendPutRequest() throws IOException {
        System.out.print("Enter filename: ");
        String filename = scanner.nextLine();
        System.out.print("Enter file content: ");
        String fileContent = scanner.nextLine();
        output.writeUTF("PUT");
        output.writeUTF(filename);
        output.writeUTF(fileContent);
    }

    private void sendDeleteRequest() throws IOException {
        System.out.print("Enter filename: ");
        String filename = scanner.nextLine();
        output.writeUTF("DELETE");
        output.writeUTF(filename);
    }

    private void processResponse(int choice) throws IOException {
        String[] result = input.readUTF().split(" ");
        if (!result[0].equals("200")) {
            System.out.println("The response says that the file was not found!");
            return;
        }
        switch (choice) {
            case 1 -> System.out.println("The content of the file is: " + processGetResponse(result));
            case 2 -> System.out.println("The response says that file was created!");
            case 3 -> System.out.println("The response says that the file was successfully deleted!");
        }
    }

    private String processGetResponse(String[] result) {
        StringBuilder fixedString = new StringBuilder();
        for (int i = 1; i < result.length-1; i++) {
            fixedString.append(result[i]).append(" ");
        }
        fixedString.append(result[result.length-1]);
        return fixedString.toString();
    }
}
