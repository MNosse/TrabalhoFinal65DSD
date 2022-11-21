package app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainServerNoGUI {
    public static void main(String[] args) {
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
        } while (true);
        try {
            System.out.println("Servidor:" + "\n" + "Host: " + InetAddress.getLocalHost().getHostAddress() + "\n" + "Port: " + port);
        } catch(UnknownHostException e) {
            System.exit(0);
        }
    }
}
