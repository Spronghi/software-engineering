package com.carloan.presentation.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Car;
import com.carloan.business.model.Contract;
import com.carloan.business.model.Customer;
import com.carloan.business.model.Type;
import com.carloan.integration.util.DateFormatter;
import com.carloan.presentation.controller.overview.internal.FunctionalStringInterface;
import com.carloan.presentation.error_handle.ErrorHandler;
import com.carloan.presentation.loader.overview.OverviewLoader;
import com.carloan.presentation.loader.overview.OverviewLoaderFactory;
import com.carloan.service.agency.AgencyService;
import com.carloan.service.car.CarService;
import com.carloan.service.contract.ContractService;
import com.carloan.service.customer.CustomerService;
import com.carloan.service.exception.CommonServiceException;
import com.carloan.service.login.LoginService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class OpenContractViewController extends Controller{
	@FXML
	private AnchorPane center;
	@FXML
	private Label comandLabel;
	@FXML
	private ButtonBar buttonBar;
	
	private Button backButton;
	private Button continueButton;
	private Button cancelButton;
	private Contract contract;

	public OpenContractViewController() {
		
	}
	@FXML
	private void initialize(){
		contract = new Contract();
		contract.setOperator(LoginService.getCurrentOperator());
		
		backButton = new Button("Back");
		
		continueButton = new Button("Continue");
		buttonBar.getButtons().add(continueButton);

		cancelButton = new Button("Cancel");
		cancelButton.setOnAction(this::handleCancel);
		buttonBar.getButtons().add(cancelButton);
		
		askForCustomer();
	}
	private void askForCustomer(){
		center.getChildren().clear();
		comandLabel.setText("Select a customer or create a new one");
		
		TableView<Customer> customerTable = new TableView<>();
		setCustomerTable(customerTable);
		
		Button addButton = new Button("Add");
		buttonBar.getButtons().add(0,addButton);
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Customer customer = new Customer();
				contract.setCustomer(customer);
				handleDetails(customer);
				TableView<Customer> customerTable = new TableView<>();
				setCustomerTable(customerTable);
			}
		});
		continueButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(customerTable.getSelectionModel().getSelectedItem() != null){
					contract.setCustomer(customerTable.getSelectionModel().getSelectedItem());
					buttonBar.getButtons().remove(addButton);
					askForAgencies();
				}
			}
		});
	}
	private void askForAgencies(){
		center.getChildren().clear();
		
		GridPane gridPane = new GridPane();
		setAnchors(gridPane, 0, 0, 0, 0);
		center.getChildren().add(gridPane);
		
		comandLabel.setText("Select start and end agencies");

		Label startAgencyLabel = new Label("Choose start agency: ");
		MenuButton startAgencyMenu = new MenuButton();
		startAgencyMenu.setPrefWidth(180);
		for(Agency agency : AgencyService.getAll())
			addMenuItem(startAgencyMenu, ()->agency.getId().toString(), ()->agency.getName());
		
		gridPane.add(startAgencyLabel, 0, 0);
		gridPane.add(startAgencyMenu, 1, 0);
		GridPane.setMargin(startAgencyLabel, new Insets(5, 10, 5, 10));
		GridPane.setMargin(startAgencyMenu, new Insets(5, 10, 5, 10));
		
		Label endAgencyLabel = new Label("Choose end agency: ");
		MenuButton endAgencyMenu = new MenuButton();
		endAgencyMenu.setPrefWidth(180);
		for(Agency agency : AgencyService.getAll())
			addMenuItem(endAgencyMenu, ()->agency.getId().toString(), ()->agency.getName());
		
		gridPane.add(endAgencyLabel, 0, 1);
		gridPane.add(endAgencyMenu, 1, 1);
		GridPane.setMargin(endAgencyLabel, new Insets(5, 10, 5, 10));
		GridPane.setMargin(endAgencyMenu, new Insets(5, 10, 5, 10));
		
		if(!buttonBar.getButtons().contains(backButton))
			buttonBar.getButtons().add(0,backButton);
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				buttonBar.getButtons().remove(backButton);
				askForCustomer();
			}
		});
		continueButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				contract.setStartAgency(AgencyService.get(Integer.parseInt(startAgencyMenu.getId())));
				contract.setEndAgency(AgencyService.get(Integer.parseInt(endAgencyMenu.getId())));
				askForDates();
			}
		});
	}
	private void askForDates(){
		center.getChildren().clear();
		
		GridPane gridPane = new GridPane();
		center.getChildren().add(gridPane);
		
		comandLabel.setText("Select start and end dates");
	
		Label startDateLabel = new Label("Choose start date: ");
		DatePicker startDatePicker = new DatePicker();
		setDatePickerFormatter(startDatePicker, "dd-MM-yyyy");
		startDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
	            @Override
	            public DateCell call(final DatePicker datePicker) {
	                return new DateCell() {
	                    @Override
	                    public void updateItem(LocalDate item, boolean empty) {
	                        super.updateItem(item, empty);
	                        if (item.isBefore(LocalDate.now())){
	                                setDisable(true);
	                                setStyle("-fx-background-color: #ffc0cb;");
	                        }
	                    }
	                };
	            }
	    	});
		gridPane.add(startDateLabel, 0, 0);
		gridPane.add(startDatePicker, 1, 0);
		GridPane.setMargin(startDateLabel, new Insets(5, 10, 5, 10));
		GridPane.setMargin(startDatePicker, new Insets(5, 10, 5, 10));
		
		Label endDateLabel = new Label("Choose end date: ");
		DatePicker endDatePicker = new DatePicker();
		setDatePickerFormatter(endDatePicker, "dd-MM-yyyy");
		startDatePicker.setOnAction((p)->{
			endDatePicker.setValue(startDatePicker.getValue().plusDays(1));
		});
		endDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(startDatePicker.getValue().plusDays(1))){
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
    	});
		gridPane.add(endDateLabel, 0, 1);
		gridPane.add(endDatePicker, 1, 1);
		GridPane.setMargin(endDateLabel, new Insets(5, 10, 5, 10));
		GridPane.setMargin(endDatePicker, new Insets(5, 10, 5, 10));
		
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				askForAgencies();
			}
		});
		continueButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				contract.setStartDate(startDatePicker.getValue());
				contract.setEndDate(endDatePicker.getValue());
				askForCar();
			}
		});
	}
	private void askForCar(){
		comandLabel.setText("Select a car and click continue");
		center.getChildren().clear();

		TableView<Car> carTable = new TableView<>();
		setCarTable(carTable);
        setAnchors(carTable, 0, 0, 0, 0);
		center.getChildren().add(carTable);
		
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				askForDates();
			}
		});
		continueButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(carTable.getSelectionModel().getSelectedItem() != null){
					contract.setCar(carTable.getSelectionModel().getSelectedItem());
					askForDetails();
				}
			}
		});
	}
	private void askForDetails(){
		center.getChildren().clear();

		GridPane gridPane = new GridPane();
		setAnchors(gridPane, 0, 0, 0, 0);
		center.getChildren().add(gridPane);
		
		comandLabel.setText("Select type and limit");

		Label typeLabel = new Label("Choose a contract type: ");
		MenuButton typeMenu = new MenuButton();
		typeMenu.setPrefWidth(180);
		for(Type type : ContractService.getTypes())
			addMenuItem(typeMenu, ()->type.getId().toString(), ()->type.get());
		
		gridPane.add(typeLabel, 0, 0);
		gridPane.add(typeMenu, 1, 0);
		GridPane.setMargin(typeLabel, new Insets(5, 10, 5, 10));
		GridPane.setMargin(typeMenu, new Insets(5, 10, 5, 10));
		
		Label kmLimitLabel = new Label("Do you want km limit?");
		MenuButton kmLimitMenu = new MenuButton();
		kmLimitMenu.setPrefWidth(180);
		MenuItem yes = new MenuItem("Yes");
		kmLimitMenu.getItems().add(yes);
		yes.setOnAction(v -> {
			kmLimitMenu.setId("0");
			kmLimitMenu.setText(yes.getText());
		});

		MenuItem no = new MenuItem("No");
		kmLimitMenu.getItems().add(no);
		no.setOnAction(v -> {
			kmLimitMenu.setId("1");
			kmLimitMenu.setText(no.getText());
		});		
		gridPane.add(kmLimitLabel, 0, 1);
		gridPane.add(kmLimitMenu, 1, 1);
		GridPane.setMargin(kmLimitLabel, new Insets(5, 10, 5, 10));
		GridPane.setMargin(kmLimitMenu, new Insets(5, 10, 5, 10));

		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				askForCar();
			}
		});
		continueButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(typeMenu.getId()!=null && kmLimitMenu.getId()!=null){
					contract.setType(ContractService.getType(Integer.parseInt(typeMenu.getId())));
					contract.setKmLimit(kmLimitMenu.getText().equals("Yes"));
					finish();
				}
			}
		});
	}
	private void finish(){
		center.getChildren().clear();
		
		comandLabel.setText("Check data");
		Label detailsLabel = new Label("Your contract is almost open! Please check your details");
		center.getChildren().add(detailsLabel);
		AnchorPane.setTopAnchor(detailsLabel, 0.0);
		AnchorPane.setLeftAnchor(detailsLabel, 0.0);
		
		GridPane gridPane = new GridPane();
		center.getChildren().add(gridPane);
		setAnchors(gridPane, 20, 0, 0, 0);
		
		Hyperlink operator = new Hyperlink(contract.getOperator().endUserStringProperty().get());
		setDetailsAction(operator, contract.getOperator());
		gridPane.add(new Label("Operator:"), 0, 0);
		gridPane.add(operator, 1, 0);
		
		Hyperlink customer = new Hyperlink(contract.getCustomer().endUserStringProperty().get());
		setDetailsAction(customer, contract.getCustomer());
		gridPane.add(new Label("Customer:"), 0, 1);
		gridPane.add(customer, 1, 1);
		
		Hyperlink car = new Hyperlink(contract.getCar().endUserStringProperty().get());
		setDetailsAction(car, contract.getCar());
		gridPane.add(new Label("Car:"), 0, 2);
		gridPane.add(car, 1, 2);
		
		Label startDate = new Label(DateFormatter.toString("dd-MM-yyyy",contract.getStartDate()));
		gridPane.add(new Label("Start date:"), 0, 3);
		gridPane.add(startDate, 1, 3);

		Label endDate = new Label(DateFormatter.toString("dd-MM-yyyy",contract.getEndDate()));
		gridPane.add(new Label("End date:"), 0, 4);
		gridPane.add(endDate, 1, 4);
		
		Hyperlink startAgency = new Hyperlink(contract.getStartAgency().endUserStringProperty().get());
		setDetailsAction(startAgency, contract.getStartAgency());
		gridPane.add(new Label("Start agency:"), 0, 5);
		gridPane.add(startAgency, 1, 5);
		
		Hyperlink endAgency = new Hyperlink(contract.getEndAgency().endUserStringProperty().get());
		setDetailsAction(endAgency, contract.getEndAgency());
		gridPane.add(new Label("End agency:"), 0, 6);
		gridPane.add(endAgency, 1, 6);
		
		String kmLimitStr = contract.isKmLimit() ? "Yes" : "No";
		Label kmLimit = new Label(kmLimitStr);
		gridPane.add(new Label("Km limit:"), 0, 7);
		gridPane.add(kmLimit, 1, 7);
		
		Label type = new Label(contract.getType().get());
		gridPane.add(new Label("Type:"), 0, 8);
		gridPane.add(type, 1, 8);
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				askForDetails();
			}
		});
		continueButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					contract.getCar().setStatus(CarService.getAllStatus().stream().filter(p->p.get().equals("hired")).findFirst().get());
					ContractService.create(contract);
					System.out.println(contract.toString());
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Contract created");
					alert.setHeaderText("Contract created!");
					alert.setContentText("Contract created successfully");
					alert.showAndWait();
					stage.close();
				} catch (CommonServiceException e) {
					ErrorHandler.handle(e, "Error while create contract");
				}
			}
		});
	}
	private <T> void setDetailsAction(ButtonBase button, T item){
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleDetails(item);
			}			
		});
	}
	private void setCarTable(TableView<Car> carTable){
		carTable.setItems(FXCollections.observableArrayList(CarService.getAvaibleByAgency(contract.getStartAgency().getNumber())));
		carTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<TableColumn<Car,?>> columns = FXCollections.observableArrayList();
        
        TableColumn<Car,String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        columns.add(modelCol);

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

        carTable.getColumns().setAll(columns);
        
        carTable.setRowFactory( t -> {
		    TableRow<Car> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (!row.isEmpty())) {
		            handleDetails(row.getItem());
		        }
		    });
		    return row;
		});
	}
	private void setCustomerTable(TableView<Customer> customerTable){
		customerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		customerTable.setItems(FXCollections.observableArrayList(CustomerService.getAll()));
		ObservableList<TableColumn<Customer,?>> columns = FXCollections.observableArrayList();
        setAnchors(customerTable, 0, 0, 0, 0);
		center.getChildren().add(customerTable);
		
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
        
        customerTable.getColumns().setAll(columns);
        
        customerTable.setRowFactory( t -> {
		    TableRow<Customer> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (!row.isEmpty())) {
		            handleDetails(row.getItem());
		        }
		    });
		    return row;
		});
	}
	private void handleDetails(Object row){
		OverviewLoader loader = OverviewLoaderFactory.getInstance();
		loader.setModel(row);
		loader.initOwner(stage);
		loader.load();
	}
	private void setAnchors(Node node, double top, double bottom, double left, double right){
		AnchorPane.setBottomAnchor(node, bottom);
		AnchorPane.setTopAnchor(node, top);
		AnchorPane.setLeftAnchor(node, left);
		AnchorPane.setRightAnchor(node, right);
	}
	private void setDatePickerFormatter(DatePicker datePicker, String pattern){
		datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
               return (date != null) ? dateFormatter.format(date) : "";
            }
            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, dateFormatter) : null;
            }
        });
		datePicker.setPromptText(pattern.toLowerCase());
	}
	private void addMenuItem(MenuButton menu, FunctionalStringInterface id, FunctionalStringInterface text){
		MenuItem item = new MenuItem();
		item.setText(text.doSomething());
		item.setId(id.doSomething());
		menu.getItems().add(item);
		item.setOnAction(v -> {
			menu.setText(item.getText());
			menu.setId(item.getId()); 
		});
	}
	private void handleCancel(ActionEvent event){
		stage.close();
	}
}
