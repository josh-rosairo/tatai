package tatai.generator;

/**
 * Represents a subtraction question.
 * @author dli294, jtha772
 *
 */
public class SubtractionQuestion extends Question {
	
	@Override
	/**
	 * Generates a subtraction question.
	 * @author dli294, jtha772
	 */
	protected void generate() {
		int num1, num2;
    	do {
			num1 = generateNum(_level);
			num2 = generateNum(_level);
		} while ((num1 - num2) < _lowerLimit);
    	_question = Integer.toString(num1) + " - " + Integer.toString(num2);
    	_answer = num1 - num2;
	}

	/**
	 * Constructor.
	 * @param level The level at which to generate questions.
	 * @author dli294
	 */
	public SubtractionQuestion(int level) {
		super(level);
	}

}
