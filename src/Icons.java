import javax.swing.*;
import java.awt.*;

public class Icons {
    private static Image num1= (new ImageIcon("images/1.png")).getImage();
    private static Image num2= (new ImageIcon("images/2.png")).getImage();
    private static Image num3= (new ImageIcon("images/3.png")).getImage();
    private static Image num4= (new ImageIcon("images/4.png")).getImage();
    private static Image num5= (new ImageIcon("images/5.png")).getImage();
    private static Image num6= (new ImageIcon("images/6.png")).getImage();

    private static Image mine= (new ImageIcon("images/mine.png")).getImage();

    private static Image flag= (new ImageIcon("images/flag.png")).getImage();

    private static Image timer= (new ImageIcon("images/timer.png")).getImage();


    public static Image[] getArrayNum() {
        return new Image[]{num1, num2, num3, num4, num5, num6};
    }

    public static Image getMine() {
        return mine;
    }

    public static Image getFlag() {
        return flag;
    }

    public static Image getTimer() {
        return timer;
    }
}


























