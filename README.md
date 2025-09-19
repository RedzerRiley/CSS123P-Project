# Typing Speed Game

**Made by: Redzer Riley Monsod CSS123P**

## 📖 Overview

A comprehensive typing speed game built with Java Swing that challenges players to type sentences correctly within a time limit. The game features multiple difficulty levels, progressive speed increases, live text highlighting, anti-cheating measures, and a persistent high score system with a modern, professional user interface.

## 🎮 Game Features

### Core Gameplay
- **Three Difficulty Levels:**
  - **Easy**: 45 seconds initial time, simple sentences
  - **Medium**: 30 seconds initial time, moderate complexity sentences
  - **Hard**: 20 seconds initial time, complex technical sentences

- **Dynamic Scoring System:**
  - +10 points for each correct sentence
  - +5 seconds added to timer for correct answers
  - 3 lives per game session

- **Progressive Difficulty:**
  - Speed increases every 5 rounds
  - Timer reduces by 2 seconds every 5 rounds (minimum 5 seconds)
  - Unlimited rounds until time runs out or lives are lost

- **Live Text Highlighting:**
  - 🟢 **Green** - Correctly typed characters
  - 🔴 **Red** - Incorrectly typed characters (with pink background)
  - 🔵 **Blue** - Current position (next character to type, with gray background)
  - ⚫ **Black** - Characters not yet reached
  - Real-time feedback as you type

- **Anti-Cheating Security:**
  - Text selection disabled on sentence display
  - Copy/Paste functionality blocked (Ctrl+C, Ctrl+V, Ctrl+X)
  - Right-click context menus disabled
  - Warning messages for attempted cheating
  - Fair play enforcement through manual typing only

### Modern User Interface
- **Start Menu**: Professional gradient design with enhanced styling and visual feedback
- **Game Panel**: Card-based statistics display with color-coded status indicators
- **Game Over Screen**: Comprehensive performance dashboard with hall of fame table
- **Consistent Design**: Modern color palette, typography, and interactive elements throughout


## 🔧 Technical Implementation

### Key Classes and Responsibilities

#### `Game.java`
- Application entry point
- Initializes the start menu using SwingUtilities

#### `StartMenu.java` - **Enhanced Design**
- **Modern UI Elements:**
  - Gradient blue header with professional styling
  - Color-coded difficulty selection (Green/Orange/Red)
  - Enhanced input fields with focus management
  - Hover effects and visual feedback
  - Professional button styling with emoji indicators

#### `GamePanel.java` - **Major Enhancements**
- **Live Text Highlighting System:**
  - Real-time character-by-character comparison
  - StyledDocument implementation for rich text formatting
  - DocumentListener for instant feedback
  - Color-coded visual feedback system
  
- **Anti-Cheating Implementation:**
  - KeyListener for blocking copy/paste shortcuts
  - Text selection prevention on sentence display
  - Context menu disabling
  - User feedback for blocked actions
  
- **Modern Interface Design:**
  - Card-based statistics display with colored borders
  - Professional header with gradient background
  - Enhanced feedback system with emoji indicators
  - Responsive color coding for game state

#### `GameOverFrame.java` - **Professional Redesign**
- **Performance Dashboard:**
  - Clean card-based layout for player statistics
  - Color-coded ranking and difficulty display
  - Special highlighting for new high scores
  
- **Hall of Fame Table:**
  - Medal emojis for top 3 players (🥇🥈🥉)
  - Enhanced table styling with alternating row colors
  - Current player highlighting with star indicator
  - Professional column headers with icons

#### `TimerManager.java`
- Countdown timer with callback interface
- Dynamic speed adjustment for rounds
- Time bonus system (+5 seconds per correct answer)
- Pause, resume, and reset functionality

#### `SentenceLoader.java`
- File-based sentence loading with fallback system
- Resource and filesystem loading support
- Default sentences for each difficulty level with appropriate complexity
- Random sentence selection algorithm

#### `HighScoreManager.java`
- Persistent high score storage using file I/O
- Player ranking calculation with proper sorting
- Score comparison and updating logic (only higher scores saved)
- Top scores retrieval with medal ranking system

#### `Difficulty.java`
- Enum defining three difficulty levels
- Initial timer values and sentence file mappings
- Clean separation of difficulty parameters

## 🎯 Game Flow

1. **Initialization**: Player launches game through `Game.java`
2. **Enhanced Start Menu**: 
   - Enter player name with validation and focus management
   - Select difficulty level with color-coded options
   - Professional interface with hover effects
3. **Main Game Loop with Live Feedback**:
   - Display random sentence with real-time highlighting
   - Live character-by-character feedback as you type
   - Anti-cheat protection prevents copying/pasting
   - Submit with Enter key for validation
   - Correct: +10 points, +5 seconds, advance round
   - Incorrect: -1 life, clear input, retry same sentence
   - Automatic speed increases every 5 rounds
4. **Game End Conditions**:
   - Timer reaches zero or player loses all lives
5. **Professional Game Over Screen**:
   - Comprehensive performance statistics
   - Hall of Fame with medal rankings
   - Enhanced visual design with proper highlighting

## 📊 Enhanced Scoring System

- **Base Points**: 10 points per correct sentence
- **Time Bonus**: +5 seconds per correct answer
- **Life System**: 3 lives with visual indicators (💚💛❤️)
- **High Score Management**: Only saves if new score exceeds previous best
- **Ranking System**: Dynamic calculation with medal indicators for top performers

## 🎨 Modern User Experience Features

### Visual Design Elements
- **Professional Color Palette:**
  - Primary Blue: `#3F51B5` for headers and accents
  - Success Green: `#4CAF50` for positive actions
  - Warning Orange: `#FF9800` for cautions
  - Danger Red: `#F44336` for alerts
  - Background: `#F0F8FF` (Alice Blue) for consistency

### Interactive Features
- **Live Text Highlighting**: Instant feedback as you type
- **Color-coded Status Indicators**: Dynamic emoji updates based on game state
- **Hover Effects**: Professional button interactions
- **Focus Management**: Smooth navigation between components
- **Anti-Cheat Warnings**: Clear feedback for blocked actions

### Responsive Design
- **Card-based Layouts**: Clean, modern interface design
- **Consistent Typography**: Professional font hierarchy throughout
- **Proper Spacing**: Enhanced margins and padding for readability
- **Visual Hierarchy**: Clear information organization

## 🔒 Security and Fair Play Features

### Anti-Cheating Measures
- **Text Selection Prevention**: Cannot highlight or select sentence text
- **Copy/Paste Blocking**: Keyboard shortcuts (Ctrl+C, Ctrl+V, Ctrl+X) disabled
- **Context Menu Removal**: Right-click menus disabled on text areas
- **User Feedback**: Warning messages for attempted cheating
- **Manual Typing Enforcement**: Only genuine typing skills are tested

### Implementation Details
```java
// Text selection prevention
highlightedSentencePane.setFocusable(false);
highlightedSentencePane.setHighlighter(null);
highlightedSentencePane.setCaret(null);

// Copy/paste prevention with user feedback
inputField.addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && (e.getKeyCode() == KeyEvent.VK_V || 
                                  e.getKeyCode() == KeyEvent.VK_C || 
                                  e.getKeyCode() == KeyEvent.VK_X)) {
            e.consume();
            showWarningMessage();
        }
    }
});
```

## 📁 File Dependencies

The game requires the following text files:

- `easy_sentences.txt`: Simple sentences for beginners
- `medium_sentences.txt`: Moderate complexity sentences
- `hard_sentences.txt`: Complex technical sentences
- `highscores.txt`: Automatically created for persistent high scores

Each sentence file contains appropriate content for its difficulty level, ensuring progressive learning.

## 🚀 Running the Game

### Prerequisites
- Java JDK 8 or higher
- Java Swing libraries (included in standard JDK)

### Execution
```bash
javac -d bin src/main/*.java src/main/managers/*.java
java -cp bin main.Game
```

### File Structure Setup
Ensure sentence files are accessible in the classpath or place them in the working directory alongside the compiled classes.

## 🔍 Error Handling

- **File Loading**: Graceful fallback to default sentences if files are missing
- **Input Validation**: Comprehensive name field validation with user feedback
- **Timer Management**: Safe timer operations with proper state checking
- **Score Persistence**: Robust error handling for file I/O operations
- **UI Threading**: Proper Swing EDT usage for all UI updates
- **Anti-Cheat Detection**: Clear user feedback for blocked actions

## 🎓 Educational Value

This project demonstrates advanced programming concepts:

- **Modern UI Design**: Professional Swing interface development
- **Real-Time Text Processing**: Live highlighting and feedback systems
- **Security Implementation**: Anti-cheating measures and input validation
- **Event-Driven Architecture**: Complex event handling and callbacks
- **File I/O Operations**: Robust reading from and writing to text files
- **State Management**: Comprehensive game state tracking and persistence
- **Object-Oriented Design**: Clean class separation and encapsulation
- **Timer Programming**: Advanced countdown implementation with callbacks
- **Data Structures**: Efficient use of Maps, Lists, and custom objects

## 🔧 Customization Options

The game supports easy customization:
- **Sentence Content**: Modify text files for different content themes
- **Difficulty Parameters**: Adjust timing and complexity in `Difficulty.java`
- **Visual Themes**: Modify color schemes and styling in UI classes
- **Scoring System**: Customize point values and bonuses
- **Anti-Cheat Settings**: Adjust security measures as needed

## 📈 Future Enhancement Possibilities

- **Sound Effects**: Audio feedback for correct/incorrect answers
- **Statistics Dashboard**: WPM calculation and accuracy tracking
- **Multiplayer Mode**: Real-time competitive typing
- **Custom Themes**: User-selectable visual themes
- **Achievement System**: Unlockable rewards and milestones
- **Export Features**: High score sharing and statistics export
- **Advanced Security**: Additional anti-cheat measures
- **Accessibility Features**: Screen reader support and keyboard navigation

## 🏆 Key Improvements in This Version

### Version 2.0 Enhancements
1. **Live Text Highlighting**: Real-time feedback as you type
2. **Anti-Cheating System**: Comprehensive security measures
3. **Modern UI Design**: Professional interface with gradient backgrounds
4. **Enhanced Game Flow**: Smooth, uninterrupted gameplay
5. **Improved Statistics**: Detailed performance tracking with visual indicators
6. **Better User Experience**: Hover effects, focus management, and clear feedback
7. **Security Features**: Fair play enforcement through technical measures

---

*This enhanced typing game represents a significant evolution from basic text input validation to a comprehensive, secure, and visually appealing application that demonstrates advanced Java Swing development practices, real-time text processing, security implementation, and modern UI design principles.*
