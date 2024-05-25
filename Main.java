import javax.swing.*;
import java.awt.*;

public class Main {
        
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowUI();
            }
        });
    }
    
    public static void createAndShowUI() {
        JFrame jframe = new JFrame();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(new ShaderPanel());
        jframe.pack();
        jframe.setVisible(true);
    }
}
