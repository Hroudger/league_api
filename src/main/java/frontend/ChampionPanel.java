package frontend;

import summoner.Summoner;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class ChampionPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final List<Champion> championList;

    public ChampionPanel(boolean rankedState, boolean normalState, boolean aramState, Summoner summoner) {
        championList = summoner.getPlayedChampion(rankedState, normalState, aramState);
        addChampionList();
    }

    private void addChampionList() {

        final TableColumnModel columnModel = new DefaultTableColumnModel();
        addColumnHeader(columnModel, "Champion");
        addColumnHeader(columnModel, "Gesamte Spiele");
        addColumnHeader(columnModel, "% Anteil Spiele");
        addColumnHeader(columnModel, "Winrate");
        addColumnHeader(columnModel, "KD/A");
        addColumnHeader(columnModel, "Durchschnittliche Spieldauer");
        addColumnHeader(columnModel, "CS");
        final TableModel<Champion> championTableModel = new DefaultTableModel();
        for (int i = 0; i < championList.size(); i++) {
            final Champion champion = championList.get(i);
            championTableModel.setValueAt(champion.getName(), 0, i);
            championTableModel.setValueAt(champion.getGamesAmount(), 1, i);
            championTableModel.setValueAt(champion.getGamesPercentage(), 2, i);
            championTableModel.setValueAt(champion.getWinRate(), 3, i);
            final KDA kda = champion.getKDA();
            final String kdaFormatted = kda.getKills() + " / " + kda.getDeaths() + " / " + kda.getAssists();
            championTableModel.setValueAt(kdaFormatted, 4, i);
            championTableModel.setValueAt(champion.getAverageGameLength(), 5, i);
            championTableModel.setValueAt(champion.getCSPerMinute(), 6, i);
        }
        final JTable<Champion> championOverviewTable = new JTable(championTableModel, columnModel);
        championOverviewTable.setBounds(0, 0, getWidth(), getHeight());
        championOverviewTable.setColumnSelectionAllowed(false);
        championOverviewTable.setRowSelectionAllowed(false);
        add(championOverviewTable);
        championOverviewTable.setVisible(true);
    }

    private static void addColumnHeader(TableColumnModel columnModel, String headerName) {
        final TableColumn tableColumn = new TableColumn();
        tableColumn.setHeaderValue(headerName);
        columnModel.addColumn(tableColumn);
    }
}
