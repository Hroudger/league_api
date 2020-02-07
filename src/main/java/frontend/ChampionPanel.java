package frontend;

import champion.Champion;
import champion.KDA;
import summoner.Summoner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class ChampionPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final List<Champion> championList;

    public ChampionPanel(boolean rankedState, boolean normalState, Summoner summoner) {
        championList = summoner.getChampionList();
        final Champion champion = new Champion(2);
        champion.setKda(10, 2, 5);
        championList.add(champion);
        setLayout(new BorderLayout());
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(100, 100, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        addChampionList();
        setVisible(true);
    }

    private void addChampionList() {

        final Vector<String> columnModel = new Vector<>();
        addColumnHeader(columnModel, "Champion");
        addColumnHeader(columnModel, "Gesamte Spiele");
        addColumnHeader(columnModel, "% Anteil Spiele");
        addColumnHeader(columnModel, "Winrate");
        addColumnHeader(columnModel, "KD/A");
        addColumnHeader(columnModel, "Durchschnittliche Spieldauer");
        addColumnHeader(columnModel, "CS");
        final DefaultTableModel championTableModel = new DefaultTableModel(columnModel, 0);

        final JTable championOverviewTable = new JTable(championTableModel);
        championOverviewTable.getTableHeader().setReorderingAllowed(false);

        final JScrollPane jScrollPane = new JScrollPane(championOverviewTable);
        jScrollPane.setViewportView(championOverviewTable);
        jScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);

        championOverviewTable.setBounds(0, 0, getWidth(), getHeight());
        championOverviewTable.setColumnSelectionAllowed(false);
        championOverviewTable.setRowSelectionAllowed(false);
        championOverviewTable.setDropMode(DropMode.INSERT);
        championOverviewTable.setRowHeight(20);
        for (final Champion champion : championList) {
            final Object[] content = new Object[7];
            content[0] = champion.getName();
            content[1] = champion.getAmountGames();
            content[2] = champion.getPercentGames() + "%";
            content[3] = champion.getWinrate() + "%";
            final KDA kda = champion.getKda();
            final String kdaFormatted = kda.getKills() + " / " + kda.getDeaths() + " / " + kda.getAssists();
            content[4] = kdaFormatted;
            content[5] = champion.getAvgGameLength() + "min";
            content[6] = champion.getCsMin();

            championTableModel.addRow(content);
        }
        championOverviewTable.setVisible(true);
        jScrollPane.setVisible(true);
        add(jScrollPane, BorderLayout.CENTER);
    }

    private static void addColumnHeader(Vector<String> columnModel, String headerName) {
        columnModel.addElement(headerName);
    }
}
