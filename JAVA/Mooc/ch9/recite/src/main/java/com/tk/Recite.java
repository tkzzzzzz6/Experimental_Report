import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.nio.file.*;
import javax.swing.border.*;

// 明确指定使用 java.util.List 而不是 java.awt.List
import java.util.List;
// 明确指定使用 javax.swing.Timer 而不是 java.util.Timer
import javax.swing.Timer;

public class Recite extends JFrame {
	// UI Components
	private JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
	private JPanel cardPanel = new JPanel(new CardLayout());
	private JPanel studyPanel = new JPanel(new BorderLayout(10, 10));
	private JPanel testPanel = new JPanel(new BorderLayout(10, 10));
	private JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
	
	// Study mode components
	private JLabel wordLabel = new JLabel("", JLabel.CENTER);
	private JLabel meaningLabel = new JLabel("", JLabel.CENTER);
	private JButton difficultButton = new JButton("标记为生词");
	private JButton knownButton = new JButton("认识");
	private JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 3);
	
	// Test mode components
	private JLabel testWordLabel = new JLabel("", JLabel.CENTER);
	private JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
	private JLabel resultLabel = new JLabel("", JLabel.CENTER);
	private JButton nextButton = new JButton("下一个");
	
	// Mode switch buttons
	private JButton studyModeButton = new JButton("学习模式");
	private JButton testModeButton = new JButton("测试模式");
	private JButton reviewButton = new JButton("复习生词");
	
	// Data
	private java.util.List<Word> allWords = new ArrayList<>();
	private java.util.List<Word> difficultWords = new ArrayList<>();
	private int currentIndex = 0;
	private javax.swing.Timer wordTimer;
	private boolean isTestMode = false;
	private boolean isReviewMode = false;
	private Random random = new Random();
	
	public Recite() {
		setTitle("单词学习助手");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Setup UI
		setupUI();
		
		// Load data
		try {
			loadWords("College_Grade4.txt");
			loadDifficultWords("difficult_words.txt");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "加载单词文件失败: " + e.getMessage(), 
										"错误", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void setupUI() {
		// Set font styles
		Font wordFont = new Font("SansSerif", Font.BOLD, 36);
		Font meaningFont = new Font("SansSerif", Font.PLAIN, 24);
		Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
		
		wordLabel.setFont(wordFont);
		meaningLabel.setFont(meaningFont);
		testWordLabel.setFont(wordFont);
		resultLabel.setFont(meaningFont);
		
		// Add borders and styling
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		studyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		testPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// Study panel
		JPanel wordPanel = new JPanel(new BorderLayout());
		wordPanel.add(wordLabel, BorderLayout.CENTER);
		wordPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(20, 0, 20, 0),
			BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY)
		));
		
		JPanel meaningPanel = new JPanel(new BorderLayout());
		meaningPanel.add(meaningLabel, BorderLayout.CENTER);
		meaningPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		
		studyPanel.add(wordPanel, BorderLayout.NORTH);
		studyPanel.add(meaningPanel, BorderLayout.CENTER);
		
		JPanel studyControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
		studyControlPanel.add(difficultButton);
		studyControlPanel.add(knownButton);
		
		JPanel speedPanel = new JPanel(new BorderLayout(5, 0));
		speedPanel.add(new JLabel("速度:"), BorderLayout.WEST);
		speedPanel.add(speedSlider, BorderLayout.CENTER);
		speedSlider.setMajorTickSpacing(3);
		speedSlider.setMinorTickSpacing(1);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		
		JPanel studyBottomPanel = new JPanel(new BorderLayout());
		studyBottomPanel.add(studyControlPanel, BorderLayout.CENTER);
		studyBottomPanel.add(speedPanel, BorderLayout.SOUTH);
		studyPanel.add(studyBottomPanel, BorderLayout.SOUTH);
		
		// Test panel
		JPanel testWordPanel = new JPanel(new BorderLayout());
		testWordPanel.add(testWordLabel, BorderLayout.CENTER);
		testWordPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		
		testPanel.add(testWordPanel, BorderLayout.NORTH);
		testPanel.add(optionsPanel, BorderLayout.CENTER);
		
		JPanel testBottomPanel = new JPanel(new BorderLayout(10, 10));
		testBottomPanel.add(resultLabel, BorderLayout.CENTER);
		testBottomPanel.add(nextButton, BorderLayout.EAST);
		testPanel.add(testBottomPanel, BorderLayout.SOUTH);
		
		// Add panels to card layout
		cardPanel.add(studyPanel, "study");
		cardPanel.add(testPanel, "test");
		
		// Main control panel (mode switches)
		controlPanel.add(studyModeButton);
		controlPanel.add(testModeButton);
		controlPanel.add(reviewButton);
		
		// Add everything to main panel
		mainPanel.add(cardPanel, BorderLayout.CENTER);
		mainPanel.add(controlPanel, BorderLayout.SOUTH);
		
		// Set background colors
		mainPanel.setBackground(new Color(240, 240, 240));
		studyPanel.setBackground(new Color(250, 250, 250));
		testPanel.setBackground(new Color(250, 250, 250));
		
		// Add event listeners
		setupEventListeners();
		
		// Add to frame
		add(mainPanel);
	}
	
	private void setupEventListeners() {
		// Speed slider listener
		speedSlider.addChangeListener(e -> {
			if (!speedSlider.getValueIsAdjusting()) {
				updateTimer();
			}
		});
		
		// Study mode buttons
		difficultButton.addActionListener(e -> {
			if (currentIndex < allWords.size()) {
				Word currentWord = getCurrentWordList().get(currentIndex);
				if (!difficultWords.contains(currentWord)) {
					difficultWords.add(currentWord);
					saveDifficultWords();
				}
				JOptionPane.showMessageDialog(this, "已添加到生词本", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		knownButton.addActionListener(e -> {
			if (isReviewMode && currentIndex < difficultWords.size()) {
				difficultWords.remove(currentIndex);
				saveDifficultWords();
				if (difficultWords.isEmpty()) {
					JOptionPane.showMessageDialog(this, "恭喜！你已经掌握了所有生词", 
												"完成", JOptionPane.INFORMATION_MESSAGE);
					switchToStudyMode();
				} else {
					showCurrentWord();
				}
			} else {
				// In normal study mode, just move to next word
				moveToNextWord();
			}
		});
		
		// Test mode buttons
		nextButton.addActionListener(e -> {
			setupNextTest();
		});
		
		// Mode switching buttons
		studyModeButton.addActionListener(e -> switchToStudyMode());
		testModeButton.addActionListener(e -> switchToTestMode());
		reviewButton.addActionListener(e -> switchToReviewMode());
	}
	
	private void switchToStudyMode() {
		isTestMode = false;
		isReviewMode = false;
		currentIndex = 0;
		CardLayout cl = (CardLayout) cardPanel.getLayout();
		cl.show(cardPanel, "study");
		startWordTimer();
	}
	
	private void switchToTestMode() {
		if (allWords.size() < 4) {
			JOptionPane.showMessageDialog(this, "单词数量不足，无法进行测试", 
										"错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		stopWordTimer();
		isTestMode = true;
		isReviewMode = false;
		currentIndex = 0;
		CardLayout cl = (CardLayout) cardPanel.getLayout();
		cl.show(cardPanel, "test");
		setupNextTest();
	}
	
	private void switchToReviewMode() {
		if (difficultWords.isEmpty()) {
			JOptionPane.showMessageDialog(this, "生词本为空", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		isTestMode = false;
		isReviewMode = true;
		currentIndex = 0;
		CardLayout cl = (CardLayout) cardPanel.getLayout();
		cl.show(cardPanel, "study");
		startWordTimer();
	}
	
	private void startWordTimer() {
		stopWordTimer();
		int delay = 11000 - (speedSlider.getValue() * 1000); // 1-10 seconds based on slider
		wordTimer = new javax.swing.Timer(delay, e -> moveToNextWord());
		wordTimer.setInitialDelay(0);
		wordTimer.start();
	}
	
	private void stopWordTimer() {
		if (wordTimer != null && wordTimer.isRunning()) {
			wordTimer.stop();
		}
	}
	
	private void updateTimer() {
		if (wordTimer != null && wordTimer.isRunning()) {
			stopWordTimer();
			startWordTimer();
		}
	}
	
	private void moveToNextWord() {
		currentIndex++;
		if (currentIndex >= getCurrentWordList().size()) {
			currentIndex = 0; // Loop back to beginning
		}
		showCurrentWord();
	}
	
	private void showCurrentWord() {
		java.util.List<Word> wordList = getCurrentWordList();
		if (!wordList.isEmpty() && currentIndex < wordList.size()) {
			Word word = wordList.get(currentIndex);
			wordLabel.setText(word.getWord());
			meaningLabel.setText(word.getMeaning());
		}
	}
	
	private java.util.List<Word> getCurrentWordList() {
		return isReviewMode ? difficultWords : allWords;
	}
	
	private void setupNextTest() {
		if (allWords.size() < 4) return;
		
		resultLabel.setText("");
		
		// Choose a random word for testing
		currentIndex = random.nextInt(allWords.size());
		Word testWord = allWords.get(currentIndex);
		testWordLabel.setText(testWord.getWord());
		
		// Create 4 options (1 correct, 3 random)
		Set<Integer> usedIndices = new HashSet<>();
		usedIndices.add(currentIndex);
		java.util.List<String> options = new ArrayList<>();
		options.add(testWord.getMeaning()); // Add correct meaning
		
		// Add 3 random different meanings
		while (options.size() < 4) {
			int idx = random.nextInt(allWords.size());
			if (!usedIndices.contains(idx)) {
				usedIndices.add(idx);
				options.add(allWords.get(idx).getMeaning());
			}
		}
		
		// Shuffle options
		Collections.shuffle(options);
		
		// Create option buttons
		optionsPanel.removeAll();
		for (String option : options) {
			JButton optionButton = new JButton(option);
			optionButton.addActionListener(e -> {
				// Check answer
				if (option.equals(testWord.getMeaning())) {
					resultLabel.setText("✓ 正确！");
					resultLabel.setForeground(new Color(0, 150, 0));
				} else {
					resultLabel.setText("✗ 错误! 正确答案: " + testWord.getMeaning());
					resultLabel.setForeground(new Color(200, 0, 0));
					
					// Add to difficult words if wrong
					if (!difficultWords.contains(testWord)) {
						difficultWords.add(testWord);
						saveDifficultWords();
					}
				}
				
				// Disable all option buttons after answer
				for (Component c : optionsPanel.getComponents()) {
					if (c instanceof JButton) {
						c.setEnabled(false);
					}
				}
			});
			optionsPanel.add(optionButton);
		}
		
		optionsPanel.revalidate();
		optionsPanel.repaint();
	}
	
	private void loadWords(String filename) throws IOException {
		allWords.clear();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filename), "GB2312"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty()) continue;
				
				int idx = line.indexOf("\t");
				if (idx > 0) {
					String word = line.substring(0, idx).trim();
					String meaning = line.substring(idx + 1).trim();
					
					// Remove phonetic notation if present (anything between [ and ])
					word = word.replaceAll("\\s*\\[.*?\\]\\s*", " ").trim();
					
					allWords.add(new Word(word, meaning));
				}
			}
		}
		
		if (!allWords.isEmpty()) {
			showCurrentWord();
		}
	}
	
	private void loadDifficultWords(String filename) throws IOException {
		difficultWords.clear();
		Path path = Paths.get(filename);
		
		if (Files.exists(path)) {
			try (BufferedReader reader = Files.newBufferedReader(path)) {
				String line;
				while ((line = reader.readLine()) != null) {
					line = line.trim();
					if (line.isEmpty()) continue;
					
					int idx = line.indexOf("\t");
					if (idx > 0) {
						String word = line.substring(0, idx).trim();
						String meaning = line.substring(idx + 1).trim();
						difficultWords.add(new Word(word, meaning));
					}
				}
			}
		}
	}
	
	private void saveDifficultWords() {
		try (PrintWriter writer = new PrintWriter(new FileWriter("difficult_words.txt"))) {
			for (Word word : difficultWords) {
				writer.println(word.getWord() + "\t" + word.getMeaning());
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "保存生词失败: " + e.getMessage(), 
										"错误", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Recite app = new Recite();
			app.setVisible(true);
			app.startWordTimer();
		});
	}
	
	// Word class to store word and meaning
	private static class Word {
		private String word;
		private String meaning;
		
		public Word(String word, String meaning) {
			this.word = word;
			this.meaning = meaning;
		}
		
		public String getWord() { return word; }
		public String getMeaning() { return meaning; }
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null || getClass() != obj.getClass()) return false;
			Word other = (Word) obj;
			return word.equals(other.word);
		}
		
		@Override
		public int hashCode() {
			return word.hashCode();
		}
	}
} 
