package com.carloan.presentation.factory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.carloan.service.ServiceFactory;
import com.carloan.service.control.PermissionControl;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import com.carloan.business.model.ModelFactory;
import com.carloan.presentation.control.ModelControl;

public enum FXFactory {
	INSTANCE;
	public static <T> TableView<T> createTableView(Class<?> clazz, List<T> items){
		final TableView<T> table = new TableView<>();
		ObservableList<T> observableItems = FXCollections.observableArrayList(items);
		table.setItems(observableItems);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		table.setRowFactory(t-> {
            final TableRow<T> row = new TableRow<>();

            final ContextMenu rowMenu = new ContextMenu();
            final ContextMenu emptyRowMenu = new ContextMenu();

            final MenuItem openItem = new MenuItem(ModelControl.OPEN);
            final MenuItem addItem = new MenuItem(ModelControl.NEW);
            final MenuItem addEmptyItem = new MenuItem(ModelControl.NEW);
            final MenuItem editItem = new MenuItem(ModelControl.EDIT);
            final MenuItem deleteItem = new MenuItem(ModelControl.DELETE);
            final MenuItem resetPasswordItem = new MenuItem(ModelControl.RESET_PASSWORD);
            final MenuItem setAdministratorItem = new MenuItem(ModelControl.SET_ADMINISTRATOR);
            final MenuItem editStatusItem = new MenuItem(ModelControl.EDIT_STATUS);
            final MenuItem closeItem = new MenuItem(ModelControl.CLOSE);

			openItem.setOnAction(e->FXLoader.loadOverview(row.getItem(),ModelControl.OPEN));
			addItem.setOnAction(e->{
				ModelFactory factory = new ModelFactory();
                T model = factory.getInstance(clazz.getSimpleName());
                if(FXLoader.loadOverview(model,ModelControl.NEW)) {
                    observableItems.add(model);
                }
			});
			addEmptyItem.setOnAction(addItem.getOnAction());
			editItem.setOnAction(e->{
				if(FXLoader.loadOverview(row.getItem(),ModelControl.EDIT)) {
                    observableItems.set(observableItems.indexOf(row.getItem()), row.getItem());
                }
			});
			deleteItem.setOnAction(e->{
				if(FXLoader.loadOverview(row.getItem(),ModelControl.DELETE)) {
                    observableItems.remove(row.getItem());
                }
			});
            resetPasswordItem.setOnAction(e-> {
                if(FXLoader.loadOverview(row.getItem(), ModelControl.RESET_PASSWORD)) {
                    observableItems.set(observableItems.indexOf(row.getItem()), row.getItem());
                }
            });
            setAdministratorItem.setOnAction(e->FXLoader.loadOverview(row.getItem(), ModelControl.SET_ADMINISTRATOR));
            editStatusItem.setOnAction(e->{
                    if(FXLoader.loadOverview(row.getItem(), ModelControl.EDIT_STATUS)) {
                        observableItems.set(observableItems.indexOf(row.getItem()), row.getItem());
                    }
            });
            closeItem.setOnAction(e->{
                if(FXLoader.loadOverview(row.getItem(),ModelControl.CLOSE)) {
                    observableItems.set(observableItems.indexOf(row.getItem()), row.getItem());
                }
            });
			row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    FXLoader.loadOverview(row.getItem(), ModelControl.OPEN);
                }
		    });

			String permission = ServiceFactory.getUtilFactory().getPermissionService().getPermissions(clazz);
            if(permission.contains(PermissionControl.READ)) {
                rowMenu.getItems().add(openItem);
            }
			if(permission.contains(PermissionControl.CREATE)){
				rowMenu.getItems().add(addItem);
				emptyRowMenu.getItems().add(addEmptyItem);
			}
			if(permission.contains(PermissionControl.UPDATE)) {
                rowMenu.getItems().add(editItem);
            }
			if(permission.contains(PermissionControl.DELETE)) {
                rowMenu.getItems().add(deleteItem);
            }
            if(permission.contains(PermissionControl.RESET_PASSWORD)) {
                rowMenu.getItems().addAll(new SeparatorMenuItem(), resetPasswordItem);
            }
            if(permission.contains(PermissionControl.SET_ADMINISTRATOR)) {
                if(rowMenu.getItems().contains(resetPasswordItem)){
                    rowMenu.getItems().add(setAdministratorItem);
                } else {
                    rowMenu.getItems().addAll(new SeparatorMenuItem(),setAdministratorItem);
                }
            }
            if(permission.contains(PermissionControl.EDIT_STATUS)) {
                rowMenu.getItems().addAll(new SeparatorMenuItem(),editStatusItem);
            }
			if(permission.contains(PermissionControl.CLOSE)) {
                rowMenu.getItems().addAll(new SeparatorMenuItem(), closeItem);
            }

		    row.contextMenuProperty().bind(
					Bindings.when(Bindings.isNotNull(row.itemProperty()))
					.then(rowMenu).otherwise(emptyRowMenu));
		    return row;
		});
		return table;
	}
	public static DatePicker createDatePicker(){
		DatePicker datePicker = new DatePicker();
		String pattern = "dd-MM-yyyy";
		datePicker.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if(date != null) {
                    return dateFormatter.format(date);
                }else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if(string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
		datePicker.setPromptText(pattern.toLowerCase());
		return datePicker;
	}
	public static TextField createTextField(String text,String regex){
		return new TextField(text) {
		    @Override 
		    public void replaceText(int start, int end, String text) {
		        if (!text.matches(regex)) 
		            super.replaceText(start, end, text);
		    }
		    @Override 
		    public void replaceSelection(String text) {
		        if (!text.matches(regex)) 
		            super.replaceSelection(text);
		    }
		};
	}
	public static TextField createTextField(String regex){
		return createTextField("",regex);
	}
}
