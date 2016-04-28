package com.carloan.service.model_service;

import com.carloan.business.model.Currency;
import com.carloan.integration.dao.DAOFactory;

import java.util.List;

class CurrencyService implements ModelService<Currency> {
    public List<Currency> getAll(){
        return DAOFactory.getInstance(Currency.class).getAll();
    }
    public Currency get(int id){
        return DAOFactory.getInstance(Currency.class).get(id);
    }
    public void create(Currency currency){
        DAOFactory.getInstance(Currency.class).create(currency);
    }
    public void update(Currency currency){
        DAOFactory.getInstance(Currency.class).create(currency);
    }
    public void delete(Currency currency){
        DAOFactory.getInstance(Currency.class).create(currency);
    }

}
