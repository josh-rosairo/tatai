package tatai.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tatai.TataiController;

/**
 * Represents an abstract page. This is a controller class, and can also show itself on a given stage.
 * @author dli294
 *
 */
public abstract class Page {
	private Scene _scene;
	private Stage _stage;
	private String _name;
	protected TataiController _controller;
	
	/**
	 * Constructor.
	 * @param stage The stage to set the scene on.
	 * @param scene The scene to show.
	 * @param sceneName The name of the scene.
	 * @param controller The state of the application to maintain a reference to.
	 * @author dli294
	 */
	public Page(Stage stage, Scene scene, String sceneName, TataiController controller) {
		_scene = scene;
		_stage = stage;
		_name = sceneName;
		_controller = controller;
	}
	
	/**
	 * Shows a scene.
	 * @author dli294
	 */
	public void show() {
		_controller._currentPage = this;
    	_stage.setScene(_scene);
        _stage.show();
	}
	
	/**
	 * Functionality to set a scene to this controller.
	 */
	public void setScene(Scene scene) {
		_scene = scene;
	}
	
	/**
	 * Returns the name of a scene.
	 * @return The name of the scene.
	 * @author dli294
	 */
	public String getName() {
		return _name;
	}
	
	/**
    ** Shows the menu.
    ** @arg ActionEvent event The event that caused this method to be called.
    ** @author dli294
    **/
    @FXML protected void showMenu(ActionEvent event) {
    	_controller._loader.getPage("menu").show();
    }
}
