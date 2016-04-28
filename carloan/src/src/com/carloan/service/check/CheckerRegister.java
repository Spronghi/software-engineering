package com.carloan.service.check;

import com.carloan.service.control.ServiceControl;

import java.util.HashMap;
import java.util.Map;

class CheckerRegister {
    private static Map< String, Checker<?>> container;
    static {
        container = new HashMap<>();
        container.put(ServiceControl.AGENCY, new AgencyChecker());
        container.put(ServiceControl.CAR, new CarChecker());
        container.put(ServiceControl.CONTRACT, new ContractChecker());
        container.put(ServiceControl.CUSTOMER, new CustomerChecker());
        container.put(ServiceControl.LOCATION, new LocationChecker());
        container.put(ServiceControl.OPERATOR, new OperatorChecker());
        container.put(ServiceControl.PAYMENT, new PaymentChecker());
        container.put(ServiceControl.PASSWORD_SECURITY, new PasswordChecker());
    }
    static Checker<?> getChecker(String key){
        return container.get(key);
    }
}
