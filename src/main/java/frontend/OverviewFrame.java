package frontend;

import summoner.Summoner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OverviewFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final Summoner summoner;
    private final AccountSelector accountSelector;

    public OverviewFrame(AccountSelector accountSelector, String summonerName) {
        this.accountSelector = accountSelector;
        accountSelector.setVisible(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(100, 100, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        setLayout(null);
        setForeground(Color.BLUE);
        setBackground(Color.BLUE);
        addMenuItem();
        setVisible(true);
        summoner = loadSummoner(summonerName);
    }

    private void addMenuItem() {
        final JMenuBar menuBar = new JMenuBar();
        addActionItems(menuBar);
        addModeSelection(menuBar);
        menuBar.setVisible(true);
        setJMenuBar(menuBar);
    }

    private void addModeSelection(JMenuBar menuBar) {
        final JMenu gameSelection = new JMenu("Spielmodus");
        final JCheckBoxMenuItem ranked = new JCheckBoxMenuItem("Ranked Game");
        final JCheckBoxMenuItem normal = new JCheckBoxMenuItem("Normal Game");
        final JCheckBoxMenuItem aram = new JCheckBoxMenuItem("ARAM Game");
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
