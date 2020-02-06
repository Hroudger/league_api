package frontend;

import champion.Champion;
import champion.KDA;
import summoner.Summoner;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class ChampionPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final List<Champion> championList;

    public ChampionPanel(boolean rankedState, boolean normalState, boolean aramState, Summoner summoner) {
        championList = summoner.getChampionList();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(100, 100, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        addChampionList();
        setVisible(true);
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
        final DefaultTableModel championTableModel = new DefaultTableModel();
        for (final Champion champion : championList) {
            championTableModel.addColumn(champion.getName());
            championTableModel.addColumn(champion.getAmountGames());
            championTableModel.addColumn(champion.getPercentGames());
            championTableModel.addColumn(champion.getWinrate());
            final KDA kda = champion.getKda();
            final String kdaFormatted = kda.getKills() + " / " + kda.getDeaths() + " / " + kda.getAssists();
            championTableModel.addColumn(kdaFormatted);
            championTableModel.addColumn(champion.getAvgGameLength());
            championTableModel.addColumn(champion.getCsMin());
        }
        final JTable championOverviewTable = new JTable(championTableModel, columnModel);
        championOverviewTable.getTableHeader().setReorderingAllowed(false);
        championOverviewTable.setBounds(0, 0, getWidth(), getHeight());
        championOverviewTable.setColumnSelectionAllowed(false);
        championOverviewTable.setRowSelectionAllowed(false);
        championOverviewTable.setVisible(true);
        add(championOverviewTable);
    }

    private static void addColumnHeader(TableColumnModel columnModel, String headerName) {
        final TableColumn tableColumn = new TableColumn();
        tableColumn.setHeaderValue(headerName);
        columnModel.addColumn(tableColumn);
    }
}
