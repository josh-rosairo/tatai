package tatai.page;

import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import tatai.TataiController;

/**
 * Controller and representation for a menu page.
 * @author dli294
 *
 */
public class MenuPage extends Page {

	/**
	 * Constructor.
	 * @param stage The stage to set the scene on.
	 * @param scene The scene to show.
	 * @param sceneName The name of the scene.
	 * @param controller The state of the application to maintain a reference to.
	 * @author dli294
	 */
	public MenuPage(Stage stage, Scene scene, String sceneName, TataiController controller) {
		super(stage, scene, sceneName, controller);
	}
	
    /**
    ** Show the statistics page.
    ** @arg ActionEvent event The event that caused this method to be called.
    ** @author dli294
    **/
    @FXML private void showStatistics(ActionEvent event) {
        // Show the scene.
    	_controller._loader.getPage("statistics").show();
    }
    
    /**
    ** Show the practice level.
    ** @arg ActionEvent event The event that caused this method to be called.
    ** @author dli294
    **/
    @FXML private void showPractice() {
        // Show the scene.
    	_controller._mode = "practice";
    	((LevelPage)_controller._loader.getPage("level")).initLevel();
    }
    
    /**
    ** Show the assess page.
    ** @arg ActionEvent event The event that caused this method to be called.
    ** @author dli294
    **/
    @FXML private void showAssess() {
        // Show the scene.
    	_controller._mode = "assess";
    	((LevelPage)_controller._loader.getPage("level")).initLevel();
    }
    
    /**
    ** Show the settings page.
    ** @arg ActionEvent event The event that caused this method to be called.
    ** @author dli294
    **/
    @FXML private void showSettings() {
        // Show the scene.
    	_controller._loader.getPage("settings").show();
    }
    
    
	
    /**
    ** Quits the application.
    ** @arg ActionEvent event The event that caused this method to be called.
    ** @author dli294
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
