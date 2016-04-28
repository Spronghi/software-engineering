package com.carloan.presentation.controller;

import com.carloan.business.model.Contract;
import com.carloan.presentation.control.LoaderControl;
import com.carloan.presentation.control.ModelControl;
import com.carloan.presentation.factory.FXLoader;
import com.carloan.presentation.factory.FXUtil;
import com.carloan.service.ServiceFactory;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
	private AnchorPane center;
    @FXML
    private MenuItem logOutItem;
    @FXML
    private MenuItem closeItem;
    @FXML
    private MenuItem resetPasswordItem;
    @FXML
    private MenuItem newContractItem;

	public HomeViewController() {}
	@FXML
	public void initialize(){
		operatorLabel.setText(ServiceFactory.getUtilFactory().getLoginService().getOperatorCredential());
		logOutItem.setOnAction(e->{
            ServiceFactory.getUtilFactory().getLoginService().logout();
            FXLoader.loadView(LoaderControl.LOGIN_VIEW,stage);
        });
        closeItem.setOnAction(e->stage.close());
        resetPasswordItem.setOnAction(e->FXLoader.loadOverview(ServiceFactory.getUtilFactory().getLoginService().getCurrentOperator(),ModelControl.RESET_PASSWORD));
        newContractItem.setOnAction(e->FXLoader.loadOverview(new Contract(), ModelControl.NEW));
        categoryList.setItems(FXCollections.observableArrayList(FXLoader.loadTableCategories()));
		categoryList.getSelectionModel().selectedItemProperty().addListener( (ov, oldVal, newVal) -> {
			if(newVal!=null) {
                loadTable(newVal);
            }
	    });
	}
	private void loadTable(String category){
		center.getChildren().clear();
		TableView<?> table = FXLoader.loadTable(category);
		table.getSelectionModel().clearSelection();
		FXUtil.setAnchors(table,0);
		center.getChildren().add(table);
		categoryLabel.setText(category);
	}
}