package summoner;

import champion.KDA;

import java.util.ArrayList;
import java.util.List;

public class Match {

    private String matchId;
    private String region;
    private String summonerId;
    private int gameDuration;
    private int championId;
    private int champLevel;
    private int win;
    private int cs;
    private int spell1;
    private int spell2;
    private List<Integer> itemList;
    private KDA kda;

    public int getGameDuration() {
        return gameDuration;
    }

    public int getGameMode() {
        return gameMode;
    }

    private int gameMode;
    private int visionScore;

    public Match(String matchId, String region, String summonerId, int gameDuration, int championId, int champLevel, int win, int cs, int spell1, int spell2, List<Integer> itemList, KDA kda, int gameMode, int visionScore) {
        this.matchId = matchId;
        this.region = region;
        this.summonerId = summonerId;
        this.gameDuration = gameDuration;
        this.championId = championId;
        this.champLevel = champLevel;
        this.win = win;
        this.cs = cs;
        this.spell1 = spell1;
        this.spell2 = spell2;
        this.itemList = itemList;
        this.kda = kda;
        this.gameMode = gameMode;
        this.visionScore = visionScore;
    }

    public String getMatchId() {
        return matchId;
    }

    public String getRegion() {
        return region;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public int getChampionId() {
        return championId;
    }

    public int getGameStatus() {
        return win;
    }

    public int getCs() {
        return cs;
    }

    public double getCsMin() {
        if (gameDuration == 0) {
            return cs;
        }
        return ((double) (cs * 60)) / gameDuration;
    }

    public int getSpell1() {
        return spell1;
    }

    public int getSpell2() {
        return spell2;
    }

    public List<Integer> getItems() {
        return new ArrayList<>();
    }

    private int getItemByIndex(int i) {
        if (i >= 0 && i < itemList.size()) {
            return itemList.get(i);
        }
        return 0;

    }

    public int getItem0() {
        return getItemByIndex(0);
    }

    public int getItem1() {
        return getItemByIndex(1);
    }

    public int getItem2() {
        return getItemByIndex(2);
    }

    public int getItem3() {
        return getItemByIndex(3);
    }

    public int getItem4() {
        return getItemByIndex(4);
    }

    public int getItem5() {
        return getItemByIndex(5);
    }

    public int getItem6() {
        return getItemByIndex(6);
    }

    public KDA getKda() {
        return kda;
    }

    public int getVisionScore() {
        return visionScore;
    }

    public int getChampLevel() {
        return champLevel;
    }

    public int getMinutes() {
        return (int) Math.floor(gameDuration / 60);
    }
}
