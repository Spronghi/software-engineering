package com.anrc.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.anrc.integration.database.ConnectorFactory;
import com.anrc.integration.util.DateFormatter;
import com.anrc.integration.util.Replacer;
import com.anrc.model.Survey;

class SurveyDAO implements DAO<Survey> {
	private RoomDAO roomDAO;
	private static final String SELECT = "SELECT `id`,`value`,`date`,`room_id` FROM survey";
	private static final String SELECT_FROM_ID = "SELECT `id`,`value`,`date`,`room_id` FROM survey WHERE ID=?";
	private static final String INSERT = "INSERT INTO survey(value,date,room_id) VALUES ('?','?',?)";
	private static final String DELETE = "DELETE FROM survey WHERE id=?";
	private static final String UPDATE = "UPDATE survey SET value='?',date='?',room_id=? WHERE id=?";
	
	public SurveyDAO() {
		roomDAO = new RoomDAO();
	}
	
	private Survey setSurvey(ResultSet rs) throws SQLException{
		Survey survey = new Survey();
		survey.setId(rs.getInt(1));
		survey.setValue(rs.getFloat(2));
		survey.setDate(rs.getDate(3).toLocalDate());
		survey.setTime(rs.getTime(3).toLocalTime());
		survey.setRoom(roomDAO.get(rs.getInt(4)));
		return survey;
	}
	@Override
	public List<Survey> getAll() {
		List<Survey> surveys = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT);
        try {
            while(rs.next()) {
            	surveys.add(setSurvey(rs));
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return surveys;
	}
	@Override
	public Survey get(int id) {
		String query = SELECT_FROM_ID;
        query = Replacer.replaceFirst(query, id);

        ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try {
            if(rs.next()) {
                return setSurvey(rs);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return null;
	}
	@Override
	public void create(Survey model) {
		String query = INSERT;
        query = Replacer.replaceFirst(query, model.getValue());
        query = Replacer.replaceFirst(query, DateFormatter.toString("yyyy-MM-dd HH:mm:ss",model.getDate(),model.getTime()));
        query = Replacer.replaceFirst(query, model.getRoom().getId());
        model.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Survey model) {
		String query = UPDATE;
        query = Replacer.replaceFirst(query, model.getValue());
        query = Replacer.replaceFirst(query, DateFormatter.toString("yyyy-MM-dd HH:mm:ss",model.getDate(),model.getTime()));
        query = Replacer.replaceFirst(query, model.getRoom().getId());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Survey model) {
		String query = DELETE;
        query = Replacer.replaceFirst(query, model.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
}
