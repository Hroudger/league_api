package service;

import summoner.Summoner;

import java.sql.*;

public class SummonerService {

    private String dbName = "league:api";
    private String dbUser = "league_api";
    private String dbPassword = "";

    public Summoner getSummoner(String id) throws SQLException {

        Connection con = DriverManager.getConnection(
                "jdbc:myDriver:".concat(dbName),
                dbUser,
                dbPassword);

        String sql = "SELECT * FROM summoners WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, id);

        ResultSet rs = stmt.executeQuery();

        SummonerService summonerService = new SummonerService();

        while (rs.next()) {
            String name = rs.getNString("name");
            String region = rs.getNString("region");
            String summonerId = rs.getNString("id");
            return new Summoner(summonerId, name, region);
        }

        return null;
    }
}
