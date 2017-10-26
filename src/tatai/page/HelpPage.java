package tatai.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import tatai.TataiController;

/**
 * Controller and representation for the help page.
 * @author dli294
 *
 */
public class HelpPage extends Page {
	
	// FXML-injected nodes.
	@FXML private ScrollPane helpPane;
	
	/**
	 * Constructor.
	 * @param stage The stage to set the scene on.
	 * @param scene The scene to show.
	 * @param sceneName The name of the scene.
	 * @param controller The state of the application to maintain a reference to.
	 * @author dli294
	 */
	public HelpPage(Stage stage, Scene scene, String sceneName, TataiController controller) {
		super(stage, scene, sceneName, controller);
	}
	
    /**
     ** Returns to the previously stored scene.
     ** @arg ActionEvent event The event that caused this method to be called.
     ** @author dli294
     **/
     @FXML protected void returnToScene(ActionEvent event) {
    	 _controller.returnToScene();
     }
}
