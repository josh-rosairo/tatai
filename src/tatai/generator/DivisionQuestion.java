package tatai.generator;

/**
 * Represents a division question.
 * @author dli294
 *
 */
public class DivisionQuestion extends Question {
	
	@Override
	/**
	 * Generates a division question.
	 */
	protected void generate() {
		int num1, num2;
    	do {
			num1 = generateNum(_level);
			num2 = generateNum(_level);
		} while (!(num1 % num2 == 0) || (num1 < num2));
    	_question = Integer.toString(num1) + " ÷ " + Integer.toString(num2);
    	_answer = num1 / num2;
	}

	/**
	 * Constructor.
	 * @param level The level at which to generate questions.
	 */
	public DivisionQuestion(int level) {
		super(level);
	}

}
