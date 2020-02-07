package frontend;

import region.Region;
import summoner.Summoner;
import utils.CloseUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class OverviewFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private final Summoner summoner;
    private final AccountSelector accountSelector;

    private JPanel openedPanel;
    private JCheckBoxMenuItem soloDuo;
    private JCheckBoxMenuItem flex;

    public OverviewFrame(AccountSelector accountSelector, String summonerName, Region region) {
        this.accountSelector = accountSelector;
        accountSelector.setVisible(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(100, 100, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        setLayout(null);
        addMenuItem();
        summoner = loadSummoner(summonerName, region);
        openedPanel = new EloPanel(summoner);
        OverviewFrame.this.add(openedPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CloseUtils.addCloseListener(this);
        openedPanel.setBounds(0, 0, getWidth(), getHeight());
        openedPanel.setVisible(true);
        setVisible(true);
    }

    private void addAnalysisItems(JMenuBar menuBar) {
        final JMenu analysis = new JMenu("Analyse");
        final JMenuItem champ = new JMenuItem("Champion");
        champ.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OverviewFrame.this.getContentPane().removeAll();
                openedPanel = new ChampionPanel(soloDuo.getState(), flex.getState(), summoner);
                OverviewFrame.this.add(openedPanel);
                openedPanel.setBounds(0, 0, OverviewFrame.this.getWidth(), OverviewFrame.this.getHeight());
                openedPanel.setVisible(true);
                setVisible(false);
                setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseClicked(e);
            }
        });
        final JMenuItem games = new JMenuItem("Spiele");
        games.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OverviewFrame.this.getContentPane().removeAll();
                openedPanel = new GamesPanel(summoner);
                OverviewFrame.this.add(openedPanel);
                openedPanel.setBounds(0, 0, OverviewFrame.this.getWidth(), OverviewFrame.this.getHeight());
                openedPanel.setVisible(true);
                setVisible(false);
                setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseClicked(e);
            }
        });
        final JMenuItem elo = new JMenuItem("Elo");
        elo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OverviewFrame.this.getContentPane().removeAll();
                openedPanel = new EloPanel(summoner);
                OverviewFrame.this.add(openedPanel);
                openedPanel.setBounds(0, 0, OverviewFrame.this.getWidth(), OverviewFrame.this.getHeight());
                openedPanel.setVisible(true);
                setVisible(false);
                setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseClicked(e);
            }
        });
        analysis.add(elo);
        analysis.add(games);
        analysis.add(champ);
        menuBar.add(analysis);
    }

    private void addMenuItem() {
        final JMenuBar menuBar = new JMenuBar();
        addActionItems(menuBar);
        addModeSelection(menuBar);
        addAnalysisItems(menuBar);
        menuBar.setVisible(true);
        setJMenuBar(menuBar);
    }

    private void addModeSelection(JMenuBar menuBar) {
        final JMenu gameSelection = new JMenu("Spielmodus");
        final JMenu rankedSelection = new JMenu("Ranglisten");
        soloDuo = new JCheckBoxMenuItem("Solo/ Duo");
        flex = new JCheckBoxMenuItem("Flex 5 vs 5");
        rankedSelection.add(soloDuo);
        rankedSelection.add(flex);
        gameSelection.add(rankedSelection);
        menuBar.add(gameSelection);
    }

    private void addActionItems(JMenuBar menuBar) {
        final JMenu options = new JMenu("Aktionen");
        final JMenuItem end = new JMenuItem("Beenden");
        end.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                accountSelector.dispose();
                dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseClicked(e);
            }
        });
        final JMenuItem changeSummoner = new JMenuItem("Beschwörer wechseln");
        changeSummoner.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                accountSelector.setVisible(true);
                dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseClicked(e);
            }
        });
        final JMenuItem update = new JMenuItem("Daten aktualisieren");
        update.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    summoner.loadMatchHistory();
                    OverviewFrame.this.getContentPane().removeAll();
                    openedPanel = new EloPanel(summoner);
                    OverviewFrame.this.add(openedPanel);
                    openedPanel.setBounds(0, 0, OverviewFrame.this.getWidth(), OverviewFrame.this.getHeight());
                    openedPanel.setVisible(true);
                    setVisible(false);
                    setVisible(true);
                }
                catch (SQLException ex) {
                    ex.getStackTrace();
                }
                dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseClicked(e);
            }
        });
        options.add(update);
        options.add(changeSummoner);
        options.add(end);
        menuBar.add(options);
    }

    private Summoner loadSummoner(String summonerName, Region region) {
        try {
            return accountSelector.getSummoners().getSummoner(summonerName, region);
        }
        catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }
}
