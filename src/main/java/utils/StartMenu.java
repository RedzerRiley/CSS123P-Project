package utils;
import managers.Difficulty;
import managers.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartMenu extends JFrame {
    private JTextField nameField;
    private JRadioButton easyButton, mediumButton, hardButton;
    private ButtonGroup difficultyGroup;
    private JButton startButton;

    public StartMenu() {
        setTitle("Typing Speed Game");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set a modern background color
        getContentPane().setBackground(new Color(240, 248, 255));

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setVisible(true);
    }

    private void initializeComponents() {
        // Name input
        nameField = new JTextField(20);
        nameField.setText("Enter your name");
        nameField.setForeground(Color.GRAY);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setBackground(Color.WHITE);
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        // Difficulty radio buttons with enhanced styling
        easyButton = new JRadioButton("Easy (45 sec, simple sentences)", true);
        mediumButton = new JRadioButton("Medium (30 sec, moderate sentences)");
        hardButton = new JRadioButton("Hard (20 sec, complex sentences)");

        // Style radio buttons
        Font radioFont = new Font("Arial", Font.PLAIN, 14);
        Color radioColor = new Color(60, 60, 60);

        easyButton.setFont(radioFont);
        easyButton.setForeground(new Color(46, 125, 50));
        easyButton.setBackground(new Color(240, 248, 255));
        easyButton.setFocusPainted(false);

        mediumButton.setFont(radioFont);
        mediumButton.setForeground(new Color(255, 152, 0));
        mediumButton.setBackground(new Color(240, 248, 255));
        mediumButton.setFocusPainted(false);

        hardButton.setFont(radioFont);
        hardButton.setForeground(new Color(211, 47, 47));
        hardButton.setBackground(new Color(240, 248, 255));
        hardButton.setFocusPainted(false);

        difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyButton);
        difficultyGroup.add(mediumButton);
        difficultyGroup.add(hardButton);

        // Enhanced start button
        startButton = new JButton("START GAME");
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(76, 175, 80));
        startButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(56, 142, 60), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        startButton.setFocusPainted(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                startButton.setBackground(new Color(67, 160, 71));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                startButton.setBackground(new Color(76, 175, 80));
            }
        });
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Create gradient-style title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(63, 81, 181));
        titlePanel.setPreferredSize(new Dimension(500, 100));
        titlePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("TYPING SPEED CHALLENGE", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

        JLabel subtitleLabel = new JLabel("Test your typing skills and beat the clock!", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitleLabel.setForeground(new Color(200, 200, 255));

        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);

        // Main content panel with modern styling
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Player name section
        JPanel nameSection = createStyledSection("Player Name", nameField);

        // Difficulty section
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.Y_AXIS));
        difficultyPanel.setBackground(new Color(240, 248, 255));

        // Add some spacing between radio buttons
        difficultyPanel.add(easyButton);
        difficultyPanel.add(Box.createVerticalStrut(8));
        difficultyPanel.add(mediumButton);
        difficultyPanel.add(Box.createVerticalStrut(8));
        difficultyPanel.add(hardButton);

        JPanel difficultySection = createStyledSection("Select Difficulty", difficultyPanel);

        // Button section
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.add(startButton);

        // Add all sections with spacing
        mainPanel.add(nameSection);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(difficultySection);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(buttonPanel);

        add(titlePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // Footer with info
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(250, 250, 250));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JLabel footerLabel = new JLabel("Each correct sentence gives you +10 points and +5 seconds!", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(100, 100, 100));
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createStyledSection(String title, JComponent content) {
        JPanel section = new JPanel(new BorderLayout(10, 10));
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(224, 224, 224), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(60, 60, 60));

        section.add(titleLabel, BorderLayout.NORTH);
        section.add(content, BorderLayout.CENTER);

        return section;
    }

    private void setupEventHandlers() {
        // Name field focus events
        nameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameField.getText().equals("Enter your name")) {
                    nameField.setText("");
                    nameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nameField.getText().isEmpty()) {
                    nameField.setText("Enter your name");
                    nameField.setForeground(Color.GRAY);
                }
            }
        });

        // Start button action
        startButton.addActionListener(e -> startGame());

        // Enter key on name field
        nameField.addActionListener(e -> startGame());
    }

    private void startGame() {
        String playerName = nameField.getText().trim();

        // Error handling for empty name
        if (playerName.isEmpty() || playerName.equals("Enter your name")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid player name!",
                    "Invalid Name",
                    JOptionPane.WARNING_MESSAGE);
            nameField.requestFocus();
            return;
        }

        // Get selected difficulty
        Difficulty difficulty;
        if (easyButton.isSelected()) {
            difficulty = Difficulty.EASY;
        } else if (mediumButton.isSelected()) {
            difficulty = Difficulty.MEDIUM;
        } else {
            difficulty = Difficulty.HARD;
        }

        // Create player and start game
        Player player = new Player(playerName);
        new GamePanel(player, difficulty);
        dispose();
    }
}
