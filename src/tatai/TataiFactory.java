package tatai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
** Factory class for constructing nodes and numbers.
**/
public class TataiFactory {
	public TataiFactory() {
		
	}

	public static List<String> _operands = Arrays.asList("+", "-", "x", "/");
    /**
    ** Creates the table to be used on the statistics page.
    ** @return TableView<TataiStatistic> The created table.
    **/
	public static TableView<TataiStatistic> makeTable() {
		TableView<TataiStatistic> table = new TableView<TataiStatistic>();
		
        // Add columns. Set widths.
		TableColumn<TataiStatistic, String> timeCol = new TableColumn<TataiStatistic, String>("Time completed");
    	timeCol.setMinWidth(195);
    	timeCol.setCellValueFactory(new PropertyValueFactory<TataiStatistic,String>("time"));
    	TableColumn<TataiStatistic, String> scoreCol = new TableColumn<TataiStatistic, String>("Score");
    	scoreCol.setMinWidth(95);
		scoreCol.setCellValueFactory(new PropertyValueFactory<TataiStatistic,String>("score"));
		TableColumn<TataiStatistic, String> levelCol = new TableColumn<TataiStatistic, String>("Level");
		levelCol.setMinWidth(195);
		levelCol.setCellValueFactory(new PropertyValueFactory<TataiStatistic,String>("level"));
		
		table.getColumns().addAll(timeCol, scoreCol, levelCol);
        // Add placeholder text while empty.
        table.setPlaceholder(new Label("No scores to display."));
        // Prevent horizontal scrollbars.
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        return table;
	}
    
    // method that generates the numbers used in the question and returns them as a 1x3 list, the first two values are the numbers to be displayed with the question, 
    // and the last number is the answer to be said. It takes as params: the operand desired (e.g. addition) as well as the level they are on.
    public static List<String> generateQuestionAssess(String operand, int level) {
    	int num1 = generateNum(level);
    	int num2 = generateNum(level);
    	int answer = 100, upperLimit = 0;
    	
    	
    	if (level == 1) {
     		upperLimit = 9;
     	} // If on level 2, set upper boundary to 99.
     	else if (level == 2) {
     		upperLimit = 99;
     	}
    	
    	// if addition, make addition question
    	while (answer > upperLimit) {
	    	if (operand.equals(_operands.get(0))) {
	    		while ((num1 + num2)>upperLimit) {
	    			num1 = generateNum(level);
	    			num2 = generateNum(level);
	    		}
	    		
	    		answer = num1 + num2;
	    	}
	    	// if subtraction, make addition question
	    	else if (operand.equals(_operands.get(1))) {
	    		while ((num1 - num2)< 1) {
	    			num1 = generateNum(level);
	    			num2 = generateNum(level);
	    		}
	
	    		answer = num1 - num2;
	    	}
	    	// if multiplication, make addition question
	    	else if (operand.equals(_operands.get(2))) {
	    		while ((num1*num2)>upperLimit) {
	    			num1 = generateNum(level);
	    			num2 = generateNum(level);
	    		}
	    		
	    		answer = num1 * num2;
	    	}
	    	// else make division question
	    	else {
	    		while (!(num1 % num2 == 0) || (num1 < num2)) {
	    			num1 = generateNum(level);
	    			num2 = generateNum(level);
	    		}
	    		
	    		answer = num1 / num2;
	    	}
	    	
	    	// break possible infinite loops
	    	if (answer > upperLimit) {
	    		num1 = generateNum(level);
    			num2 = generateNum(level);
	    	}
    	}
    	
    	List<String> nums = new ArrayList<>();
    	nums.add(Integer.toString(num1) + " " + operand + " " + Integer.toString(num2));
    	nums.add(Integer.toString(answer));
    	return nums;
    }
     
}
