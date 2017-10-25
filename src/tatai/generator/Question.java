package tatai.generator;

import java.util.Random;

/**
 * Represents a question to be displayed in Tatai.
 * @author dli294
 *
 */
public class Question {
	private String _answer;
	private String _question;
	private int _level;
	
	public String getAnswer() {
		return _answer;
	}
	
	public String getQuestion() {
		return _question;
	}
	
	/**
	 * Generates a question and assigns the question and answer to its own private fields.
	 * Default is practice mode, i.e. number only.
	 */
	protected void generate() {
		_question = Integer.toString(generateNum(_level));
		_answer = _question;
	}
	
    /**
    ** Generates a random number based on the level.
    ** @arg int level The level to generate numbers for.
    ** @return int The randomly generated number.
    **/
    protected static int generateNum(int level) {
     	// Define Random object and boundaries for random number generation.
     	Random rand = new Random();
    	int upperLimit = 1;
     	int lowerLimit = 1;
     	
     	// If on level 1, set upper boundary to 9.
     	if (level == 1) {
     		upperLimit = 9;
     	} // If on level 2, set upper boundary to 99.
     	else if (level == 2) {
     		upperLimit = 99;
     	}
     	
     	// Return randomly generated integer within boundaries (inclusive).
     	return rand.nextInt(upperLimit) + lowerLimit;
     }
	
	public Question(int level) {
		this.generate();
	}
}
