package com.carloan.service.model_service;

import com.carloan.service.control.ServiceControl;

import java.util.HashMap;
import java.util.Map;

class OrderedServiceRegister extends ServiceControl{
    private static Map< String, OrderedService<?>> container;
    static {
        container = new HashMap<>();
        container.put(CAR, new CarService());
        container.put(CONTRACT, new ContractService());
        container.put(PAYMENT, new PaymentService());
    }
    static OrderedService<?> getOrderedService(String key){
        return container.get(key);
    }
}
