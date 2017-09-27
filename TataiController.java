package tatai;
 
import java.util.HashMap;
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
	// Hashmap of scenes.
	HashMap<String,Scene> _scenes = new HashMap<String,Scene>();
	// Stage to swap scenes in and out of.
	Stage _stage = null;
	// Scenes
	TataiLoader _loader;
	
	/**
	// Uncomment as necessary to access buttons, etc.
	@FXML private Text number;
	@FXML private Button level1Button;
	@FXML private Button level2Button;
	**/
	@FXML private Button recordButton;
	@FXML private Button returnButton;
	@FXML private Button redoButton;
	@FXML private Button playButton;
	@FXML private Button nextButton;
	@FXML private Text announceRight;
	@FXML private Text announceWrong;
	@FXML private Text numberCorrect;
	
	public TataiController(Stage stage) {
		_stage = stage;
    	_loader = new TataiLoader(this);
	}
	
	public void init() {
		Scene scene = _loader.getScene("menu");
	    
        _stage.setTitle("Welcome to Tatai!");
        _stage.setScene(scene);
        _stage.show();
	}
	    
    @FXML protected void showLevel1(ActionEvent event) {
        showLevel(1);
    }
    
    @FXML protected void showLevel2(ActionEvent event) {
        showLevel(2);
    }
    
    @FXML protected void showMenu(ActionEvent event) {
    	Scene scene = _loader.getScene("menu");
    	_stage.setScene(scene);
        _stage.show();
    }
    
    @FXML protected void record(ActionEvent event) {
    	
    	
    }
    
    @FXML protected void play(ActionEvent event) {
    	
    	
    }
    
    @FXML protected void next(ActionEvent event) {
    	showEndLevel(1);
    }
    
    @FXML protected void nextLevel(ActionEvent event) {
    	
    }
    
    @FXML protected void replay(ActionEvent event) {
    	
    }
    
    private void showLevel(int level) {
    	Scene scene = _loader.getScene("level");
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
    	_stage.setScene(scene);
        _stage.show();
    }
    
    private void showEndLevel(int numCorrect) {
    	numberCorrect.setText(Integer.toString(numCorrect));
    	Scene scene = _loader.getScene("endlevel");
    	_stage.setScene(scene);
        _stage.show();
    }
    
    @FXML protected void quit() {
    	Platform.exit();
    }

}