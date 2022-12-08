package view.client.components;

import view.resourses.ColorsIntances;
import view.resourses.FontsInstances;
import view.resourses.ImagesInstances;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class JSideMenu extends JPanel {
    //VARIABLES
    private boolean isClose;
    private Color color;
    private int height;
    private final int minWidth;
    private final int maxWidth;
    private final int buttonSize;
    private final int buttonMargin;
    
    //SWING
    private JPanel panUsers;
    private JPanel panButtons;
    private JScrollPane scpUsers;
    private JSideMenuButton smbMore;
    private JSideMenuButton smbQuit;
    private JSideMenuButton smbLogout;
    private GridLayout panUsersLayout;
    private Map<String, JUserIcon> users;
    
    public JSideMenu(Color color, int minWidth, int maxWidth, int height) {
        users = new HashMap<>();
        this.color = color;
        this.height = height;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        isClose = true;
        buttonSize = (int)(minWidth * 0.8);
        buttonMargin = (minWidth - buttonSize)/2;
        initialize();
        initializeActions();
    }
    
    public void initialize() {
        //THIS
        setLayout(new BorderLayout());
        setBackground(color);
        setMinimumSize(new Dimension(minWidth, height));
        setMaximumSize(new Dimension(maxWidth, height));
        setPreferredSize(getMinimumSize());
        setSize(getPreferredSize());
        //LAYOUT panButtonsLayout
        SpringLayout panButtonsLayout = new SpringLayout();
        //JPANEL panButtons
        panButtons = new JPanel();
        panButtons.setLayout(panButtonsLayout);
        panButtons.setBackground(color);
        panButtons.setBorder(new EmptyBorder(buttonMargin, buttonMargin, buttonMargin, buttonMargin));
        panButtons.setMinimumSize(new Dimension(maxWidth, (buttonSize*3) + (buttonMargin*4)));
        panButtons.setMaximumSize(panButtons.getMinimumSize());
        panButtons.setPreferredSize(panButtons.getMinimumSize());
        panButtons.setSize(panButtons.getPreferredSize());
        //JSIDEMENUBUTTON smbMore
        smbMore = new JSideMenuButton(buttonSize, ImagesInstances.MORE_BTN, ImagesInstances.MORE_CLICKED_BTN);
        //JLABEL lblQuit
        JLabel lblQuit = new JLabel("Sair");
        lblQuit.setMinimumSize(new Dimension((maxWidth - buttonSize), buttonSize));
        lblQuit.setMaximumSize(lblQuit.getMinimumSize());
        lblQuit.setPreferredSize(lblQuit.getMinimumSize());
        lblQuit.setSize(lblQuit.getPreferredSize());
        lblQuit.setAlignmentY(CENTER_ALIGNMENT);
        lblQuit.setForeground(ColorsIntances.LIGHT_BLUE_THEME);
        lblQuit.setFont(FontsInstances.COMFORTAA_SEMIBOLD.deriveFont(15f));
        //JSIDEMENUBUTTON smbQuit
        smbQuit = new JSideMenuButton(buttonSize, ImagesInstances.QUIT_BTN, ImagesInstances.QUIT_CLICKED_BTN);
        //JLABEL lblLogout
        JLabel lblLogout = new JLabel("Desconectar");
        lblLogout.setMinimumSize(new Dimension((maxWidth - buttonSize), buttonSize));
        lblLogout.setMaximumSize(lblLogout.getMinimumSize());
        lblLogout.setPreferredSize(lblLogout.getMinimumSize());
        lblLogout.setSize(lblLogout.getPreferredSize());
        lblLogout.setAlignmentY(CENTER_ALIGNMENT);
        lblLogout.setForeground(ColorsIntances.LIGHT_BLUE_THEME);
        lblLogout.setFont(FontsInstances.COMFORTAA_SEMIBOLD.deriveFont(15f));
        //JSIDEMENUBUTTON smbLogout
        smbLogout = new JSideMenuButton(buttonSize, ImagesInstances.LOGOUT_BTN, ImagesInstances.LOGOUT_CLICKED_BTN);
        //JPANEL panButtons
        panButtons.add(smbMore);
        panButtons.add(smbQuit);
        panButtons.add(lblQuit);
        panButtons.add(smbLogout);
        panButtons.add(lblLogout);
        panButtonsLayout.putConstraint(SpringLayout.NORTH, smbQuit, buttonMargin, SpringLayout.SOUTH, smbMore);
        panButtonsLayout.putConstraint(SpringLayout.NORTH, lblQuit, buttonMargin, SpringLayout.SOUTH, smbMore);
        panButtonsLayout.putConstraint(SpringLayout.WEST, lblQuit, buttonMargin, SpringLayout.EAST, smbQuit);
        panButtonsLayout.putConstraint(SpringLayout.NORTH, smbLogout, buttonMargin, SpringLayout.SOUTH, smbQuit);
        panButtonsLayout.putConstraint(SpringLayout.NORTH, lblLogout, buttonMargin, SpringLayout.SOUTH, smbQuit);
        panButtonsLayout.putConstraint(SpringLayout.WEST, lblLogout, buttonMargin, SpringLayout.EAST, smbLogout);
        //LAYOUT panUsersLayout
        panUsersLayout = new GridLayout(0, 1, 0, buttonMargin);
        //JPANEL panUsers;
        panUsers = new JPanel();
        panUsers.setLayout(new BoxLayout(panUsers, BoxLayout.Y_AXIS));
        panUsers.setBackground(color);
        //JSCROLLPANE scpUsers
        scpUsers = new JScrollPane(panUsers) {
            @Override
            public JScrollBar createVerticalScrollBar() {
                JScrollBar verticalScrollBar = new JScrollPane.ScrollBar(1);
                verticalScrollBar.setPreferredSize(new Dimension(0,0));
                return verticalScrollBar;
            }
        };
        scpUsers.setBorder(new EmptyBorder(buttonMargin, buttonMargin, buttonMargin, 0));
        scpUsers.setBackground(ColorsIntances.BLUE_THEME);
        scpUsers.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //THIS
        add(scpUsers, BorderLayout.CENTER);
        add(panButtons, BorderLayout.PAGE_END);
        setVisible(true);
    }
    
    public void initializeActions() {
        smbMore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isClose) {
                    isClose = false;
                    new Thread(() -> {
                        try {
                            for (int i = minWidth; i <= maxWidth; i = i + 2) {
                                setSize(i, getHeight());
                                panButtons.setSize(i, panButtons.getHeight());
                                scpUsers.setSize(i, scpUsers.getHeight());
                                Thread.sleep(1);
                            }
                        } catch(InterruptedException e1) {
                            setSize(maxWidth, getHeight());
                            panButtons.setSize(maxWidth, panButtons.getHeight());
                            scpUsers.setSize(maxWidth, scpUsers.getHeight());
                        } finally {
                            for (String key : users.keySet()) {
                                users.get(key).open();
                            }
                        }
                    }).start();
                } else {
                    isClose = true;
                    new Thread(() -> {
                        try {
                            for (int i = maxWidth; i >= minWidth; i = i - 2) {
                                setSize(i, getHeight());
                                panButtons.setSize(i, panButtons.getHeight());
                                scpUsers.setSize(i, scpUsers.getHeight());
                                Thread.sleep(1);
                            }
                        } catch(InterruptedException e1) {
                            setSize(minWidth, getHeight());
                            panButtons.setSize(minWidth, panButtons.getHeight());
                            scpUsers.setSize(minWidth, scpUsers.getHeight());
                        } finally {
                            for (String key : users.keySet()) {
                                users.get(key).close();
                            }
                        }
                    }).start();
                }
            }
        });
    }
    
    public void notifyOnline(String name) {
        if (!users.containsKey(name)) {
            users.put(name, new JUserIcon(color, minWidth, maxWidth, buttonSize, true, buttonMargin, name));
//            panUsers.setSize(panUsers.getWidth(), panUsers.getHeight()+buttonSize+buttonMargin);
//            panUsersLayout.setRows(panUsersLayout.getRows() + 1);
            panUsers.add(users.get(name));
            panUsers.revalidate();
            panUsers.repaint();
        } else {
            users.get(name).notifyOnline();
        }
    }
    
    public void notifyOffline(String name) {
        if (!users.containsKey(name)) {
            users.put(name, new JUserIcon(color, minWidth, maxWidth, buttonSize, false, buttonMargin, name));
            panUsers.add(users.get(name));
            panUsers.revalidate();
            panUsers.repaint();
        } else {
            users.get(name).notifyOffline();
        }
    }
    
    public void notifyQuit(String name) {
        if (users.containsKey(name)) {
            panUsers.remove(users.get(name));
            panUsers.revalidate();
            panUsers.repaint();
            users.remove(name);
        }
    }
    
    public void removeUser(String host) {
        users.remove(host);
    }
    
    public void setSmbLogoutActionListener(ActionListener actionListener) {
        smbLogout.addActionListener(actionListener);
    }
    
    public void setSmbQuitActionListener(ActionListener actionListener) {
        smbQuit.addActionListener(actionListener);
    }
}
