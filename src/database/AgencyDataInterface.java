package database;

import javafx.collections.ObservableList;
import address.model.Agency;

public interface AgencyDataInterface {
	public ObservableList<Agency> getContainer();
	public Agency get(String name);
}
