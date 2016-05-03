package com.anrc.integration.dao;

import java.util.HashMap;
import java.util.Map;

import com.anrc.model.Detector;
import com.anrc.model.DetectorType;
import com.anrc.model.Location;
import com.anrc.model.Place;
import com.anrc.model.Room;
import com.anrc.model.Survey;

class DAORegisterService {
	private static Map< Class<?>, DAO<?>> container;
	static {
		container = new HashMap<>();
		container.put(Detector.class, new DetectorDAO());
		container.put(DetectorType.class, new DetectorTypeDAO());
		container.put(Location.class, new LocationDAO());
		container.put(Place.class, new PlaceDAO());
		container.put(Room.class, new RoomDAO());
		container.put(Survey.class, new SurveyDAO());
	}
	static DAO<?> getDAO(Class<?> clazz){
		return container.get(clazz);
	}
}
