package app;

import view.server.ServerGUI;

import java.io.IOException;

public class MainServerGUI {
    public static void main(String[] args) {
        try {
            new ServerGUI();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
