package summoner;

import champion.Champion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Summoner {

    private MatchHistory matchHistory;
    private final List<Match> matchList;
    private String id;
    private String name;
    private String region;
    private List<Champion> championList = new ArrayList<>();


    public Summoner(String id, String name, String region) throws SQLException {
        this.id = id;
        this.name = name;
        this.region = region;
        this.matchHistory = new MatchHistory(this.id);
        matchList = new ArrayList<>();
    }

    public static void addSummoner(String name, String region) throws IOException {
        String[] cmd = {
                "python",
                "MEIN SCRIPTPFAD",
                name,
                region
        };
        Runtime.getRuntime().exec(cmd);
    }

    public void loadMatchHistory() throws SQLException {
//        matchHistory.loadHistory();
    }

    public int getWinRate(String queue) {
        return matchHistory.getWinRate(queue);
    }

    public int getWinRate() {
        return matchHistory.getWinRate();
    }

    public int getAmount(String queue) {
        return matchHistory.getAmount(queue);
    }

    public int getAmount() {
        return matchHistory.getAmount();
    }

    public int getPercentage(String queue) {
        return matchHistory.getPercentage(queue);
    }

    public int getPercentage() {
        return matchHistory.getPercentage();
    }


    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public List<Champion> getChampionList() {
        return championList;
    }

    public List<Match> getMatchList() {
        return matchList;
    }

    public Elo getElo() {
        return Elo.IRON;
    }

    public int getLp() {
        return 0;
    }
}
