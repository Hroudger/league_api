package frontend;

import summoner.Summoner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class OverviewFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private Summoner summoner;
    private final AccountSelector accountSelector;

    private JPanel openedPanel;
    private JCheckBoxMenuItem ranked;
    private JCheckBoxMenuItem normal;
    private JCheckBoxMenuItem aram;

    public OverviewFrame(AccountSelector accountSelector, String summonerName) {
        this.accountSelector = accountSelector;
        accountSelector.setVisible(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(100, 100, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        setLayout(null);
        addMenuItem();
        try {
            summoner = new Summoner("dsad", "DarkBlace", "EUW");
            openedPanel = new EloPanel(ranked.getState(), normal.getState(), aram.getState(), summoner);
        }
        catch (SQLException e) {
            e.getStackTrace();
        }
//        summoner = loadSummoner(summonerName);
        OverviewFrame.this.add(openedPanel);
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
                openedPanel = new ChampionPanel(ranked.getState(), normal.getState(), aram.getState(), summoner);
                OverviewFrame.this.remove(openedPanel);
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
                openedPanel = new GamesPanel(ranked.getState(), normal.getState(), aram.getState(), summoner);
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
                openedPanel = new EloPanel(ranked.getState(), normal.getState(), aram.getState(), summoner);
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
        ranked = new JCheckBoxMenuItem("Ranked Game");
        normal = new JCheckBoxMenuItem("Normal Game");
        aram = new JCheckBoxMenuItem("ARAM Game");
        gameSelection.add(ranked);
        gameSelection.add(normal);
        gameSelection.add(aram);
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
        options.add(update);
        options.add(changeSummoner);
        options.add(end);
        menuBar.add(options);
    }

    private Summoner loadSummoner(String summonerName) {
        return null;
    }
}
