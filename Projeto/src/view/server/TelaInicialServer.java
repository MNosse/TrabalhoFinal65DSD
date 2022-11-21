package view.server;

import controller.server.TelaInicialServerController;

import javax.swing.*;

public class TelaInicialServer {
    private int port;
    private TelaInicialServerController controller;
    
    public TelaInicialServer() {
        controller = new TelaInicialServerController();
    }
    
    public void iniciar() {
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
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
        UIManager.put("OptionPane.okButtonText", "Encerrar");
        JOptionPane.showMessageDialog(null, "Host: " + controller.getHostAdress() + "\n" + "Port: " + port, "Servidor", JOptionPane.INFORMATION_MESSAGE, null);
    }
}
