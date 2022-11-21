package view.resourses;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class ImagesInstances {
    public static Image LOGOUT_BTN;
    public static Image LOGOUT_CLICKED_BTN;
    public static Image MORE_BTN;
    public static Image MORE_CLICKED_BTN;
    public static Image QUIT_BTN;
    public static Image QUIT_CLICKED_BTN;
    public static Image USER_OFF_LBL;
    public static Image USER_ON_LBL;
    
    static {
        try {
            LOGOUT_BTN = ImageIO.read(Objects.requireNonNull(ImagesInstances.class.getResource("/images/logout_btn.png")));
            LOGOUT_CLICKED_BTN = ImageIO.read(Objects.requireNonNull(ImagesInstances.class.getResource("/images/logout_clicked_btn.png")));
            MORE_BTN = ImageIO.read(Objects.requireNonNull(ImagesInstances.class.getResource("/images/more_btn.png")));
            MORE_CLICKED_BTN = ImageIO.read(Objects.requireNonNull(ImagesInstances.class.getResource("/images/more_clicked_btn.png")));
            QUIT_BTN = ImageIO.read(Objects.requireNonNull(ImagesInstances.class.getResource("/images/quit_btn.png")));
            QUIT_CLICKED_BTN = ImageIO.read(Objects.requireNonNull(ImagesInstances.class.getResource("/images/quit_clicked_btn.png")));
            USER_OFF_LBL = ImageIO.read(Objects.requireNonNull(ImagesInstances.class.getResource("/images/user_off_lbl.png")));
            USER_ON_LBL = ImageIO.read(Objects.requireNonNull(ImagesInstances.class.getResource("/images/user_on_lbl.png")));
        } catch(IOException e) {
            System.exit(0);
        }
    }
}
