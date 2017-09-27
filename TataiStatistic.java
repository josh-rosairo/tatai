package tatai;

import java.util.Date;
import java.text.DateFormat;

// Models a Tatai statistic, to be displayed on the statistics page.
public class TataiStatistic {
	private Date _timestamp;
	private int _score;
	private final static int NUMQUESTIONS = 10;
	private int _level;
	
	public TataiStatistic(int score, int level) {
		_timestamp = new Date();
		_score = score;
		_level = level;
	}
	
	// Returns the score, displayed neatly, e.g. 9/10
	public String getScore() {
		return Integer.toString(_score) + "/" + Integer.toString(NUMQUESTIONS);
	}
	
	// Returns the level that was completed, as a string
	public String getLevel() {
		if (_level == 1) {
			return "Level 1: 1-9";
		}
		else if (_level == 1) {
			return "Level 2: 1-99";
		}
		else {
			return "Invalid level";
		}
	}
	
	// Returns the time that a level was completed
	public String getTime() {
		return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(_timestamp);
	}
}
