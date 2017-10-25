package tatai.model;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TataiTable extends TableView<TataiStatistic> {
	
	public TataiTable() {
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
		
		this.getColumns().addAll(timeCol, scoreCol, levelCol);
	    // Add placeholder text while empty.
	    this.setPlaceholder(new Label("No scores to display."));
	    // Prevent horizontal scrollbars.
	    this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	public void setTataiData(List<TataiStatistic> statistics) {
		ObservableList<TataiStatistic> data = FXCollections.observableArrayList(statistics);
		this.setItems(data);
	}
	
    
}
