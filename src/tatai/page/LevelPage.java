package tatai.page;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tatai.TataiController;
import tatai.TataiFactory;
import tatai.generator.Question;
import tatai.model.TataiStatistic;
import tatai.model.TimedProgressBar;
import tatai.speech.SpeechHandler;

/**
 * Controller and representation for a level page.
 * @author dli294
 *
 */
public class LevelPage extends Page {
	
	// FXML-injected nodes.
	@FXML private Text number;
	@FXML private Button recordButton;
	@FXML private Button returnButton;
	@FXML private Button redoButton;
	@FXML private Button playButton;
	@FXML private Button nextButton;
	@FXML private Text announceRight;
	@FXML private Text announceWrong;
	@FXML private ProgressBar progressBar;
	@FXML private HBox imageRight;
	@FXML private HBox imageWrong;
	@FXML private VBox recordingArea;
	@FXML private Text questionNumber;
	@FXML private BorderPane root;
	
	// Total number of questions.
	final static private int NUM_QUESTIONS = 10;
	// Current question number up to.
	private int _currentQuestionNumber = 0;
	// Number of correct answers this level.
	private int _numCorrect = 0;
	// Number of previous attempts.
	private int _tries = 0;
	// Correct streak.
	private int _streak;
	private int _longestCurrentStreak;
	// Number that user has to pronounce in Maori
	private int _numToSay = 0;
	// Progress bar to display time for recording
	public TimedProgressBar _progress;
	// List of questions for this level.
	private List<Question> _questionList;
	// List of list of questions saved.
	private List<List<Question>> _questionsSaved;
	
	/**
	 * Constructor.
	 * @param stage The stage to set the scene on.
	 * @param scene The scene to show.
	 * @param sceneName The name of the scene.
	 * @param controller The state of the application to maintain a reference to.
	 * @author dli294
	 */
	public LevelPage(Stage stage, Scene scene, String sceneName, TataiController controller) {
		super(stage, scene, sceneName, controller);
		_questionsSaved = new ArrayList<List<Question>>();
	}
	
    /**
    ** Executed after initialization of FXML-injected nodes.
    ** @author dli294
    **/
	@FXML private void initialize() {
		// Bind "managed" to "visible", so that hiding a node also removes it from the flow of the scene.
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

	/**
	** Function to minimize all the buttons. Show as necessary.
	** @author dli294
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
		// Hide progress bar.
		if (_progress != null) {
     		 _progress.stop();
     	 }
		
	}
	
    /**
    ** Initializes each level at the start of a round.
    ** @author dli294
    **/
    public void initLevel() {
    	_questionList = new ArrayList<Question>();
    	_numCorrect = 0;
    	_currentQuestionNumber = 1;
    	_tries = 0;
    	_streak = 0;
    	_longestCurrentStreak = 0;
    	showLevel();
    }
	
	/**
    ** Show a question for this level.
    ** @author jtha772, dli294
    **/
    private void showLevel() {

    	// Hide all buttons.
    	minimizeButtons();
    	// Reset colour of background.
    	root.setStyle("-fx-background-color:#37474F;");
    	// Show record button
    	recordButton.setVisible(true);
    	
    	// Question setup here.
    	
    	// Numbers to test for current question.
    	Question question = new Question(_controller._level);
    	
    	if (_controller._mode == "assess") {
    		// Show progress bar.
        	progressBar.setProgress((float) _currentQuestionNumber/NUM_QUESTIONS);
        	// Show question number.
        	questionNumber.setText(Integer.toString(_currentQuestionNumber) + "/" + Integer.toString(NUM_QUESTIONS));
        	question = getTestQuestion();
    	} else {
    		progressBar.setProgress(0);
    		// Show question number.
        	questionNumber.setText(Integer.toString(_currentQuestionNumber) + "/?");
    	}
    	
    	// Add to saved list.
    	_questionList.add(question);
    	
    	// Number to say.
    	_numToSay = question.getAnswer();
    	// Edit display text to display question.
    	number.setText(question.getQuestion()); 
    	
    	this.show();
    }
    
    /**
     * Generates a test question.
     * @return A test question, based on the currently available operators.
     * @author dli294
     */
    private Question getTestQuestion() {
    	// Numbers to test for current question.
    	Question question = new Question(_controller._level);
    	// Get all the question types that are currently enabled.
    	Set<String> questions = TataiFactory.getKeysByValue(_controller._questionTypes, true);
    	// Generate assessment questions.
    	if (_controller._mode == "assess") {
	    	try {
	    		// Select a random question.
	    		String questionType = TataiFactory.getRandomString(questions);
	    		// Create a class.
	        	Class<?> questionClass = Class.forName("tatai.generator." + questionType);
	        	Constructor<?> constructorQuestion = questionClass.getConstructor(new Class<?>[]{Integer.TYPE});
	        	question = (Question) constructorQuestion.newInstance(_controller._level); 
	    	} catch (Exception e) {
	    		if(questions.isEmpty()) {
	    			// No operators enabled. Continue forward.
	    		}
	    	}
    	}
    	return question;
    }
    
    /**
     * Shows the menu.
     * @param ActionEvent event The event that caused this function to be called.
     * @author dli294
     */
    @FXML protected void showMenu(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Return to menu?");
		alert.setHeaderText("Return to menu?");
		alert.setContentText("Are you sure you wish to return to the main menu? You may lose unsaved progress.");

		Optional<ButtonType> result = alert.showAndWait();
		// Confirmed
		if (result.get() == ButtonType.OK){
			super.showMenu(null);
		} else {
		    // Cancelled
		}
		
		boolean anyAchievement = false;
		String achieved = "";
		// Check for achievements - practice length.
    	if (_controller._mode == "practice" && _currentQuestionNumber > _controller._longestPractice) {
    		_controller._longestPractice = _currentQuestionNumber;
    		achieved = "Longest practice session!";
    		anyAchievement = true;
    	}
    	if(_controller._mode == "practice" && _longestCurrentStreak > _controller._longestStreakPractice) {
    		_controller._longestStreakPractice = _longestCurrentStreak;
    		achieved = "Longest practice streak!";
    		anyAchievement = true;
    	}
    	if (anyAchievement) {
    		_controller.updateAchievements();
    		_controller.showAchievement(achieved);
    		return;
    	}
    }
    
    /**
    ** Shows the next question, or the end level screen, depending on which question the user is on.
    ** @arg ActionEvent event The event that caused this method to be called.
    ** @author dli294
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
     * Checks for achievements then shows the end-of-level screen.
     * @param numCorrect
     * @author dli294
     */
    private void showEndLevel(int numCorrect) {
    	// Add statistic.
    	TataiStatistic stat = new TataiStatistic(numCorrect, _controller._level);
    	((StatisticsPage)_controller._loader.getPage("statistics")).addStatistic(stat);
    	
    	((EndlevelPage)_controller._loader.getPage("endlevel")).show(numCorrect);
    	boolean anyAchievement = false;
    	String achieved = "";
    	// Check for achievements - personal best.
    	if (_controller._level == 1 && _numCorrect > _controller._personalBest2) {
    		_controller._personalBest1 = _numCorrect;
    		achieved = "Most correct for level 1!";
    		anyAchievement = true;
    	}
    	if (_controller._level == 2 && _numCorrect > _controller._personalBest2) {
    		_controller._personalBest1 = _numCorrect;
    		achieved = "Most correct for level 2!";
    		anyAchievement = true;
    	}
    	// Check for achievements - streak.
    	if(_controller._mode == "assess" && _longestCurrentStreak > _controller._longestStreakAssess) {
    		_controller._longestStreakAssess = _longestCurrentStreak;
    		achieved = "Longest test streak!";
    		anyAchievement = true;
    	}
    	if (anyAchievement) {
    		_controller.updateAchievements();
    		_controller.showAchievement(achieved);
    	}
    }
    
    /**
    ** Plays audio.
    ** @arg ActionEvent event The event that caused this method to be called.
    ** @author jtha772
    **/
    @FXML protected void play(ActionEvent event) {
    	// Play audio here.
    	
    	// Ensure GUI concurrency by doing in background.
		Task<Void> task = new Task<Void>() {
			@Override public Void call(){
				SpeechHandler handler = new SpeechHandler();
				handler.play();
				return null;
		    }
		};
		new Thread(task).start();

    }
    
    /**
    ** Allows a user to redo their recording.
    ** @arg ActionEvent event The event that caused this method to be called.
    ** @author jtha772
    **/
    @FXML protected void redo(ActionEvent event) {
    	_tries++;
    	record(null);
    }
    
    /**
    ** Records the user and processes the recording.
    ** @arg ActionEvent event The event that caused this method to be called.
    ** @author dli294, jtha772
    **/
    @FXML protected void record(ActionEvent event) {
    	// Hide recording button, show recording dialog.
    	minimizeButtons();
    	
    	_progress = new TimedProgressBar(0);
    	_progress.setId("recordingBar");
    	recordingArea.getChildren().addAll(_progress);
    	_progress.start();
    	
    	SpeechHandler handler = new SpeechHandler();
    	
    	// Ensure GUI concurrency by doing in background
		Task<Void> task = new Task<Void>() {
			@Override public Void call(){				
				handler.recordAndProcess();
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
	        	boolean isCorrect = handler.isRecordingCorrect(_numToSay);
	        	
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
	        		
	        		imageWrong.setVisible(true);
	        		root.setStyle("-fx-background-color:#D32F2F;");
	        		// If this is your first time wrong, you get to try again.
	        		if (_tries == 0) {
	        			redoButton.setVisible(true);
	        			announceWrong.setText("Incorrect, try again, answer will be displayed if incorrect");
	        		}
	        		else {
	        			announceWrong.setText("Incorrect, answer was: '" + SpeechHandler.ConvertIntToMaori(_numToSay) + "'");
	        		}
	        		
	        		announceWrong.setVisible(true);
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
     * Saves the current list of questions.
     */
    public void saveCurrentLevel() {
    	_questionsSaved.add(_questionList);
    }
    
    /**
     * Gets the current list of saved questions.
     */
    public List<List<Question>> getSavedQuestions() {
    	return _questionsSaved;
    }

}
