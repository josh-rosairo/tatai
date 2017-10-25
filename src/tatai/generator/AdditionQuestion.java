package tatai.generator;

/**
 * Represents an addition question.
 * @author dli294
 *
 */
public class AdditionQuestion extends Question {
	
	@Override
	/**
	 * Generates an addition question.
	 */
	protected void generate() {
		int num1, num2;
    	do {
			num1 = generateNum(_level);
			num2 = generateNum(_level);
		} while ((num1 + num2) > _upperLimit);
    	_question = Integer.toString(num1) + " + " + Integer.toString(num2);
    	_answer = num1 + num2;
	}

	/**
	 * Constructor.
	 * @param level The level at which to generate questions.
	 */
	public AdditionQuestion(int level) {
		super(level);
	}

}
