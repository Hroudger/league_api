package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DataBaseConnector {

    private static String dbName = "league:api";
    private static String dbUser = "league_api";
    private static String dbPassword = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:myDriver:".concat(dbName),
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
