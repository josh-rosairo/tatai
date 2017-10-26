package tatai.page;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class PageFactory {
	public static Page makePage(Stage stage, Scene scene, String sceneName) {
		if (sceneName == "achievement") {
			return new AchievementPage(stage, scene, sceneName);
		} else if (sceneName == "level") {
			return new LevelPage(stage, scene, sceneName);
		} else if (sceneName == "settings") {
			return new SettingsPage(stage, scene, sceneName);
		} else if (sceneName == "endlevel") {
			return new EndlevelPage(stage, scene, sceneName);
		} else if (sceneName == "menu") {
			return new MenuPage(stage, scene, sceneName);
		} else if (sceneName == "statistics") {
			return new StatisticsPage(stage, scene, sceneName);
		}
		else return null;
	}
}
