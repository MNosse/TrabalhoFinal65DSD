package controller.server;

public interface ServerGUIObserver {
    void notifyOnline(String name);
    void notifyOffline(String name);
    void notifyQuit(String name);
    void notifyWaiting();
    void notifyError();
}
