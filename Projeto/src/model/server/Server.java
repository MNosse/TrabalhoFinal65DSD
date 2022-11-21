package model.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class Server extends Thread {
    //STATIC
    private static final List<PrintStream> HOSTS_CONECTADOS = new ArrayList<>();
    
    //INSTANCE
    private final ServerObserver observer;
    private final Socket conexao;
    private String nomeCliente;

    public Server(ServerObserver observer, Socket conexao) {
        this.observer = observer;
        this.conexao = conexao;
    }
    
    @Override
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(this.conexao.getInputStream()));
            PrintStream saida = new PrintStream(this.conexao.getOutputStream());
            this.nomeCliente = entrada.readLine();
            observer.notifyOnline(nomeCliente);
            HOSTS_CONECTADOS.add(saida);
            String msg = entrada.readLine();
            lerMsg:
            while(true) {
                switch(msg) {
                    case "DISCONNECT":
                        observer.notifyOffline(nomeCliente);
                        sendToAll(saida, "OFFLINE");
                        break lerMsg;
                    case "QUIT":
                        observer.notifyQuit(nomeCliente);
                        sendToAll(saida, "QUIT");
                        HOSTS_CONECTADOS.remove(saida);
                        break lerMsg;
                    default:
                        sendToAll(null, msg);
                        msg = entrada.readLine();
                        break;
                }
            }
            this.conexao.close();
        } catch (IOException e) {
            observer.notifyOffline(nomeCliente);
            sendToAll(null, "OFFLINE");
        }
    }

    public void sendToAll(PrintStream saida, String msg) {
        for (PrintStream host : HOSTS_CONECTADOS) {
            if (host != saida) {
                host.println(this.nomeCliente + " "+ msg);
            }
        }
    }
}