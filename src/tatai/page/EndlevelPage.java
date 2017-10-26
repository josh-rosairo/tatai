package tatai.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tatai.TataiController;

public class EndlevelPage extends Page {
	
	// Number of questions.
	private static final int NUM_QUESTIONS = 10;

	// FXML-injected nodes.
	@FXML private Button nextLevelButton;
	@FXML private Text numberCorrect;
	@FXML private Button saveButton;
	
	/**
	 * Constructor.
	 * @param stage The stage to set the scene on.
	 * @param scene The scene to show.
	 * @param sceneName The name of the scene.
	 * @param controller The state of the application to maintain a reference to.
	 * @author dli294
	 */
	public EndlevelPage(Stage stage, Scene scene, String sceneName, TataiController controller) {
		super(stage, scene, sceneName, controller);
	}
	
    /**
    ** Executed after initialization of FXML-injected nodes.
    ** @author dli294
    **/
	@FXML private void initialize() {
		nextLevelButton.managedProperty().bind(nextLevelButton.visibleProperty());
	}
	
    /**
    ** Shows the next level. Only two levels, so show level 2.
    ** @arg ActionEvent event The event that caused this method to be called.
    ** @author dli294
    **/
    @FXML protected void nextLevel(ActionEvent event) {
    	_controller._level = 2;
    	((LevelPage)_controller._loader.getPage("level")).initLevel();
    }
    
    /**
    ** Replays the current level.
    ** @arg ActionEvent event The event that caused this method to be called.
    ** @author dli294
    **/
    @FXML protected void replay(ActionEvent event) {
    	((LevelPage)_controller._loader.getPage("level")).initLevel();
    }
    
	
	/**
    ** Show the end level screen.
    ** @arg int numCorrect The number correct to display.
    ** @author dli294
    **/
	public void show(int numCorrect) {
		// If on level 1 and number correct is greater than or equal to 8, show next level button, else hide it
    	if (_controller._level == 1 && numCorrect >= 8) {
    		nextLevelButton.setVisible(true);
    	}
    	else {
    		nextLevelButton.setVisible(false);
    	}
    	
    	// Show number correct.
    	numberCorrect.setText(Integer.toString(numCorrect) + "/" + Integer.toString(NUM_QUESTIONS));
    	
    	super.show();
	}
	
    /**
    ** Saves the current level.
    ** @arg ActionEvent event The event that caused this method to be called.
    ** @author dli294
    **/
    @FXML protected void saveLevel(ActionEvent event) {
    	((LevelPage)_controller._loader.getPage("level")).saveCurrentLevel();
    }
    

}
