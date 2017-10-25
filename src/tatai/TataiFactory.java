package tatai;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tatai.model.TataiStatistic;

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
	
	/**
	 * Finds all values in a map where the value is a certain value.
	 * @param map The map to search.
	 * @param value The value to find.
	 * @return A set of all the keys with this value.
	 */
	public static Set<String> getKeysByValue(Map<String, Boolean> map, Boolean value) {
	    Set<String> keys = new HashSet<String>();
	    for (Entry<String, Boolean> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            keys.add(entry.getKey());
	        }
	    }
	    return keys;
	}
	
	/**
	 * Gets a random string from a set of strings.
	 * @param from The set to search in.
	 * @return Returns a random string.
	 */
	public static String getRandomString(Set<String> from) {
		Random r = new Random();
		int i = r.nextInt(from.size());
		return (String) from.toArray()[i];
	}
     
}
