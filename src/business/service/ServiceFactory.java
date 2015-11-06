package business.service;

public class ServiceFactory {
	private static final String PATH = "business.service.";

	private ServiceFactory(){}
		
	@SuppressWarnings("unchecked")
	public static <T> GenericService<T> getInstance(String className){
		String canonicalName = PATH + className;
		Class<?> serviceClass=null;
		try {
			serviceClass = Class.forName(canonicalName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		GenericService<T> instance=null;
		try {
			instance = (GenericService<T>) serviceClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return instance;
	}
}
