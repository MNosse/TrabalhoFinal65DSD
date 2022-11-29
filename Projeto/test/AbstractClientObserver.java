import model.client.ClientObserver;

public abstract class AbstractClientObserver implements ClientObserver {
    @Override
    public void listHosts(String[] hosts) {}
    
    @Override
    public void notifyClose() {}
    
    @Override
    public void notifyError() {}
    
    @Override
    public void notifyOffline(String name) {}
    
    @Override
    public void notifyOnline(String name) {}
    
    @Override
    public void notifyQuit(String name) {}
    
    @Override
    public void showMessage(String who, String message) {}
}
