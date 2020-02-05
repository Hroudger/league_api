package summoner;

import java.io.IOException;

public class Summoner {

    private MatchHistory matchHistory = new MatchHistory();
    private String id;
    private String name;
    private String region;


    public static void addSummoner(String name, String region) throws IOException {
        String[] cmd = {
                "python",
                "MEIN SCRIPTPFAD",
                name,
                region
        };
        Runtime.getRuntime().exec(cmd);
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


}
