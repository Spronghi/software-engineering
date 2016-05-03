package com.carloan.presentation.factory;

import java.time.LocalDate;

import com.carloan.presentation.factory.model.ModelMenuButton;
import com.carloan.presentation.factory.model.ModelMenuItem;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import javafx.util.Callback;

public enum FXUtil {
	INSTANCE;
    public static <T> void addMenuItem(ModelMenuButton<T> menu, String title, T model){
        final ModelMenuItem<T> item = new ModelMenuItem<>(title,model);
        menu.getItems().add(item);
        item.setOnAction(v -> {
            menu.setModel(item.getModel());
            menu.setText(item.getText());
        });
    }
    public static void addGridRow(GridPane grid, Node leftItem, Node rightItem, boolean separator){
        int index = grid.getRowConstraints().size();
        grid.add(leftItem, 0, index);
        grid.add(rightItem, 1, index);
        grid.getRowConstraints().add(new RowConstraints(10,40, Control.USE_COMPUTED_SIZE));
        if(separator){
            index += 1;
            grid.add(new Separator(), 0, index);
            grid.add(new Separator(), 1, index);
            grid.getRowConstraints().add(new RowConstraints(-1,1,1));
        }
    }
    public static void setAnchors(Node node, double value){
		setAnchors(node,value, value, value, value);
	}
    public static void setAnchors(Node node, double top, double bottom, double left, double right){
        AnchorPane.setBottomAnchor(node, bottom);
        AnchorPane.setTopAnchor(node, top);
        AnchorPane.setLeftAnchor(node, left);
        AnchorPane.setRightAnchor(node, right);
    }
    public static void setTopLeftAnchors(Node node,double top, double left){
        AnchorPane.setTopAnchor(node, top);
        AnchorPane.setLeftAnchor(node, left);
    }
	public static <T> ObservableValue<T> toObservableValue(T value){
		return new ReadOnlyObjectWrapper<>(value);
	}
	public static void setStartDate(DatePicker datePicker, DatePicker startPicker){
		datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker picker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(startPicker.getValue().plusDays(1))){
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
    	});
	}
	public static void setStartDate(DatePicker datePicker, LocalDate startDate){
		datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker picker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(startDate)){
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
    	});
	}
}
