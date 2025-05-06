import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("Game Ikan Cupang");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel panel = new GamePanel();
        add(panel);
        pack();
        setLocationRelativeTo(null); // center
        setVisible(true);
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}
