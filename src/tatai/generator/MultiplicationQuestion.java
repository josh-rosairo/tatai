package tatai.generator;

/**
 * Represents a multiplication question.
 * @author dli294
 *
 */
public class MultiplicationQuestion extends Question {
	
	@Override
	/**
	 * Generates a multiplication question.
	 */
	protected void generate() {
		int num1, num2;
    	do {
			num1 = generateNum(_level);
			num2 = generateNum(_level);
		} while ((num1 * num2) > _upperLimit);
    	_question = Integer.toString(num1) + " × " + Integer.toString(num2);
    	_answer = num1 * num2;
	}

	/**
	 * Constructor.
	 * @param level The level at which to generate questions.
	 */
	public MultiplicationQuestion(int level) {
		super(level);
	}

}
