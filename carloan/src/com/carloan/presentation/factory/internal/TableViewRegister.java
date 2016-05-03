package com.carloan.presentation.factory.internal;

import java.util.Map;
import java.util.Set;

import com.carloan.presentation.control.ModelControl;
import com.carloan.presentation.control.TableControl;
import com.carloan.presentation.factory.FXFactory;
import com.carloan.presentation.factory.FXLoader;
import com.carloan.presentation.factory.FXUtil;
import com.carloan.service.ServiceFactory;
import com.carloan.service.control.PermissionControl;
import com.carloan.service.control.ServiceControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Car;
import com.carloan.business.model.Contract;
import com.carloan.business.model.Customer;
import com.carloan.business.model.Operator;
import com.carloan.business.model.PriceCategory;
import com.carloan.integration.util.DateFormatter;
import com.carloan.integration.util.DoubleFormatter;

public class TableViewRegister extends TableControl{
	private Map<String, TableCommand> tableRegister;

    private interface TableCommand {
        TableView<?> getTable();
    }

    public TableViewRegister(){
		tableRegister = new java.util.HashMap<>();
        if(ServiceFactory.getUtilFactory().getPermissionService().getPermissions(Agency.class).contains(PermissionControl.VIEW))
            tableRegister.put(AGENCY,this::setAgencyTable);
        if(ServiceFactory.getUtilFactory().getPermissionService().getPermissions(Car.class).contains(PermissionControl.VIEW))
            tableRegister.put(CAR, this::setCarTable);
        if(ServiceFactory.getUtilFactory().getPermissionService().getPermissions(Contract.class).contains(PermissionControl.VIEW))
            tableRegister.put(CONTRACT, this::setContractTable);
        if(ServiceFactory.getUtilFactory().getPermissionService().getPermissions(Customer.class).contains(PermissionControl.VIEW))
            tableRegister.put(CUSTOMER, this::setCustomerTable);
        if(ServiceFactory.getUtilFactory().getPermissionService().getPermissions(Operator.class).contains(PermissionControl.VIEW))
            tableRegister.put(OPERATOR, this::setOperatorTable);
        if(ServiceFactory.getUtilFactory().getPermissionService().getPermissions(PriceCategory.class).contains(PermissionControl.VIEW))
            tableRegister.put(PRICE_CATEGORY, this::setPriceCategoryTable);
	}

    @SuppressWarnings("unchecked")
	public <T> TableView<T> getTable(String key){
        return (TableView<T>) tableRegister.get(key).getTable();
    }
    public Set<String> getCategories(){
        return tableRegister.keySet();
    }
	private TableView<PriceCategory> setPriceCategoryTable(){
		TableView<PriceCategory> modelTable = FXFactory.createTableView(PriceCategory.class, ServiceFactory.getModelFactory().<PriceCategory>getInstance(ServiceControl.PRICE_CATEGORY).getAll());
        ObservableList<TableColumn<PriceCategory,?>> columns = FXCollections.observableArrayList();

        TableColumn<PriceCategory,String> categoryCel = new TableColumn<>("Category");
        categoryCel.setCellValueFactory(p-> FXUtil.toObservableValue(p.getValue().get()));
        columns.add(categoryCel);

        TableColumn<PriceCategory,String> baseRateLimitCel = new TableColumn<>("Base Rate Limited");
        baseRateLimitCel.setCellValueFactory(p->FXUtil.toObservableValue("€ "+DoubleFormatter.toString(p.getValue().getBaseRateLimit())));
        columns.add(baseRateLimitCel);

        TableColumn<PriceCategory,String> baseRateUnlimitCel = new TableColumn<>("Base Rate Unlimited");
        baseRateUnlimitCel.setCellValueFactory(p->FXUtil.toObservableValue("€ "+DoubleFormatter.toString(p.getValue().getBaseRateUnlimit())));
        columns.add(baseRateUnlimitCel);

        TableColumn<PriceCategory,String> kmRateCel = new TableColumn<>("Km Rate");
        kmRateCel.setCellValueFactory(p->FXUtil.toObservableValue("€ "+DoubleFormatter.toString(p.getValue().getKmRate())));
        columns.add(kmRateCel);

        modelTable.getColumns().setAll(columns);
        modelTable.setPlaceholder(new Label("No price category present"));

        return modelTable;
	}
	private TableView<Contract> setContractTable(){
        int agencyId = ServiceFactory.getUtilFactory().getLoginService().getCurrentOperator().getAgency().getId();
        TableView<Contract> modelTable = FXFactory.createTableView(Contract.class, ServiceFactory.getModelFactory().<Contract>getOrderedInstance(ServiceControl.CONTRACT).getAllBy(agencyId));
        ObservableList<TableColumn<Contract,?>> columns = FXCollections.observableArrayList();

        TableColumn<Contract,Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        idCol.setMinWidth(30);
        idCol.setMaxWidth(40);
        columns.add(idCol);

        TableColumn<Contract,String> openCel =new TableColumn<>("Open");
        openCel.setCellValueFactory(p->FXUtil.toObservableValue(p.getValue().getOpen()));
        openCel.setMinWidth(70);
        openCel.setMaxWidth(80);
        columns.add(openCel);
        
        TableColumn<Contract,String> startDateCel = new TableColumn<>("Start Date");
        startDateCel.setCellValueFactory(p->FXUtil.toObservableValue(DateFormatter.toString("dd-MM-yyyy", p.getValue().getStartDate())));
        startDateCel.setMinWidth(100);
        startDateCel.setMaxWidth(120);
        columns.add(startDateCel);
		
        TableColumn<Contract,String> endDateCel = new TableColumn<>("End Date");
        endDateCel.setCellValueFactory(p->FXUtil.toObservableValue(DateFormatter.toString("dd-MM-yyyy", p.getValue().getEndDate())));
        endDateCel.setMinWidth(100);
        endDateCel.setMaxWidth(120);
        columns.add(endDateCel);

        TableColumn<Contract,String> carCel = new TableColumn<>("Car");
        carCel.setCellValueFactory(p->FXUtil.toObservableValue(p.getValue().getCar().getModel()));
        columns.add(carCel);

        TableColumn<Contract,String> customerCel = new TableColumn<>("Customer");
        customerCel.setCellValueFactory(p->FXUtil.toObservableValue(p.getValue().getCustomer().endUserString()));
        columns.add(customerCel);

        TableColumn<Contract,String> amountCel = new TableColumn<>("Amount");
        amountCel.setCellValueFactory(p->FXUtil.toObservableValue("€ "+DoubleFormatter.toString(p.getValue().getAmount())));
        columns.add(amountCel);

        modelTable.getColumns().setAll(columns);

        Hyperlink emptyText = new Hyperlink("No contract present");
        emptyText.setOnAction(e-> {
            Contract model = new Contract();
            if(FXLoader.loadOverview(model, ModelControl.NEW)) {
                modelTable.getItems().add(model);
            }
        });
        modelTable.setPlaceholder(emptyText);

        return modelTable;
	}
	private TableView<Customer> setCustomerTable(){
		TableView<Customer> modelTable = FXFactory.createTableView(Customer.class, ServiceFactory.getModelFactory().<Customer>getInstance(ServiceControl.CUSTOMER).getAll());
        ObservableList<TableColumn<Customer,?>> columns = FXCollections.observableArrayList();
        
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

        Hyperlink emptyText = new Hyperlink("No customer present");
        emptyText.setOnAction(e-> {
            Customer model = new Customer();
            if(FXLoader.loadOverview(model, ModelControl.NEW)) {
                modelTable.getItems().add(model);
            }
        });
        modelTable.setPlaceholder(emptyText);

        return modelTable;
	}
	private TableView<Operator> setOperatorTable(){
		TableView<Operator> modelTable = FXFactory.createTableView(Operator.class, ServiceFactory.getModelFactory().<Operator>getInstance(ServiceControl.OPERATOR).getAll());
        ObservableList<TableColumn<Operator,?>> columns = FXCollections.observableArrayList();

        TableColumn<Operator,Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
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

        modelTable.getColumns().setAll(columns);

        Hyperlink emptyText = new Hyperlink("No operator present");
        emptyText.setOnAction(e-> {
            Operator model = new Operator();
            if(FXLoader.loadOverview(model, ModelControl.NEW)) {
                modelTable.getItems().add(model);
            }
        });
        modelTable.setPlaceholder(emptyText);

        return modelTable;
	}
	private TableView<Agency> setAgencyTable(){
		TableView<Agency> modelTable = FXFactory.createTableView(Agency.class, ServiceFactory.getModelFactory().<Agency>getInstance(ServiceControl.AGENCY).getAll());
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
        cityCol.setCellValueFactory(p->FXUtil.toObservableValue(p.getValue().getLocation().getCity()));
        columns.add(cityCol);

        TableColumn<Agency,String> postalNoCol = new TableColumn<>("Postal Number");
        postalNoCol.setCellValueFactory(p->FXUtil.toObservableValue(p.getValue().getLocation().getPostalNo()));
        columns.add(postalNoCol);

        TableColumn<Agency,String> roadCol = new TableColumn<>("Road");
        roadCol.setCellValueFactory(p->FXUtil.toObservableValue(p.getValue().getLocation().getRoad()));
        columns.add(roadCol);
        
        modelTable.getColumns().setAll(columns);

        Hyperlink emptyText = new Hyperlink("No agency present");
        emptyText.setOnAction(e-> {
            Agency model = new Agency();
            if(FXLoader.loadOverview(model, ModelControl.NEW)) {
                modelTable.getItems().add(model);
            }
        });
        modelTable.setPlaceholder(emptyText);

        return modelTable;
	}
	private TableView<Car> setCarTable(){
        int agencyId = ServiceFactory.getUtilFactory().getLoginService().getCurrentOperator().getAgency().getId();
        TableView<Car> modelTable = FXFactory.createTableView(Car.class, ServiceFactory.getModelFactory().<Car>getOrderedInstance(ServiceControl.CAR).getAllBy(agencyId));

        ObservableList<TableColumn<Car,?>> columns = FXCollections.observableArrayList();

        TableColumn<Car,String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        columns.add(modelCol);

        TableColumn<Car,String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(p->FXUtil.toObservableValue(p.getValue().getCategory().get()));
        categoryCol.setMinWidth(50);
        categoryCol.setMaxWidth(90);
        columns.add(categoryCol);

        TableColumn<Car,String> agencyCol = new TableColumn<>("Agency");
        agencyCol.setCellValueFactory(p->FXUtil.toObservableValue(p.getValue().getAgency().getName()));
        columns.add(agencyCol);

        TableColumn<Car,String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(p->FXUtil.toObservableValue(p.getValue().getStatus().get()));
        columns.add(statusCol);
        
        modelTable.getColumns().setAll(columns);

        Hyperlink emptyText = new Hyperlink("No car present");
        emptyText.setOnAction(e-> {
            Car model = new Car();
            if(FXLoader.loadOverview(model, ModelControl.NEW)) {
                modelTable.getItems().add(model);
            }
        });
        if(ServiceFactory.getUtilFactory().getLoginService().getCurrentOperator().isAdmin())
            modelTable.setPlaceholder(emptyText);
        else
            modelTable.setPlaceholder(new Label("No car Present"));

        return modelTable;
	}
}
