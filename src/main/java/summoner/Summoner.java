package summoner;

import champion.Champion;
import region.Region;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Summoner {

    private final MatchHistory matchHistory;
    private final List<Match> matchList;
    private final String id;
    private final String name;
    private final Region region;
    private final List<Champion> championList = new ArrayList<>();
    private final Elo elo;
    private final String division;
    private final int lp;

    public Summoner(String id, String name, Region region, Elo elo, String division, int lp) throws SQLException {
        this.id = id;
        this.name = name;
        this.region = region;
        this.matchHistory = new MatchHistory(this.id);
        this.elo = elo;
        this.division = division;
        this.lp = lp;
        matchList = new ArrayList<>();
    }

    public static void addSummoner(String name, String region) throws Exception {
        String[] cmd = {
            "python",
            "MEIN SCRIPTPFAD",
            name,
            region
        };
        Process p = Runtime.getRuntime().exec(cmd);
        p.waitFor();
        int exitValue = p.exitValue();
        if (p.exitValue() > 0) {
            switch (exitValue) {
                case 1:
                    throw new Exception("API key used to often");
                case 2:
                    throw new Exception("Summoner not existing");
                default:
                    throw new Exception("Error at adding Summoner");
            }
        }
    }

    public void loadMatchHistory() throws SQLException {
        matchHistory.loadHistory();
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

    public String getName() {
        return name;
    }

    public String getDivision() {
        return division;
    }

    public Champion getBestChampion() {
        return matchHistory.getBestChampion();
    }

    public int getAvgGameLength() {
        final List<Match> matchList = matchHistory.getMatchList();
        int sum = 0;
        for (Match match : matchList) {
            sum += match.getGameDuration();
        }
        sum = sum / matchList.size();
        return sum;
    }

    public Region getRegion() {
        return region;
    }

    public List<Champion> getChampionList() {
        return championList;
    }

    public List<Match> getMatchList() {
        return matchHistory.getMatchList();
    }

    public Elo getElo() {
        return elo;
    }

    public int getLp() {
        return lp;
    }
}
