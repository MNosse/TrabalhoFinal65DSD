package controller.server;

public interface ServerGUIObserver {
    void notifyClose();
    void notifyError();
    void notifyOffline(String name);
    void notifyOnline(String name);
    void notifyQuit(String name);
    void notifyWaiting();
}
