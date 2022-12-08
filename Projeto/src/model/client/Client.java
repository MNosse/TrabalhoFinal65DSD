package model.client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Client {
    private final ServerSocket SERVER_SOCKET;
    private final ClientObserver OBSERVER;
    private final String HOST;
    private final int PORT;
    private final String NAME;

    public Client(ClientObserver observer, String host, int port, String name) throws IOException {
        OBSERVER = observer;
        NAME = name;
        HOST = host;
        PORT = port;
        SERVER_SOCKET = new ServerSocket(0);
        SERVER_SOCKET.setReuseAddress(true);
        serverListener();
        connect();
        listHosts();
    }
    
    private void serverListener() {
        new Thread(() -> {
            try {
                while(!SERVER_SOCKET.isClosed()) {
                    Socket socket = SERVER_SOCKET.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message = in.readLine();
                    String[] messageSplited = message.split(";");
                    switch(messageSplited[0]) {
                        case "CONNECT":
                            OBSERVER.notifyOnline(messageSplited[1]);
                            break;
                        case "LISTHOSTS":
                            OBSERVER.listHosts(Arrays.copyOfRange(messageSplited, 1, messageSplited.length));
                            break;
                        case "MESSAGE":
                            OBSERVER.showMessage((messageSplited[2].equals(NAME) ? "Voce" : messageSplited[2]), messageSplited[1].replace(".,", ";"));
                            break;
                        case "DISCONNECT":
                            OBSERVER.notifyOffline(messageSplited[1]);
                            if (NAME.equals(messageSplited[1])) {
                                SERVER_SOCKET.close();
                            }
                            break;
                        case "QUIT":
                            OBSERVER.notifyQuit(messageSplited[1]);
                            if (NAME.equals(messageSplited[1])) {
                                SERVER_SOCKET.close();
                            }
                            break;
                    }
                    in.close();
                    socket.close();
                }
            } catch(IOException e) {
                OBSERVER.notifyError();
            }
            finally {
                OBSERVER.notifyClose();
            }
        }).start();
    }
    
    public void sendMessage(String message) throws IOException {
        Socket socket = new Socket(HOST, PORT);
        PrintStream out = new PrintStream(socket.getOutputStream());
        out.println("MESSAGE;" + message.replace(";", ".,") + ";" + NAME);
        out.close();
        socket.close();
    }
    
    private void connect() throws IOException {
        Socket socket = new Socket(HOST, PORT);
        PrintStream out = new PrintStream(socket.getOutputStream());
        out.println("CONNECT;" + socket.getLocalAddress().getHostAddress() + ";" + SERVER_SOCKET.getLocalPort() + ";" +NAME);
        out.close();
        socket.close();
    }
    
    private void listHosts() throws IOException {
        Socket socket = new Socket(HOST, PORT);
        PrintStream out = new PrintStream(socket.getOutputStream());
        out.println("LISTHOSTS;" + NAME);
        out.close();
        socket.close();
    }
    
    public void disconnect() throws IOException {
        Socket socket = new Socket(HOST, PORT);
        PrintStream out = new PrintStream(socket.getOutputStream());
        out.println("DISCONNECT;" + NAME);
        out.close();
        socket.close();
    }

    public void quit() throws IOException {
        Socket socket = new Socket(HOST, PORT);
        PrintStream out = new PrintStream(socket.getOutputStream());
        out.println("QUIT;" + NAME);
        out.close();
        socket.close();
    }
}