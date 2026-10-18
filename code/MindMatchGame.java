
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

class GameLogic {
    private int secretNumber;
    private int attempts;
    private ArrayList<Integer> guesses;
    private Random random;

    public GameLogic() {
        random = new Random();
        guesses = new ArrayList<>();
        newGame();
    }

    public void newGame() {
        secretNumber = random.nextInt(100) + 1;
        attempts = 0;
        guesses.clear();
    }

    public String checkGuess(int guess) {
        attempts++;
        guesses.add(guess);

        if (guess < secretNumber) {
            return "Too low! Try a higher number.";
        } else if (guess > secretNumber) {
            return "Too high! Try a lower number.";
        } else {
            return "Correct! You won in " + attempts + " attempts!";
        }
    }

    public int getAttempts() {
        return attempts;
    }

    public boolean isCorrect(int guess) {
        return guess == secretNumber;
    }

    public ArrayList<Integer> getGuesses() {
        return new ArrayList<>(guesses);
    }
}

public class MindMatchGame extends JFrame {

    private GameLogic game;
    private JTextField guessField;
    private JLabel messageLabel;
    private JLabel attemptsLabel;
    private JTextArea historyArea;
    private JButton checkButton;

    public MindMatchGame() {
        game = new GameLogic();

        setTitle("MindMatch - Number Guessing Game");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel(
                "MINDMATCH", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(30, 70, 150));
        title.setBorder(BorderFactory.createEmptyBorder(
                20, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6, 1, 8, 8));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(
                10, 30, 10, 30));

        JLabel instruction = new JLabel(
                "Guess a number between 1 and 100",
                SwingConstants.CENTER);

        guessField = new JTextField();
        guessField.setHorizontalAlignment(JTextField.CENTER);
        guessField.setFont(new Font("Arial", Font.PLAIN, 20));

        checkButton = new JButton("Check Guess");
        checkButton.setBackground(new Color(40, 100, 200));
        checkButton.setForeground(Color.WHITE);
        checkButton.addActionListener(e -> checkGuess());

        messageLabel = new JLabel(
                "Enter your first guess!",
                SwingConstants.CENTER);

        attemptsLabel = new JLabel(
                "Attempts: 0", SwingConstants.CENTER);

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> startNewGame());

        centerPanel.add(instruction);
        centerPanel.add(guessField);
        centerPanel.add(checkButton);
        centerPanel.add(messageLabel);
        centerPanel.add(attemptsLabel);
        centerPanel.add(newGameButton);

        add(centerPanel, BorderLayout.CENTER);

        historyArea = new JTextArea(4, 20);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder(
                "Guess History"));
        historyPanel.add(new JScrollPane(historyArea),
                BorderLayout.CENTER);

        add(historyPanel, BorderLayout.SOUTH);
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(
                    guessField.getText().trim());

            if (guess < 1 || guess > 100) {
                messageLabel.setText(
                        "Enter a number from 1 to 100.");
                return;
            }

            String result = game.checkGuess(guess);
            messageLabel.setText(result);
            attemptsLabel.setText(
                    "Attempts: " + game.getAttempts());

            historyArea.setText("");
            for (int number : game.getGuesses()) {
                historyArea.append(number + "\n");
            }

            if (game.isCorrect(guess)) {
                checkButton.setEnabled(false);
            }

            guessField.setText("");
            guessField.requestFocus();

        } catch (NumberFormatException ex) {
            messageLabel.setText("Please enter a valid number.");
        }
    }

    private void startNewGame() {
        game.newGame();
        messageLabel.setText("New game! Enter your guess.");
        attemptsLabel.setText("Attempts: 0");
        historyArea.setText("");
        guessField.setText("");
        checkButton.setEnabled(true);
        guessField.requestFocus();
    }
@Override
public String toString() {
    return "MindMatch Game Window";
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MindMatchGame window = new MindMatchGame();
            window.setVisible(true);
        });
    }
public String getGameName() {
    return "MindMatch Number Guessing Game";
}

// Method overloading
public String getGameName(String playerName) {
    return playerName + "'s MindMatch Game";
}
}
