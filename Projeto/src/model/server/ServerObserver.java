package model.server;

public interface ServerObserver {
    void notifyClose();
    void notifyError();
    void notifyOffline(String name);
    void notifyOnline(String name);
    void notifyQuit(String name);
    void notifyWaiting();
}
