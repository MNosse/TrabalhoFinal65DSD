package controller.server;

import model.server.Server;
import model.server.ServerObserver;

import java.io.IOException;

public class ServerGUIController implements ServerObserver {
    private final ServerGUIObserver OBSERVER;
    private final Server SERVER;
    
    public ServerGUIController(ServerGUIObserver observer, int port) throws IOException {
        this.OBSERVER = observer;
        SERVER = new Server(this, port);
    }
    
    @Override
    public void notifyClose() {
        OBSERVER.notifyClose();
    }
    
    @Override
    public void notifyError() {
        OBSERVER.notifyError();
    }
    
    @Override
    public void notifyOffline(String name) {
        OBSERVER.notifyOffline(name);
    }
    
    @Override
    public void notifyOnline(String name) {
        OBSERVER.notifyOnline(name);
    }
    
    @Override
    public void notifyQuit(String name) {
        OBSERVER.notifyQuit(name);
    }
    
    @Override
    public void notifyWaiting() {
        OBSERVER.notifyWaiting();
    }
}
