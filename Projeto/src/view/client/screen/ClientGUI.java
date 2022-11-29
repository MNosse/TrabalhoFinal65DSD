package view.client.screen;

import controller.client.ClientGUIController;
import controller.client.ClientGUIObserver;
import view.client.components.JSideMenu;
import view.client.components.JSideMenuButton;
import view.resourses.ColorsIntances;
import view.resourses.FontsInstances;
import view.resourses.ImagesInstances;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ClientGUI implements ClientGUIObserver {
    //CONTROLLER
    private ClientGUIController controller;
    
    //SWING
    private JFrame frmFrame;
    private JPanel panBackground;
    private JPanel panChat;
    private JSideMenu jsmMenu;
    private JTextArea txaInput;
    private JSideMenuButton smbSend;
    private JTextArea txaMessages;
    
    public ClientGUI() {
        String host;
        int port = 0;
        String name;
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
        do {
            name = JOptionPane.showInputDialog(null, "Insira um nome de usuario:", "Nome de usuário", JOptionPane.QUESTION_MESSAGE);
            if (name != null) {
                if(!name.isBlank()) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "O nome de usuario nao pode ser vazio.", "Nome invalido", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.exit(0);
            }
        } while (true);
        initialize();
        initializeActions();
        try {
            controller = new ClientGUIController(this, host, port, name);
        } catch(IOException e) {
            notifyError();
        }
    }
    
    
    public void initialize() {
        //VARIABLES
        int screenMinWidth = (int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.5856515373352855);
        int screenMinHeight = (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.78125);
        //JFRAME frmFrme
        frmFrame = new JFrame();
        frmFrame.setMinimumSize(new Dimension(screenMinWidth, screenMinHeight));
        frmFrame.setPreferredSize(frmFrame.getMinimumSize());
        frmFrame.setSize(frmFrame.getPreferredSize());
        frmFrame.setLocationRelativeTo(null);
//        frmFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //VARIABLES
        int panBackgroundMinWidth = screenMinWidth - frmFrame.getInsets().left - frmFrame.getInsets().right;
        int panBackgroundMinHeight = screenMinHeight - frmFrame.getInsets().top - frmFrame.getInsets().bottom;
        //JPANEL panBackground
        panBackground = new JPanel();
        panBackground.setLayout(new BorderLayout());
        panBackground.setBackground(ColorsIntances.LIGHT_GRAY_THEME);
        panBackground.setMinimumSize(new Dimension(panBackgroundMinWidth, panBackgroundMinHeight));
        panBackground.setPreferredSize(panBackground.getMinimumSize());
        panBackground.setSize(panBackground.getPreferredSize());
        //VARIABLES
        int sideBarMinWidth = (int)(panBackgroundMinWidth * 0.075);
        int sideBarMaxWidth = (int)(panBackgroundMinWidth * 0.25);
        //JSIDEMENU jsmMenu
        jsmMenu = new JSideMenu(ColorsIntances.BLUE_THEME, sideBarMinWidth, sideBarMaxWidth, panBackgroundMinHeight);
        //JTEXTAREA txaMessages
        txaMessages = new JTextArea();
        txaMessages.setEditable(false);
        txaMessages.setLineWrap(true);
        txaMessages.setWrapStyleWord(true);
        txaMessages.setBackground(Color.WHITE);
        txaMessages.setFont(FontsInstances.COMFORTAA_REGULAR.deriveFont(13f));
        //JPANEL panWrapperMessages
        JPanel panWrapperMessages = new JPanel();
        panWrapperMessages.setLayout(new CardLayout());
        panWrapperMessages.setBackground(Color.WHITE);
        panWrapperMessages.setBorder(new EmptyBorder(10, 10, 10, 10));
        panWrapperMessages.add(txaMessages);
        //JSCROLLPANE scpMessages
        JScrollPane scpMessages = new JScrollPane(panWrapperMessages);
        scpMessages.setBorder(new EmptyBorder(0, 0, 0, 0));
        scpMessages.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scpMessages.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //VARIABLES
        int txaInputHeight = (int)(panBackgroundMinHeight * 0.1);
        //JTEXTAREA txaInput
        txaInput = new JTextArea();
        txaInput.setLineWrap(true);
        txaInput.setWrapStyleWord(true);
        txaInput.setBackground(ColorsIntances.LIGHT_GRAY_THEME);
        txaInput.setBorder(new EmptyBorder(5, 5, 5, 5));
        //JSCROLLPANE scpInput
        JScrollPane scpInput = new JScrollPane(txaInput);
        scpInput.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scpInput.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scpInput.setBackground(ColorsIntances.LIGHT_GRAY_THEME);
        scpInput.setMinimumSize(new Dimension(scpInput.getMaximumSize().width, txaInputHeight));
        scpInput.setMaximumSize(scpInput.getMinimumSize());
        scpInput.setPreferredSize(scpInput.getMinimumSize());
        scpInput.setSize(scpInput.getPreferredSize());
        scpInput.setBorder(new LineBorder(ColorsIntances.BLUE_THEME));
        //VARIABLES
        int buttonSize = (int)(sideBarMinWidth * 0.8);
        //JSIDEMENUBUTTON smbSend
        smbSend = new JSideMenuButton(buttonSize, ImagesInstances.SEND_BTN, ImagesInstances.SEND_CLICKED_BTN);
        //JPANEL panWrapperCenter
        JPanel panWrapperCenter = new JPanel();
        panWrapperCenter.setBackground(Color.WHITE);
        panWrapperCenter.setLayout(new GridBagLayout());
        panWrapperCenter.add(smbSend);
        //JPANEL panInput
        JPanel panInput = new JPanel();
        panInput.setBackground(Color.WHITE);
        panInput.setBorder(new EmptyBorder(10, 10, 10, 10));
        panInput.setLayout(new BorderLayout(10, 0));
        panInput.add(scpInput, BorderLayout.CENTER);
        panInput.add(panWrapperCenter, BorderLayout.LINE_END);
        //JPANEL panChat;
        panChat = new JPanel();
        panChat.setBackground(Color.WHITE);
        panChat.setLayout(new BorderLayout());
        panChat.setBorder(new LineBorder(ColorsIntances.LIGHT_GRAY_THEME, 10));
        panChat.add(scpMessages, BorderLayout.CENTER);
        panChat.add(panInput, BorderLayout.PAGE_END);
        //JPANEL panBackground
        panBackground.add(jsmMenu, BorderLayout.LINE_START);
        panBackground.add(panChat, BorderLayout.CENTER);
        //JFRAME frmFrame
        frmFrame.setContentPane(panBackground);
        frmFrame.pack();
        frmFrame.setVisible(true);
    }
    
    public void initializeActions() {
        //JFRAME frmFrame
        frmFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    controller.disconnect();} catch(IOException ignored) {}
                System.exit(0);
            }
        });
        //JSIDEMENUBUTON smbSend
        smbSend.addActionListener(action -> {
            try {
                controller.sendMessage(txaInput.getText());
                txaInput.setText("");
            } catch(IOException e) {
                notifyError();
            }
        });
        //JSIDEMENU jsmMenu
        jsmMenu.setSmbLogoutActionListener(action -> {
            try {
                controller.disconnect();
            } catch(IOException e) {
                notifyError();
            }
        });
        jsmMenu.setSmbQuitActionListener(action -> {
            try {
                controller.quit();
            } catch(IOException e) {
                notifyError();
            }
        });
    }
    
    @Override
    public void listHosts(String[] hosts) {
        for (int i = 0; i < hosts.length; i = i + 2) {
            if (hosts[i+1].equals("ONLINE")) {
                notifyOnline(hosts[i]);
            } else {
                notifyOffline(hosts[i]);
            }
        }
    }
    
    @Override
    public void notifyClose() {
        frmFrame.dispose();
        new ClientGUI();
    }
    
    @Override
    public void notifyError() {
        JOptionPane.showMessageDialog(frmFrame, "Ocorreu algum erro inesperado", "Erro", JOptionPane.ERROR_MESSAGE);
        notifyClose();
    }
    
    @Override
    public void notifyOffline(String name) {
        jsmMenu.notifyOffline(name);
    }
    
    @Override
    public void notifyOnline(String name) {
        jsmMenu.notifyOnline(name);
    }
    
    @Override
    public void notifyQuit(String name) {
        jsmMenu.notifyQuit(name);
    }
    
    @Override
    public void showMessage(String who, String message) {
        txaMessages.append(who + ": " + message + "\n\n");
        txaMessages.setCaretPosition(txaMessages.getDocument().getLength());
    }
    
    private boolean verificaHost(String host) {
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
