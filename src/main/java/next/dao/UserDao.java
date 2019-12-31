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
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setParameter(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getEmail());
			}
		};
		JdbcTemplate template = new JdbcTemplate() {
			@Override
			public void setParameter(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getEmail());
			}

			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}			
		};
		String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
		template.executeUpdate(sql, pss);
    }
	

    public void update(User user) throws SQLException {
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setParameter(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getPassword());
				pstmt.setString(2, user.getName());
				pstmt.setString(3, user.getEmail());
				pstmt.setString(4, user.getUserId());
			}
    	};
		JdbcTemplate template = new JdbcTemplate() {
			@Override
			public void setParameter(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getPassword());
				pstmt.setString(2, user.getName());
				pstmt.setString(3, user.getEmail());
				pstmt.setString(4, user.getUserId());
			}

			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}			
		};
		String sql = "UPDATE USERS set password = ?, name = ?, email= ? where userId = ?";
		template.executeUpdate(sql, pss);
    }
    
    public void remove(User user) throws SQLException {
    	PreparedStatementSetter pss = new PreparedStatementSetter() {

			@Override
			public void setParameter(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
			}
    	};
		JdbcTemplate template = new JdbcTemplate() {
			@Override
			public void setParameter(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
			}

			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}			
		};
		String sql = "DELETE FROM USERS  where userId = ?";
		template.executeUpdate(sql, pss);
    }

    public List<User> findAll() throws SQLException {
        // TODO 구현 필요함.
        return new ArrayList<User>();
    }

    public User findByUserId(String userId) throws SQLException {
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setParameter(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, userId);
			}
    	};
    	RowMapper rm = new RowMapper() {
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
		JdbcTemplate template = new JdbcTemplate() {
			@Override
			public void setParameter(PreparedStatement pstmt) throws SQLException {
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
		return (User)template.executeQuery(sql, pss, rm);
    }
}
