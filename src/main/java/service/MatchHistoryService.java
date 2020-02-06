package service;

import summoner.Match;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatchHistoryService {

    List<Match> matchList;


    public MatchHistoryService(String summonerid) throws SQLException {

        matchList = new ArrayList<>();

        String sql = "SELECT matchid FROM summonermatches WHERE summonerid = ?";
        PreparedStatement stmt = DataBaseConnector.getPreparedStatement(sql, summonerid);

        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            matchList.add(MatchService.getMatch(rs.getNString("matchid"), summonerid));
        }

    }

    public List<Match> getMatchList() {
        return matchList;
    }

}
