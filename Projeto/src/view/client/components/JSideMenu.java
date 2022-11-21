package view.client.components;

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
    private boolean isClose;
    private Color color;
    private int height;
    private final int minWidth;
    private final int maxWidth;
    private final int buttonSize;
    private final int buttonMargin;
    private Map<String, JUserIcon> users;
    private GridLayout layoutUsers;
    private JPanel panUsers;
    private JScrollPane scpUsers;
    private JSideMenuButton smbMore;
    private JSideMenuButton smbLogout;
    private JSideMenuButton smbQuit;
    
    public JSideMenu(Color color, int minWidth, int maxWidth, int height) {
        users = new HashMap<>();
        this.color = color;
        this.height = height;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        isClose = true;
        buttonSize = (int)(minWidth * 0.8);
        buttonMargin = (minWidth - buttonSize)/2;
        layoutUsers = new GridLayout();
        layoutUsers.setColumns(1);
        layoutUsers.setRows(0);
        layoutUsers.setVgap(buttonMargin);
        //THIS
        setLayout(new BorderLayout());
        setVisible(true);
        setBackground(color);
        setMinimumSize(new Dimension(minWidth, height));
        setMaximumSize(new Dimension(maxWidth, height));
        setPreferredSize(getMinimumSize());
        setSize(getPreferredSize());
        //JPANEL panButtons
        JPanel panButtons = new JPanel();
        panButtons.setLayout(new GridBagLayout());
        panButtons.setBackground(color);
        panButtons.setMinimumSize(new Dimension(minWidth, (buttonSize*3) + (buttonMargin*3)));
        panButtons.setMaximumSize(panButtons.getMinimumSize());
        panButtons.setPreferredSize(panButtons.getMinimumSize());
        panButtons.setSize(panButtons.getPreferredSize());
        //JSIDEMENUBUTTON smbMore
        smbMore = new JSideMenuButton(buttonSize, ImagesInstances.MORE_BTN, ImagesInstances.MORE_CLICKED_BTN);
        //JSIDEMENUBUTTON smbLogout
        smbLogout = new JSideMenuButton(buttonSize, ImagesInstances.LOGOUT_BTN, ImagesInstances.LOGOUT_CLICKED_BTN);
        //JSIDEMENUBUTTON smbQuit
        smbQuit = new JSideMenuButton(buttonSize, ImagesInstances.QUIT_BTN, ImagesInstances.QUIT_CLICKED_BTN);
        //JPANEL panButtons
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = -1;
        constraints.insets = new Insets(0, 0, buttonMargin, 0);
        panButtons.add(smbMore, constraints);
        panButtons.add(smbLogout, constraints);
        panButtons.add(smbQuit, constraints);
        //JPANEL panUsers;
        panUsers = new JPanel();
        panUsers.setLayout(layoutUsers);
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
        scpUsers.setBorder(new EmptyBorder(0, buttonMargin, buttonMargin, 0));
        scpUsers.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //THIS
        add(scpUsers, BorderLayout.CENTER);
        add(panButtons, BorderLayout.PAGE_END);
        
        //Actions
        smbMore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isClose) {
                    isClose = false;
                    new Thread(() -> {
                        try {
                            for (int i = minWidth; i <= maxWidth; i = i + 2) {
                                scpUsers.setSize(i, scpUsers.getHeight());
                                setSize(i, getHeight());
                                Thread.sleep(1);
                            }
                        } catch(InterruptedException e1) {
                            setSize(maxWidth, getHeight());
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
                                scpUsers.setSize(i, scpUsers.getHeight());
                                setSize(i, getHeight());
                                Thread.sleep(1);
                            }
                        } catch(InterruptedException e1) {
                            setSize(minWidth, getHeight());
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
    
    public void addUser(String host, String username, boolean isOnline) {
        users.put(host, new JUserIcon(color, minWidth, maxWidth, buttonSize, isOnline, false, username));
        layoutUsers.setRows(layoutUsers.getRows() + 1);
        panUsers.setSize(panUsers.getWidth(), panUsers.getHeight()+buttonSize+buttonMargin);
        panUsers.add(users.get(host));
        panUsers.revalidate();
        panUsers.repaint();
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
