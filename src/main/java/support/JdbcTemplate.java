package support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import core.jdbc.ConnectionManager;
import next.dao.UserDao;
import next.model.User;

public class JdbcTemplate {
	
    public void executeUpdate(String sql, PreparedStatementSetter pss) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setParameter(pstmt);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }
    
    
    public void executeUpdate(String sql, Object... parameters) throws SQLException {
        executeUpdate(sql, createPrepareStatementSetter(parameters));
    }
    
    public <T> T executeQuery(String sql, RowMapper<T> rm, PreparedStatementSetter pss) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (!rs.next()) {
            	return null;
			}
            
            return rm.mapRow(rs);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public <T> T executeQuery(String sql, RowMapper<T> rm, Object... parameters) throws SQLException {
    	return executeQuery(sql, rm, createPrepareStatementSetter(parameters));
    }


	private PreparedStatementSetter createPrepareStatementSetter(Object... parameters) {
		return new PreparedStatementSetter() {
			@Override
			public void setParameter(PreparedStatement pstmt) throws SQLException {
	            for (int i = 0; i < parameters.length; i++) {
	            	pstmt.setObject(i+1, parameters[i]);
				}
			}
    	};
	}
}
