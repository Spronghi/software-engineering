package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Operator;
import com.carloan.integration.database.ConnectorFactory;
import com.carloan.integration.util.Replacer;
import com.carloan.presentation.factory.FXAlert;

class OperatorDAO implements DAO<Operator> {
    private static final String SELECT_ALL = "SELECT * FROM operator";
	private static final String SELECT_FROM_ID = "SELECT * FROM operator WHERE id=?";
	private static final String INSERT = "INSERT INTO operator(first_name,last_name,email,username,password,agency_id)"
			+ "VALUES('?','?','?','?','?',?)";
	private static final String UPDATE = "UPDATE operator SET first_name='?',last_name='?',email='?',"
			+ "username='?',password='?',agency_id=?, admin=? WHERE id=?";
	private static final String DELETE = "DELETE FROM operator WHERE username='?'";

    private DAO<Agency> agencyDAO;

    OperatorDAO(){
        agencyDAO = new AgencyDAO();
    }
    private Operator setOperator(ResultSet rs) throws SQLException{
        Operator operator = new Operator();
        operator.setId(rs.getInt(1));
        operator.setFirstName(rs.getString(2));
        operator.setLastName(rs.getString(3));
        operator.setEmail(rs.getString(4));
        operator.setUsername(rs.getString(5));
        operator.setPassword(rs.getString(6));
        operator.setAgency(agencyDAO.get(rs.getInt(7)));
        operator.setAdmin(rs.getBoolean(8));
        return operator;
	}
	@Override
	public Operator get(int id) {
		String query = SELECT_FROM_ID;
        query = Replacer.replaceFirst(query, id);
        ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try {
            if(rs.next()) {
                return setOperator(rs);
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return null;
	}
	@Override
	public List<Operator> getAll() {
		List<Operator> operators = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_ALL);
        try {
            while(rs.next()) {
                operators.add(setOperator(rs));
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return operators;
	}
	@Override
	public void create(Operator operator) {
		String query = INSERT;
        query = Replacer.replaceFirst(query, operator.getFirstName());
        query = Replacer.replaceFirst(query, operator.getLastName());
        query = Replacer.replaceFirst(query, operator.getEmail());
        query = Replacer.replaceFirst(query, operator.getUsername());
        query = Replacer.replaceFirst(query, operator.getPassword());
        query = Replacer.replaceFirst(query, operator.getAgency().getId());

        operator.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Operator operator) {
		String query = UPDATE;
        query = Replacer.replaceFirst(query, operator.getFirstName());
        query = Replacer.replaceFirst(query, operator.getLastName());
        query = Replacer.replaceFirst(query, operator.getEmail());
        query = Replacer.replaceFirst(query, operator.getUsername());
        query = Replacer.replaceFirst(query, operator.getPassword());
        query = Replacer.replaceFirst(query, operator.getAgency().getId());
        query = Replacer.replaceFirst(query, operator.isAdmin());
        query = Replacer.replaceFirst(query, operator.getId());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Operator operator) {
		String query = DELETE;
        query = Replacer.replaceFirst(query, operator.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
}
