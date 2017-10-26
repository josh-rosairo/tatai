package tatai.page;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tatai.TataiController;

/**
 * Controller and representation for an achievement page.
 * @author dli294
 *
 */
public class AchievementPage extends Page {
	
	// FXML-injected nodes.
	@FXML private Text achievementText;
	
	/**
	 * Constructor.
	 * @param stage The stage to set the scene on.
	 * @param scene The scene to show.
	 * @param sceneName The name of the scene.
	 * @param controller The state of the application to maintain a reference to.
	 * @author dli294
	 */
	public AchievementPage(Stage stage, Scene scene, String sceneName, TataiController controller) {
		super(stage, scene, sceneName, controller);
	}
	
	/**
	 * Sets the achievement text to the specified string.
	 * @param text The text to set.
	 */
	public void setText(String text) {
		achievementText.setText(text);
	}
}
