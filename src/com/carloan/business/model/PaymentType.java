package com.carloan.business.model;

public class PaymentType extends Type {
	private static final long serialVersionUID = -3027597984737950196L;
	
	public PaymentType(){
		super();
	}
	public PaymentType(int id, String type) {
		super(id, type);
	}
}
