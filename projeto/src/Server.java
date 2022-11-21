import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class Server extends Thread {
    private Socket conexao;
    private String nomeCliente;
    private static List<PrintStream> hostsConectados = new ArrayList<>();

    public Server(Socket socket) {
        this.conexao = socket;
    }

    public static void main(String args[]) throws IOException {
        BufferedReader mainReader = new BufferedReader(new InputStreamReader(System.in));
        int serverPort;

        System.out.println("Insira a porta deseja utilizar no server (ex.: 80)");
        while (true) {
            try {
                serverPort = Integer.parseInt(mainReader.readLine());
                break;
            } catch (NumberFormatException | IOException e) {
                System.out.println("Insira uma porta valida (ex.: 80)");
            }
        }

        ServerSocket server = new ServerSocket(serverPort);
        while (true) {
            System.out.println("Aguardando conexao...");
            Socket conexao = server.accept();
            Thread t = new Server(conexao);
            t.start();
        }
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(this.conexao.getInputStream()));

            PrintStream saida = new PrintStream(this.conexao.getOutputStream());

            this.nomeCliente = entrada.readLine();
            System.out.println(this.nomeCliente + " ONLINE ");

            if (this.nomeCliente == null) {
                return;
            }
            hostsConectados.add(saida);
            String msg = entrada.readLine();
            while (true) {
                if(!msg.equals("DISCONNECT")){
                    sendToAll(saida, msg);
                    msg = entrada.readLine();
                }
                else{
                    break;
                }
            }
            System.out.println(this.nomeCliente + " OFFLINE");
            sendToAll(saida, " OFFLINE");
            hostsConectados.remove(saida);
            this.conexao.close();
        } catch (IOException e) {
            System.out.println(nomeCliente + "Algo deu errado");
            try {
                sendToAll(null, "Offline");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void sendToAll(PrintStream saida, String msg) throws IOException {
        for (PrintStream e : hostsConectados) {
            if (e != saida) {
                e.println(this.nomeCliente + " "+ msg);
            }
        }
    }
}