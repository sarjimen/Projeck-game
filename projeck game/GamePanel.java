import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private Thread gameThread;
    private boolean running;

    public static final int WIDTH = 1500;
    public static final int HEIGHT = 1200;

    private int playerX, playerY;
    private int playerSize = 100;
    private int speed = 5;
    private boolean left, right, up, down;

    private Image playerImage, foodImage, enemyImage, backgroundImage;
    private int foodX, foodY, foodSize = 70;
    private int enemyX, enemyY, enemySize = 60, enemySpeed = 2;
    private boolean gameOver = false;

    private int score = 0;
    private Font scoreFont = new Font("Arial", Font.BOLD, 24);

    // Tombol
    private JButton startButton, restartButton;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null); // untuk posisi tombol
        setFocusable(true);
        addKeyListener(this);

        // Load gambar
        playerImage = new ImageIcon("cupang.png").getImage();
        backgroundImage = new ImageIcon("back.jpeg").getImage();
        foodImage = new ImageIcon("makan.png").getImage();
        enemyImage = new ImageIcon("musuh.png").getImage();

        setupButtons();
    }

    private void setupButtons() {
        startButton = new JButton("Mulai");
        startButton.setBounds(WIDTH / 2 - 75, HEIGHT / 2 - 30, 150, 40);
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.addActionListener(e -> startGame());
        add(startButton);

        restartButton = new JButton("Main Lagi");
        restartButton.setBounds(WIDTH / 2 - 75, HEIGHT / 2 + 30, 150, 40);
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.setVisible(false);
        restartButton.addActionListener(e -> restartGame());
        add(restartButton);
    }

    public void startGame() {
        startButton.setVisible(false);
        restartButton.setVisible(false);
        requestFocusInWindow();

        resetGame();
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void restartGame() {
        gameOver = false;
        startGame();
    }

    public void resetGame() {
        playerSize = 100;
        score = 0;
        playerX = WIDTH / 2;
        playerY = HEIGHT / 2;
        spawnFood();
        spawnEnemy();
    }

    @Override
    public void run() {
        while (running) {
            update();
            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (gameOver) return;

        if (left) playerX -= speed;
        if (right) playerX += speed;
        if (up) playerY -= speed;
        if (down) playerY += speed;

        Rectangle playerRect = new Rectangle(playerX, playerY, playerSize, playerSize);
        Rectangle foodRect = new Rectangle(foodX, foodY, foodSize, foodSize);
        Rectangle enemyRect = new Rectangle(enemyX, enemyY, enemySize, enemySize);

        if (playerRect.intersects(foodRect)) {
            playerSize += 5;
            score += 10;
            spawnFood();
        }

        // Musuh bergerak ke arah pemain
        if (playerX > enemyX) enemyX += enemySpeed;
        if (playerX < enemyX) enemyX -= enemySpeed;
        if (playerY > enemyY) enemyY += enemySpeed;
        if (playerY < enemyY) enemyY -= enemySpeed;

        // Tabrak musuh
        if (playerRect.intersects(enemyRect)) {
            gameOver = true;
            running = false;
            restartButton.setVisible(true);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, this);
        g.drawImage(foodImage, foodX, foodY, foodSize, foodSize, this);
        g.drawImage(enemyImage, enemyX, enemyY, enemySize, enemySize, this);
        g.drawImage(playerImage, playerX, playerY, playerSize, playerSize, this);

        g.setColor(Color.BLACK);
        g.setFont(scoreFont);
        g.drawString("Skor: " + score, 20, 30);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("GAME OVER", WIDTH / 2 - 170, HEIGHT / 2 - 100);
        }
    }

    // Input
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_UP -> up = true;
            case KeyEvent.VK_DOWN -> down = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_RIGHT -> right = false;
            case KeyEvent.VK_UP -> up = false;
            case KeyEvent.VK_DOWN -> down = false;
        }
    }

    public void keyTyped(KeyEvent e) {}

    private void spawnFood() {
        foodX = (int) (Math.random() * (WIDTH - foodSize));
        foodY = (int) (Math.random() * (HEIGHT - foodSize));
    }

    private void spawnEnemy() {
        enemyX = (int) (Math.random() * (WIDTH - enemySize));
        enemyY = (int) (Math.random() * (HEIGHT - enemySize));
    }
}
