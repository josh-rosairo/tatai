package tatai.model;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import tatai.generator.*;

/**
 * Models a list of saved questions, to be displayed on the level settings page.
 * @author dli294
 *
 */
public class SavedQuestionList {
	// Time a level was completed.
	private Date _timestamp;
	// Number of the level (1, 2).
	private int _level;
	// Internal list of questions.
	private List<Question> _questions;
	
	/**
	 * Constructor.
	 * @param level The level of the questions.
	 * @param questions The list of questions.
	 * @author dli294
	 */
	public SavedQuestionList(int level, List<Question> questions) {
		_timestamp = new Date();
		_level = level;
		_questions = questions;
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
	
	
	/**
	** Returns the list of questions to be used.
	** @return List<Questions> The list of questions.
	** @author dli294
	**/
	public List<Question> getQuestions() {
		return _questions;
	}
	
	/**
	 * Returns the question at a specific index.
	 * @param int index The index to get the question at.
	 * @author dli294
	 */
	public Question get(int i) {
		return _questions.get(i);
	}
}
