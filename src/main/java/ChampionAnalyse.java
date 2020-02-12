import frontend.AccountSelector;
import summoner.SummonerList;

import java.sql.SQLException;
import java.util.ArrayList;

public class ChampionAnalyse {

    public static void main(String[] args) throws SQLException {
        AccountSelector accountSelector = new AccountSelector(new SummonerList());
    }
}
