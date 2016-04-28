package com.carloan.service.model_service;

import com.carloan.business.model.PaymentType;
import com.carloan.integration.dao.DAOFactory;

import java.util.List;

class PaymentTypeService implements ModelService<PaymentType> {
    public List<PaymentType> getAll() {
        return DAOFactory.getInstance(PaymentType.class).getAll();
    }
    public PaymentType get(int id) {
        return DAOFactory.getInstance(PaymentType.class).get(id);
    }
    public void create(PaymentType type) {
        DAOFactory.getInstance(PaymentType.class).create(type);
    }

    public void update(PaymentType type) {
        DAOFactory.getInstance(PaymentType.class).update(type);
    }

    public void delete(PaymentType type) {
        DAOFactory.getInstance(PaymentType.class).delete(type);
    }
}
