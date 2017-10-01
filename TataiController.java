package tatai;
 
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
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
 
public class TataiController {
	// Stage to swap scenes in and out of.
	private Stage _stage = null;
	// Scenes.
	private TataiLoader _loader;
	// Current level.
	private int _level = 0;
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
	
	public TataiController(Stage stage) {
		_stage = stage;
    	_loader = new TataiLoader(this);
	}
	
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
	}
	
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
	    
    @FXML protected void showLevel1(ActionEvent event) {
    	_level = 1;
    	initLevel();
    }
    
    @FXML protected void showLevel2(ActionEvent event) {
    	_level = 2;
    	initLevel();
    }
    
    private void initLevel() {
    	_numCorrect = 0;
    	_currentQuestionNumber = 1;
    	_tries = 0;
    	showLevel();
    }
    
    @FXML protected void showMenu(ActionEvent event) {
    	
    	Scene scene = _loader.getScene("menu");
    	_stage.setScene(scene);
        _stage.show();
    }
    
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
	        		_numCorrect++;
	        		announceRight.setVisible(true);
	        		imageRight.setVisible(true);
	        		root.setStyle("-fx-background-color:#388E3C;");
	        	}
	        	else {
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
    
    @FXML protected void redo(ActionEvent event) {
    	
    	_tries++;
    	record(null);
    }
    
    @FXML protected void play(ActionEvent event) {
    	// Play audio here.
    	
    	// Ensure GUI concurrency by doing in background
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
    
    
    @FXML protected void next(ActionEvent event) {
    	_tries = 0;
    	
    	if(_currentQuestionNumber >= NUM_QUESTIONS) {
    		showEndLevel(_numCorrect);
    	}
    	else {
    		// Show the next question.
    		_currentQuestionNumber++;
    		showLevel();
    	}
    }
    
    // Shows the next level. Only two levels, so show level 2.
    @FXML protected void nextLevel(ActionEvent event) {
    	showLevel2(null);
    }
    
    // Replay current level
    @FXML protected void replay(ActionEvent event) {
    	initLevel();
    }
    
    // Function to minimize all the buttons. Show as necessary.
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
    
    // Show a question for this level.
    private void showLevel() {
    	
    	Scene scene = _loader.getScene("level");
    	// Hide all buttons.
    	minimizeButtons();
    	// Reset colour of background.
    	root.setStyle("-fx-background-color:#37474F;");
    	// Show record button
    	recordButton.setVisible(true);
    	
    	// Question setup here.
    	
    	_numToSay = TataiFactory.generateNum(_level); // generate number to test for current question
    	number.setText(Integer.toString(_numToSay)); // edit display text for number to say
    	
    	// Show progress bar.
    	progressBar.setProgress((float) _currentQuestionNumber/NUM_QUESTIONS);
    	
    	// Show question number.
    	questionNumber.setText(Integer.toString(_currentQuestionNumber) + "/" + Integer.toString(NUM_QUESTIONS));
    	
    	_stage.setScene(scene);
        _stage.show();
    }
    
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
    }
    
    @FXML private void showStatistics() {
    	ObservableList<TataiStatistic> data = FXCollections.observableArrayList(_statistics);
    	_table.setItems(data);
    		
    	Scene scene = _loader.getScene("statistics");
    	_stage.setScene(scene);
        _stage.show();
    }
    
    @FXML protected void quit() {
    	Platform.exit();
    }

}