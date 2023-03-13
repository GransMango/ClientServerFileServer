package server;

import java.util.ArrayList;

public class FileServer {
    private ArrayList<String> files;

    public FileServer() {
        files = new ArrayList<>();
    }

    public boolean add(String filename) {
        if (filename.equals("file10")) {
            files.add(filename);
            return true;
        } else if (!(filename.matches("file[0-9]"))) {
            return false;
        }
        if (files.size() < 10 && !files.contains(filename)) {
            files.add(filename);
            return true;
        }
        return false;
    }

    public boolean get(String filename) {
        return (files.contains(filename));
    }

    public boolean delete(String filename) {
        return files.remove(filename);
    }
}
