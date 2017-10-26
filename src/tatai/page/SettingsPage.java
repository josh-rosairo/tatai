package tatai.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tatai.TataiController;

/**
 * Controller and representation for a settings page.
 * @author dli294
 *
 */
public class SettingsPage extends Page {
	
	// FXML-injected nodes.
	@FXML private Button chooseAdditionButton;
	@FXML private Button chooseSubtractionButton;
	@FXML private Button chooseDivisionButton;
	@FXML private Button chooseMultiplicationButton;
	@FXML private Button chooseLevel1Button;
	@FXML private Button chooseLevel2Button;
	
	/**
	 * Constructor.
	 * @param stage The stage to set the scene on.
	 * @param scene The scene to show.
	 * @param sceneName The name of the scene.
	 * @param controller The state of the application to maintain a reference to.
	 * @author dli294
	 */
	public SettingsPage(Stage stage, Scene scene, String sceneName, TataiController controller) {
		super(stage, scene, sceneName, controller);
	}
	
	/**
	 * Setup of buttons and depressed states for the settings page.
	 */
	public void show() {
		if (_controller._level == 1) {
    		setLevel1();
    	} else {
    		setLevel2();
    	}
    	
    	if (_controller._questionTypes.get("AdditionQuestion")) {
    		setAddition(null);
    	}
    	if (_controller._questionTypes.get("SubtractionQuestion")) {
    		setSubtraction(null);
    	}
    	if (_controller._questionTypes.get("DivisionQuestion")) {
    		setDivision(null);
    	}
    	if (_controller._questionTypes.get("MultiplicationQuestion")) {
    		setMultiplication(null);
    	}
	}
	
    /**
    ** Set the level to level 1. Make the level 1 button depressed and the level 2 button not depressed.
    ** @arg ActionEvent event The event that caused this method to be called.
    ** @author dli294
    **/
    @FXML private void setLevel1() {
        // Show the scene.
    	_controller._level = 1;
    	chooseLevel1Button.getStyleClass().clear();
    	chooseLevel1Button.getStyleClass().add("button");
    	chooseLevel1Button.getStyleClass().add("depressedbutton");
    	chooseLevel2Button.getStyleClass().clear();
    	chooseLevel2Button.getStyleClass().add("button");
    }
    
    /**
    ** Set the level to level 2. Make the level 2 button depressed and the level 2 button not depressed.
    ** @arg ActionEvent event The event that caused this method to be called.
    ** @author dli294
    **/
    @FXML private void setLevel2() {
        // Show the scene.
    	_controller._level = 2;
    	chooseLevel1Button.getStyleClass().clear();
    	chooseLevel1Button.getStyleClass().add("button");
    	chooseLevel2Button.getStyleClass().clear();
    	chooseLevel2Button.getStyleClass().add("button");
    	chooseLevel2Button.getStyleClass().add("depressedbutton");
    }
    
    /**
     ** Add addition questions to the questions generated.
     ** @arg ActionEvent event The event that caused this method to be called.
     ** @author dli294
     **/
     @FXML private void setAddition(ActionEvent event) {
    	 chooseAdditionButton.getStyleClass().clear();
     	 chooseAdditionButton.getStyleClass().add("button");
     	
    	 if (_controller._questionTypes.get("AdditionQuestion") == false) {
    	 	 _controller._questionTypes.put("AdditionQuestion", true);
    	 	chooseAdditionButton.getStyleClass().add("depressedbutton");
    	 } else {
    		 _controller._questionTypes.put("AdditionQuestion", false);
    	 }
     }
     
     /**
      ** Add subtraction questions to the questions generated.
      ** @arg ActionEvent event The event that caused this method to be called.
      ** @author dli294
      **/
      @FXML private void setSubtraction(ActionEvent event) {
     	 chooseSubtractionButton.getStyleClass().clear();
      	 chooseSubtractionButton.getStyleClass().add("button");
      	
     	 if (_controller._questionTypes.get("SubtractionQuestion") == false) {
     		_controller._questionTypes.put("SubtractionQuestion", true);
     	 	chooseSubtractionButton.getStyleClass().add("depressedbutton");
     	 } else {
     		_controller._questionTypes.put("SubtractionQuestion", false);
     	 }
      }
      
      /**
       ** Add division questions to the questions generated.
       ** @arg ActionEvent event The event that caused this method to be called.
       ** @author dli294
       **/
       @FXML private void setDivision(ActionEvent event) {
      	 chooseDivisionButton.getStyleClass().clear();
       	 chooseDivisionButton.getStyleClass().add("button");
       	
      	 if (_controller._questionTypes.get("DivisionQuestion") == false) {
      		_controller._questionTypes.put("DivisionQuestion", true);
      	 	chooseDivisionButton.getStyleClass().add("depressedbutton");
      	 } else {
      		_controller._questionTypes.put("DivisionQuestion", false);
      	 }
       }
       
       /**
        ** Add multiplication questions to the questions generated.
        ** @arg ActionEvent event The event that caused this method to be called.
        ** @author dli294
        **/
        @FXML private void setMultiplication(ActionEvent event) {
       	 	chooseMultiplicationButton.getStyleClass().clear();
        	 chooseMultiplicationButton.getStyleClass().add("button");
        	
       	 if (_controller._questionTypes.get("MultiplicationQuestion") == false) {
       		_controller._questionTypes.put("MultiplicationQuestion", true);
       	 	chooseMultiplicationButton.getStyleClass().add("depressedbutton");
       	 } else {
       		_controller._questionTypes.put("MultiplicationQuestion", false);
       	 }
        }
}
