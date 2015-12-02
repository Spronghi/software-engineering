package com.carloan.presentation.controller;

import com.carloan.business.model.ModelFactory;
import com.carloan.business.model.ModelFactoryImpl;
import com.carloan.presentation.controller.internal.RegisterService;
import com.carloan.presentation.controller.internal.TableViewAdminRegister;
import com.carloan.presentation.controller.internal.TableViewUserRegister;
import com.carloan.presentation.loader.Loader;
import com.carloan.presentation.loader.LoaderFactory;
import com.carloan.presentation.loader.overview.OverviewLoader;
import com.carloan.presentation.loader.overview.OverviewLoaderFactory;
import com.carloan.service.login.LoginService;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class HomeViewController extends Controller{
	@FXML
	private Label operatorLabel;
	@FXML
	private MenuBar menuTitle;
	@FXML
	private ListView<String> categoryList;
	@FXML
	private Label categoryLabel;
	@FXML
	private Button addButton;
	@FXML
	private AnchorPane center;
	
	private RegisterService tableRegister;
	private ModelFactory modelFactory;
	
	public HomeViewController() {}
	
	@FXML
	private void initialize(){
		tableRegister = LoginService.getCurrentOperator().isAdmin() ? 
				new TableViewAdminRegister() : new TableViewUserRegister();
		categoryList.setItems(FXCollections.observableArrayList(tableRegister.getCategories()));
		addButton.setOnAction(this::handleAdd);
		operatorLabel.setText(LoginService.getOperatorCredential());
		
		categoryList.getSelectionModel().selectedItemProperty().addListener(
			(ov, oldVal, newVal) -> {
				if(newVal!=null){
					//tableRegister.getTable(newVal).getSelectionModel().clearSelection();
					center.getChildren().clear();
					TableView<?> table = tableRegister.getTable(newVal);
					center.getChildren().add(table);
					setAnchors(table,0,0,0,0);
					categoryLabel.setText(newVal);
					
					if(newVal.equals("Price Category") || newVal.equals("Car") && !LoginService.getCurrentOperator().isAdmin()){
						addButton.setDisable(true);
					} else if(newVal.equals("Contract")){
						addButton.setDisable(false);
						addButton.setText("Open");
						addButton.setOnAction(this::handleOpen);
					} else {
						addButton.setDisable(false);
						addButton.setText("Add");
						addButton.setOnAction(this::handleAdd);
					}
				}
		    });
		for(TableView<?> table : tableRegister){
			table.setRowFactory( t -> {
			    TableRow<?> row = new TableRow<>();
			    row.setOnMouseClicked(event -> {
			        if (event.getClickCount() == 2 && (!row.isEmpty())) {
			            handleDetails(row.getItem());
			        }
			    });
			    return row;
			});
		}
	}
	private void setAnchors(Node node, double top, double bottom, double right, double left){
		AnchorPane.setTopAnchor(node, top);
	     AnchorPane.setLeftAnchor(node, left);
	     AnchorPane.setRightAnchor(node, right);
	     AnchorPane.setBottomAnchor(node, bottom);
	}
	private void handleAdd(ActionEvent event){
		OverviewLoader loader = OverviewLoaderFactory.getInstance();
		modelFactory = new ModelFactoryImpl();
		loader.setModel(modelFactory.getInstance(categoryLabel.getText()));
		loader.initOwner(stage);
		loader.load();
		refreshTable();
	}
	private void handleDetails(Object row){
		OverviewLoader loader = OverviewLoaderFactory.getInstance();
		loader.setModel(row);
		loader.initOwner(stage);
		loader.load();
		refreshTable();
	}
	private void handleOpen(ActionEvent event){
		Loader loader = LoaderFactory.getInstance("OpenContractView");
		loader.load();
		refreshTable();
	}
	private void refreshTable(){
		initialize();
	}
}