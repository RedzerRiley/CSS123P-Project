package utils;
import managers.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameOverFrame extends JFrame {
    private Player player;
    private Difficulty difficulty;
    private HighScoreManager hsm;

    public GameOverFrame(Player player, Difficulty difficulty) {
        this.player = player;
        this.difficulty = difficulty;
        this.hsm = new HighScoreManager();
        
        // Save the score
        hsm.saveScore(player.getName(), player.getScore());
        
        initializeComponents();
        setupLayout();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Game Over - Typing Speed Game");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set modern background color
        getContentPane().setBackground(new Color(240, 248, 255));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Enhanced title panel with gradient
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(244, 67, 54)); // Red background for game over
        titlePanel.setPreferredSize(new Dimension(600, 80));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
        JLabel titleLabel = new JLabel("GAME OVER", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Player stats panel with modern styling
        JPanel playerStatsPanel = createPlayerStatsPanel();

        // High scores panel with enhanced design
        JPanel highScoresPanel = createHighScoresPanel();

        // Enhanced button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        JButton tryAgainButton = new JButton("PLAY AGAIN");
        tryAgainButton.setPreferredSize(new Dimension(160, 50));
        tryAgainButton.setFont(new Font("Arial", Font.BOLD, 16));
        tryAgainButton.setForeground(Color.WHITE);
        tryAgainButton.setBackground(new Color(76, 175, 80));
        tryAgainButton.setBorder(BorderFactory.createLineBorder(new Color(56, 142, 60), 2));
        tryAgainButton.setFocusPainted(false);
        tryAgainButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tryAgainButton.addActionListener(e -> {
            new StartMenu();
            dispose();
        });
        
        // Add hover effect to try again button
        tryAgainButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                tryAgainButton.setBackground(new Color(67, 160, 71));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                tryAgainButton.setBackground(new Color(76, 175, 80));
            }
        });
        
        JButton exitButton = new JButton("EXIT");
        exitButton.setPreferredSize(new Dimension(100, 50));
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(158, 158, 158));
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(117, 117, 117), 2));
        exitButton.setFocusPainted(false);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.addActionListener(e -> System.exit(0));
        
        // Add hover effect to exit button
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                exitButton.setBackground(new Color(97, 97, 97));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                exitButton.setBackground(new Color(158, 158, 158));
            }
        });
        
        buttonPanel.add(tryAgainButton);
        buttonPanel.add(exitButton);

        // Main content panel with modern layout
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(new Color(240, 248, 255));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        contentPanel.add(playerStatsPanel, BorderLayout.NORTH);
        contentPanel.add(highScoresPanel, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createPlayerStatsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(63, 81, 181), 2),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        // Title
        JLabel titleLabel = new JLabel("YOUR PERFORMANCE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(63, 81, 181));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        
        panel.add(Box.createVerticalStrut(15));

        // Create stats in a grid-like layout
        JPanel statsGrid = new JPanel(new GridLayout(3, 2, 20, 10));
        statsGrid.setBackground(Color.WHITE);

        // Player name
        JLabel nameIconLabel = new JLabel("Player:");
        nameIconLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameIconLabel.setForeground(new Color(100, 100, 100));
        JLabel nameLabel = new JLabel(player.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(new Color(63, 81, 181));

        // Score
        JLabel scoreIconLabel = new JLabel("Final Score:");
        scoreIconLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scoreIconLabel.setForeground(new Color(100, 100, 100));
        JLabel scoreLabel = new JLabel(String.valueOf(player.getScore()));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setForeground(new Color(255, 193, 7));

        // Rounds completed
        JLabel roundsIconLabel = new JLabel("Rounds:");
        roundsIconLabel.setFont(new Font("Arial", Font.BOLD, 14));
        roundsIconLabel.setForeground(new Color(100, 100, 100));
        JLabel roundsLabel = new JLabel(String.valueOf(player.getRound() - 1));
        roundsLabel.setFont(new Font("Arial", Font.BOLD, 14));

        statsGrid.add(nameIconLabel);
        statsGrid.add(nameLabel);
        statsGrid.add(scoreIconLabel);
        statsGrid.add(scoreLabel);
        statsGrid.add(roundsIconLabel);
        statsGrid.add(roundsLabel);

        panel.add(statsGrid);
        
        panel.add(Box.createVerticalStrut(15));

        // Difficulty and rank in a separate section
        JPanel bottomStats = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        bottomStats.setBackground(Color.WHITE);

        // Difficulty
        JLabel difficultyLabel = new JLabel("" + difficulty.name().toLowerCase());
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        Color diffColor = difficulty == Difficulty.EASY ? new Color(76, 175, 80) :
                         difficulty == Difficulty.MEDIUM ? new Color(255, 152, 0) :
                         new Color(244, 67, 54);
        difficultyLabel.setForeground(diffColor);

        // Rank
        int rank = hsm.getPlayerRank(player.getName(), player.getScore());
        JLabel rankLabel = new JLabel("Rank: #" + rank);
        rankLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Color code the rank
        if (rank == 1) {
            rankLabel.setForeground(new Color(244, 67, 54));
        } else if (rank <= 3) {
            rankLabel.setForeground(new Color(255, 152, 0));
        } else if (rank <= 5) {
            rankLabel.setForeground(new Color(33, 150, 243));
        } else {
            rankLabel.setForeground(new Color(100, 100, 100));
        }

        bottomStats.add(difficultyLabel);
        bottomStats.add(rankLabel);
        panel.add(bottomStats);

        // High score indicator with enhanced styling
        if (hsm.isNewHighScore(player.getName(), player.getScore())) {
            panel.add(Box.createVerticalStrut(15));
            JLabel newHighScoreLabel = new JLabel("NEW HIGH SCORE!");
            newHighScoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
            newHighScoreLabel.setForeground(new Color(244, 67, 54));
            newHighScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // Add a background highlight
            newHighScoreLabel.setOpaque(true);
            newHighScoreLabel.setBackground(new Color(255, 235, 235));
            newHighScoreLabel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            
            panel.add(newHighScoreLabel);
        }

        return panel;
    }

    private JPanel createHighScoresPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        // Enhanced title
        JLabel titleLabel = new JLabel("HALL OF FAME - TOP PLAYERS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(76, 175, 80));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Create table data
        List<Map.Entry<String, Integer>> topScores = hsm.getTopScores(10);
        
        String[] columnNames = {"Rank", "Player", "Score"};
        Object[][] data = new Object[Math.min(10, topScores.size())][3];
        
        for (int i = 0; i < Math.min(10, topScores.size()); i++) {
            Map.Entry<String, Integer> entry = topScores.get(i);
            
            String rank = "#" + (i + 1);
//            if (i == 0) rank = "#1";
//            else if (i == 1) rank = "#2";
//            else if (i == 2) rank = "#3";
//
            data[i][0] = rank;
            data[i][1] = entry.getKey();
            data[i][2] = entry.getValue() + " pts";
        }

        JTable table = new JTable(data, columnNames);
        table.setEnabled(false); // Make it non-editable
        table.getTableHeader().setReorderingAllowed(false);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(25);
        
        // Style the table header
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(76, 175, 80));
        table.getTableHeader().setForeground(Color.WHITE);
        
        // Highlight current player's row with enhanced styling
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;
            
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
                
                if (row < topScores.size() && topScores.get(row).getKey().equals(player.getName())) {
                    c.setBackground(new Color(255, 243, 224));
                    setFont(getFont().deriveFont(Font.BOLD));
                    setForeground(new Color(255, 152, 0));
                } else {
                    c.setBackground(row % 2 == 0 ? 
                        new Color(248, 248, 248) : Color.WHITE);
                    setFont(getFont().deriveFont(Font.PLAIN));
                    setForeground(new Color(60, 60, 60));
                }
                
                // Center align rank and score columns
                setHorizontalAlignment(column == 0 || column == 2 ? 
                    SwingConstants.CENTER : SwingConstants.LEFT);
                
                return c;
            }
        });

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(80);  // Rank
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Player
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // Score

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add a footer note
        JLabel footerNote = new JLabel("Your best score is automatically saved!");
        footerNote.setFont(new Font("Arial", Font.ITALIC, 12));
        footerNote.setForeground(new Color(150, 150, 150));
        footerNote.setHorizontalAlignment(JLabel.CENTER);
        footerNote.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panel.add(footerNote, BorderLayout.SOUTH);

        return panel;
    }
}