package service;

import summoner.Summoner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SummonerListService {

    private List<Summoner> summoners = new ArrayList<>();
    private String dbName = "league:api";
    private String dbUser = "league_api";
    private String dbPassword = "";


    public SummonerListService() throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:myDriver:".concat(dbName),
                dbUser,
                dbPassword);

        String sql = "SELECT id FROM summoners";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        SummonerService summonerService = new SummonerService();

        while (rs.next()) {
            String summonerId = rs.getNString("id");
            Summoner summoner = summonerService.getSummoner(summonerId);
            summoners.add(summoner);
        }

    }

    public List<Summoner> getSummonerList() {
        return summoners;
    }


}
