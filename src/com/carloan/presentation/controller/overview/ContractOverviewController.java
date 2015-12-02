package com.carloan.presentation.controller.overview;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Car;
import com.carloan.business.model.Contract;
import com.carloan.business.model.ContractType;
import com.carloan.business.model.Customer;
import com.carloan.business.model.Operator;
import com.carloan.business.model.Payment;
import com.carloan.business.model.Type;
import com.carloan.integration.util.DateFormatter;
import com.carloan.integration.util.DoubleFormatter;
import com.carloan.presentation.controller.overview.internal.FunctionalStringInterface;
import com.carloan.presentation.error_handle.ErrorHandler;
import com.carloan.presentation.loader.overview.OverviewLoader;
import com.carloan.presentation.loader.overview.OverviewLoaderFactory;
import com.carloan.service.agency.AgencyService;
import com.carloan.service.car.CarService;
import com.carloan.service.contract.ContractService;
import com.carloan.service.customer.CustomerService;
import com.carloan.service.exception.CommonServiceException;
import com.carloan.service.exception.UpdateContractException;
import com.carloan.service.login.LoginService;
import com.carloan.service.operator.OperatorService;
import com.carloan.service.payment.PaymentService;

public class ContractOverviewController extends OverviewController {
	@FXML
	private GridPane gridPane;
	@FXML
	private ButtonBar buttonBar;
	@FXML
	private Label openLabel;
	@FXML
	private Button cancelButton;
	
	private Button addPaymentButton;
	private Button confirmButton;
	private Button deleteButton;
	private Label numberLabel;
	private Label startDateLabel;
	private Label endDateLabel;
	private Label kmLabel;
	private Label kmLimitLabel;
	private Label amountLabel;
	private Label paymentLabel;
	private Label typeLabel;
	private Label paidLabel;
	private Label toPayLabel;
	private TextField kmField;
	private Hyperlink operatorLink;
	private Hyperlink customerLink;
	private Hyperlink carLink;
	private Hyperlink startAgencyLink;
	private Hyperlink endAgencyLink;
	private Hyperlink paymentLink;
	private DatePicker startDatePicker;
	private DatePicker endDatePicker;
	private MenuButton typeMenu;
	private MenuButton operatorMenu;
	private MenuButton customerMenu;
	private MenuButton carMenu;
	private MenuButton startAgencyMenu;
	private MenuButton endAgencyMenu;
	private MenuButton kmLimitMenu;
	private boolean isDateSetted;
	private boolean isAgencySetted;
	private Contract contract;
	private OverviewLoader loader;
	public ContractOverviewController() {}
	@FXML
	public void initialize(){
		isDateSetted = false;
		isAgencySetted = false;
		loader = OverviewLoaderFactory.getInstance();
		cancelButton.setOnAction(this::handleCancel);
	}
	@Override
	public void setModel(Object model) {
		contract = (Contract) model;
		if(contract.getId()==0){
			openLabel.setText("");
			
			numberLabel = new Label("Not defined");
			gridPane.add(numberLabel, 1, 0);
			
			startDatePicker = new DatePicker();
			setStartDatePicker("dd-MM-yyyy");
			startDatePicker.setValue(LocalDate.now());
			gridPane.add(startDatePicker, 1, 2);
			
			endDatePicker = new DatePicker();
			setEndDatePicker("dd-MM-yyyy");
			gridPane.add(endDatePicker, 1, 4);
						
			typeMenu = new MenuButton("Select a type");
			for(Type type : ContractService.getTypes())
				addMenuItem(typeMenu, ()->type.getId().toString(), ()->type.get());
			gridPane.add(typeMenu, 1, 6);
			
			kmLabel = new Label("0");
			gridPane.add(kmLabel, 1, 8);
			

			kmLimitMenu = new MenuButton("Select a type");
			MenuItem typeYes = new MenuItem("Yes");
			typeYes.setOnAction(v -> {
				kmLimitMenu.setText("Yes");
				kmLimitMenu.setId("0"); 
			});
			kmLimitMenu.getItems().add(typeYes);
			MenuItem typeNo = new MenuItem("No");
			typeNo.setOnAction(v -> {
				kmLimitMenu.setText("No");
				kmLimitMenu.setId("1"); 
			});
			kmLimitMenu.getItems().add(typeNo);
			gridPane.add(kmLimitMenu, 1, 10);
			
			startAgencyMenu = new MenuButton("Select a start agency");
			for(Agency agency : AgencyService.getAll())
				addStartAgencyItem(()->agency.getId().toString(), ()->agency.getName());
			gridPane.add(startAgencyMenu, 7, 0);

			endAgencyMenu = new MenuButton("Select an end agency");
			for(Agency agency : AgencyService.getAll())
				addMenuItem(endAgencyMenu, ()->agency.getId().toString(), ()->agency.getName());
			gridPane.add(endAgencyMenu, 7, 2);
			
			carMenu = new MenuButton("Select a car");
			carMenu.setDisable(true);
			gridPane.add(carMenu, 7, 4);
			
			if(LoginService.getCurrentOperator().isAdmin()){
				operatorMenu = new MenuButton("Select an operator");
				for(Operator operator : OperatorService.getAll())
					if(!operator.isAdmin())
						addMenuItem(operatorMenu, ()->operator.getId().toString(), ()->operator.toString());
				gridPane.add(operatorMenu, 7, 6);
			} else {
				operatorLink = new Hyperlink(LoginService.getCurrentOperator().getUsername());
				operatorLink.setOnAction(this::handleCurrentOperator);
				gridPane.add(operatorLink, 7, 6);
			}
			customerMenu = new MenuButton("Select a customer");
			for(Customer customer : CustomerService.getAll())
				addMenuItem(customerMenu, ()->customer.getId().toString(), ()->customer.toString());
			gridPane.add(customerMenu, 7, 8);
			
			
			paymentLabel = new Label("No payments avaible");
			gridPane.add(paymentLabel, 7, 10);
			
			amountLabel = new Label("No payments avaible");
			gridPane.add(amountLabel, 7, 12);
			
			paidLabel = new Label("No Payments avaible");
			gridPane.add(paidLabel, 7, 13);
			
			toPayLabel = new Label("No Payments avaible");
			gridPane.add(toPayLabel, 7, 14);
			
			confirmButton = new Button();
			confirmButton.setText("Add");
			confirmButton.setOnAction(this::handleAdd);
			buttonBar.getButtons().add(confirmButton);
			
			deleteButton = new Button();
			deleteButton.setText("Clear");
			deleteButton.setOnAction(this::handleClear);
			buttonBar.getButtons().add(deleteButton);
		} else {
			if(contract.isOpen()){
				openLabel.setText("open");
			} else {
				openLabel.setText("closed");
			}
			
			numberLabel = new Label(contract.getId().toString());
			gridPane.add(numberLabel, 1, 0);
			
			startDateLabel = new Label(DateFormatter.toString("dd-MM-yyyy",contract.getStartDate()));
			gridPane.add(startDateLabel, 1, 2);
			
			endDateLabel = new Label(DateFormatter.toString("dd-MM-yyyy",contract.getEndDate()));
			gridPane.add(endDateLabel, 1, 4);
			
			typeLabel = new Label(contract.getType().get());
			gridPane.add(typeLabel, 1, 6);
			
			kmLabel = new Label(Integer.toString(contract.getKm()));
			gridPane.add(kmLabel, 1, 8);
			
			kmLimitLabel = new Label(contract.isKmLimit() ? "yes" : "no");
			gridPane.add(kmLimitLabel, 1, 10);
			
			startAgencyLink = new Hyperlink(contract.getStartAgency().getName());
			startAgencyLink.setOnAction(this::handleAgency);
			gridPane.add(startAgencyLink, 7, 0);
			
			endAgencyLink = new Hyperlink(contract.getEndAgency().getName());
			endAgencyLink.setOnAction(this::handleAgency);
			gridPane.add(endAgencyLink, 7, 2);
			
			carLink = new Hyperlink(contract.getCar().getModel());
			carLink.setOnAction(this::handleCar);
			gridPane.add(carLink, 7, 4);
			
			operatorLink = new Hyperlink(contract.getOperator().toString());
			operatorLink.setOnAction(this::handleOperator);
			gridPane.add(operatorLink, 7, 6);
			
			customerLink = new Hyperlink(contract.getCustomer().toString());
			customerLink.setOnAction(this::handleCustomer);
			gridPane.add(customerLink, 7, 8);

			List<Payment> payments = PaymentService.getPaymentsByContract(contract);
			String payment = payments.size()==0 ? "No payments" : Integer.toString(payments.size())+" payments";
			paymentLink = new Hyperlink(payment);
			paymentLink.setOnAction(this::handlePayments);
			gridPane.add(paymentLink, 7, 10);
			
			double amount = contract.getAmount();
			amountLabel = new Label("€" + DoubleFormatter.toString(amount));
			gridPane.add(amountLabel, 7, 12);
			
			double paid = (contract.getAmountPaid());
			paidLabel = new Label("€" + DoubleFormatter.toString(paid));
			gridPane.add(paidLabel, 7, 13);
			
			double toPay = (contract.getAmount()-contract.getAmountPaid());
			toPayLabel = new Label("€" + DoubleFormatter.toString(toPay));
			gridPane.add(toPayLabel, 7, 14);
			
			if(LoginService.getCurrentOperator().isAdmin()){
				confirmButton = new Button();
				confirmButton.setText("Edit");
				confirmButton.setOnAction(this::handleEdit);
				buttonBar.getButtons().add(confirmButton);
				
				deleteButton = new Button();
				deleteButton.setText("Delete");
				deleteButton.setOnAction(this::handleDelete);
				buttonBar.getButtons().add(deleteButton);
			}
			addPaymentButton = new Button();
			addPaymentButton.setText("Add Payment");
			addPaymentButton.setOnAction(this::handleAddPayment);
			buttonBar.getButtons().add(addPaymentButton);
		}
	}
	private void handleEdit(ActionEvent event){
		startDatePicker = new DatePicker(contract.getStartDate());
		setStartDatePicker("dd-MM-yyyy");
		gridPane.add(startDatePicker, 1, 2);
		
		endDatePicker = new DatePicker(contract.getEndDate());
		setEndDatePicker("dd-MM-yyyy");
		gridPane.add(endDatePicker, 1, 4);
					
		typeMenu = new MenuButton();
		typeMenu.setText(contract.getType().get());
		typeMenu.setId(contract.getType().getId().toString());
		
		for(Type type : ContractService.getTypes())
			addMenuItem(typeMenu, ()->type.getId().toString(), ()->type.get());
		gridPane.add(typeMenu, 1, 6);
		
		kmField = new TextField(Integer.toString(contract.getKm()));

		String kmLimit = contract.isKmLimit() ? "Yes" : "No";
		String kmLimitId = contract.isKmLimit() ? "1" : "0";
		kmLimitMenu = new MenuButton();
		kmLimitMenu.setText(kmLimit);
		kmLimitMenu.setId(kmLimitId);
		
		MenuItem typeYes = new MenuItem("Yes");
		typeYes.setOnAction(v -> {
			kmLimitMenu.setText("Yes");
			kmLimitMenu.setId("0"); 
		});
		kmLimitMenu.getItems().add(typeYes);
		MenuItem typeNo = new MenuItem("No");
		typeNo.setOnAction(v -> {
			kmLimitMenu.setText("No");
			kmLimitMenu.setId("1"); 
		});
		kmLimitMenu.getItems().add(typeNo);
		gridPane.add(kmLimitMenu, 1, 10);
		
		startAgencyMenu = new MenuButton();
		startAgencyMenu.setText(contract.getStartAgency().getName());
		startAgencyMenu.setId(contract.getStartAgency().getId().toString());
		for(Agency agency : AgencyService.getAll())
			addStartAgencyItem(()->agency.getId().toString(), ()->agency.getName());
		gridPane.add(startAgencyMenu, 7, 0);

		endAgencyMenu = new MenuButton();
		endAgencyMenu.setText(contract.getEndAgency().getName());
		endAgencyMenu.setId(contract.getEndAgency().getId().toString());
		for(Agency agency : AgencyService.getAll())
			addMenuItem(endAgencyMenu, ()->agency.getId().toString(), ()->agency.getName());
		gridPane.add(endAgencyMenu, 7, 2);
		
		carMenu = new MenuButton();
		carMenu.setText(contract.getCar().getModel());
		carMenu.setId(contract.getCar().getId().toString());
		for(Car car : CarService.getAll()){
			if(car.getAgency().getNumber() == Integer.parseInt(startAgencyMenu.getId())){
				addMenuItem(carMenu, ()->car.getId().toString(), ()->car.getModel());
			}
		}
		gridPane.add(carMenu, 7, 4);

		if(LoginService.getCurrentOperator().isAdmin()){
			operatorMenu = new MenuButton("Select an operator");
			operatorMenu.setText(contract.getOperator().getUsername());
			operatorMenu.setId(contract.getOperator().getId().toString());
			for(Operator operator : OperatorService.getAll())
				if(!operator.isAdmin())
					addMenuItem(operatorMenu, ()->operator.getId().toString(), ()->operator.toString());
			gridPane.add(operatorMenu, 7, 6);
		} else {
			operatorLink = new Hyperlink(contract.getOperator().getUsername());
			operatorLink.setOnAction(this::handleCurrentOperator);
			gridPane.add(operatorLink, 7, 6);
		}
		
		customerMenu = new MenuButton();
		customerMenu.setText(contract.getCustomer().toString());
		customerMenu.setId(contract.getCustomer().getId().toString());
		for(Customer customer : CustomerService.getAll())
			addMenuItem(customerMenu, ()->customer.getId().toString(), ()->customer.toString());
		gridPane.add(customerMenu, 7, 8);
		
		confirmButton.setText("Ok");
		confirmButton.setOnAction(this::handleAddUpdate);
		
		deleteButton.setText("Clear");
		deleteButton.setOnAction(this::handleClear);
	}
	private void handleDelete(ActionEvent event){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Agency");
		alert.setHeaderText("Are you sure to delete this contract?");
		alert.setContentText("Press ok to confirm");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			ContractService.delete(contract);
		}
		stage.close();
	}
	private void handleAddUpdate(ActionEvent event){
		try {
			checkMenus();
			ContractType type = ContractService.getType(Integer.parseInt(typeMenu.getId()));
			Agency startAgency = AgencyService.get(Integer.parseInt(startAgencyMenu.getId()));
			Agency endAgency = AgencyService.get(Integer.parseInt(endAgencyMenu.getId()));
			Car car = CarService.get(Integer.parseInt(carMenu.getId()));
			Customer customer = CustomerService.get(Integer.parseInt(customerMenu.getId()));
			Operator operator = LoginService.getCurrentOperator().isAdmin() ? OperatorService.get(Integer.parseInt(operatorMenu.getId())) : LoginService.getCurrentOperator();

			contract.setStartDate(startDatePicker.getValue());
			contract.setEndDate(endDatePicker.getValue());
			contract.setKm(Integer.parseInt(kmField.getText()));
			contract.setKmLimit(kmLimitMenu.getText().equals("Yes"));
			contract.setType(type);
			contract.setStartAgency(startAgency);
			contract.setEndAgency(endAgency);
			contract.setOperator(operator);
			contract.setCustomer(customer);
			contract.setCar(car);
		
			ContractService.update(contract);
			stage.close();
		} catch (CommonServiceException e) {
			ErrorHandler.handle(e, "Error while update contract");

		}
	}
	private void setStartDatePicker(String pattern){
		startDatePicker.setConverter(new StringConverter<LocalDate>() {
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
		startDatePicker.setPromptText(pattern.toLowerCase());
		startDatePicker.setOnAction((p)->{
			endDatePicker.setValue(startDatePicker.getValue().plusDays(1));
		});
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
	}
	private void setEndDatePicker(String pattern){
		endDatePicker.setConverter(new StringConverter<LocalDate>() {
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
		endDatePicker.setPromptText(pattern.toLowerCase());
		endDatePicker.setOnAction((p)->{
			isDateSetted = true;
			if(isAgencySetted){
				carMenu.setDisable(false);
				carMenu.setText("Select a car");
				carMenu.getItems().clear();
				for(Car car : CarService.getAvaibleByAgency(Integer.parseInt(startAgencyMenu.getId())))
					if(CarService.isAvaible(car, startDatePicker.getValue(), endDatePicker.getValue()))
						addMenuItem(carMenu, ()->car.getId().toString(), ()->car.getModel());
			}
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
	}
	private void handleAdd(ActionEvent event){
		try {
			checkMenus();
			ContractType type = ContractService.getType(Integer.parseInt(typeMenu.getId()));
			Agency startAgency = AgencyService.get(Integer.parseInt(startAgencyMenu.getId()));
			Agency endAgency = AgencyService.get(Integer.parseInt(endAgencyMenu.getId()));
			Car car = CarService.get(Integer.parseInt(carMenu.getId()));
			Customer customer = CustomerService.get(Integer.parseInt(customerMenu.getId()));
			Operator operator = LoginService.getCurrentOperator().isAdmin() ? OperatorService.get(Integer.parseInt(operatorMenu.getId())) : LoginService.getCurrentOperator();
			
			contract.setStartDate(startDatePicker.getValue());
			contract.setEndDate(endDatePicker.getValue());
			contract.setKm(0);
			contract.setKmLimit(kmLimitMenu.getText().equals("Yes"));
			contract.setType(type);
			contract.setStartAgency(startAgency);
			contract.setEndAgency(endAgency);
			contract.setOperator(operator);
			contract.setCustomer(customer);
			contract.setCar(car);
		
			ContractService.create(contract);
			stage.close();
		} catch (CommonServiceException e) {
			ErrorHandler.handle(e, "Error while create contract");

		}
	}
	private void checkMenus() throws CommonServiceException{
		String errorMessage = "";
		
		if(typeMenu.getId() == null)
			errorMessage += "Please select a type\n";
		if(startAgencyMenu.getId() == null)
			errorMessage += "Please select a start agency\n";
		if(endAgencyMenu.getId() == null)
			errorMessage += "Please select an end egency\n";
		if(carMenu.getId() == null)
			errorMessage += "Please select a car\n";
		if(customerMenu.getId() == null)
			errorMessage += "Please select a customer\n";
		if(operatorMenu.getId() == null && LoginService.getCurrentOperator().isAdmin())
			errorMessage += "Please select an operator\n";
		if(!errorMessage.equals(""))
			throw new UpdateContractException(errorMessage);
	}
	private void handleClear(ActionEvent event){
			startDatePicker.setValue(LocalDate.now());
			typeMenu.setText("Select a type");
			kmLabel = new Label("0");
			kmLimitMenu.setText("Select a type");
			startAgencyMenu.setText("Select a start agency");
			endAgencyMenu.setText("Select an end agency");
			carMenu.setText("Select a car");
			customerMenu.setText("Select a customer");
			paymentLabel.setText("No payments avaible");
			amountLabel.setText("No payments avaible");

			if(LoginService.getCurrentOperator().isAdmin())
				operatorMenu.setText("Select an operator");
	}
	private void handleAgency(ActionEvent event){
		loader.setModel(contract.getStartAgency());
		loader.load();
	}
	private void handleAddPayment(ActionEvent event){
		Payment payment = new Payment();
		payment.setContract(contract);
		loader.setModel(payment);
		loader.load();
		try {
			PaymentService.create(payment);
			String paymentMessage = Integer.toString(PaymentService.getPaymentsByContract(contract).size()) +" payments";
			paymentLink.setText(paymentMessage);
		} catch (CommonServiceException e) {
			Alert errorAlert = new Alert(AlertType.WARNING);
			errorAlert.initOwner(stage);
			errorAlert.setTitle("Error while creating payment");
			errorAlert.setContentText(e.getLocalizedMessage());
			errorAlert.showAndWait();		}
	}
	private void handleCar(ActionEvent event){
		loader.setModel(contract.getCar());
		loader.load();
	}
	private void handleOperator(ActionEvent event){
		loader.setModel(contract.getOperator());
		loader.load();
	}
	private void handleCurrentOperator(ActionEvent event){
		loader.setModel(LoginService.getCurrentOperator());
		loader.load();
	}
	private void handleCustomer(ActionEvent event){
		loader.setModel(contract.getCustomer());
		loader.load();
	}
	private void handlePayments(ActionEvent event){
		loader.setModel(PaymentService.getPaymentsByContract(contract));
		loader.load();
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
	private void addStartAgencyItem(FunctionalStringInterface id, FunctionalStringInterface text){
		MenuItem item = new MenuItem();
		item.setText(text.doSomething());
		item.setId(id.doSomething());
		startAgencyMenu.getItems().add(item);
		item.setOnAction(v -> {
					startAgencyMenu.setText(item.getText());
					startAgencyMenu.setId(item.getId());
					isAgencySetted = true;
					if(isDateSetted){
						carMenu.setDisable(false);
						carMenu.setText("Select a car");
						carMenu.getItems().clear();
						for(Car car : CarService.getAvaibleByAgency(Integer.parseInt(item.getId())))
							if(CarService.isAvaible(car, startDatePicker.getValue(), endDatePicker.getValue()))
								addMenuItem(carMenu, ()->car.getId().toString(), ()->car.getModel());
					}
				});
	}
	private void handleCancel(ActionEvent event){
		stage.close();
	}
}
