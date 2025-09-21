package utils;
import managers.TimerManager;
import managers.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JFrame implements TimerManager.TimerCallback {
    private Player player;
    private SentenceLoader loader;
    private TimerManager timerManager;
    private Difficulty difficulty;

    // UI Components
    private JLabel roundLabel, scoreLabel, livesLabel, timerLabel;
    private JLabel sentenceLabel;
    private JTextField inputField;
    private JLabel feedbackLabel;
    private JPanel statsPanel;
    private JTextPane highlightedSentencePane;

    private String currentSentence;
    private boolean gameActive;

    public GamePanel(Player player, Difficulty difficulty) {
        this.player = player;
        this.difficulty = difficulty;
        this.loader = new SentenceLoader(difficulty.getSentenceFile());
        this.timerManager = new TimerManager(difficulty.getInitialTime(), this);
        this.gameActive = true;

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadNewSentence();
        timerManager.start();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Typing Speed Game - Playing");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set modern background color
        getContentPane().setBackground(new Color(240, 248, 255));

        // Stats labels with enhanced styling
        roundLabel = new JLabel("Round: " + player.getRound());
        scoreLabel = new JLabel("Score: " + player.getScore());
        livesLabel = new JLabel("Lives: " + player.getLives());
        timerLabel = new JLabel("Time: " + difficulty.getInitialTime());

        // Style stats labels
        Font statsFont = new Font("Arial", Font.BOLD, 16);
        Color statsColor = new Color(60, 60, 60);

        roundLabel.setFont(statsFont);
        roundLabel.setForeground(statsColor);
        roundLabel.setIcon(createColorIcon(new Color(33, 150, 243))); // Blue

        scoreLabel.setFont(statsFont);
        scoreLabel.setForeground(statsColor);
        scoreLabel.setIcon(createColorIcon(new Color(255, 193, 7))); // Amber

        livesLabel.setFont(statsFont);
        livesLabel.setForeground(statsColor);
        livesLabel.setIcon(createColorIcon(new Color(76, 175, 80))); // Green

        timerLabel.setFont(statsFont);
        timerLabel.setForeground(statsColor);
        timerLabel.setIcon(createColorIcon(new Color(156, 39, 176))); // Purple

        // Game components with modern styling
        sentenceLabel = new JLabel("Type this sentence:");
        sentenceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        sentenceLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create highlighted sentence pane with enhanced styling
        highlightedSentencePane = new JTextPane();
        highlightedSentencePane.setEditable(false);
        highlightedSentencePane.setFont(new Font("Courier New", Font.PLAIN, 18));
        highlightedSentencePane.setBackground(Color.WHITE);
        highlightedSentencePane.setFocusable(false);
        highlightedSentencePane.setHighlighter(null);
        highlightedSentencePane.setCaret(null);
        highlightedSentencePane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                        "Sentence to type:",
                        0,
                        0,
                        new Font("Arial", Font.BOLD, 14),
                        new Color(63, 81, 181)
                ),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        inputField = new JTextField();
        inputField.setFont(new Font("Courier New", Font.PLAIN, 18));
        inputField.setBackground(Color.WHITE);
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
                        "⌨️ Your input:",
                        0,
                        0,
                        new Font("Arial", Font.BOLD, 14),
                        new Color(46, 125, 50)
                ),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Disable copy/paste functionality on input field
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Disable Ctrl+V (paste), Ctrl+C (copy), Ctrl+X (cut)
                if (e.isControlDown()) {
                    if (e.getKeyCode() == KeyEvent.VK_V ||
                            e.getKeyCode() == KeyEvent.VK_C ||
                            e.getKeyCode() == KeyEvent.VK_X) {
                        e.consume(); // Block the key event

                        // Show feedback message
                        feedbackLabel.setText("Copy/Paste is not allowed! Type manually!");
                        feedbackLabel.setForeground(new Color(255, 152, 0));

                        // Clear the message after 2 seconds
                        Timer clearMessage = new Timer(2000, clearEvent -> {
                            if (feedbackLabel.getText().contains("Copy/Paste")) {
                                feedbackLabel.setText(" ");
                            }
                        });
                        clearMessage.setRepeats(false);
                        clearMessage.start();
                    }
                }
            }
        });

        // Disable right-click context menu on both components
        inputField.setComponentPopupMenu(null);
        highlightedSentencePane.setComponentPopupMenu(null);

        feedbackLabel = new JLabel(" ");
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 16));
        feedbackLabel.setHorizontalAlignment(JLabel.CENTER);
        feedbackLabel.setOpaque(true);
        feedbackLabel.setBackground(new Color(250, 250, 250));
        feedbackLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Style the timer based on time remaining
        updateTimerDisplay();
    }

    private Icon createColorIcon(Color color) {
        return new Icon() {
            @Override
            public void paintIcon(java.awt.Component c, Graphics g, int x, int y) {
                g.setColor(color);
                g.fillOval(x, y, getIconWidth(), getIconHeight());
                g.setColor(color.darker());
                g.drawOval(x, y, getIconWidth(), getIconHeight());
            }

            @Override
            public int getIconWidth() { return 12; }

            @Override
            public int getIconHeight() { return 12; }
        };
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Header panel with gradient background
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(63, 81, 181));
        headerPanel.setPreferredSize(new Dimension(800, 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel headerLabel = new JLabel("TYPING CHALLENGE IN PROGRESS", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // Stats panel with modern card design
        statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setBackground(new Color(240, 248, 255));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Create stat cards
        JPanel roundCard = createStatCard(roundLabel, new Color(33, 150, 243));
        JPanel scoreCard = createStatCard(scoreLabel, new Color(255, 193, 7));
        JPanel livesCard = createStatCard(livesLabel, new Color(76, 175, 80));
        JPanel timerCard = createStatCard(timerLabel, new Color(156, 39, 176));

        statsPanel.add(roundCard);
        statsPanel.add(scoreCard);
        statsPanel.add(livesCard);
        statsPanel.add(timerCard);

        // Game panel with enhanced styling
        JPanel gamePanel = new JPanel(new BorderLayout(15, 15));
        gamePanel.setBackground(new Color(240, 248, 255));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        gamePanel.add(highlightedSentencePane, BorderLayout.NORTH);
        gamePanel.add(inputField, BorderLayout.CENTER);

        // Feedback panel with enhanced styling
        JPanel feedbackPanel = new JPanel(new BorderLayout());
        feedbackPanel.setBackground(new Color(240, 248, 255));
        feedbackPanel.add(feedbackLabel, BorderLayout.CENTER);

        gamePanel.add(feedbackPanel, BorderLayout.SOUTH);

        // Instructions panel with modern styling
        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setBackground(new Color(250, 250, 250));
        instructionsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

//        JLabel instructionsLabel = new JLabel(
//                "<html><center><b> HOW TO PLAY:</b><br>" +
//                        "Press ENTER to submit •  Green = Correct •  Red = Incorrect •  Blue = Current Position<br>" +
//                        "<i>Every correct answer: +10 points and +5 seconds • Speed increases every 5 rounds!</i><br>" +
//                        "<b> Note: Copy/Paste and text selection are disabled for fair play!</b></center></html>"
//        );
//        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 13));
//        instructionsLabel.setForeground(new Color(100, 100, 100));
//        instructionsLabel.setHorizontalAlignment(JLabel.CENTER);
//        instructionsPanel.add(instructionsLabel);

        add(headerPanel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(gamePanel, BorderLayout.SOUTH);

        // Create a container for game and instructions
        JPanel bottomContainer = new JPanel(new BorderLayout());
        bottomContainer.add(gamePanel, BorderLayout.CENTER);
        bottomContainer.add(instructionsPanel, BorderLayout.SOUTH);

        add(bottomContainer, BorderLayout.SOUTH);
    }

    private JPanel createStatCard(JLabel statLabel, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accentColor, 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Remove the icon since we'll style the text differently
        statLabel.setIcon(null);
        statLabel.setHorizontalAlignment(JLabel.CENTER);
        statLabel.setForeground(new Color(60, 60, 60));

        card.add(statLabel, BorderLayout.CENTER);

        // Add a subtle shadow effect
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(accentColor, 2),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                )
        ));

        return card;
    }

    private void setupEventHandlers() {
        inputField.addActionListener(e -> {
            if (gameActive) {
                checkInput();
            }
        });

        // Add document listener for live highlighting
        inputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateHighlighting();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateHighlighting();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateHighlighting();
            }
        });

        // Focus on input field when window opens
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                inputField.requestFocus();
            }
        });
    }

    private void loadNewSentence() {
        currentSentence = loader.getRandomSentence();
        updateHighlighting();
        inputField.setText("");
        inputField.requestFocus();

        // Clear previous feedback after a short delay
        Timer clearFeedback = new Timer(3000, e -> {
            if (feedbackLabel.getText().contains("Correct") ||
                    feedbackLabel.getText().contains("Wrong")) {
                feedbackLabel.setText(" ");
            }
        });
        clearFeedback.setRepeats(false);
        clearFeedback.start();
    }

    private void updateHighlighting() {
        if (currentSentence == null) return;

        String userInput = inputField.getText();
        StyledDocument doc = highlightedSentencePane.getStyledDocument();

        try {
            // Clear existing content
            doc.remove(0, doc.getLength());

            // Define styles
            Style correctStyle = highlightedSentencePane.addStyle("correct", null);
            StyleConstants.setForeground(correctStyle, Color.GREEN);
            StyleConstants.setBold(correctStyle, true);

            Style incorrectStyle = highlightedSentencePane.addStyle("incorrect", null);
            StyleConstants.setForeground(incorrectStyle, Color.RED);
            StyleConstants.setBold(incorrectStyle, true);
            StyleConstants.setBackground(incorrectStyle, Color.PINK);

            Style currentStyle = highlightedSentencePane.addStyle("current", null);
            StyleConstants.setForeground(currentStyle, Color.BLUE);
            StyleConstants.setBold(currentStyle, true);
            StyleConstants.setBackground(currentStyle, Color.LIGHT_GRAY);

            Style defaultStyle = highlightedSentencePane.addStyle("default", null);
            StyleConstants.setForeground(defaultStyle, Color.BLACK);

            // Apply highlighting character by character
            for (int i = 0; i < currentSentence.length(); i++) {
                char sentenceChar = currentSentence.charAt(i);
                String charStr = String.valueOf(sentenceChar);

                if (i < userInput.length()) {
                    char userChar = userInput.charAt(i);
                    if (userChar == sentenceChar) {
                        // Correct character
                        doc.insertString(doc.getLength(), charStr, correctStyle);
                    } else {
                        // Incorrect character
                        doc.insertString(doc.getLength(), charStr, incorrectStyle);
                    }
                } else if (i == userInput.length()) {
                    // Current position (next character to type)
                    doc.insertString(doc.getLength(), charStr, currentStyle);
                } else {
                    // Not yet typed
                    doc.insertString(doc.getLength(), charStr, defaultStyle);
                }
            }

            // If user typed more than the sentence length, show error
            if (userInput.length() > currentSentence.length()) {
                String extraText = userInput.substring(currentSentence.length());
                doc.insertString(doc.getLength(), " [EXTRA: " + extraText + "]", incorrectStyle);
            }

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void checkInput() {
        if (!gameActive) return;

        String typed = inputField.getText().trim();

        if (typed.equals(currentSentence)) {
            // Correct answer
            player.addScore(10);
            player.nextRound();
            timerManager.addTime(5);

            // Show positive feedback
            feedbackLabel.setText("Correct! +10 points and +5 seconds!");
            feedbackLabel.setForeground(Color.GREEN);

            // Update displays
            updateDisplays();

            // Check for speed increase every 5 rounds
            if ((player.getRound() - 1) % 5 == 0 && player.getRound() > 1) {
                timerManager.adjustSpeedForRound(player.getRound());
            }

            loadNewSentence();

        } else {
            // Wrong answer
            player.loseLife();
            feedbackLabel.setText("Wrong! Life lost. Try again!");
            feedbackLabel.setForeground(Color.RED);

            // Clear input field and refocus
            inputField.setText("");
            inputField.requestFocus();

            updateDisplays();

            // Check if game should end
            if (!player.isAlive()) {
                endGame();
            }
        }
    }

    private void updateDisplays() {
        roundLabel.setText("Round: " + player.getRound());
        scoreLabel.setText("Score: " + player.getScore());
        livesLabel.setText("Lives: " + player.getLives());

        // Update lives label color and styling
        if (player.getLives() <= 1) {
            livesLabel.setForeground(Color.RED);
            livesLabel.setText("Lives: " + player.getLives());
        } else if (player.getLives() <= 2) {
            livesLabel.setForeground(new Color(255, 152, 0));
            livesLabel.setText("Lives: " + player.getLives());
        } else {
            livesLabel.setForeground(new Color(60, 60, 60));
            livesLabel.setText("Lives: " + player.getLives());
        }
    }

    private void updateTimerDisplay() {
        int time = timerManager.getTimeRemaining();

        // Color coding and emoji for timer
        if (time <= 5) {
            timerLabel.setForeground(Color.RED);
            timerLabel.setText("Time: " + time);
        } else if (time <= 10) {
            timerLabel.setForeground(new Color(255, 152, 0));
            timerLabel.setText("Time: " + time);
        } else {
            timerLabel.setForeground(new Color(60, 60, 60));
            timerLabel.setText("Time: " + time);
        }
    }

    // TimerManager.TimerCallback implementation
    @Override
    public void onTimeUpdate(int timeRemaining) {
        SwingUtilities.invokeLater(() -> updateTimerDisplay());
    }

    @Override
    public void onTimeUp() {
        SwingUtilities.invokeLater(() -> {
            gameActive = false;
            feedbackLabel.setText("Time's up! Game Over!");
            feedbackLabel.setForeground(Color.RED);

            Timer delay = new Timer(2000, e -> endGame());
            delay.setRepeats(false);
            delay.start();
        });
    }

    private void endGame() {
        gameActive = false;
        timerManager.stop();
        new GameOverFrame(player, difficulty);
        dispose();
    }
}
