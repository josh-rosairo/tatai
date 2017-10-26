package tatai.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tatai.TataiController;
import tatai.model.QuestionTable;

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
	@FXML private VBox savedQuestionPanel;
	
	// Statistics table to update.
	private QuestionTable _table = new QuestionTable();
	
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
     	
     	 if (event != null) {
        	 if (_controller._questionTypes.get("AdditionQuestion") == false) {
        	 	 _controller._questionTypes.put("AdditionQuestion", true);
        	 	chooseAdditionButton.getStyleClass().add("depressedbutton");
        	 } else {
        		 _controller._questionTypes.put("AdditionQuestion", false);
        	 }
     	 } else {
     		if (_controller._questionTypes.get("AdditionQuestion") == true) {
     			chooseAdditionButton.getStyleClass().add("depressedbutton");
     		}
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
      	
      	 if (event != null) {
         	 if (_controller._questionTypes.get("SubtractionQuestion") == false) {
          		_controller._questionTypes.put("SubtractionQuestion", true);
          	 	chooseSubtractionButton.getStyleClass().add("depressedbutton");
          	 } else {
          		_controller._questionTypes.put("SubtractionQuestion", false);
          	 }
      	 } else {
      		 if (_controller._questionTypes.get("SubtractionQuestion") == true) {
      			chooseSubtractionButton.getStyleClass().add("depressedbutton");
      		 }
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
       	
       	 if (event != null) {
       		if (_controller._questionTypes.get("DivisionQuestion") == false) {
          		_controller._questionTypes.put("DivisionQuestion", true);
          	 	chooseDivisionButton.getStyleClass().add("depressedbutton");
          	 } else {
          		_controller._questionTypes.put("DivisionQuestion", false);
          	 }
       	 } else {
       		 if (_controller._questionTypes.get("DivisionQuestion") == true) {
       			chooseDivisionButton.getStyleClass().add("depressedbutton");
       		 }
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
        	
        	if (event != null) {
	   	       	 if (_controller._questionTypes.get("MultiplicationQuestion") == false) {
	 	       		_controller._questionTypes.put("MultiplicationQuestion", true);
	 	       	 	chooseMultiplicationButton.getStyleClass().add("depressedbutton");
	 	       	 } else {
	 	       		_controller._questionTypes.put("MultiplicationQuestion", false);
	 	       	 }
        	} else {
        		if (_controller._questionTypes.get("MultiplicationQuestion") == true) {
        			chooseMultiplicationButton.getStyleClass().add("depressedbutton");
        		}
        	}

        }
        
    	/**
    	 * Initialization function to set data and nodes.
    	 * @author dli294
    	 */
    	@FXML private void initialize() {
    		// Initialize the statistics page with a table.
    		_table = new QuestionTable();
    		
            // Add the table to the panel.
            savedQuestionPanel.getChildren().addAll(_table);
            
            // Add listener to update the selected level when the level changes.
         	_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
         	    ((LevelPage)_controller._loader.getPage("level")).setQuestionList(newSelection);
         	});
    	}
        
    	/**
         ** Show the settings page.
         ** @author dli294
         **/
     	public void show() {
     		// Update the table with the new list of data.
         	_table.setQuestionData(((LevelPage)_controller._loader.getPage("level")).getSavedQuestions());
         	
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
        	
     		super.show();
     	}
     	
        /**
         ** Clears the table of a selection.
         ** @arg ActionEvent event The event that caused this method to be called.
         ** @author dli294
         **/
     	@FXML protected void clearTable(ActionEvent event) {
     		_table.getSelectionModel().clearSelection();
     	}
}
