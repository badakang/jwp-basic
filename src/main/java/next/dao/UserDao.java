package next.dao;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;
import support.JdbcTemplate;
import support.PreparedStatementSetter;
import support.RowMapper;

public class UserDao {

	public void insert(User user) throws SQLException {
		JdbcTemplate template = new JdbcTemplate();
		String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
		template.executeUpdate(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }
	

    public void update(User user) throws SQLException {
    	JdbcTemplate template = new JdbcTemplate();
		String sql = "UPDATE USERS set password = ?, name = ?, email= ? where userId = ?";
		template.executeUpdate(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }
    
    public void remove(User user) throws SQLException {
    	JdbcTemplate template = new JdbcTemplate();
		String sql = "DELETE FROM USERS  where userId = ?";
		template.executeUpdate(sql, user.getUserId());
    }

    public User findByUserId(String userId) throws SQLException {
    	RowMapper<User> rm = new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				return new User(rs.getString("userId"), 
				    		rs.getString("password"), 
				    		rs.getString("name"),
				            rs.getString("email"));
			}
    	};
    	JdbcTemplate template = new JdbcTemplate();
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
		return template.executeQuery(sql, rm, userId);
    }

    public List<User> findAll() throws SQLException {
    	RowMapper<User> rm = new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				return new User(rs.getString("userId"), 
				    		rs.getString("password"), 
				    		rs.getString("name"),
				            rs.getString("email"));
			}
    	};
    	JdbcTemplate template = new JdbcTemplate();
		String sql = "SELECT userId, password, name, email FROM USERS";
		return template.list(sql, rm);
    }

}
