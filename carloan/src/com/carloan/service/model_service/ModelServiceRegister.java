package com.carloan.service.model_service;


import com.carloan.service.control.ServiceControl;

import java.util.HashMap;
import java.util.Map;

class ModelServiceRegister extends ServiceControl {
    private static Map<String, ModelService<?>> container;
    static {
        container = new HashMap<>();
        container.put(AGENCY, new AgencyService());
        container.put(CAR, new CarService());
        container.put(CAR_STATUS, new CarStatusService());
        container.put(CONTRACT, new ContractService());
        container.put(CONTRACT_TYPE, new ContractTypeService());
        container.put(CURRENCY, new CurrencyService());
        container.put(CUSTOMER, new CustomerService());
        container.put(LOCATION, new LocationService());
        container.put(OPERATOR, new OperatorService());
        container.put(PAYMENT, new PaymentService());
        container.put(PAYMENT_TYPE, new PaymentTypeService());
        container.put(PRICE_CATEGORY, new PriceCategoryService());
    }
    static ModelService<?> getService(String key){
        return container.get(key);
    }
}
