package model.server;

public interface ServerObserver {
    void notifyOnline(String name);
    void notifyOffline(String name);
    void notifyQuit(String name);
}
