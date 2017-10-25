package tatai;
 
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tatai.generator.*;
 
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
	// List of statistics.
	private List<TataiStatistic> _statistics = new ArrayList<TataiStatistic>();
	// Statistics table to update.
	private TableView<TataiStatistic> _table = new TableView<TataiStatistic>();
	// Number that user has to pronounce in Maori
	private int _numToSay = 0;
	// Filename.
	final static private String FILENAME = "recording.wav";
	// Mode.
	private String _mode;
	// Correct streak.
	private int _streak;
	private int _longestCurrentStreak;
	// Scene to return to.
	private Scene _returnScene;
	// Questions enabled.
	private HashMap<String, Boolean> _questionTypes = new HashMap<String, Boolean>() {{
	    put("AdditionQuestion",true);
	    put("SubtractionQuestion",true);
	    put("DivisionQuestion",false);
	    put("MultiplicationQuestion",true);
	}};;
	
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
	@FXML private Text numberCorrect;
	@FXML private Button nextLevelButton;
	@FXML private HBox statsPanel;
	@FXML private Text questionNumber;
	@FXML private ProgressBar progressBar;
	@FXML private HBox imageRight;
	@FXML private HBox imageWrong;
	@FXML private BorderPane root;
	@FXML private Button chooseLevel1Button;
	@FXML private Button chooseLevel2Button;
	@FXML private VBox recordingArea;
	
	@FXML private Text personalBest1;
	@FXML private Text personalBest2;
	@FXML private Text longestStreakPractice;
	@FXML private Text longestStreakAssess;
	@FXML private Text longestPractice;
	
	@FXML private Button chooseAdditionButton;
	@FXML private Button chooseSubtractionButton;
	@FXML private Button chooseDivisionButton;
	@FXML private Button chooseMultiplicationButton;
	
	@FXML private Text achievementText;
	
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
			imageRight.managedProperty().bind(imageRight.visibleProperty());
			imageWrong.managedProperty().bind(imageWrong.visibleProperty());
		}
		if (nextLevelButton != null) {
			nextLevelButton.managedProperty().bind(nextLevelButton.visibleProperty());
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
    	_streak = 0;
    	_longestCurrentStreak = 0;
    	showLevel();
    }
    
    /**
     ** Pops up an achievement unlocked modal box.
     **/
    private void showAchievement(String achieved) {
    	_returnScene = _stage.getScene();
    	achievementText.setText(achieved);
    	
    	Scene scene = _loader.getScene("achievement");
    	_stage.setScene(scene);
        _stage.show();
    }
    
    /**
     ** Returns to the previously stored scene.
     ** @arg ActionEvent event The event that caused this method to be called.
     **/
     @FXML protected void returnToScene(ActionEvent event) {
    	 _stage.setScene(_returnScene);
         _stage.show();
     }
    
    /**
    ** Shows the menu.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML protected void showMenu(ActionEvent event) {
    	if(_stage.getScene().equals(_loader.getScene("level"))) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Return to menu?");
    		alert.setHeaderText("Return to menu?");
    		alert.setContentText("Are you sure you wish to return to the main menu? You may lose unsaved progress.");

    		Optional<ButtonType> result = alert.showAndWait();
    		// Confirmed
    		if (result.get() == ButtonType.OK){
    			showMenu();
    		} else {
    		    // Cancelled
    		}
    	} else {
    		showMenu();
    	}
    }
    
    /**
     ** Shows the menu.
     **/
    private void showMenu() {
    	Scene scene = _loader.getScene("menu");
    	_stage.setScene(scene);
        _stage.show();
        
        // Check for achievements - practice length.
    	if (_mode == "practice" && _currentQuestionNumber > _longestPractice) {
    		_longestPractice = _currentQuestionNumber;
    		longestPractice.setText(Integer.toString(_longestPractice));
    		showAchievement("Longest practice session!");
    		return;
    	}
    }
    
    /**
    ** Records the user and processes the recording.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML protected void record(ActionEvent event) {
    	// Hide recording button, show recording dialog.
    	minimizeButtons();
    	
    	TimedProgressBar progress = new TimedProgressBar(0);
    	recordingArea.getChildren().addAll(progress);
    	progress.start();
    	
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
	        		if(_streak > _longestCurrentStreak) {
	        			_longestCurrentStreak = _streak;
	        		}
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
    	Question question = new Question(_level);
    	
    	if (_mode == "assess") {
    		// Show progress bar.
        	progressBar.setProgress((float) _currentQuestionNumber/NUM_QUESTIONS);
        	// Show question number.
        	questionNumber.setText(Integer.toString(_currentQuestionNumber) + "/" + Integer.toString(NUM_QUESTIONS));
        	
        	// Generate assessment questions.
        	try {
        		// Get all the question types that are currently enabled.
        		Set<String> questions = TataiFactory.getKeysByValue(_questionTypes, true);
        		// Select a random question.
        		String questionType = TataiFactory.getRandomString(questions);
        		// Create a class.
	        	Class<?> questionClass = Class.forName("tatai.generator." + questionType);
	        	Constructor<?> constructorQuestion = questionClass.getConstructor(new Class<?>[]{Integer.TYPE});
	        	question = (Question) constructorQuestion.newInstance(_level); 
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
    	} else {
    		progressBar.setProgress(0);
    		// Show question number.
        	questionNumber.setText(Integer.toString(_currentQuestionNumber) + "/?");
    	}
    	
    	// Number to say.
    	_numToSay = question.getAnswer();
    	// Edit display text to display question.
    	number.setText(question.getQuestion()); 

    	_stage.setScene(scene);
        _stage.show();
    }
    
    /**
    ** Show the end level screen.
    ** @arg int numCorrect The number correct to display.
    **/
    private void showEndLevel(int numCorrect) {
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
        
        // Check for achievements - personal best.
    	if (_level == 1 && _numCorrect > _personalBest2) {
    		_personalBest1 = _numCorrect;
    		showAchievement("Most correct for level 1!");
    		personalBest1.setText(Integer.toString(_personalBest1));
    		return;
    	}
    	if (_level == 2 && _numCorrect > _personalBest2) {
    		_personalBest1 = _numCorrect;
    		showAchievement("Most correct for level 2!");
    		personalBest2.setText(Integer.toString(_personalBest2));
    		return;
    	}
    	// Check for achievements - streak.
    	if(_mode == "practice" && _longestCurrentStreak > _longestStreakPractice) {
    		_longestStreakPractice = _longestCurrentStreak;
    		showAchievement("Longest practice streak!");
    		longestStreakPractice.setText(Integer.toString(_longestStreakPractice));
    		return;
    	}
    	if(_mode == "assess" && _longestCurrentStreak > _longestStreakAssess) {
    		_longestStreakAssess = _longestCurrentStreak;
    		showAchievement("Longest test streak!");
    		longestStreakAssess.setText(Integer.toString(_longestStreakAssess));
    		return;
    	}
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
    	
    	if (_questionTypes.get("AdditionQuestion")) {
    		setAddition(null);
    	}
    	if (_questionTypes.get("SubtractionQuestion")) {
    		setSubtraction(null);
    	}
    	if (_questionTypes.get("DivisionQuestion")) {
    		setDivision(null);
    	}
    	if (_questionTypes.get("MultiplicationQuestion")) {
    		setMultiplication(null);
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
     ** Add addition questions to the questions generated.
     ** @arg ActionEvent event The event that caused this method to be called.
     **/
     @FXML private void setAddition(ActionEvent event) {
    	 chooseAdditionButton.getStyleClass().clear();
     	 chooseAdditionButton.getStyleClass().add("button");
     	
    	 if (_questionTypes.get("AdditionQuestion") == false) {
    	 	 _questionTypes.put("AdditionQuestion", true);
    	 	chooseAdditionButton.getStyleClass().add("depressedbutton");
    	 } else {
    		 _questionTypes.put("AdditionQuestion", false);
    	 }
     }
     
     /**
      ** Add subtraction questions to the questions generated.
      ** @arg ActionEvent event The event that caused this method to be called.
      **/
      @FXML private void setSubtraction(ActionEvent event) {
     	 chooseSubtractionButton.getStyleClass().clear();
      	 chooseSubtractionButton.getStyleClass().add("button");
      	
     	 if (_questionTypes.get("SubtractionQuestion") == false) {
     	 	 _questionTypes.put("SubtractionQuestion", true);
     	 	chooseSubtractionButton.getStyleClass().add("depressedbutton");
     	 } else {
     		 _questionTypes.put("SubtractionQuestion", false);
     	 }
      }
      
      /**
       ** Add division questions to the questions generated.
       ** @arg ActionEvent event The event that caused this method to be called.
       **/
       @FXML private void setDivision(ActionEvent event) {
      	 chooseDivisionButton.getStyleClass().clear();
       	 chooseDivisionButton.getStyleClass().add("button");
       	
      	 if (_questionTypes.get("DivisionQuestion") == false) {
      	 	 _questionTypes.put("DivisionQuestion", true);
      	 	chooseDivisionButton.getStyleClass().add("depressedbutton");
      	 } else {
      		 _questionTypes.put("DivisionQuestion", false);
      	 }
       }
       
       /**
        ** Add multiplication questions to the questions generated.
        ** @arg ActionEvent event The event that caused this method to be called.
        **/
        @FXML private void setMultiplication(ActionEvent event) {
       	 	chooseMultiplicationButton.getStyleClass().clear();
        	 chooseMultiplicationButton.getStyleClass().add("button");
        	
       	 if (_questionTypes.get("MultiplicationQuestion") == false) {
       	 	 _questionTypes.put("MultiplicationQuestion", true);
       	 	chooseMultiplicationButton.getStyleClass().add("depressedbutton");
       	 } else {
       		 _questionTypes.put("MultiplicationQuestion", false);
       	 }
        }
    
    /**
    ** Quits the application.
    ** @arg ActionEvent event The event that caused this method to be called.
    **/
    @FXML protected void quit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Quit Tātai?");
		alert.setHeaderText("Quit Tātai?");
		alert.setContentText("Are you sure you wish to quit Tātai? You may lose unsaved progress.");

		Optional<ButtonType> result = alert.showAndWait();
		// Confirmed
		if (result.get() == ButtonType.OK){
			Platform.exit();
		} else {
		    // Cancelled
		}
    }

}