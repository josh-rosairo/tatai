package tatai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;

/**
** Handles all scenes and loading operations.
**/
public class TataiLoader extends FXMLLoader {
	
	// Width of the application.
	private final static int WIDTH = 600;
	// Height of the application.
	private final static int HEIGHT = 600;
	// Hashmap mapping human-readable names for each scene (as strings) to each loaded scene.
	private HashMap<String,Scene> _scenes = new HashMap<String, Scene>();
	// The controller that controls each scene.
	TataiController _controller;
	
	/**
	** Constructor. Loads the scenes when constructed.
	** @arg TataiController controller The controller to assign to each scene when loaded.
	**/
	public TataiLoader(TataiController controller) {
		_controller = controller;
		this.loadScenes();
	}

	/**
	** Loads the scenes from their FXMl files.
	**/
	private void loadScenes() {
		
		// List of scenes to load.
		List<String> scenes = new ArrayList<String>(Arrays.asList("menu", "level", "endlevel", "statistics", "settings", "achievement"));
		
		// Load each scene from their FXML files and put them into the hashmap.
		for(String sceneName: scenes) {
			try {
				// Load the FXML file.
			    FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName + ".fxml"));
			    loader.setController(_controller);
			    Parent root = (Parent)loader.load();
			    // Construct the scene.
				Scene scene = constructScene(root);

				// Add stylesheets.
				scene.getStylesheets().add (TataiLoader.class.getResource("styles.css").toExternalForm());
				// Load fonts.
				Font.loadFont(getClass().getResourceAsStream("fonts/Roboto-Regular.ttf"), 14);
				Font.loadFont(getClass().getResourceAsStream("fonts/Roboto-Thin.ttf"), 14);
				Font.loadFont(getClass().getResourceAsStream("fonts/Roboto-Bold.ttf"), 14);

				// Add to the hashmap.
				_scenes.put(sceneName, scene);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	** Constructs a scene from a root node.
	** @arg Parent root The root node to construct a scene from.
	** @return Scene The constructed scene, fitted to the application height and width.
	**/
	private Scene constructScene(Parent root) {
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		return scene;
	}
	
	/**
	** Gets a scene from this class's private internal hashmap.
	** @arg String sceneName The name of the scene.
	** @return Scene The scene requested.
	**/
	public Scene getScene(String sceneName) {
		return _scenes.get(sceneName);
	}
}
