package interation.dao;

import interation.database.ConnectorFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.model.Operator;

class OperatorDAO extends GenericDAO<Operator> {
	public OperatorDAO() {
	}

	private Operator setOperator(ResultSet rs) throws SQLException{
		Operator operator = new Operator(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(5));
		operator.setId(rs.getInt(1));
		return operator;
	}
	@Override
	public List<Operator> getAll() {
		List<Operator> operators = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(Queries.SELECT_OPERATOR);
		try {
			while(rs.next())
				operators.add(setOperator(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return operators;
	}
	@Override
	public void add(Operator operator) {
		String query = Queries.INSERT_OPERATOR.replaceFirst("[?]", operator.getFirstName())
				.replaceFirst("[?]", operator.getLastName())
				.replaceFirst("[?]", operator.getEmail())
				.replaceFirst("[?]", operator.getUsername())
				.replaceFirst("[?]", operator.getPassword());
		operator.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void edit(Operator operator) {
		String query = Queries.UPDATE_OPERATOR.replaceFirst("[?]", operator.getFirstName())
				.replaceFirst("[?]", operator.getLastName())
				.replaceFirst("[?]", operator.getEmail())
				.replaceFirst("[?]", operator.getUsername())
				.replaceFirst("[?]", operator.getPassword())
				.replaceFirst("[?]", Integer.toString(operator.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Operator operator) {
		String query = Queries.DELETE_OPERATOR.replaceFirst("[?]", operator.getUsername());
		ConnectorFactory.getConnection().executeUpdate(query);
	}

}
