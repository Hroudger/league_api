package champion;

public class KDA {
    private float kills;
    private float deaths;
    private float assists;

    public KDA(float kills, float deaths, float assists) {
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
    }

    public float getKills() {
        return kills;
    }

    public float getDeaths() {
        return deaths;
    }

    public float getAssists() {
        return assists;
    }


}
