package summoner;

import java.util.ArrayList;
import java.util.List;

public class MatchHistory {
    private List<Match> matchList = new ArrayList<>();

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
