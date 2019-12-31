package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;
import support.JdbcTemplate;
import support.SelectJdbcTemplate;

public class UserDao {

	public void insert(User user) throws SQLException {
		JdbcTemplate template = new JdbcTemplate() {
			@Override
			public void setParameter(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getEmail());
			}			
		};
		String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
		template.executeUpdate(sql);
    }
	

    public void update(User user) throws SQLException {
		JdbcTemplate template = new JdbcTemplate() {
			@Override
			public void setParameter(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getPassword());
				pstmt.setString(2, user.getName());
				pstmt.setString(3, user.getEmail());
				pstmt.setString(4, user.getUserId());
			}			
		};
		String sql = "UPDATE USERS set password = ?, name = ?, email= ? where userId = ?";
		template.executeUpdate(sql);
    }
    
    public void remove(User user) throws SQLException {
		JdbcTemplate template = new JdbcTemplate() {
			@Override
			public void setParameter(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
			}			
		};
		String sql = "DELETE FROM USERS  where userId = ?";
		template.executeUpdate(sql);
    }

    public List<User> findAll() throws SQLException {
        // TODO 구현 필요함.
        return new ArrayList<User>();
    }

    public User findByUserId(String userId) throws SQLException {
		SelectJdbcTemplate template = new SelectJdbcTemplate() {

			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, userId);
			}

			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				User user = null;
				if (rs.next()) {
				    user = new User(rs.getString("userId"), 
				    		rs.getString("password"), 
				    		rs.getString("name"),
				            rs.getString("email"));
				}
				return user;
			}
		};
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
		return (User)template.executeQuery(sql);
    }
}
