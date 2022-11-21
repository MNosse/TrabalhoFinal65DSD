package controller.server;

import model.server.Server;
import model.server.ServerObserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerGUIController implements ServerObserver {
    private ServerGUIObserver observer;
    private ServerSocket serverSocket;
    
    public ServerGUIController(ServerGUIObserver observer, int port) throws IOException {
        this.observer = observer;
        serverSocket = new ServerSocket(port);
        createAcceptThread();
    }
    
    private void createAcceptThread() {
        new Thread(() -> {
            try {
                observer.notifyWaiting();
                while(true) {
                    Socket conexao = serverSocket.accept();
                    new Server(this, conexao).start();
                }
            } catch(IOException e) {
                observer.notifyError();
            }
        }).start();
    }
    
    @Override
    public void notifyOnline(String name) {
        observer.notifyOnline(name);
    }
    
    @Override
    public void notifyOffline(String name) {
        observer.notifyOffline(name);
    }
    
    @Override
    public void notifyQuit(String name) {
        observer.notifyQuit(name);
    }
}
