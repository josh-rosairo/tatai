package tatai.page;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Page {
	private Scene _scene;
	private Stage _stage;
	private String _name;
	
	public Page(Stage stage, Scene scene, String sceneName) {
		_scene = scene;
		_stage = stage;
		_name = sceneName;
	}
	
	public void show() {
    	_stage.setScene(_scene);
        _stage.show();
	}
	
	public String getName() {
		return _name;
	}
}
