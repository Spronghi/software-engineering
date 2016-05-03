package com.carloan.presentation.factory.internal;

import com.carloan.business.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryRegister {
    private static Map<Class<?>, String> container;
    static {
        container = new HashMap<>();
        container.put(Agency.class, "Agency");
        container.put(Car.class, "Car");
        container.put(Contract.class, "Contract");
        container.put(Customer.class, "Customer");
        container.put(Location.class, "Location");
        container.put(Operator.class, "Operator");
        container.put(Payment.class, "Payment");
        container.put(ArrayList.class, "Payments");
        container.put(PriceCategory.class, "PriceCategory");
    }
    public static String get(Class<?> key){
        return container.get(key);
    }
}
