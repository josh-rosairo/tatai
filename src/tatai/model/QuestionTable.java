package tatai.model;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Represents a table of saved questions to be used on the settings page.
 * @author dli294
 *
 */
public class QuestionTable extends TableView<SavedQuestionList> {
	
	/**
	 * Constructor. Creates the table and its columns.
	 * @author dli294
	 */
	public QuestionTable() {
		// Add columns. Set widths.
		TableColumn<SavedQuestionList, String> timeCol = new TableColumn<SavedQuestionList, String>("Time saved");
		timeCol.setMinWidth(195);
		timeCol.setCellValueFactory(new PropertyValueFactory<SavedQuestionList,String>("time"));
		TableColumn<SavedQuestionList, String> levelCol = new TableColumn<SavedQuestionList, String>("Level");
		levelCol.setMinWidth(195);
		levelCol.setCellValueFactory(new PropertyValueFactory<SavedQuestionList,String>("level"));
		
		this.getColumns().addAll(timeCol, levelCol);
	    // Add placeholder text while empty.
	    this.setPlaceholder(new Label("No saved games to display."));
	    // Prevent horizontal scrollbars.
	    this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	/**
	 * Places list data in the table.
	 * @param statistics A list of the TataiStatistics data to set.
	 * @author dli294
	 */
	public void setQuestionData(List<SavedQuestionList> questions) {
		ObservableList<SavedQuestionList> data = FXCollections.observableArrayList(questions);
		this.setItems(data);
	}
	
    
}
