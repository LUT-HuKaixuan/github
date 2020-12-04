package dao;

import entity.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageDao {
    Conn connection = new Conn();

    public boolean findAll(Manager manager) {
        boolean flag = false;
        PreparedStatement pstmt = null;
        Connection conn = connection.getConnection();
        String sql = "select * from manager where hukaixuan_managerid=? and hukaixuan_password=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, manager.getHukaixuan_managerid());
            pstmt.setString(2, manager.getHukaixuan_password());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                flag = true;
                System.out.println("query success!");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;

    }
}
