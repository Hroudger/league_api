package service;

import champion.KDA;
import summoner.Match;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatchService {


    public static Match getMatch(String matchId, String summonerId) throws SQLException {

        Connection con = DataBaseConnector.getConnection();

        String sql = "SELECT * FROM summonermatches WHERE matchId = ? AND summonerid = ?";

        List<String> tmp = new ArrayList<>();
        tmp.add(0, matchId);
        tmp.add(1, summonerId);


        PreparedStatement stmt = DataBaseConnector.getPreparedStatement(sql, tmp);
        ResultSet rs = stmt.executeQuery();


        while (rs.next()) {
            String matchid = rs.getNString("matchid");
            String region = rs.getNString("region");
            String summonerid = rs.getNString("summonerid");
            int championId = rs.getInt("championid");
            int champLevel = rs.getInt("level");
            int gameMode = rs.getInt("queueId");
            int win = rs.getInt("win");
            int cs = rs.getInt("cs");
            int spell1 = rs.getInt("spell1");
            int spell2 = rs.getInt("spell2");
            int gameDuration = rs.getInt("duration");
            int visionScore = rs.getInt("visionscore");

            List<Integer> itemList = new ArrayList<>();
            itemList.add(rs.getInt("item0"));
            itemList.add(rs.getInt("item1"));
            itemList.add(rs.getInt("item2"));
            itemList.add(rs.getInt("item3"));
            itemList.add(rs.getInt("item4"));
            itemList.add(rs.getInt("item5"));
            itemList.add(rs.getInt("item6"));

            int kills = rs.getInt("kills");
            int deaths = rs.getInt("deaths");
            int assists = rs.getInt("assists");
            KDA kda = new KDA(kills, deaths, assists);

            return new Match(matchid, region, summonerid, gameDuration, championId, champLevel, win, cs, spell1, spell2, itemList,kda, gameMode, visionScore);
        }

        return null;
    }

}
