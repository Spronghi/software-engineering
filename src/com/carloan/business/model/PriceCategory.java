package com.carloan.business.model;

import com.carloan.integration.util.DoubleFormatter;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PriceCategory implements Model{
	private static final long serialVersionUID = 4504332358753101339L;
	private IntegerProperty id;
	private StringProperty category;
	private DoubleProperty baseRateLimit;
	private DoubleProperty baseRateUnlimit;
	private DoubleProperty kmRate;
	
	public PriceCategory() {
		super();
		this.id = new SimpleIntegerProperty(0);
		this.category = new SimpleStringProperty();
		this.baseRateLimit = new SimpleDoubleProperty();
		this.baseRateUnlimit = new SimpleDoubleProperty();
		this.kmRate = new SimpleDoubleProperty();
	}
	public PriceCategory(char category, double baseRateLimit,
			double baseRateUnlimit, double kmRate) {
		super();
		this.id = new SimpleIntegerProperty();
		this.category = new SimpleStringProperty(Character.toString(category));
		this.baseRateLimit = new SimpleDoubleProperty(baseRateLimit);
		this.baseRateUnlimit = new SimpleDoubleProperty(baseRateUnlimit);
		this.kmRate = new SimpleDoubleProperty(kmRate);
	}
	public Integer getId() {
		return id.get();
	}
	public char getCategory() {
		return category.get().charAt(0);
	}
	public double getBaseRateLimit() {
		return baseRateLimit.get();
	}
	public double getBaseRateUnlimit() {
		return baseRateUnlimit.get();
	}
	public double getKmRate() {
		return kmRate.get();
	}
	public void setId(int id) {
		this.id.set(Integer.valueOf(id));
	}
	public void setCategory(char category) {
		this.category.set(Character.toString(category));
	}
	public void setBaseRateLimit(double baseRateLimit) {
		this.baseRateLimit.set(baseRateLimit);
	}
	public void setBaseRateUnlimit(double caseRateUnlimit) {
		this.baseRateUnlimit.set(caseRateUnlimit);
	}
	public void setKmRate(double kmRate) {
		this.kmRate.set(kmRate);
	}
	public IntegerProperty getIdProperty() {
		return id;
	}
	public StringProperty categoryProperty() {
		return category;
	}
	public DoubleProperty baseRateLimitProperty() {
		return baseRateLimit;
	}
	public DoubleProperty baseRateUnlimitProperty() {
		return baseRateUnlimit;
	}
	public DoubleProperty kmRateProperty() {
		return kmRate;
	}
	public StringProperty baseRateLimitEndUserProperty() {
		return new SimpleStringProperty("€ " + DoubleFormatter.toString(getBaseRateLimit()));
	}
	public StringProperty baseRateUnlimitEndUserProperty() {
		return new SimpleStringProperty("€ " + DoubleFormatter.toString(getBaseRateUnlimit()));
	}
	public StringProperty kmRateEndUserProperty() {
		return new SimpleStringProperty("€ " + DoubleFormatter.toString(getKmRate()));
	}
	public StringProperty endUserStringProperty() {
		return category;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PriceCategory))
			return false;
		PriceCategory other = (PriceCategory) obj;
		if (category != other.category)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return category.get();
	}

}
