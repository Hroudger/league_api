package summoner;

import service.SummonerListService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SummonerList {

    private List<Summoner> summoners = new ArrayList<>();


    public SummonerList() throws SQLException {
        load();
    }

    private void load() throws SQLException {
        SummonerListService summonerListService = new SummonerListService();

        summoners = summonerListService.getSummonerList();
    }

    public void reload() throws SQLException {
        load();
    }


    public List<Summoner> getSummonerList() {
        return summoners;
    }

    public Summoner getSummoner(String name, String region) throws Exception {
        for (Summoner summoner : summoners) {
            if (summoner.getName().equals(name) && summoner.getRegion().equals(region)) {
                return summoner;
            }
        }
        throw new Exception("Summoner not found with region: " + region + " name: " + name);
    }

}
