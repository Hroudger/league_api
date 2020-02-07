package champion;

public class Champion {
    private String name;
    private KDA kda;
    private int championId;
    private int amountGames;
    private int percentGames;
    private int winrate;
    private int avgGameLength;
    private float csMin;


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
