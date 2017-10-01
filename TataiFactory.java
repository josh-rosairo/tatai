package tatai;

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
	
    /**
    ** Generates a random number based on the level.
    ** @arg int level The level to generate numbers for.
    ** @return int The randomly generated number.
    **/
    public static int generateNum(int level) {
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
}
