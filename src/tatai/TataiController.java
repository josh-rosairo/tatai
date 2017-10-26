package tatai;
 
import java.util.HashMap;

import javafx.stage.Stage;
import tatai.page.AchievementPage;
import tatai.page.Page;
import tatai.page.StatisticsPage;
 
/**
** State and controller class. Controls and stores state and behaviour of the application.
** @author dli294
**/
public class TataiController {
	// Stage to swap scenes in and out of.
	public Stage stage = null;
	// Scenes.
	public TataiLoader _loader;
	// Current level.
	public int _level = 1;
	// Mode.
	public String _mode;
	// Questions enabled.
	public HashMap<String, Boolean> _questionTypes = new HashMap<String, Boolean>();
	// Pages to return to.
	public Page _currentPage;
	private Page _returnPage;
	
	// Statistics.
	public int _personalBest1 = 0;
	public int _personalBest2 = 0;
	public int _longestStreakPractice = 0;
	public int _longestStreakAssess = 0;
	public int _longestPractice = 0;
	
    /**
    ** Constructor. Sets the stage to a private field so that it is usable everywhere. Loads the scenes.
    ** @arg Stage stage The stage to display the application on.
    ** @author dli294
    **/
	public TataiController(Stage s) {
		stage = s;
		_questionTypes.put("AdditionQuestion",true);
		_questionTypes.put("SubtractionQuestion",true);
		_questionTypes.put("DivisionQuestion",false);
		_questionTypes.put("MultiplicationQuestion",true);
    	_loader = new TataiLoader(this);
	}


    /**
    ** Initialization script to show the main menu.
    ** @author dli294
    **/
	public void init() {
        stage.setTitle("Welcome to Tatai!");
        
        // Show menu.
        _loader.getPage("menu").show();
	}
	
    /**
     ** Pops up an achievement unlocked modal box.
     ** @author dli294
     **/
    public void showAchievement(String achieved) {
    	_returnPage = _currentPage;
    	((AchievementPage)_loader.getPage("achievement")).setText(achieved);
    	_loader.getPage("achievement").show();
    }
    
     public void returnToScene() {
    	 _returnPage.show();
     }
     
     /**
      * Updates the achievements displayed on the statistics screen.
      */
     public void updateAchievements() {
    	 ((StatisticsPage)_loader.getPage("statistics")).updateAchievements();
     }

}