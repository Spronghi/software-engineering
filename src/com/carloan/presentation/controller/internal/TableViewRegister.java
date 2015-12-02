package com.carloan.presentation.controller.internal;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Car;
import com.carloan.business.model.Contract;
import com.carloan.business.model.Customer;
import com.carloan.business.model.Location;
import com.carloan.business.model.Operator;
import com.carloan.business.model.PriceCategory;

abstract class TableViewRegister implements RegisterService{
	private ObservableDataRegister observableData;

	public TableViewRegister(){
		observableData = new ObservableDataRegister();
	}
	private <T> TableView<T> getDefaultTable(ObservableList<T> container){
		TableView<T> tableView = new TableView<>();
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setItems(container);
        return tableView;
	}
	protected TableView<PriceCategory> setPriceCategoryTable(){
		TableView<PriceCategory> modelTable = getDefaultTable(observableData.get("PriceCategory"));
        ObservableList<TableColumn<PriceCategory,?>> columns = FXCollections.observableArrayList();
		
        TableColumn<PriceCategory,Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        idCol.setMinWidth(30);
        idCol.setMaxWidth(40);
        columns.add(idCol);
        
        TableColumn<PriceCategory,String> categoryCel = new TableColumn<>("Category");
        categoryCel.setCellValueFactory(p->p.getValue().endUserStringProperty());
        columns.add(categoryCel);
        
        TableColumn<PriceCategory,String> baseRateLimitCel = new TableColumn<>("Base Rate Limited");
        baseRateLimitCel.setCellValueFactory(p->p.getValue().baseRateLimitEndUserProperty());
        columns.add(baseRateLimitCel);

        TableColumn<PriceCategory,String> baseRateUnlimitCel = new TableColumn<>("Base Rate Unlimited");
        baseRateUnlimitCel.setCellValueFactory(p->p.getValue().baseRateUnlimitEndUserProperty());
        columns.add(baseRateUnlimitCel);

        TableColumn<PriceCategory,String> kmRateCel = new TableColumn<>("Km Rate");
        kmRateCel.setCellValueFactory(p->p.getValue().kmRateEndUserProperty());
        columns.add(kmRateCel);
        
        modelTable.getColumns().setAll(columns);
        return modelTable;
	}
	protected TableView<Contract> setContractTable(){
		TableView<Contract> modelTable = getDefaultTable(observableData.get("Contract"));
        ObservableList<TableColumn<Contract,?>> columns = FXCollections.observableArrayList();
		
        TableColumn<Contract,String> openCel = new TableColumn<>("Open");
        openCel.setCellValueFactory(p->p.getValue().endUserOpenProperty());
        openCel.setMinWidth(70);
        openCel.setMaxWidth(80);
        columns.add(openCel);
        
        TableColumn<Contract,Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        idCol.setMinWidth(30);
        idCol.setMaxWidth(40);
        columns.add(idCol);
        
        TableColumn<Contract,LocalDate> startDateCel = new TableColumn<>("Start Date");
        startDateCel.setCellValueFactory(new PropertyValueFactory<>("formattedStartDate"));
        startDateCel.setMinWidth(100);
        startDateCel.setMaxWidth(120);
        columns.add(startDateCel);
		
        TableColumn<Contract,LocalDate> endDateCel = new TableColumn<>("End Date");
        endDateCel.setCellValueFactory(new PropertyValueFactory<>("formattedEndDate"));
        endDateCel.setMinWidth(100);
        endDateCel.setMaxWidth(120);
        columns.add(endDateCel);

        TableColumn<Contract,String> carCel = new TableColumn<>("Car");
        carCel.setCellValueFactory(p->p.getValue().getCar().endUserStringProperty());
        columns.add(carCel);

        TableColumn<Contract,String> customerCel = new TableColumn<>("Customer");
        customerCel.setCellValueFactory(p->p.getValue().getCustomer().endUserStringProperty());
        columns.add(customerCel);

        TableColumn<Contract,String> startAgencyCel = new TableColumn<>("Start Agency");
        startAgencyCel.setCellValueFactory(p->p.getValue().getStartAgency().endUserStringProperty());
        columns.add(startAgencyCel);

        TableColumn<Contract,String> endAgencyCel = new TableColumn<>("End Agency");
        endAgencyCel.setCellValueFactory(p->p.getValue().getEndAgency().endUserStringProperty());
        columns.add(endAgencyCel);

        TableColumn<Contract,String> amountCel = new TableColumn<>("Amount");
        amountCel.setCellValueFactory(new PropertyValueFactory<>("endUserAmount"));
        amountCel.setMinWidth(70);
        amountCel.setMaxWidth(80);
        columns.add(amountCel);
        
        modelTable.getColumns().setAll(columns);
        return modelTable;
	}
	protected TableView<Location> setLocationTable(){
		TableView<Location> modelTable = getDefaultTable(observableData.get("Location"));
        ObservableList<TableColumn<Location,?>> columns = FXCollections.observableArrayList();
		
        TableColumn<Location,Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        idCol.setMinWidth(30);
        idCol.setMaxWidth(40);
        columns.add(idCol);

        TableColumn<Location,String> cityCel = new TableColumn<>("City");
        cityCel.setCellValueFactory(new PropertyValueFactory<>("city"));
        columns.add(cityCel);
        
        TableColumn<Location,String> postalNoCel = new TableColumn<>("Postal Number");
        postalNoCel.setCellValueFactory(new PropertyValueFactory<>("postalNo"));
        columns.add(postalNoCel);

        TableColumn<Location,String>  roadCel = new TableColumn<>("Road");
        roadCel.setCellValueFactory(new PropertyValueFactory<>("road"));
        columns.add(roadCel);
        
        modelTable.getColumns().setAll(columns);
        return modelTable;
	}
	protected TableView<Customer> setCustomerTable(){
		TableView<Customer> modelTable = getDefaultTable(observableData.get("Customer"));
        ObservableList<TableColumn<Customer,?>> columns = FXCollections.observableArrayList();
        
        TableColumn<Customer,Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        idCol.setMinWidth(30);
        idCol.setMaxWidth(40);
        columns.add(idCol);
        
        TableColumn<Customer,String> firstNameCel = new TableColumn<>("First Name");
        firstNameCel.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columns.add(firstNameCel);

        TableColumn<Customer,String> lastNameCel = new TableColumn<>("Last Name");
        lastNameCel.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columns.add(lastNameCel);

        TableColumn<Customer,String> emailCel = new TableColumn<>("E-mail");
        emailCel.setCellValueFactory(new PropertyValueFactory<>("email"));
        columns.add(emailCel);
        
        TableColumn<Customer,String> telephoneCel = new TableColumn<>("Telephone");
        telephoneCel.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        columns.add(telephoneCel);
        
        modelTable.getColumns().setAll(columns);
        return modelTable;
	}
	protected TableView<Operator> setOperatorTable(){
		TableView<Operator> modelTable = getDefaultTable(observableData.get("Operator"));
        ObservableList<TableColumn<Operator,?>> columns = FXCollections.observableArrayList();
        
        TableColumn<Operator,Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        idCol.setMinWidth(30);
        idCol.setMaxWidth(40);
        columns.add(idCol);
        
        TableColumn<Operator,String> firstNameCel = new TableColumn<>("First Name");
        firstNameCel.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columns.add(firstNameCel);

        TableColumn<Operator,String> lastNameCel = new TableColumn<>("Last Name");
        lastNameCel.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columns.add(lastNameCel);

        TableColumn<Operator,String> emailCel = new TableColumn<>("E-mail");
        emailCel.setCellValueFactory(new PropertyValueFactory<>("email"));
        columns.add(emailCel);
        
        TableColumn<Operator,String> usernameCel = new TableColumn<>("Username");
        usernameCel.setCellValueFactory(new PropertyValueFactory<>("username"));
        columns.add(usernameCel);

        TableColumn<Operator,String> passwordCel = new TableColumn<>("Password");
        passwordCel.setCellValueFactory(new PropertyValueFactory<>("password"));
        columns.add(passwordCel);
        
        modelTable.getColumns().setAll(columns);
        return modelTable;
	}
	protected TableView<Agency> setAgencyTable(){
		TableView<Agency> modelTable = getDefaultTable(observableData.get("Agency"));
        ObservableList<TableColumn<Agency,?>> columns = FXCollections.observableArrayList();
        
        TableColumn<Agency,Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        idCol.setMinWidth(30);
        idCol.setMaxWidth(40);
        columns.add(idCol);

        TableColumn<Agency,String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        columns.add(nameCol);

        TableColumn<Agency,String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(p->p.getValue().getLocation().cityProperty());
        columns.add(cityCol);

        TableColumn<Agency,String> postalNoCol = new TableColumn<>("Postal Number");
        postalNoCol.setCellValueFactory(p->p.getValue().getLocation().postalNoProperty());
        columns.add(postalNoCol);

        TableColumn<Agency,String> roadCol = new TableColumn<>("Road");
        roadCol.setCellValueFactory(p->p.getValue().getLocation().roadProperty());
        columns.add(roadCol);
        
        modelTable.getColumns().setAll(columns);
        return modelTable;
	}
	protected TableView<Car> setCarTable(){
		TableView<Car> modelTable = getDefaultTable(observableData.get("Car"));
        ObservableList<TableColumn<Car,?>> columns = FXCollections.observableArrayList();
        
		TableColumn<Car,Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        idCol.setMinWidth(30);
        idCol.setMaxWidth(40);
        columns.add(idCol);

        TableColumn<Car,String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        columns.add(modelCol);

        TableColumn<Car,String> licensePlateCol = new TableColumn<>("License Plate");
        licensePlateCol.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        licensePlateCol.setMinWidth(80);
        licensePlateCol.setMaxWidth(140);
        columns.add(licensePlateCol);

        TableColumn<Car,Integer> kmCol = new TableColumn<>("Km");
        kmCol.setCellValueFactory(new PropertyValueFactory<>("km"));
        kmCol.setMinWidth(60);
        kmCol.setMaxWidth(90);
        columns.add(kmCol);

        TableColumn<Car,String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(p->p.getValue().getCategory().endUserStringProperty());
        categoryCol.setMinWidth(50);
        categoryCol.setMaxWidth(90);
        columns.add(categoryCol);

        TableColumn<Car,String> agencyCol = new TableColumn<>("Agency");
        agencyCol.setCellValueFactory(p->p.getValue().getAgency().endUserStringProperty());
        columns.add(agencyCol);

        TableColumn<Car,String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(p->p.getValue().getStatus().endUserStringProperty());
        columns.add(statusCol);
        
        modelTable.getColumns().setAll(columns);
        return modelTable;
	}
}
