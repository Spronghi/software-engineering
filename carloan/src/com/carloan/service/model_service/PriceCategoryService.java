package com.carloan.service.model_service;

import java.util.List;

import com.carloan.business.model.PriceCategory;
import com.carloan.integration.dao.DAOFactory;

class PriceCategoryService implements ModelService<PriceCategory> {
	public List<PriceCategory> getAll(){
		return DAOFactory.getInstance(PriceCategory.class).getAll();
	}
	public PriceCategory get(int id){
		return DAOFactory.getInstance(PriceCategory.class).get(id);
	}
	public void create(PriceCategory priceCategory){
		DAOFactory.getInstance(PriceCategory.class).create(priceCategory);
	}
	public void update(PriceCategory priceCategory){
		DAOFactory.getInstance(PriceCategory.class).update(priceCategory);
	}
	public void delete(PriceCategory priceCategory){
		DAOFactory.getInstance(PriceCategory.class).delete(priceCategory);
	}
}
