package tatai.page;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tatai.TataiController;
import tatai.model.TataiStatistic;
import tatai.model.TataiTable;

public class StatisticsPage extends Page {
	
	// FXML injected nodes.
	@FXML public Text personalBest1;
	@FXML public Text personalBest2;
	@FXML public Text longestStreakPractice;
	@FXML public Text longestStreakAssess;
	@FXML public Text longestPractice;
	@FXML private HBox statsPanel;
	
	// List of statistics.
	private List<TataiStatistic> _statistics = new ArrayList<TataiStatistic>();
	// Statistics table to update.
	private TataiTable _table = new TataiTable();
	
	/**
	 * Constructor.
	 * @param stage The stage to set the scene on.
	 * @param scene The scene to show.
	 * @param sceneName The name of the scene.
	 * @param controller The state of the application to maintain a reference to.
	 * @author dli294
	 */
	public StatisticsPage(Stage stage, Scene scene, String sceneName, TataiController controller) {
		super(stage, scene, sceneName, controller);
	}
	
	@FXML private void initialize() {
		// Initialize the statistics page with a table.
		_table = new TataiTable();
		_table.setTataiData(_statistics);
		
        // Add the table to the panel.
        statsPanel.getChildren().addAll(_table);
	}
	
	/**
    ** Show the statistics page.
    ** @author dli294
    **/
	public void show() {
		// Update the table with the new list of data.
    	_table.setTataiData(_statistics);
		super.show();
	}
	
	public void updateAchievements() {
		personalBest1.setText(Integer.toString(_controller._personalBest1));
		personalBest2.setText(Integer.toString(_controller._personalBest2));
		longestStreakPractice.setText(Integer.toString(_controller._longestStreakPractice));
		longestStreakAssess.setText(Integer.toString(_controller._longestStreakAssess));
		longestPractice.setText(Integer.toString(_controller._longestPractice));
	}

}
