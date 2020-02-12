package champion;

public class Champion {
    private String name = "Hugo";
    private KDA kda;
    private int championId;
    private int amountGames = 1;
    private int percentGames = 1;
    private int winrate = 0;
    private int avgGameLength = 3;
    private float csMin = 1;


    public Champion(int championId) {
        this.championId = championId;
    }

    public void setKda(float kills, float deaths, float assists) {
        this.kda = new KDA(kills, deaths, assists);
    }

    public KDA getKda() {
        return kda;
    }

    public String getName() {
        return name;
    }

    public int getChampionId() {
        return championId;
    }

    public int getAmountGames() {
        return amountGames;
    }

    public int getPercentGames() {
        return percentGames;
    }

    public int getWinrate() {
        return winrate;
    }

    public int getAvgGameLength() {
        return avgGameLength;
    }

    public float getCsMin() {
        return csMin;
    }
}
