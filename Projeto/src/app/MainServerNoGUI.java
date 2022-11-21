package app;

import model.server.Server;
import model.server.ServerObserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainServerNoGUI {
    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            int port;
            do {
                try {
                    System.out.println("Insira a porta que deseja utilizar:");
                    port = Integer.parseInt(s.next());
                    break;
                } catch(NumberFormatException e) {
                    System.out.println("A porta deve ser um valor inteiro (ex.: 8081).");
                }
            } while(true);
            try {
                System.out.println("Servidor:" + "\n" + "Host: " + InetAddress.getLocalHost().getHostAddress() + "\n" + "Port: " + port);
            } catch(UnknownHostException e) {
                System.exit(0);
            }
            ServerSocket serverSocket = new ServerSocket(port);
            new Thread(() -> {
                try {
                    System.out.println("Aguardando conexao...");
                    while(true) {
                        Socket conexao = serverSocket.accept();
                        new Server(new ServerObserver() {
                            @Override
                            public void notifyOnline(String name) {
                                System.out.println(name + " conectou");
                            }
    
                            @Override
                            public void notifyOffline(String name) {
                                System.out.println(name + " desconectou");
                            }
    
                            @Override
                            public void notifyQuit(String name) {
                                System.out.println(name + "saiu do grupo");
                            }
                        }, conexao).start();
                    }
                } catch(IOException e) {
                    System.out.println("Ocorreu algum erro inesperado");
                }
            }).start();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
