package tatai.generator;

import java.util.Random;

/**
 * Represents a question to be displayed in Tatai.
 * @author dli294
 *
 */
public class Question {
	protected int _answer;
	protected String _question;
	protected int _level;
	
	protected int _upperLimit = 1;
 	protected int _lowerLimit = 1;
	
	public int getAnswer() {
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
		_answer = generateNum(_level);
		_question = Integer.toString(_answer);
	}
	
    /**
    ** Generates a random number based on the level.
    ** @arg int level The level to generate numbers for.
    ** @return int The randomly generated number.
    **/
    protected int generateNum(int level) {
     	// Define Random object and boundaries for random number generation.
     	Random rand = new Random();
     	
     	// Return randomly generated integer within boundaries (inclusive).
     	return rand.nextInt(_upperLimit) + _lowerLimit;
     }
	
    /**
     * Constructor
     * @param level The level to generate questions at.
     */
	public Question(int level) {
		// If on level 1, set upper boundary to 9.
     	if (level == 1) {
     		_upperLimit = 9;
     	} // If on level 2, set upper boundary to 99.
     	else if (level == 2) {
     		_upperLimit = 99;
     	}
		
		this.generate();
	}
}
