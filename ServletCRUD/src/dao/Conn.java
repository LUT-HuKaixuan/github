package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conn {
    public Connection getConnection() {
        Connection conn=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //localhost 为本级地址，studata为数据库名
            String url = "jdbc:mysql://localhost:3306/studata";
            String userName = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 获取连接
        return conn;
    }

    public static void main(String[] args) {
        System.out.println(new Conn().getConnection());
    }

}
