package tatai;

import javafx.application.Application;
import javafx.stage.Stage;

/**
** Main application.
** @author dli294
**/
public class Main extends Application {
	
	/**
	 * Entry point into the application when run as a Java application.
	 * @param args Arguments.
	 * @author dli294
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
    /**
    ** Launches the application.
    ** @author dli294
    **/
    public void start(Stage stage) {
    	TataiController controller = new TataiController(stage);
    	controller.init();
    }
}
