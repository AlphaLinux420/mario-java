package com.game.graphics;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import com.game.Object.HighscoreTableModel;
import com.game.Object.User;
import com.game.superMario.Game;
public class Windows extends JFrame implements KeyListener {
    private Game game;
    private final Dimension size;
    private JPanel titelPanel;
    private JPanel gameOver;
    private JPanel mainPanel;
    private JPanel winPanel;
    private String levelName;
    private Image restartImg;
    private int currentLevelIndex;
    private final List<String> levelNames;
    private List<JButton> levelButtons;  // Liste für die Buttons
    private final List<User> users = new ArrayList<>();
    Comparator<User> userComparator = (user1, user2) -> Integer.compare(user2.getScore(), user1.getScore());
    private void createUser(int score) {
        users.add(new User(score, "User"));
        users.sort(userComparator);
    }
    public Windows(int width, int height, String title) {
        size = new Dimension(width, height);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);  // Setze den JFrame in den Fullscreen-Modus
        setLocationRelativeTo(null);
        setResizable(false);
        levelNames = new ArrayList<>();
        levelNames.add("level1.png");
        levelNames.add("level2.png");
        levelNames.add("level3.png");
        levelNames.add("level4.png");
        levelNames.add("level5.png");
        levelNames.add("level6.png");
        currentLevelIndex = 0; // Start mit dem ersten Level
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        createTitlePanel();
        createGameoverPanel();
        createWinPanel();
        gameOver.setVisible(false);
        // Setze das Layout des Windows-Objekts auf BorderLayout
        setLayout(new BorderLayout());
        add(titelPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }
    // Helper method to update the displayed level name and highlight the current button
    private void updateLevel() {
        levelName = levelNames.get(currentLevelIndex);
        highlightCurrentLevel();
    }
    private void highlightCurrentLevel() {
        // Setze die Hintergrundfarben der Buttons zurück
        for (int i = 0; i < levelButtons.size(); i++) {
            if (i == currentLevelIndex) {
                levelButtons.get(i).setBackground(Color.GREEN); // Hervorgehobenes Level
            } else {
                levelButtons.get(i).setBackground(null); // Standardfarbe
            }
        }
    }
    private void newGame() {
        game = new Game(this);
    }
    private void createTitlePanel() {
        titelPanel = new TitelPanel();
        titelPanel.setPreferredSize(size);
        setLocationRelativeTo(null);
        // Erstelle die Level-Buttons und speichere sie in der Liste
        levelButtons = new ArrayList<>();
        JButton level1 = new JButton("Level 1");
        JButton level2 = new JButton("Level 2");
        JButton level3 = new JButton("Level 3");
        JButton level4 = new JButton("Level 4");
        JButton level5 = new JButton("Jump n Run 1");
        JButton level6 = new JButton("Jump n Run 2");
        levelButtons.add(level1);
        levelButtons.add(level2);
        levelButtons.add(level3);
        levelButtons.add(level4);
        levelButtons.add(level5);
        levelButtons.add(level6);
        // Füge die Buttons zum Panel hinzu
        titelPanel.add(level1);
        titelPanel.add(level2);
        titelPanel.add(level3);
        titelPanel.add(level4);
        titelPanel.add(level5);
        titelPanel.add(level6);
        // Initiales Hervorheben des ersten Levels
        highlightCurrentLevel();
        // ActionListener für die Buttons
        level1.addActionListener(e -> {
            currentLevelIndex = 0;
            updateLevel();
            removeTitelPanel();
        });
        level2.addActionListener(e -> {
            currentLevelIndex = 1;
            updateLevel();
            removeTitelPanel();
        });
        level3.addActionListener(e -> {
            currentLevelIndex = 2;
            updateLevel();
            removeTitelPanel();
        });
        level4.addActionListener(e -> {
            currentLevelIndex = 3;
            updateLevel();
            removeTitelPanel();
        });
        level5.addActionListener(e -> {
            currentLevelIndex = 4;
            updateLevel();
            removeTitelPanel();
        });
        level6.addActionListener(e -> {
            currentLevelIndex = 5;
            updateLevel();
            removeTitelPanel();
        });
        JLabel name = new JLabel("Name: ");
        JTextField nameEntry = new JTextField();
        nameEntry.setPreferredSize(new Dimension(200, 25));
        JTable scoreList2 = new JTable();
        scoreList2.setModel(new HighscoreTableModel(users));
        titelPanel.add(name);
        titelPanel.add(nameEntry);
        titelPanel.add(scoreList2);
    }
    private void removeTitelPanel() {
        remove(titelPanel);
        titelPanel.setVisible(false);
        newGame();
        createMainPanel();
        add(mainPanel);
        revalidate();
        repaint();
        game.initialize();
        game.requestFocus();
    }

    public String getLevelName() {
        return levelName;
    }
    public void showWinPanel(int coinCount) {
        remove(mainPanel);
        createUser(coinCount);
        add(winPanel);
        winPanel.setVisible(true);
        game.stop();
    }
    public void createWinPanel() {
        winPanel = new WinPanel();
        winPanel.setPreferredSize(size);
        setLocationRelativeTo(null);
        winPanel.setVisible(false);
        JButton startButton = new JButton("You Win");
        addAction(startButton, winPanel);
        winPanel.add(startButton, BorderLayout.CENTER);
    }
    private void addAction(JButton startButton, JPanel winPanel) {
        startButton.addActionListener(e -> {
            remove(winPanel);
            winPanel.setVisible(false);
            createTitlePanel();
            add(titelPanel);
            titelPanel.setVisible(true);
            revalidate();
            repaint();
            game.requestFocus();
        });
    }
    public void createGameoverPanel() {
        gameOver = new GameoverPanel();
        gameOver.setPreferredSize(size);
        setLocationRelativeTo(null);
        gameOver.setVisible(false);
        try {
            restartImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("../resource/restartimg.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageButton restart = new ImageButton("Restart", restartImg);
        gameOver.add(restart);
        addAction(restart, gameOver);
    }
    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(game, BorderLayout.CENTER);
        mainPanel.setPreferredSize(size);
        setLocationRelativeTo(null);
        levelName = levelNames.get(currentLevelIndex);
    }
    public void showGameOverPanel() {
        remove(mainPanel);
        mainPanel.setVisible(false);
        gameOver.setVisible(true);
        add(gameOver);
        revalidate();
        repaint();
        game.getLevelHandler().getPlayer().setHealth(2);
        game.stop();
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            // Zum vorherigen Level wechseln
            currentLevelIndex = (currentLevelIndex - 1 + levelNames.size()) % levelNames.size();
            updateLevel();
        } else if (key == KeyEvent.VK_D) {
            // Zum nächsten Level wechseln
            currentLevelIndex = (currentLevelIndex + 1) % levelNames.size();
            updateLevel();
        } else if (key == KeyEvent.VK_SPACE) {
            if (gameOver.isVisible()) {
                remove(gameOver);
                gameOver.setVisible(false);
                createTitlePanel();
                add(titelPanel);
                titelPanel.setVisible(true);
                revalidate();
                repaint();
            } else if (winPanel.isVisible()) {
                remove(winPanel);
                winPanel.setVisible(false);
                createTitlePanel();
                add(titelPanel);
                titelPanel.setVisible(true);
                revalidate();
                repaint();
            } else {
                removeTitelPanel();
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
