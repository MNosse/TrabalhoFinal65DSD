package view.client.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JSideMenuButton extends JButton {
    private final int size;
    private Image currentImage;
    private Image image;
    private Image imageClicked;
    private boolean over;
    
    public JSideMenuButton(int size, Image image, Image imageClicked) {
        this.size = size;
        this.image = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        this.imageClicked = imageClicked.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        currentImage = this.image;
        over = false;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setMinimumSize(new Dimension(size, size));
        setMaximumSize(getMinimumSize());
        setPreferredSize(getMinimumSize());
        setSize(getPreferredSize());
        setText("");
        initializeActions();
    }
    
    private void initializeActions() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentImage = imageClicked;
                repaint();
                currentImage = image;
                repaint();
            }
        
            @Override
            public void mousePressed(MouseEvent e) {
                currentImage = imageClicked;
                repaint();
            }
        
            @Override
            public void mouseReleased(MouseEvent e) {
                currentImage = image;
                repaint();
            }
        
            @Override
            public void mouseEntered(MouseEvent e) {
                //
            }
        
            @Override
            public void mouseExited(MouseEvent e) {
                currentImage = image;
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(currentImage, 0, 0, this);
        super.paintComponent(g);
    }
}
