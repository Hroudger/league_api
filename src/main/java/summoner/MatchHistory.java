package summoner;

import service.MatchHistoryService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatchHistory {
    private List<Match> matchList = new ArrayList<>();
    private String summonerid;


    public MatchHistory(String summonerid) throws SQLException {
        this.summonerid = summonerid;
        loadHistory();
    }

    public void loadHistory() throws SQLException {
        if (summonerid != null && !summonerid.equals("")) {
            return;
        }

        MatchHistoryService matchHistoryService = new MatchHistoryService(summonerid);

        matchList = matchHistoryService.getMatchList();
    }


    public int getWinRate() {
        return getWinRate("ranked");
    }

    public int getWinRate(String queue) {
        return 0;
    }

    public int getAmount() {
        return getAmount("ranked");
    }

    public int getAmount(String queue) {
        return 0;
    }

    public int getPercentage(String queue) {
        return 0;
    }

    public int getPercentage() {
        return getPercentage("ranked");
    }


}
