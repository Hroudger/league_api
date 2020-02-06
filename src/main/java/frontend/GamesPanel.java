package frontend;

import summoner.Match;
import summoner.Summoner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;

public class GamesPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final List<Match> matchesList;

    public GamesPanel(boolean rankedState, boolean normalState, boolean aramState, Summoner summoner) {
        matchesList = summoner.getMatches(rankedState, normalState, aramState);
        addChampionList();
    }

    private void addChampionList() {
        final String[] columnNames = {
            "Spielmodus",
            "Ergebnis",
            "Champion",
            "Spieldauer",
            "Level",
            "Summoner Spells",
            "KeyStone",
            "KD/A",
            "CS",
            "CS/min",
            "Gesetzte Augen/min",
            "Items"
        };
        final TableModel championTableModel = new DefaultTableModel();
        for (int i = 0; i < matchesList.size(); i++) {
            final Match match = matchesList.get(i);
            championTableModel.setValueAt(match.getGameMode(), 0, i);
            championTableModel.setValueAt(match.getGameState(), 1, i);
            championTableModel.setValueAt(match.getChampion(), 2, i);
            championTableModel.setValueAt(match.getPlayDuration(), 3, i);
            championTableModel.setValueAt(match.getLevel(), 4, i);
            championTableModel.setValueAt(match.getSummonerSpells(), 5, i);
            championTableModel.setValueAt(match.getKeyStone(), 6, i);
            final KDA kda = match.getKDA();
            final String kdaFormatted = kda.getKills() + " / " + kda.getDeaths() + " / " + kda.getAssists();
            championTableModel.setValueAt(kdaFormatted, 7, i);
            championTableModel.setValueAt(match.getAllCS(), 8, i);
            championTableModel.setValueAt(match.getCsPerMinute(), 9, i);
            championTableModel.setValueAt(match.getWardsPerMinute(), 10, i);
            championTableModel.setValueAt(match.getItems(), 10, i);
        }
        final JTable championOverviewTable = new JTable(, columnNames);
        championOverviewTable.setBounds(0, 0, getWidth(), getHeight());
        championOverviewTable.setColumnSelectionAllowed(false);
        championOverviewTable.setRowSelectionAllowed(false);
        add(championOverviewTable);
        championOverviewTable.setVisible(true);
    }
}
