package tatai;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
// import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
    /**
    ** Controller class. Controls behaviour of the application.
    **/
public class TataiController {
	// Stage to swap scenes in and out of.
	private Stage _stage = null;
	// Scenes.
	private TataiLoader _loader;
	// Current level.
	private int _level = 1;
	// Total number of questions.
	final static private int NUM_QUESTIONS = 10;
	// Current question number up to.
	private int _currentQuestionNumber = 0;
	// Number of correct answers this level.
	private int _numCorrect = 0;
	// Number of previous attempts.
	private int _tries = 0;
	// Player.
	// private MediaPlayer _mediaPlayer;
	// List of statistics.
	private List<TataiStatistic> _statistics = new ArrayList<TataiStatistic>();
	// Statistics table to update.
	private TableView<TataiStatistic> _table = new TableView<TataiStatistic>();
	// Number that user has to pronounce in Maori
	private int _numToSay = 0;
	// Boolean to determine if sounds should stop loading. If not on the same page as it was when it started loading, it will not play the sound.
	// private boolean _samePage = true;
	// Filename.
	final static private String FILENAME = "recording.wav";
	// Mode.
	private String _mode;
	// Correct streak.
	private int _streak;
	
	// Statistics.
	private int _personalBest1 = 0;
	private int _personalBest2 = 0;
	private int _longestStreakPractice = 0;
	private int _longestStreakAssess = 0;
	private int _longestPractice = 0;
	
	// FXML-injected nodes.
	@FXML private Text number;
	@FXML private Button recordButton;
	@FXML private Button returnButton;
	@FXML private Button redoButton;
	@FXML private Button playButton;
	@FXML private Button nextButton;
	@FXML private Text announceRight;
	@FXML private Text announceWrong;
	@FXML private Text announceRecording;
	@FXML private Text numberCorrect;
	@FXML private Button nextLevelButton;
	@FXML private HBox statsPanel;
	@FXML private Text questionNumber;
	@FXML private ProgressBar progressBar;
	@FXML private ImageView imageRight;
	@FXML private ImageView imageWrong;
	@FXML private BorderPane root;
	@FXML private Button chooseLevel1Button;
	@FXML private Button chooseLevel2Button;
	
	@FXML private Text personalBest1;
	@FXML private Text personalBest2;
	@FXML private Text longestStreakPractice;
	@FXML private Text longestStreakAssess;
	@FXML private Text longestPractice;
	
    /**
    ** Constructor. Sets the stage to a private field so that it is usable everywhere. Loads the scenes.
    ** @arg Stage stage The stage to display the application on.
    **/
	public TataiController(Stage stage) {
		_stage = stage;
    	_loader = new TataiLoader(this);
	}
	
    /**
    ** Executed after initialization of FXML-injected nodes.
    **/
	@FXML private void initialize() {
		// Bind "managed" to "visible", so that hiding a node also removes it from the flow of the scene.
		if (recordButton != null) {
			recordButton.managedProperty().bind(recordButton.visibleProperty());
			returnButton.managedProperty().bind(returnButton.visibleProperty());
			redoButton.managedProperty().bind(redoButton.visibleProperty());
			playButton.managedProperty().bind(playButton.visibleProperty());
			nextButton.managedProperty().bind(nextButton.visibleProperty());
			announceRight.managedProperty().bind(announceRight.visibleProperty());
			announceWrong.managedProperty().bind(announceWrong.visibleProperty());
			announceRecording.managedProperty().bind(announceRecording.visibleProperty());
			imageRight.managedProperty().bind(imageRight.visibleProperty());
			imageWrong.managedProperty().bind(imageWrong.visibleProperty());
		}
		if (nextLevelButton != null) {
			nextLevelButton.managedProperty().bind(nextLevelButton.visibleProperty());
		}
		if(personalBest1 != null) {
		    personalBest1.textProperty().bind(new SimpleIntegerProperty(_personalBest1).asString());
		    personalBest2.textProperty().bind(new SimpleIntegerProperty(_personalBest2).asString());
		    longestStreakPractice.textProperty().bind(new SimpleIntegerProperty(_longestStreakPractice).asString());
		    longestStreakAssess.textProperty().bind(new SimpleIntegerProperty(_longestStreakAssess).asString());
		    longestPractice.textProperty().bind(new SimpleIntegerProperty(_longestPractice).asString());
		}
	}

    /**
    ** Initialization script to show the main menu and set up scenes and data.
    **/
	public void init() {
		
		// Initialize the statistics page with a table.
    	ObservableList<TataiStatistic> data = FXCollections.observableArrayList(_statistics);
		_table = TataiFactory.makeTable();
		_table.setItems(data);
		
        // Add the table to the panel.
        statsPanel.getChildren().addAll(_table);
		
        // Show menu.
		Scene scene = _loader.getScene("menu");
	    
        _stage.setTitle("Welcome to Tatai!");
        _stage.setScene(scene);
        _stage.show();
	}

    /**
    ** Shows the first level.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML protected void showLevel1(ActionEvent event) {
    	_level = 1;
    	initLevel();
    }
    
    /**
    ** Shows the second level.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML protected void showLevel2(ActionEvent event) {
    	_level = 2;
    	initLevel();
    }
    
    /**
    ** Initializes each level at the start of a round.
    **/
    private void initLevel() {
    	_numCorrect = 0;
    	_currentQuestionNumber = 1;
    	_tries = 0;
    	showLevel();
    }
    
    /**
     ** Pops up an achievement unlocked modal box.
     ** TODO Complete this.
     **/
    private void showAchievement(String achieved) {
    	System.out.println(achieved);
    }
    
    /**
    ** Shows the menu.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML protected void showMenu(ActionEvent event) {
    	// Check for achievements - practice length.
    	if (_mode == "practice" && _currentQuestionNumber-1 > _longestPractice) {
    		_longestPractice = _currentQuestionNumber;
    		showAchievement("Longest practice session!");
    	}
    	
    	Scene scene = _loader.getScene("menu");
    	_stage.setScene(scene);
        _stage.show();
    }
    
    /**
    ** Records the user and processes the recording.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML protected void record(ActionEvent event) {
    	// Hide recording button, show recording dialog.
    	minimizeButtons();
		announceRecording.setVisible(true);
    	// Ensure GUI concurrency by doing in background
		Task<Void> task = new Task<Void>() {
			@Override public Void call(){				
				SpeechHandler.recordAndProcess();
				return null;
		    }
		};
		new Thread(task).start();
		
		// When recording has finished:
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	        @Override
	        public void handle(WorkerStateEvent t) {
	        	minimizeButtons();
	        	
	        	// Process recording.
	        	boolean isCorrect = SpeechHandler.isRecordingCorrect(_numToSay);
	        	
	        	// If correct, hide the redo button and increase the number correct.
	        	if (isCorrect) {
	        		_streak++;
	        		_numCorrect++;
	        		announceRight.setVisible(true);
	        		imageRight.setVisible(true);
	        		root.setStyle("-fx-background-color:#388E3C;");
	        	}
	        	else {
	        		_streak = 0;
                    // If wrong, show the redo button and allow the user to try again.
	        		announceWrong.setVisible(true);
	        		imageWrong.setVisible(true);
	        		root.setStyle("-fx-background-color:#D32F2F;");
	        		// If this is your first time wrong, you get to try again.
	        		if (_tries == 0) {
	        			redoButton.setVisible(true);
	        		}
	        	}
	        	
	        	// Check for achievements.
	        	if(_mode == "practice" && _streak > _longestStreakPractice) {
	        		_longestStreakPractice = _streak;
	        		showAchievement("Longest streak of correct questions (practice mode)!");
	        	}
	        	if(_mode == "assess" && _streak > _longestStreakAssess) {
	        		_longestStreakAssess = _streak;
	        		showAchievement("Longest streak of correct questions (test mode)!");
	        	}
	        	
	        	// Always show the play button and the next button and hide the record button.
	        	// Show next button.
	        	nextButton.setVisible(true);
	        	// Show play button.
	        	playButton.setVisible(true);
	        }
	    });
    	
    }
    
    /**
    ** Allows a user to redo their recording.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML protected void redo(ActionEvent event) {
    	
    	_tries++;
    	record(null);
    }
    
    /**
    ** Plays audio.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML protected void play(ActionEvent event) {
    	// Play audio here.
    	
    	// Ensure GUI concurrency by doing in background.
		Task<Void> task = new Task<Void>() {
			@Override public Void call(){
				
				String cmd = "aplay " + FILENAME;
				ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
				try {
					builder.start();
				} catch (Exception e) {
					
				}
				return null;
		    }
		};
		new Thread(task).start();

    }
    
    
    /**
    ** Shows the next question, or the end level screen, depending on which question the user is on.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML protected void next(ActionEvent event) {
    	_tries = 0;
    	    	
        // If the current question number is the last question, show the end level screen.
    	if(_currentQuestionNumber >= NUM_QUESTIONS) {
    		showEndLevel(_numCorrect);
    	}
    	else {
    		// Show the next question.
    		_currentQuestionNumber++;
    		showLevel();
    	}
    }
    
    /**
    ** Shows the next level. Only two levels, so show level 2.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML protected void nextLevel(ActionEvent event) {
    	showLevel2(null);
    }
    
    /**
    ** Replays the current level.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML protected void replay(ActionEvent event) {
    	initLevel();
    }
    
    /**
    ** Function to minimize all the buttons. Show as necessary.
    **/
    private void minimizeButtons() {
    	// Hide redo button.
    	redoButton.setVisible(false);
    	// Hide next button.
    	nextButton.setVisible(false);
    	// Hide play button.
    	playButton.setVisible(false);
    	recordButton.setVisible(false);
    	// Hide announcements.
    	announceRight.setVisible(false);
    	announceRecording.setVisible(false);
    	announceWrong.setVisible(false);
    	// Hide images.
    	imageRight.setVisible(false);
    	imageWrong.setVisible(false);
    	
    }
    
    /**
    ** Show a question for this level.
    **/
    private void showLevel() {
    	
    	Scene scene = _loader.getScene("level");
    	// Hide all buttons.
    	minimizeButtons();
    	// Reset colour of background.
    	root.setStyle("-fx-background-color:#37474F;");
    	// Show record button
    	recordButton.setVisible(true);
    	
    	// Question setup here.
    	
    	// Numbers to test for current question.
    	List<String> questionNums;
    	
    	if (_mode == "assess") {
    		// Show progress bar.
        	progressBar.setProgress((float) _currentQuestionNumber/NUM_QUESTIONS);
        	// Show question number.
        	questionNumber.setText(Integer.toString(_currentQuestionNumber) + "/" + Integer.toString(NUM_QUESTIONS));
        	
        	// Test Division TODO Make division have up to  99 as an answer
        	// Temporarily make operation choice random.
        	Random rand = new Random();
        	String operand = TataiFactory._operands.get(rand.nextInt(TataiFactory._operands.size()));
        	// Generate assessment questions.
        	questionNums =  TataiFactory.generateQuestionAssess(operand, _level); 
    	} else {
    		progressBar.setProgress((float) 0);
    		// Show question number.
        	questionNumber.setText(Integer.toString(_currentQuestionNumber) + "/?");
        	
        	// Generate assessment questions.
        	questionNums =  TataiFactory.generateQuestionPractice(_level); 
    	}
    	
    	// Number to say.
    	_numToSay = Integer.parseInt(questionNums.get(1));
    	// Edit display text to display question.
    	number.setText(questionNums.get(0)); 

    	_stage.setScene(scene);
        _stage.show();
    }
    
    /**
    ** Show the end level screen.
    ** @arg int numCorrect The number correct to display.
    **/
    private void showEndLevel(int numCorrect) {
    	// Check for achievements - personal best.
    	if (_level == 1 && _numCorrect > _personalBest2) {
    		_personalBest1 = _numCorrect;
    		showAchievement("Most correct questions for level 1!");
    	}
    	if (_level == 2 && _numCorrect > _personalBest2) {
    		_personalBest1 = _numCorrect;
    		showAchievement("Most correct questions for level 2!");
    	}
    	
    	// If on level 1 and number correct is greater than or equal to 8, show next level button, else hide it
    	if (_level == 1 && numCorrect >= 8) {
    		nextLevelButton.setVisible(true);
    	}
    	else {
    		nextLevelButton.setVisible(false);
    	}
    	
    	// Show number correct.
    	numberCorrect.setText(Integer.toString(numCorrect) + "/" + NUM_QUESTIONS);
    	
    	// Create a new TataiStatistic entry.
    	TataiStatistic entry = new TataiStatistic(numCorrect, _level);
    	
    	// Add it to the statistics list.
    	_statistics.add(entry);
    	
    	Scene scene = _loader.getScene("endlevel");
    	_stage.setScene(scene);
        _stage.show();
    }
    
    /**
    ** Show the statistics page.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML private void showStatistics() {
        // Update the table with the new list of data.
    	ObservableList<TataiStatistic> data = FXCollections.observableArrayList(_statistics);
    	_table.setItems(data);

        // Show the scene.
    	Scene scene = _loader.getScene("statistics");
    	_stage.setScene(scene);
        _stage.show();
    }
    
    /**
    ** Show the practice level.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML private void showPractice() {
        // Show the scene.
    	_mode = "practice";
    	initLevel();
    }
    
    /**
    ** Show the assess page.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML private void showAssess() {
        // Show the scene.
    	_mode = "assess";
    	initLevel();
    }
    
    /**
    ** Show the settings page.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML private void showSettings() {
        // Show the scene.
    	Scene scene = _loader.getScene("settings");
    	
    	if (_level == 1) {
    		setLevel1();
    	} else {
    		setLevel2();
    	}
    	
    	_stage.setScene(scene);
        _stage.show();
    }
    
    /**
    ** Set the level to level 1. Make the level 1 button depressed and the level 2 button not depressed.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML private void setLevel1() {
        // Show the scene.
    	_level = 1;
    	chooseLevel1Button.getStyleClass().clear();
    	chooseLevel1Button.getStyleClass().add("button");
    	chooseLevel1Button.getStyleClass().add("depressedbutton");
    	chooseLevel2Button.getStyleClass().clear();
    	chooseLevel2Button.getStyleClass().add("button");
    }
    
    /**
    ** Set the level to level 2. Make the level 2 button depressed and the level 2 button not depressed.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML private void setLevel2() {
        // Show the scene.
    	_level = 2;
    	chooseLevel1Button.getStyleClass().clear();
    	chooseLevel1Button.getStyleClass().add("button");
    	chooseLevel2Button.getStyleClass().clear();
    	chooseLevel2Button.getStyleClass().add("button");
    	chooseLevel2Button.getStyleClass().add("depressedbutton");
    }
    
    /**
    ** Quits the application.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML protected void quit() {
    	Platform.exit();
    }

}