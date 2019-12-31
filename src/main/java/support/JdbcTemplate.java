package support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import core.jdbc.ConnectionManager;
import next.dao.UserDao;
import next.model.User;

public class JdbcTemplate {
	
    public void insert(User user, UserDao userDAO) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = userDAO.createQuery();
            pstmt = con.prepareStatement(sql);
            userDAO.setParameter(user, pstmt);

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
}
