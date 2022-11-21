package view.resourses;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FontsInstances {
    public static Font COMFORTAA_BOLD;
    public static Font COMFORTAA_LIGHT;
    public static Font COMFORTAA_MEDIUM;
    public static Font COMFORTAA_REGULAR;
    public static Font COMFORTAA_SEMIBOLD;
    
    static {
        try {
            COMFORTAA_BOLD = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontsInstances.class.getResourceAsStream("/fonts/Comfortaa-Bold.ttf")));
            COMFORTAA_LIGHT = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontsInstances.class.getResourceAsStream("/fonts/Comfortaa-Light.ttf")));
            COMFORTAA_MEDIUM = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontsInstances.class.getResourceAsStream("/fonts/Comfortaa-Medium.ttf")));
            COMFORTAA_REGULAR = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontsInstances.class.getResourceAsStream("/fonts/Comfortaa-Regular.ttf")));
            COMFORTAA_SEMIBOLD = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontsInstances.class.getResourceAsStream("/fonts/Comfortaa-Semibold.ttf")));
        } catch(FontFormatException | IOException e) {
            System.exit(0);
        }
    }
}
