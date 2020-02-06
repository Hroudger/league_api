package frontend;

import summoner.Summoner;

import javax.swing.*;
import java.awt.*;

public class EloPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Summoner summoner;

    public EloPanel(boolean rankedState, boolean normalState, boolean aramState, Summoner summoner) {
        this.summoner = summoner;
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(100, 100, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        setVisible(true);
        addNameField();
        addEloField();
//        addEloAtSeasonEndField();
//        addDetailedEloField();
//        addBestChampField();
//        addGameLengthField();
    }

    private void addEloField() {
        String elo = "";
        switch (summoner.getElo()) {
            case IRON:
                elo = "Iron.jpg";
                break;
            case BRONZE:
                elo = "Bronze.jpg";
                break;
            case SILVER:
                elo = "Silver.jpg";
                break;
            case GOLD:
                elo = "Gold.jpg";
                break;
            case PLATIN:
                elo = "Platin.jpg";
                break;
            case DIAMOND:
                elo = "Diamond.jpg";
                break;
            case MASTER:
                elo = "Master.jpg";
                break;
            case GRANDMASTER:
                elo = "GrandMaster.jpg";
                break;
            case CHALLENGER:
                elo = "Challenger.jpg";
                break;
        }
        final ImageIcon eloIcon = new ImageIcon("files/" + elo);
        final JLabel iconLabel = new JLabel(summoner.getLp() + "LP", eloIcon, SwingConstants.CENTER);
        add(iconLabel);
        iconLabel.setBounds(50, 250, 300, 300);
        iconLabel.setVisible(true);
    }

    private void addNameField() {
        final JLabel nameLabel = new JLabel("Beschwörername:");
        nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        nameLabel.setBounds(50, 130, 150, 50);
        final JLabel summonerNameLabel = new JLabel(summoner.getName());
        summonerNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        summonerNameLabel.setBounds(50, 150, 150, 50);
        summonerNameLabel.setVisible(true);
        nameLabel.setVisible(true);
        add(nameLabel);
        add(summonerNameLabel);
    }
}
