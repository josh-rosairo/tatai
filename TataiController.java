package tatai;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
	private MediaPlayer _mediaPlayer;
	// Filename.
	final static private String FILENAME = "recording.mp3";
	// Filename.
	final static private String FILENAMEBASE = "recording";
	// List of statistics.
	private List<TataiStatistic> _statistics = new ArrayList<TataiStatistic>();
	// Statistics table to update.
	private TableView<TataiStatistic> _table = new TableView<TataiStatistic>();

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
	
	public TataiController(Stage stage) {
		_stage = stage;
    	_loader = new TataiLoader(this);
	}
	
	// Executes some bash command received as an argument.
	private List<String> executeCommand(String cmd) {
		// Read output from the bash.
		List<String> output = new ArrayList<>();
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		try {
			Process process = builder.start();
			InputStream stdout = process.getInputStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
			String line = null;
			while ((line = stdoutBuffered.readLine()) != null ) {
				output.add(line);
			}
		} catch (Exception e) {
			
		}
		return output;
	}
	
	public void init() {
		// Initialize the statistics page with a table.
		
    	ObservableList<TataiStatistic> data = FXCollections.observableArrayList(_statistics);
    	TableColumn timeCol = new TableColumn("Time completed");
    	timeCol.setMinWidth(200);
    	timeCol.setCellValueFactory(new PropertyValueFactory<TataiStatistic,String>("time"));
    	TableColumn scoreCol = new TableColumn("Score");
    	scoreCol.setMinWidth(100);
		scoreCol.setCellValueFactory(new PropertyValueFactory<TataiStatistic,String>("score"));
		TableColumn levelCol = new TableColumn("Level");
		levelCol.setMinWidth(200);
		levelCol.setCellValueFactory(new PropertyValueFactory<TataiStatistic,String>("level"));
		
		_table.setItems(data);
        _table.getColumns().addAll(timeCol, scoreCol, levelCol);
        // Add placeholder text while empty.
        _table.setPlaceholder(new Label("No scores to display."));
        
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
    	stopSound();
    	Scene scene = _loader.getScene("menu");
    	_stage.setScene(scene);
        _stage.show();
    }
    
    @FXML protected void record(ActionEvent event) {
    	// Hide recording button, show recording dialog.
    	recordButton.setVisible(false);
		recordButton.setManaged(false);
		announceRecording.setVisible(true);
		announceRecording.setManaged(true);
    	// Ensure GUI concurrency by doing in background
		Task<Void> task = new Task<Void>() {
			@Override public Void call(){
				executeCommand("ffmpeg -f alsa -i default -loglevel quiet -t 3 "+FILENAMEBASE+".wav");
				executeCommand("ffmpeg -loglevel quiet -i "+FILENAMEBASE+".wav -f mp3 "+FILENAMEBASE+".mp3");
				return null;
		    }
		};
		new Thread(task).start();
		
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	        @Override
	        public void handle(WorkerStateEvent t) {
	        	announceRecording.setVisible(false);
	    		announceRecording.setManaged(false);
	        	
	        	// TODO Process here.
	        	
	        	boolean isCorrect = false;
	        	
	        	// If correct, hide the redo button and increase the number correct.
	        	if (isCorrect) {
	        		_numCorrect++;
	        		announceRight.setVisible(true);
	        		announceRight.setManaged(true);
	        		announceWrong.setVisible(false);
	        		announceWrong.setManaged(false);
	        		redoButton.setVisible(false);
	        		redoButton.setManaged(false);
	        	}
	        	else {
	        		announceWrong.setVisible(true);
	        		announceWrong.setManaged(true);
	        		announceRight.setVisible(false);
	        		announceRight.setManaged(false);
	        		// If you this is your first time, you get to try again.
	        		if (_tries == 0) {
	        			redoButton.setVisible(true);
	            		redoButton.setManaged(true);
	        		} else {
	        			redoButton.setVisible(false);
	            		redoButton.setManaged(false);
	        		}
	        	}
	        	
	        	// Always show the play button and the next button and hide the record button.
	        	// Show next button.
	        	nextButton.setVisible(true);
	        	nextButton.setManaged(true);
	        	// Show play button.
	        	playButton.setVisible(true);
	        	playButton.setManaged(true);
	        	// Hide record button.
	        	recordButton.setVisible(false);
	        	recordButton.setManaged(false);
	        }
	    });
    	
    }
    
    @FXML protected void redo(ActionEvent event) {
    	stopSound();
    	_tries++;
    	record(null);
    }
    
    @FXML protected void play(ActionEvent event) {
    	// Play audio here.
    	String filename = FILENAME;
    	
    	// Ensure GUI concurrency by doing in background
		Task<Void> task = new Task<Void>() {
			@Override public Void call(){
				// Create new MediaPlayer
				Media sound = new Media(new File(filename).toURI().toString());
				_mediaPlayer = new MediaPlayer(sound);
				return null;
		    }
		};
		new Thread(task).start();
		
		// When audio has finished loading.
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	        @Override
	        public void handle(WorkerStateEvent t) {
	        	_mediaPlayer.play();
	        }
	    });
    }
    
    // Stop any currently playing sounds.
    private void stopSound() {
    	if (_mediaPlayer != null) {
    		_mediaPlayer.stop();
    	}
    }
    
    @FXML protected void next(ActionEvent event) {
    	_tries = 0;
    	stopSound();
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
    
    // Show a question for this level.
    private void showLevel() {
    	stopSound();
    	Scene scene = _loader.getScene("level");
    	// Show record button
    	recordButton.setVisible(true);
    	recordButton.setManaged(true);
    	// Hide redo button.
    	redoButton.setVisible(false);
    	redoButton.setManaged(false);
    	// Hide next button.
    	nextButton.setVisible(false);
    	//nextButton.setManaged(false);
    	// Hide play button.
    	playButton.setVisible(false);
    	playButton.setManaged(false);
    	// Hide announcements.
    	announceRight.setVisible(false);
    	announceRight.setManaged(false);
    	announceRecording.setVisible(false);
		announceRecording.setManaged(false);
    	announceWrong.setVisible(false);
    	announceWrong.setManaged(false);
    	
    	// Question setup here.
    	
    	_stage.setScene(scene);
        _stage.show();
    }
    
    private void showEndLevel(int numCorrect) {
    	// If on level 1 and number correct is greater than or equal to 8, show next level button, else hide it
    	if (_level == 1 && numCorrect >= 8) {
    		nextLevelButton.setVisible(true);
    		nextLevelButton.setManaged(true);
    	}
    	else {
    		nextLevelButton.setVisible(false);
    		nextLevelButton.setManaged(false);
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