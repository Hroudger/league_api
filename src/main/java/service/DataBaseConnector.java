package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public abstract class DataBaseConnector {

    private static String dbName = "league_api";
    private static String dbUser = "league_api";
    private static String dbPassword = "";

    public static Connection getConnection() throws SQLException {

        /*
        try {
            Class.forName("cjom.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.out.println("aasasasad");
            return null;
        }*/
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/".concat(dbName),
                dbUser,
                dbPassword);
    }


    public static Statement getStatement(String sql) throws SQLException {
        return getPreparedStatement(sql, new ArrayList<String>() {
        });
    }


    public static PreparedStatement getPreparedStatement(String sql, String value) throws SQLException {
        List<String> list = new ArrayList<>();
        list.add(value);
        return getPreparedStatement(sql, list);
    }

    public static PreparedStatement getPreparedStatement(String sql, List<String> list) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(sql);

        for (int i = 0; i <= list.size(); i++) {
            stmt.setString(i + 1, list.get(i));
        }
        return stmt;
    }

}
