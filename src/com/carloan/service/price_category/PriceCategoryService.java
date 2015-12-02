package com.carloan.service.price_category;

import java.util.List;

import com.carloan.business.model.PriceCategory;
import com.carloan.integration.dao.DAOFactory;

public enum PriceCategoryService {
	INSTANCE;
	public static List<PriceCategory> getAll(){
		return DAOFactory.getInstance(PriceCategory.class).getAll();
	}
	public static PriceCategory get(int id){
		return DAOFactory.getInstance(PriceCategory.class).get(id);
	}
	public static void create(PriceCategory priceCategory){
		DAOFactory.getInstance(PriceCategory.class).create(priceCategory);
	}
	public static void update(PriceCategory priceCategory){
		DAOFactory.getInstance(PriceCategory.class).update(priceCategory);
	}
	public static void delete(PriceCategory priceCategory){
		DAOFactory.getInstance(PriceCategory.class).delete(priceCategory);
	}
}
