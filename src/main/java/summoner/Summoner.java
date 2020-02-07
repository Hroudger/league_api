package summoner;

import champion.Champion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Summoner {

    private MatchHistory matchHistory;
    private String id;
    private String name;
    private String region;
    private List<Champion> championList = new ArrayList<>();


    public Summoner(String id, String name, String region) throws SQLException {
        this.id = id;
        this.name = name;
        this.region = region;
        this.matchHistory = new MatchHistory(this.id);
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
}
