package interation.database;


public abstract class ConnectorFactory {
	public static Connector getConnection(){
		return new ConnectorImpl();
	}
}
