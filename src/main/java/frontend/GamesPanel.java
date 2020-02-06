package frontend;

import champion.KDA;
import summoner.Match;
import summoner.Summoner;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class GamesPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final List<Match> matchesList;

    public GamesPanel(boolean rankedState, boolean normalState, boolean aramState, Summoner summoner) {
        matchesList = summoner.getMatchList();
        setLayout(new BorderLayout());
        matchesList.add(new Match("1", "EUW", "1S", 34, 1, 16, 1, 300, 2, 3, List.of(3, 4, 5), new KDA(5, 1, 2), 2, 17));
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(100, 100, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        addChampionList();
        setVisible(true);
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

        final DefaultTableModel gameTableModel = new DefaultTableModel();
        for (final Match match : matchesList) {
            gameTableModel.addColumn(match.getGameMode());
            gameTableModel.addColumn(match.getGameStatus());
            gameTableModel.addColumn(match.getChampionId());
            gameTableModel.addColumn(match.getGameDuration());
            gameTableModel.addColumn(match.getChampLevel());
            gameTableModel.addColumn(match.getSpell1() + " " + match.getSpell2());
            final KDA kda = match.getKda();
            final String kdaFormatted = kda.getKills() + " / " + kda.getDeaths() + " / " + kda.getAssists();
            gameTableModel.addColumn(kdaFormatted);
            gameTableModel.addColumn(match.getCs());
            gameTableModel.addColumn(match.getCsMin());
            gameTableModel.addColumn(match.getVisionScore());
            gameTableModel.addColumn(match.getItems());
        }
        final JTable gameOverviewTable = new JTable(gameTableModel, columnModel);
        gameOverviewTable.getTableHeader().setReorderingAllowed(false);
        final JScrollPane jScrollPane = new JScrollPane(gameOverviewTable);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        gameOverviewTable.setBounds(0, 0, getWidth(), getHeight());
        gameOverviewTable.setColumnSelectionAllowed(false);
        gameOverviewTable.setRowSelectionAllowed(false);
        gameOverviewTable.setDropMode(DropMode.INSERT);
        gameOverviewTable.setRowHeight(20);
        gameOverviewTable.setVisible(true);
        jScrollPane.setVisible(true);
        add(jScrollPane, BorderLayout.CENTER);
    }

    private static void addColumnHeader(TableColumnModel columnModel, String headerName) {
        final TableColumn tableColumn = new TableColumn();
        tableColumn.setHeaderValue(headerName);
        columnModel.addColumn(tableColumn);
    }
}
