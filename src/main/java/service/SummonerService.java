package service;

import summoner.Summoner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SummonerService {

    public Summoner getSummoner(String id) throws SQLException {

        String sql = "SELECT * FROM summoners WHERE id = ?";
        PreparedStatement stmt = DataBaseConnector.getPreparedStatement(sql, id);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String name = rs.getNString("name");
            String region = rs.getNString("region");
            String summonerId = rs.getNString("id");
            return new Summoner(summonerId, name, region);
        }

        return null;
    }
}
