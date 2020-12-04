package dao;

import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    Conn connection = new Conn();
    // 添加信息
    public boolean addUser(User user) {
        // 添加的SQL语句
        String sql = "INSERT INTO user(hukaixuan_name,hukaixuan_tel) VALUES (?,?)";
        Connection conn = connection.getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.getHukaixuan_name());
            pst.setString(2, user.getHukaixuan_tel());
            int count = pst.executeUpdate();
            pst.close();
            return count > 0 ? true : false; // 是否添加的判断
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getAllUser() { // 查询所有信息
        List<User> list = new ArrayList<User>(); // 创建集合
        Connection conn = connection.getConnection();
        String sql = "select * from user"; // SQL查询语句
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                User user = new User();
                user.setHukaixuan_id(rst.getInt("hukaixuan_id")); // 得到ID
                user.setHukaixuan_name(rst.getString("hukaixuan_name"));
                user.setHukaixuan_tel(rst.getString("hukaixuan_tel"));
                list.add(user);
            }
            rst.close(); // 关闭
            pst.close(); // 关闭
        } catch (SQLException e) {
            e.printStackTrace(); // 抛出异常
        }
        return list; // 返回一个集合
    }

    public boolean delUser(int id) throws SQLException {
        boolean flag = false;
        Connection conn = connection.getConnection();
        String sql = "delete from user where hukaixuan_id=?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            int count = pst.executeUpdate();
            if(count>0){
                flag = true;
                System.out.println("delete success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    public boolean UpdateUser(User user){
        boolean flag = false;
        PreparedStatement pstmt = null;
        Connection conn = connection.getConnection();
        String sql = "update user set hukaixuan_name=?,hukaixuan_tel=? where hukaixuan_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getHukaixuan_name());
            pstmt.setString(2, user.getHukaixuan_tel());
            pstmt.setInt(3, user.getHukaixuan_id());

            int count =pstmt.executeUpdate();
            if(count>0){
                flag = true;
                System.out.println("update success");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                pstmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return flag;
    }
    public List<User> findOne(String id){
        List<User> list = new ArrayList<User>(); // 创建集合
        PreparedStatement pstmt = null;
        Conn connection = new Conn();
        Connection conn = connection.getConnection();
        String sql = "select * from user where hukaixuan_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rst = pstmt.executeQuery();
            while (rst.next()) {
                User user = new User();
                user.setHukaixuan_id(rst.getInt("hukaixuan_id"));
                user.setHukaixuan_name(rst.getString("hukaixuan_name"));
                user.setHukaixuan_tel(rst.getString("hukaixuan_tel"));
                list.add(user);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                pstmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return list;
    }
}
