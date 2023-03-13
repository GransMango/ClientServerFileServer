package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server {
    private final ServerSocket serverSocket;
    private DataInputStream input;
    private DataOutputStream output;
    private Socket socket;
    private final String absolutePath = "C:\\Users\\danie\\Documents\\IdeaProjects\\TMCProjects\\mooc-java-programming-ii\\part13-Part13_01.MyFirstApplication\\File Server\\File Server\\task\\src\\server\\data";
    private final String address = "127.0.0.1";
    private final int port = 2350;
    public Server () throws IOException {
        serverSocket = new ServerSocket(port, 50, InetAddress.getByName(address));
        System.out.println("Server started!");
    }

    public void openSocket() throws IOException{
        while(true) {
            socket = serverSocket.accept();
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            if (!handleRequest()) {
                input.close();
                output.close();
                socket.close();
                serverSocket.close();
                break;
            }
        }
    }

    private boolean handleRequest() throws IOException {
        String request = input.readUTF();
        switch (request) {
            case "GET" -> {
                return handleGetRequest();
            }
            case "DELETE" -> {
                return handleDeleteRequest();
            }
            case "PUT" -> {
                return handlePutRequest();
            }
           case "exit" -> {
                return false;
           }
        }
        return false;
    }

    private boolean handleGetRequest() throws IOException {
        String filename = input.readUTF();
        try {
            Path filepath = Path.of(absolutePath + "\\" + filename);
            String content = Files.readString(filepath);
            output.writeUTF("200 " + content);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean handlePutRequest() throws IOException {
        String filename = input.readUTF();
        File file = new File(absolutePath + "\\" + filename);
        boolean value = file.createNewFile();
        if (value) {
            try (FileWriter writeToFile = new FileWriter(file)) {
                writeToFile.write(input.readUTF());
                output.writeUTF("200");
            } catch (Exception e) {
                output.writeUTF("403");
                return false;
            }
        }
        return true;
    }

    private boolean handleDeleteRequest() throws IOException {
        String filename = input.readUTF();
        File file = new File(absolutePath + "\\" + filename.trim());
        if (file.delete()) {
            output.writeUTF("200");
            return true;
        }
        output.writeUTF("404");
        return false;
    }
}
