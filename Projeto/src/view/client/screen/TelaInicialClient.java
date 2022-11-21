package view.client.screen;

import com.formdev.flatlaf.FlatIntelliJLaf;
import view.resourses.ColorsIntances;

import javax.swing.*;
import java.awt.*;

public class TelaInicialClient {
    private int port;
    private String host;
    
    public void iniciar() {
        FlatIntelliJLaf.registerCustomDefaultsSource("styles");
        FlatIntelliJLaf.setup();
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        do {
            host = JOptionPane.showInputDialog(null, "Insira o host que deseja conectar:", "Servidor", JOptionPane.QUESTION_MESSAGE);
            if (host != null) {
                if(verificaHost(host)) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "A host deve ser no formato 0.0.0.0 (ex.: 192.168.1.5).", "Host invalido", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.exit(0);
            }
        } while (true);
        do {
            try {
                String value = JOptionPane.showInputDialog(null, "Insira a porta que deseja utilizar:", "Servidor", JOptionPane.QUESTION_MESSAGE);
                if (value != null) {
                    port = Integer.parseInt(value);
                } else {
                    System.exit(0);
                }
                break;
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "A porta deve ser um valor inteiro (ex.: 8081).", "Porta invalida", JOptionPane.ERROR_MESSAGE);
            }
        } while (true);
        navToChat();
    }
    
    private void navToChat() {
        new ChatClient(host, port);
    }
    
    private static boolean verificaHost(String host) {
        String[] bytes = host.split("\\.");
        try {
            for(int i = 0; i < 4; i++) {
                Integer.parseInt(bytes[i]);
            }
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
