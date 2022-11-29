package model.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private final Set<String> HOSTS_ONLINE = new HashSet<>();
    private final Set<String> HOSTS_OFFLINE = new HashSet<>();
    private final Map<String, String> HOSTSPORTS = new HashMap<>();
    private final ServerSocket SERVER_SOCKET;
    private final ServerObserver OBSERVER;
    private final int PORT;

    public Server(ServerObserver observer, int port) throws IOException {
        this.OBSERVER = observer;
        this.PORT = port;
        SERVER_SOCKET = new ServerSocket(PORT);
        SERVER_SOCKET.setReuseAddress(true);
        clientsListener();
    }
    
    private void clientsListener() {
        new Thread(() -> {
            try {
                while(!SERVER_SOCKET.isClosed()) {
                    OBSERVER.notifyWaiting();
                    Socket socket = SERVER_SOCKET.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message = in.readLine();
                    String[] messageSplited = message.split(";");
                    String name;
                    switch(messageSplited[0]) {
                        case "CONNECT":
                            name = messageSplited[3];
                            HOSTS_OFFLINE.remove(name);
                            HOSTS_ONLINE.add(name);
                            HOSTSPORTS.put(name, messageSplited[1]+":"+messageSplited[2]);
                            OBSERVER.notifyOnline(name);
                            sendToAll(messageSplited[0] + ";" + name);
                            break;
                        case "LISTHOSTS":
                            String list = messageSplited[0];
                            name = messageSplited[1];
                            for (String n: HOSTS_ONLINE) {
                                if (!n.equals(name)) {
                                    list += ";" + n + ";" + "ONLINE";
                                }
                            }
                            for (String n: HOSTS_OFFLINE) {
                                if (!n.equals(name)) {
                                    list += ";" + n + ";" + "OFFLINE";
                                }
                            }
                            sendToOne(messageSplited[1], list);
                            break;
                        case "MESSAGE":
                            sendToAll(message);
                            break;
                        case "DISCONNECT":
                            name = messageSplited[1];
                            sendToAll(message);
                            OBSERVER.notifyOffline(name);
                            HOSTS_ONLINE.remove(name);
                            HOSTS_OFFLINE.add(name);
                            break;
                        case "QUIT":
                            name = messageSplited[1];
                            OBSERVER.notifyQuit(name);
                            sendToAll(message);
                            HOSTS_ONLINE.remove(name);
                            break;
                    }
                    in.close();
                    socket.close();
                }
            } catch(IOException e) {
                OBSERVER.notifyError();
            } finally {
                OBSERVER.notifyClose();
            }
        }).start();
    }
    
    private void sendToOne(String name, String msg) {
        String hostport = HOSTSPORTS.get(name);
        String host = hostport.split(":")[0];
        int port = Integer.parseInt(hostport.split(":")[1]);
        try {
            Socket socket = new Socket(host, port);
            new PrintStream(socket.getOutputStream()).println(msg);
            socket.close();
        } catch(IOException e) {
            HOSTS_ONLINE.remove(name);
            HOSTS_OFFLINE.add(name);
            OBSERVER.notifyOffline(name);
            sendToAll("DISCONNECT;" + name);
        }
    }

    private void sendToAll(String msg) {
        for (String name : HOSTS_ONLINE) {
            String hostport = HOSTSPORTS.get(name);
            String host = hostport.split(":")[0];
            int port = Integer.parseInt(hostport.split(":")[1]);
            try {
                Socket socket = new Socket(host, port);
                new PrintStream(socket.getOutputStream()).println(msg);
                socket.close();
            } catch(IOException e) {
                HOSTS_ONLINE.remove(name);
                HOSTS_OFFLINE.add(name);
                OBSERVER.notifyOffline(name);
                sendToAll("DISCONNECT;" + name);
            }
        }
    }
}