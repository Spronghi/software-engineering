package interation.dao;

public class DAOFactory {
	private static final String PATH = "interation.dao.";

	private DAOFactory(){}
		
	@SuppressWarnings("unchecked")
	public static <T> GenericDAO<T> getInstance(String className){
		String canonicalName = PATH + className;
		Class<?> daoClass=null;
		try {
			daoClass = Class.forName(canonicalName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		GenericDAO<T> instance=null;
		try {
			instance = (GenericDAO<T>) daoClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return instance;
	}
}
