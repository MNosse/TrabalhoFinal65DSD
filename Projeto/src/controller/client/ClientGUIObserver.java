package controller.client;

public interface ClientGUIObserver {
    void listHosts(String[] hosts);
    void notifyClose();
    void notifyError();
    void notifyOffline(String name);
    void notifyOnline(String name);
    void notifyQuit(String name);
    void showMessage(String who, String message);
}
