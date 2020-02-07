package summoner;

import champion.Champion;
import service.MatchHistoryService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchHistory {

    private List<Match> matchList = new ArrayList<>();
    private String summonerid;

    private int winrateAll;
    private int winrateSolo;
    private int winrateFlex;
    private int amountGamesAll;
    private int amountGamesSolo;
    private int amountGamesFlex;
    private int winAmountAll;
    private int winAmountSolo;
    private int winAmountFlex;
    private Champion bestChampionAll;
    private Champion bestChampionSolo;
    private Champion bestChampionFlex;

    private int flexValue = 440;
    private int soloValue = 420;


    public MatchHistory(String summonerid) throws SQLException {
        this.summonerid = summonerid;
        loadHistory();
    }

    public void loadHistory() throws SQLException {
        if (summonerid != null || summonerid.equals("")) {
            return;
        }

        MatchHistoryService matchHistoryService = new MatchHistoryService(summonerid);

        matchList = matchHistoryService.getMatchList();
        initValues();
    }

    private void initValues() {

        List<Match> all = new ArrayList<>();
        List<Match> solo = new ArrayList<>();
        List<Match> flex = new ArrayList<>();
        for (Match match : matchList) {

            all.add(match);
            int gameMode = match.getGameMode();
            if (gameMode == flexValue) {
                flex.add(match);
            } else if (gameMode == soloValue) {
                solo.add(match);
            }
        }

        List<Integer> calcedValuesAll = calcValues(all);
        List<Integer> calcedValuesSolo = calcValues(solo);
        List<Integer> calcedValuesFlex = calcValues(flex);

        winrateAll = calcedValuesAll.get(0);
        amountGamesAll = calcedValuesAll.get(1);
        winAmountAll = calcedValuesAll.get(2);
        bestChampionAll = new Champion(calcedValuesAll.get(3));

        winrateSolo = calcedValuesSolo.get(0);
        amountGamesSolo = calcedValuesSolo.get(1);
        winAmountSolo = calcedValuesSolo.get(2);
        bestChampionSolo = new Champion(calcedValuesSolo.get(3));

        winrateFlex = calcedValuesFlex.get(0);
        amountGamesFlex = calcedValuesFlex.get(1);
        winAmountFlex = calcedValuesFlex.get(2);
        bestChampionFlex = new Champion(calcedValuesFlex.get(3));

    }

    private List<Integer> calcValues(List<Match> matches) {
        List<Integer> values = new ArrayList<>();

        List<Match> matchWon = new ArrayList<>();
        //List<Match> matchLose = new ArrayList<>();

        Map<Integer, Integer> champGameAmount = new HashMap<>();
        Map<Integer, Integer> champWinAmount = new HashMap<>();


        for (Match match : matches) {
            int champId = match.getChampionId();
            if (champGameAmount.containsKey(champId)) {
                champGameAmount.put(champId, champGameAmount.get(champId) + 1);
            } else {
                champGameAmount.put(champId, 1);
            }

            if (match.getGameStatus() == 0) {
                //matchLose.add(match);
            } else if (match.getGameStatus() == 1) {
                matchWon.add(match);
                if (champWinAmount.containsKey(champId)) {
                    champWinAmount.put(champId, champWinAmount.get(champId) + 1);
                } else {
                    champWinAmount.put(champId, 1);
                }
            }
        }

        int bestChampion = 0;
        int bestChampWinrate = 0;
        if (champWinAmount.size() > 0) {
            for (Integer integer : champWinAmount.keySet()) {
                int champWinrate = champWinAmount.get(integer) * 100 / champGameAmount.get(integer);
                int champId = 0;
                if (champWinrate > bestChampWinrate) {
                    bestChampion = champId;
                    bestChampWinrate = champWinrate;
                }
            }
        }


        int amountGames = matches.size();
        int winAmount = matchWon.size();
        int winrate = 0;

        if (winAmount > 0) {
            winrate = matchWon.size() * 100 / amountGames;
        }

        values.add(winrate);
        values.add(amountGames);
        values.add(winAmount);
        values.add(bestChampion);

        return values;
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

    public Champion getBestChampion() {

        return new Champion(0);
    }

    public List<Match> getMatchList() {
        return matchList;
    }

    public Champion getBestSoloChamp() {
        return null;
    }

    public Champion getBestFlexChamp() {
        return null;
    }
}
