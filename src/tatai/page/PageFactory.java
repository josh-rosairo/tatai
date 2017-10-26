package tatai.page;

import javafx.scene.Scene;
import javafx.stage.Stage;
import tatai.TataiController;

/**
 * PageFactory class to generate Page objects used to control each page in the application.
 * @author dli294
 *
 */
public class PageFactory {
	public static Page makePage(Stage stage, Scene scene, String sceneName, TataiController controller) {
		if (sceneName == "achievement") {
			return new AchievementPage(stage, scene, sceneName, controller);
		} else if (sceneName == "level") {
			return new LevelPage(stage, scene, sceneName, controller);
		} else if (sceneName == "settings") {
			return new SettingsPage(stage, scene, sceneName, controller);
		} else if (sceneName == "endlevel") {
			return new EndlevelPage(stage, scene, sceneName, controller);
		} else if (sceneName == "menu") {
			return new MenuPage(stage, scene, sceneName, controller);
		} else if (sceneName == "statistics") {
			return new StatisticsPage(stage, scene, sceneName, controller);
		}
		else return null;
	}
}
