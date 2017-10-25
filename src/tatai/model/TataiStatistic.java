package tatai.model;

import java.util.Date;
import java.text.DateFormat;

/**
* Models a Tatai statistic, to be displayed on the statistics page.
* @author dli294
**/
public class TataiStatistic {
	// Time a level was completed.
	private Date _timestamp;
	// Score of the level.
	private int _score;
	// Total number of questions in the level.
	private final static int NUMQUESTIONS = 10;
	// Number of the level (1, 2).
	private int _level;
	
	public TataiStatistic(int score, int level) {
		_timestamp = new Date();
		_score = score;
		_level = level;
	}
	
	/**
	** Returns the score, displayed neatly, e.g. 9/10, as a string.
	** @return String The score as a string.
	** @author dli294
	**/
	public String getScore() {
		return Integer.toString(_score) + "/" + Integer.toString(NUMQUESTIONS);
	}
	
	/**
	** Returns the level that was completed, as a string.
	** @return String The level as a string.
	** @author dli294
	**/
	public String getLevel() {
		if (_level == 1) {
			return "1 - 9";
		}
		else if (_level == 2) {
			return "1 - 99";
		}
		else {
			return "Invalid level";
		}
	}
	
	/**
	** Returns the time that was completed, as a string.
	** @return String The time as a string.
	** @author dli294
	**/
	public String getTime() {
		return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(_timestamp);
	}
}
