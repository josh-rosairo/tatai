package tatai;
 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
@SuppressWarnings("unused")
public class TataiController {
	// Stage to swap scenes in and out of.
	private Stage _stage = null;
	// Scenes
	private TataiLoader _loader;
	// Current level
	private int _level = 0;
	// Total number of questions
	final static private int NUM_QUESTIONS = 10;
	// Current question number up to
	private int _currentQuestionNumber = 0;
	// Number of correct answers this level
	private int _numCorrect = 0;
	// Number of previous attempts
	private int _tries = 0;
	// Number that user has to pronounce in Maori
	private int _numToSay = 0;

	@FXML private Button recordButton;
	@FXML private Button returnButton;
	@FXML private Button redoButton;
	@FXML private Button playButton;
	@FXML private Button nextButton;
	@FXML private Text announceRight;
	@FXML private Text announceWrong;
	@FXML private Text numberCorrect;
	@FXML private Text number;
	@FXML private Button nextLevelButton;
	
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
			InputStream stderr = process.getErrorStream();
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
    	// Record and process here.
    	
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
    
    @FXML protected void redo(ActionEvent event) {
    	_tries++;
    	record(null);
    }
    
    @FXML protected void play(ActionEvent event) {
    	// Play audio here.
    	
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
    
    // Show a question for this level.
    private void showLevel() {
    	Scene scene = _loader.getScene("level");
    	// Show record button
    	recordButton.setVisible(true);
    	recordButton.setManaged(true);
    	// Hide redo button.
    	redoButton.setVisible(false);
    	redoButton.setManaged(false);
    	// Hide next button.
    	nextButton.setVisible(false);
    	nextButton.setManaged(false);
    	// Hide play button.
    	playButton.setVisible(false);
    	playButton.setManaged(false);
    	// Hide announcements, but make sure the gap remains.
    	announceRight.setVisible(false);
    	announceRight.setManaged(false);
    	announceWrong.setVisible(false);
    	
    	// Question setup here.
    	
    	_numToSay = generateNum(); // generate number to test for current question
    	number.setText(Integer.toString(_numToSay)); // edit display text for number to say
    	
    	
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
    	
    	// Show number correct
    	numberCorrect.setText(Integer.toString(numCorrect) + "/" + NUM_QUESTIONS);
    	
    	Scene scene = _loader.getScene("endlevel");
    	_stage.setScene(scene);
        _stage.show();
    }
    
    @FXML protected void quit() {
    	Platform.exit();
    }
    
    private int generateNum() {
    	// define Random object and boundaries for random number generation
    	Random rand = new Random();
    	int upperLimit = 1;
    	int lowerLimit = 1;
    	
    	// if on level 1, set upper boundary to 9
    	if (_level == 1) {
    		upperLimit = 9;
    	} // if level 2, set upper boundary to 99
    	else if (_level == 2) {
    		upperLimit = 99;
    	}
    	
    	// return randomly generated integer within boundaries (inclusive)
    	return rand.nextInt(upperLimit) + lowerLimit;
    }

}