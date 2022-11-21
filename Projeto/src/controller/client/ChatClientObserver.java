package controller.client;

public interface ChatClientObserver {
    void addUser(String host, boolean isOnline, String username);
    void removeUser(String host, String username);
    void changeToOnline(String host);
    void changeToOffline(String host);
}
