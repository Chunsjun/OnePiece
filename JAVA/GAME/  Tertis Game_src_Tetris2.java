import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: leonardkgoadi
 * Date: 2013/07/01
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */

//Draw a red cell with black border

public class Tetris2 extends JPanel {

    public void init(){
        this.setPreferredSize(new Dimension(640,480));
        this.setBackground(Color.GREEN);
    }

    public void paint(Graphics gr){
        super.paint(gr);
        gr.setColor(Color.BLACK);
        gr.fillRect(0,0,24,24);
        gr.setColor(Color.RED);
        gr.fillRect(1,1,22,22);
    }

    public static void main(String[]args){
        JFrame window = new JFrame("Macteki Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Tetris2 tetris = new Tetris2();
        tetris.init();

        window.add(tetris);
        window.pack();
        window.setVisible(true);
    }

}
