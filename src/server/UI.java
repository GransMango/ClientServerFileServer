package server;

import java.util.Scanner;

public class UI {
    private Scanner scanner;
    private FileServer fileServer;

    public UI() {
        scanner = new Scanner(System.in);
        fileServer = new FileServer();
    }

    public void start() {
        askUserForCommands();
    }

    public void askUserForCommands() {
        while(true) {
            String[] command = scanner.nextLine().split(" ");
            if (command[0].equals("exit")) {
                break;
            }
            String filename = command[1];
            if (command[0].equals("add")) {
                if (fileServer.add(filename)) {
                    System.out.println("The file " + filename + " added successfully");
                } else {
                    System.out.println("Cannot add the file " + filename);
                }
            } else if (command[0].equals("get")) {
                if (fileServer.get(filename)) {
                    System.out.println("The file " + filename + " was sent");
                } else {
                    System.out.println("The file " + filename + " not found");
                }
            } else if (command[0].equals("delete")) {
                if (fileServer.delete(filename)) {
                    System.out.println("The file " + filename + " was deleted");
                } else {
                    System.out.println("The file " + filename + " not found");
                }
            } else {
                System.out.println("Invalid input");
            }
        }
    }
}
