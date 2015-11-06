package interation.database;

public final class Queries {
	private static String[] queries = {Constants.CREATE_DB,Constants.USE_DB,Constants.CREATE_OPERATOR,
		Constants.CREATE_CUSTOMER,Constants.CREATE_LOCATION,Constants.CREATE_AGENCY,
		Constants.CREATE_CAR_STATUS,Constants.CREATE_CAR_CATEGORY,Constants.CREATE_CAR,
		Constants.CREATE_CONTRACT_TYPE,Constants.CREATE_PAYMENT_TYPE,Constants.CREATE_CURRENCY,
		Constants.CREATE_CONTRACT,Constants.CREATE_PAYMENT};
	static String[] array(){
		return queries;
	}
}
