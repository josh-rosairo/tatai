package tatai;

import java.util.Random;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TataiFactory {
	public TataiFactory() {
		
	}
	
	public static TableView<TataiStatistic> makeTable() {
		TableView<TataiStatistic> table = new TableView<TataiStatistic>();
		
		TableColumn timeCol = new TableColumn("Time completed");
    	timeCol.setMinWidth(195);
    	timeCol.setCellValueFactory(new PropertyValueFactory<TataiStatistic,String>("time"));
    	TableColumn scoreCol = new TableColumn("Score");
    	scoreCol.setMinWidth(95);
		scoreCol.setCellValueFactory(new PropertyValueFactory<TataiStatistic,String>("score"));
		TableColumn levelCol = new TableColumn("Level");
		levelCol.setMinWidth(195);
		levelCol.setCellValueFactory(new PropertyValueFactory<TataiStatistic,String>("level"));
		
		table.getColumns().addAll(timeCol, scoreCol, levelCol);
        // Add placeholder text while empty.
        table.setPlaceholder(new Label("No scores to display."));
        // Prevent horizontal scrollbars.
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        return table;
	}
	
    public static int generateNum(int level) {
     	// define Random object and boundaries for random number generation
     	Random rand = new Random();
    	int upperLimit = 1;
     	int lowerLimit = 1;
     	
     	// if on level 1, set upper boundary to 9
     	if (level == 1) {
     		upperLimit = 9;
     	} // if level 2, set upper boundary to 99
     	else if (level == 2) {
     		upperLimit = 99;
     	}
     	
     	// return randomly generated integer within boundaries (inclusive)
     	return rand.nextInt(upperLimit) + lowerLimit;
     }
}
