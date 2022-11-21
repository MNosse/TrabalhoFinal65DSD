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

public class JUserIcon extends JPanel {
    private static final int LIMIT_CHARACTERS = 29;
    private boolean isComplete;
    private boolean isOnline;
    private final Color color;
    private final int height;
    private final int minWidth;
    private final int maxWidth;
    private final String username;
    private JTextArea txaName;
    
    public JUserIcon(Color color, int minWidth, int maxWidth, int height, boolean isOnline, boolean isComplete, String username) {
        this.color = color;
        this.height = height;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.isComplete = isComplete;
        this.isOnline = isOnline;
        this.username = username;
        initialize();
    }
    
    private void initialize() {
        //THIS
        setLayout(new BorderLayout());
        setVisible(true);
        setBackground(color);
        setMinimumSize(new Dimension(minWidth, height));
        setMaximumSize(new Dimension(maxWidth, height));
        setPreferredSize(getMinimumSize());
        setSize(getPreferredSize());
        //JLABEL lblIcon
        JLabel lblIcon = new JLabel(username.substring(0, 1));
        lblIcon.setFont(FontsInstances.COMFORTAA_BOLD.deriveFont(25f));
        lblIcon.setMinimumSize(new Dimension(height, height));
        lblIcon.setMaximumSize(lblIcon.getMinimumSize());
        lblIcon.setPreferredSize(lblIcon.getMinimumSize());
        lblIcon.setSize(lblIcon.getPreferredSize());
        lblIcon.setIcon(isOnline ? new ImageIcon(ImagesInstances.USER_ON_LBL.getScaledInstance(height, height, Image.SCALE_SMOOTH)) : new ImageIcon(ImagesInstances.USER_OFF_LBL.getScaledInstance(height, height, Image.SCALE_SMOOTH)));
        lblIcon.setHorizontalTextPosition(JLabel.CENTER);
        lblIcon.setVerticalTextPosition(JLabel.CENTER);
        lblIcon.setForeground(ColorsIntances.LIGHT_BLUE_THEME);
        //JTEXTAREA txaName
        txaName = new JTextArea(username.substring(0, Math.min(username.length(), LIMIT_CHARACTERS)));
        txaName.setBorder(new EmptyBorder(5, 5, 5, 5));
        txaName.setBackground(ColorsIntances.BLUE_THEME);
        txaName.setForeground(ColorsIntances.LIGHT_BLUE_THEME);
        txaName.setFont(FontsInstances.COMFORTAA_SEMIBOLD.deriveFont(15f));
        txaName.setEditable(false);
        txaName.setLineWrap(true);
        txaName.setWrapStyleWord(true);
        txaName.setMinimumSize(new Dimension(0, height));
        txaName.setMaximumSize(new Dimension(maxWidth-height, height));
        txaName.setPreferredSize(txaName.getMinimumSize());
        txaName.setSize(txaName.getPreferredSize());
        txaName.setVisible(false);
        //THIS
        add(lblIcon, BorderLayout.LINE_START);
        add(txaName, BorderLayout.CENTER);
    }
    
    public void open() {
        setSize(getMaximumSize());
        txaName.setVisible(true);
        revalidate();
        repaint();
    }

    public void close() {
        setSize(getMinimumSize());
        txaName.setVisible(false);
        revalidate();
        repaint();
    }
}
