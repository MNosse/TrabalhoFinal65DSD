
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client extends Thread {
    private Socket conexao;
    private static String nome;
    private static String serverHost;
    private static int serverPort;

    public Client(Socket socket) {
        this.conexao = socket;
    }

    public static void main(String args[]) throws IOException {
        BufferedReader mainReader = new BufferedReader(new InputStreamReader(System.in));
        entrar(mainReader);
        Socket server = new Socket(serverHost, serverPort);
        PrintStream saida = new PrintStream(server.getOutputStream());
        saida.println(nome);
        Thread thread = new Client(server);
        thread.start();
        while (true) {
            String mensagemAEnviar;
            System.out.println("1 - Enviar mensagem 2- Desconectar");
            mensagemAEnviar = mainReader.readLine();
            if(mensagemAEnviar != null) {
                try {
                    switch (Integer.parseInt(mensagemAEnviar)) {
                        case 1:
                            enviarMensagem(mainReader,saida);
                            break;
                        case 2:
                            desconectar(saida);
                            break;
                        default:
                            System.out.println("Valor invalido");
                            break;
                    }
                }catch(NumberFormatException e) {
                    System.out.println("Informe um numero");
                }
            }
        }
    }

    public void run() {
        try {
            //Fica escutando o servidor esperando ele retornar a fala de algum outro client
            BufferedReader entrada = new BufferedReader(new InputStreamReader(this.conexao.getInputStream()));
            String msg;
            while (true) {
                msg = entrada.readLine();
                if (msg == null) {
                    System.out.println("Servidor offline");
                    System.exit(0);
                }
                System.out.println(msg);
            }
        } catch (IOException e) {
            System.out.println("Algo deu errado");
        }
    }

    public static void entrar(BufferedReader mainReader) throws IOException {
        System.out.println("Insira o endereco ip do servidor (ex.: 192.168.0.1)");
        serverHost = mainReader.readLine();
        while (!verificaHost(serverHost)) {
            System.out.println("Insira um endereco ip valido (ex.: 192.168.0.1)");
            serverHost = mainReader.readLine();
        }
        System.out.println("Insira a porta que deseja se conectar (ex.: 80)");
        while (true) {
            try {
                serverPort = Integer.parseInt(mainReader.readLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Insira uma porta valida (ex.: 80)");
            }
        }
        System.out.println("Insira um nome de usuário: ");
        nome = mainReader.readLine();
    }

    private static void enviarMensagem(BufferedReader mainReader, PrintStream saida) throws IOException {
        System.out.print("> ");
        String msg = mainReader.readLine();
        saida.println(msg);
    }

    private static void desconectar(PrintStream saida) throws IOException {
        saida.println("DISCONNECT");
    }

    private static boolean verificaHost(String host) {
        String[] bytes = host.split("\\.");
        try {
            for (int i = 0; i < 4; i++) {
                Integer.parseInt(bytes[i]);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}