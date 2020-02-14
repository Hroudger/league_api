package frontend;

import champion.KDA;
import summoner.Match;
import summoner.Summoner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class GamesPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final List<Match> matchesList;

    public GamesPanel(Summoner summoner) {
        matchesList = summoner.getMatchList();
        setLayout(new BorderLayout());
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(100, 100, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        addChampionList();
        setVisible(true);
    }

    private void addChampionList() {
        final Vector<String> columnModel = new Vector<>();
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

        final DefaultTableModel gameTableModel = new DefaultTableModel(columnModel, 0);

        final JTable gameOverviewTable = new JTable(gameTableModel);
        gameOverviewTable.getTableHeader().setReorderingAllowed(false);

        final JScrollPane jScrollPane = new JScrollPane(gameOverviewTable);
        jScrollPane.setViewportView(gameOverviewTable);
        jScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);

        gameOverviewTable.setBounds(0, 0, getWidth(), getHeight());
        gameOverviewTable.setColumnSelectionAllowed(false);
        gameOverviewTable.setRowSelectionAllowed(false);
        gameOverviewTable.setDropMode(DropMode.INSERT);
        gameOverviewTable.setRowHeight(20);
        for (final Match match : matchesList) {
            final Object[] content = new Object[11];
            switch (match.getGameMode()) {
                case 420:
                    content[0] = "Ranked Solo/Duo";
                    break;
                case 440:
                    content[0] = "Flex 5 vs 5";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + match.getGameMode());
            }
            if (match.getGameStatus() == 1) {
                content[1] = "Sieg";
            }
            else {
                content[1] = "Niederlage";
            }
            content[2] = match.getChampionId();
            content[3] = match.getGameDuration();
            content[4] = match.getChampLevel();
            content[5] = match.getSpell1() + " " + match.getSpell2();
            final KDA kda = match.getKda();
            final String kdaFormatted = kda.getKills() + " / " + kda.getDeaths() + " / " + kda.getAssists();
            content[6] = kdaFormatted;
            content[7] = match.getCs();
            content[8] = match.getCsMin();
            content[9] = match.getVisionScore();
            content[10] = match.getItems();

            gameTableModel.addRow(content);
        }
        gameOverviewTable.setVisible(true);
        jScrollPane.setVisible(true);
        add(jScrollPane, BorderLayout.CENTER);
    }

    private static void addColumnHeader(Vector<String> columnModel, String headerName) {
        columnModel.addElement(headerName);
    }
}
