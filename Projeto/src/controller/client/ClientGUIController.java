package controller.client;

import model.client.Client;
import model.client.ClientObserver;

import java.io.IOException;

public class ClientGUIController implements ClientObserver {
    private final ClientGUIObserver OBSERVER;
    private final Client CLIENT;
    
    public ClientGUIController(ClientGUIObserver observer, String host, int port, String name) throws IOException {
        this.OBSERVER = observer;
        CLIENT = new Client(this, host, port, name);
    }

    public void sendMessage(String message) throws IOException {
        CLIENT.sendMessage(message);
    }
    
    public void disconnect() throws IOException {
        CLIENT.disconnect();
    }
    
    public void quit() throws IOException {
        CLIENT.quit();
    }
    
    @Override
    public void listHosts(String[] hosts) {
        OBSERVER.listHosts(hosts);
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
    public void showMessage(String who, String message) {
        OBSERVER.showMessage(who, message);
    }
}
