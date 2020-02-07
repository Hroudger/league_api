package service;

import region.Region;
import summoner.Elo;
import summoner.Summoner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SummonerService {

    public Summoner getSummoner(String id) throws SQLException {

        final String sql = "SELECT * FROM summoners WHERE id = ?";
        final PreparedStatement stmt = DataBaseConnector.getPreparedStatement(sql, id);

        final ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            final String name = rs.getNString("name");
            final String region = rs.getNString("region");
            final Region regionAsEnum;
            switch (region) {
                case "EUW":
                    regionAsEnum = Region.EUW;
                    break;
                case "NA":
                    regionAsEnum = Region.NA;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + region);
            }
            final String elo = rs.getNString("solorank");
            final Elo eloAsEnum;
            switch (elo) {
                case "IRON":
                    eloAsEnum = Elo.IRON;
                    break;
                case "BRONZE":
                    eloAsEnum = Elo.BRONZE;
                    break;
                case "SILVER":
                    eloAsEnum = Elo.SILVER;
                    break;
                case "GOLD":
                    eloAsEnum = Elo.GOLD;
                    break;
                case "PLATIN":
                    eloAsEnum = Elo.PLATIN;
                    break;
                case "DIAMOND":
                    eloAsEnum = Elo.DIAMOND;
                    break;
                case "MASTER":
                    eloAsEnum = Elo.MASTER;
                    break;
                case "GRANDMASTER":
                    eloAsEnum = Elo.GRANDMASTER;
                    break;
                case "CHALLENGER":
                    eloAsEnum = Elo.CHALLENGER;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + elo);
            }
            final int divison = rs.getInt("solotier");
            final int lp = rs.getInt("sololp");
            final String summonerId = rs.getNString("id");
            return new Summoner(summonerId, name, regionAsEnum, eloAsEnum, divison, lp);
        }

        return null;
    }
}
