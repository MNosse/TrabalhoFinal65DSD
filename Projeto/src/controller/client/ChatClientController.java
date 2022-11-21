package controller.client;

public class ChatClientController {
    ChatClientObserver observer;
    String host;
    int port;
    
    public ChatClientController(ChatClientObserver observer, String host, int port) {
        this.observer= observer;
        this.host = host;
        this.port = port;
        observer.addUser("1.1.1.1", true, "Mateus Coelho Nosse");
        observer.addUser("1.1.1.2", false, "Ana Cristina Vasconcellos Reinert");
        observer.addUser("1.1.1.3", false, "Maria de Fatima Lopes Coelho do Nascimento Gomes");
        observer.addUser("1.1.1.4", true, "Thalita Moreira");
        observer.addUser("1.1.1.5", true, "Mateus Coelho Nosse");
        observer.addUser("1.1.1.6", true, "Mateus Coelho Nosse");
        observer.addUser("1.1.1.7", true, "Mateus Coelho Nosse");
        observer.addUser("1.1.1.8", true, "Mateus Coelho Nosse");
        observer.addUser("1.1.1.9", true, "Mateus Coelho Nosse");
    }
    
}
