import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: leonardkgoadi
 * Date: 2013/07/01
 * Time: 9:07 PM
 * To change this template use File | Settings | File Templates.
 */

//Drawing a green background

public class Tetris1 extends JPanel {

    public void init(){
        this.setPreferredSize(new Dimension(640,480));
        this.setBackground(Color.GREEN);
    }
    public static void main(String[]args) throws Exception{
        javax.swing.JFrame window = new JFrame("Macteki Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Tetris1 tetris = new Tetris1();
        tetris.init();

        window.add(tetris);
        window.pack();
        window.setVisible(true);
    }
}
