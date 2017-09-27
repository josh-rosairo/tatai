package tatai;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
    public void start(Stage stage) {
    	TataiController controller = new TataiController(stage);
    	controller.init();
    }
}
