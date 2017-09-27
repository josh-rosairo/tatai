package tatai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

// Handles all scenes and loading.
public class TataiLoader extends FXMLLoader {
	
	private final static int WIDTH = 800;
	private final static int HEIGHT = 600;
	private HashMap<String,Scene> _scenes = new HashMap<String, Scene>();
	TataiController _controller;
	
	public TataiLoader(TataiController controller) {
		_controller = controller;
		this.loadScenes();
	}
	
	private void loadScenes() {
		
		List<String> scenes = new ArrayList<String>(Arrays.asList("menu", "level", "endlevel"));
		
		// Load each scene from their FXML files and put them into the hashmap.
		for(String sceneName: scenes) {
			try {
			    FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName + ".fxml"));
			    loader.setController(_controller);
			    Parent root = (Parent)loader.load();
			    
				Scene scene = constructScene(root);
				_scenes.put(sceneName, scene);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private Scene constructScene(Parent root) {
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		return scene;
	}
	
	public Scene getScene(String sceneName) {
		return _scenes.get(sceneName);
	}
}
