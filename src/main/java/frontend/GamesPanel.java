package frontend;

import summoner.Match;
import summoner.Summoner;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
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
        final TableColumnModel columnModel = new DefaultTableColumnModel();
        addColumnHeader(columnModel, "Spielmodus");
        addColumnHeader(columnModel, "Ergebnis");
        addColumnHeader(columnModel, "Champion");
        addColumnHeader(columnModel, "Spieldauer");
        addColumnHeader(columnModel, "Level");
        addColumnHeader(columnModel, "Summoner Spells");
        addColumnHeader(columnModel, "KD/A");
        addColumnHeader(columnModel, "CS");
        addColumnHeader(columnModel, "CS/min");
        addColumnHeader(columnModel, "Visionscore");
        addColumnHeader(columnModel, "Item");

        final TableModel championTableModel = new DefaultTableModel();
        for (int i = 0; i < matchesList.size(); i++) {
            final Match match = matchesList.get(i);
            championTableModel.setValueAt(match.getGameMode(), 0, i);
            championTableModel.setValueAt(match.getGameState(), 1, i);
            championTableModel.setValueAt(match.getChampion(), 2, i);
            championTableModel.setValueAt(match.getPlayDuration(), 3, i);
            championTableModel.setValueAt(match.getLevel(), 4, i);
            championTableModel.setValueAt(match.getSummonerSpells(), 5, i);
            final KDA kda = match.getKDA();
            final String kdaFormatted = kda.getKills() + " / " + kda.getDeaths() + " / " + kda.getAssists();
            championTableModel.setValueAt(kdaFormatted, 6, i);
            championTableModel.setValueAt(match.getAllCS(), 7, i);
            championTableModel.setValueAt(match.getCsPerMinute(), 8, i);
            championTableModel.setValueAt(match.getVisionScore(), 9, i);
            championTableModel.setValueAt(match.getItems(), 10, i);
        }
        final JTable championOverviewTable = new JTable(championTableModel, columnModel);

        championOverviewTable.setBounds(0, 0, getWidth(), getHeight());
        championOverviewTable.setColumnSelectionAllowed(false);
        championOverviewTable.setRowSelectionAllowed(false);
        championOverviewTable.setDropMode(DropMode.INSERT);
        championOverviewTable.setRowHeight(20);
        add(championOverviewTable);
        championOverviewTable.setVisible(true);
    }

    private static void addColumnHeader(TableColumnModel columnModel, String headerName) {
        final TableColumn tableColumn = new TableColumn();
        tableColumn.setHeaderValue(headerName);
        columnModel.addColumn(tableColumn);
    }
}
