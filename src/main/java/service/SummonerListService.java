package service;

import summoner.Summoner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SummonerListService {

    private List<Summoner> summoners = new ArrayList<>();



    public SummonerListService() throws SQLException {

        Connection con = DataBaseConnector.getConnection();

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
