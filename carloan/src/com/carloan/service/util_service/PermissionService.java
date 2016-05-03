package com.carloan.service.util_service;

import java.util.HashMap;
import java.util.Map;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Car;
import com.carloan.business.model.Contract;
import com.carloan.business.model.Customer;
import com.carloan.business.model.Operator;
import com.carloan.business.model.PriceCategory;
import com.carloan.service.ServiceFactory;
import com.carloan.service.control.PermissionControl;

class PermissionService extends PermissionControl implements PermissionServiceInt {
	private static Map<Class<?>, String> user;
	private static Map<Class<?>, String> admin;
	
	static {
		user = new HashMap<>();
		user.put(Agency.class, VIEW+READ);
		user.put(Car.class, VIEW+READ+EDIT_STATUS);
		user.put(Contract.class,VIEW+READ+CREATE+CLOSE);
		user.put(Customer.class,VIEW+READ+CREATE+UPDATE+DELETE);
		user.put(Operator.class, RESET_PASSWORD);
		user.put(PriceCategory.class,VIEW+READ);

		admin = new HashMap<>();
		admin.put(Agency.class, VIEW+READ+CREATE+UPDATE+DELETE);
		admin.put(Car.class, VIEW+READ+CREATE+UPDATE+DELETE+EDIT_STATUS);
		admin.put(Contract.class,VIEW+READ+CREATE+UPDATE+DELETE+CLOSE);
		admin.put(Customer.class,VIEW+READ+CREATE+UPDATE+DELETE);
		admin.put(Operator.class, VIEW+READ+CREATE+UPDATE+RESET_PASSWORD+SET_ADMINISTRATOR+DELETE);
		admin.put(PriceCategory.class,VIEW+READ+UPDATE);
	}
	
	public String getPermissions(Class<?> key){
        if(ServiceFactory.getUtilFactory().getLoginService().getCurrentOperator().isAdmin()) {
            return admin.get(key);
        } else {
            return user.get(key);
        }
	}
}
