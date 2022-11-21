package view.server;

import controller.server.ServerGUIController;
import controller.server.ServerGUIObserver;
import view.resourses.ColorsIntances;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerGUI implements ServerGUIObserver {
    //CONTROLLER
    private ServerGUIController controller;
    
    //VARIABLES
    int port;
    
    //SWING
    private JFrame frmScreen;
    private JTextArea txaConsole;
    
    public ServerGUI() throws IOException {
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        port = 0;
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
        initialize();
        controller = new ServerGUIController(this, port);
    }
    
    private void initialize() throws UnknownHostException {
        //JFRAME frmScreen
        frmScreen = new JFrame();
        frmScreen.setTitle("Servidor");
        frmScreen.setMinimumSize(new Dimension(400, 300));
        frmScreen.setPreferredSize(frmScreen.getMinimumSize());
        frmScreen.setLayout(new CardLayout());
        frmScreen.setLocationRelativeTo(null);
        frmScreen.setDefaultCloseOperation(finish());
        //JPANEL panBackground
        JPanel panBackground = new JPanel();
        panBackground.setMinimumSize(new Dimension(400-frmScreen.getInsets().left-frmScreen.getInsets().right, 300-frmScreen.getInsets().top-frmScreen.getInsets().bottom));
        panBackground.setPreferredSize(panBackground.getMinimumSize());
        panBackground.setBackground(Color.WHITE);
        panBackground.setLayout(new BorderLayout());
        //JTEXTAREA txaInfo
        JTextArea txaInfo = new JTextArea("Host: " + InetAddress.getLocalHost().getHostAddress() + "\nPort: " + port);
        txaInfo.setEditable(false);
        txaInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
        txaInfo.revalidate();
        //JTEXTAREA txaConsole
        txaConsole = new JTextArea();
        txaConsole.setEditable(false);
        txaConsole.setBackground(Color.WHITE);
        //BORDER consoleBorder
        Border consoleBorder = BorderFactory.createTitledBorder(new LineBorder(ColorsIntances.BLUE_THEME), "Console");
        //JSCROLLPANE scpConsole
        JScrollPane scpConsole = new JScrollPane(txaConsole);
        scpConsole.setBackground(Color.WHITE);
        scpConsole.setBorder(consoleBorder);
        //JPANEL panConsoleContainer;
        JPanel panConsoleContainer = new JPanel();
        panConsoleContainer.setBackground(Color.WHITE);
        panConsoleContainer.setBorder(new EmptyBorder(10, 10, 10, 10));
        panConsoleContainer.setLayout(new CardLayout());
        panConsoleContainer.add(scpConsole);
        panConsoleContainer.revalidate();
        //JPANEL panBackground
        panBackground.add(txaInfo, BorderLayout.PAGE_START);
        panBackground.add(panConsoleContainer, BorderLayout.CENTER);
        //JFRAME frmScreen
        frmScreen.add(panBackground);
        frmScreen.pack();
        frmScreen.setVisible(true);
    }
    
    private int finish() {
        //TODO encerrar o controller
        return JFrame.EXIT_ON_CLOSE;
    }
    
    @Override
    public void notifyOnline(String name) {
        txaConsole.append(name + " conectou\n");
    }
    
    @Override
    public void notifyOffline(String name) {
        txaConsole.append(name + " desconectou\n");
    }
    
    @Override
    public void notifyQuit(String name) {
        txaConsole.append(name + " saiu do grupo\n");
    }
    
    @Override
    public void notifyWaiting() {
        txaConsole.append("Aguardando conexão...\n");
    }
    
    @Override
    public void notifyError() {
        JOptionPane.showMessageDialog(frmScreen, "Ocorreu algum erro inesperado", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
