package view.client.screen;

import controller.client.ChatClientController;
import controller.client.ChatClientObserver;
import view.client.components.JSideMenu;
import view.resourses.ColorsIntances;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

public class ChatClient implements ChatClientObserver {
    private final int screenMinWidth = (int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.5856515373352855);
    private final int screenMinHeight = (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.78125);
    private final int panBackgroundMinWidth = screenMinWidth;
    private final int panBackgroundMinHeight = screenMinHeight;
    private final int sideBarMinWidth = (int)(panBackgroundMinWidth * 0.075);
    private final int sideBarMaxWidth = (int)(panBackgroundMinWidth * 0.25);
    
    private ChatClientController controller;
    
    private Map<String, String> users;
    
    private JFrame frmFrame;
    private JPanel panBackground;
    private JPanel panChat;
    private JSideMenu jsmMenu;
    
    public ChatClient(String host, int port) {
        users = new HashMap<>();
        initialize();
        initializeActions();
        controller = new ChatClientController(this, host, port);
    }
    
    
    public void initialize() {
        //JFRAME frmFrme
        frmFrame = new JFrame();
        JFrame.setDefaultLookAndFeelDecorated(true);
        frmFrame.getRootPane().putClientProperty("JRootPane.titleBarBackground", ColorsIntances.BLUE_THEME);
        frmFrame.getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
        frmFrame.setMinimumSize(new Dimension(screenMinWidth, screenMinHeight));
        frmFrame.setPreferredSize(frmFrame.getMinimumSize());
        frmFrame.setSize(frmFrame.getPreferredSize());
        frmFrame.setLocationRelativeTo(null);
        frmFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //JPANEL panBackground
        panBackground = new JPanel();
        panBackground.setLayout(new BorderLayout());
        panBackground.setBackground(new Color(245, 245, 245));
        panBackground.setMinimumSize(new Dimension(panBackgroundMinWidth, panBackgroundMinHeight));
        panBackground.setPreferredSize(panBackground.getMinimumSize());
        panBackground.setSize(panBackground.getPreferredSize());;
        //JFRAME frmFrame
        frmFrame.setContentPane(panBackground);
        //JSIDEMENU jsmMenu
        jsmMenu = new JSideMenu(ColorsIntances.BLUE_THEME, sideBarMinWidth, sideBarMaxWidth, panBackgroundMinHeight);
        //JPANEL panChat;
        panChat = new JPanel();
        panChat.setMaximumSize(new Dimension(panBackground.getWidth()-sideBarMinWidth, panBackgroundMinHeight));
        panChat.setMinimumSize(new Dimension(panBackground.getWidth()-sideBarMaxWidth, panBackgroundMinHeight));
        panChat.setPreferredSize(panChat.getMaximumSize());
        panChat.setSize(panChat.getPreferredSize());
        panChat.setBackground(Color.WHITE);
        panChat.setBorder(new LineBorder(new Color(245, 245, 245), 10));
        //JPANEL panBackground
        panBackground.add(jsmMenu, BorderLayout.LINE_START);
        panBackground.add(panChat, BorderLayout.CENTER);
        //JFRAME frmFrame
        frmFrame.pack();
        frmFrame.setVisible(true);
    }
    
    public void initializeActions() {
        //JFRAME frmFrame
        frmFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                panBackground.setSize(new Dimension(frmFrame.getWidth(), frmFrame.getHeight()));
                jsmMenu.setSize(new Dimension((int)jsmMenu.getSize().getWidth(), panBackground.getHeight()));
                panChat.setSize(new Dimension((int)(panBackground.getWidth()-jsmMenu.getSize().getWidth()), panBackground.getHeight()));
            }
        });
        //JSIDEMENU jsmMenu
        jsmMenu.setSmbLogoutActionListener(action -> {
            frmFrame.dispose();
            new TelaInicialClient().iniciar();
        });
        jsmMenu.setSmbQuitActionListener(action -> {
            frmFrame.dispose();
            new TelaInicialClient().iniciar();
        });
    }
    
    @Override
    public void addUser(String host, boolean isOnline, String username) {
        users.put(host, username);
        jsmMenu.addUser(host, username, isOnline);
    }
    
    @Override
    public void removeUser(String host, String username) {
        users.remove(host);
        jsmMenu.removeUser(host);
    }
    
    @Override
    public void changeToOnline(String host) {
    
    }
    
    @Override
    public void changeToOffline(String host) {
    
    }
}
