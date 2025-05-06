import javax.swing.*;

public class dasar {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game Cupang");
        GamePanel gamePanel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        

        gamePanel.startGame();
    }
}
