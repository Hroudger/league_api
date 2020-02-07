package frontend;

import summoner.Summoner;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EloPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Summoner summoner;

    public EloPanel(boolean rankedState, boolean normalState, Summoner summoner) {
        this.summoner = summoner;
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(100, 100, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        setVisible(true);
        setLayout(new GridLayout(0, 3));
        add(addNameField());
        add(addRegionField());
        add(addEloField());
        add(addBestChampField());
        add(addAvergageGameLength());
    }

    private JPanel addAvergageGameLength() {
        final JPanel avgGameLength = new JPanel();
        final JLabel text = new JLabel("Durschnittliche Spieldauer");
        final JLabel minutes = new JLabel(summoner.getAvgGameLength() + "min");
        avgGameLength.add(text);
        avgGameLength.add(minutes);
        return avgGameLength;
    }

    private JPanel addBestChampField() {
        final JPanel bestChampPanel = new JPanel();
        final JLabel bestChampText = new JLabel("Best Champ");
        bestChampPanel.add(bestChampText);
        final String bestChampName = summoner.getBestChampion().getName();
        try {
            final BufferedImage myPicture = ImageIO.read(new File("files/champion/" + bestChampName + ".png"));
            final JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setVisible(true);
            bestChampPanel.add(picLabel);
        }
        catch (IOException e) {
            e.getStackTrace();
        }
        return bestChampPanel;
    }

    private JPanel addEloField() {
        final JPanel eloPanel = new JPanel();
        final JLabel eloText = new JLabel("Elo:" + summoner.getElo().toString() + summoner.getDivision() + " " + summoner.getLp() + "LP");
        eloPanel.add(eloText);
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
        try {
            final BufferedImage myPicture = ImageIO.read(new File("files/" + elo));
            final JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setVisible(true);
            eloPanel.add(picLabel);
            return eloPanel;
        }
        catch (IOException e) {
            e.getStackTrace();
        }
        return null;
    }

    private JLabel addNameField() {
        final JLabel nameLabel = new JLabel("Beschwörername: " + summoner.getName());
        nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        nameLabel.setBounds(50, 130, 150, 50);
        nameLabel.setVisible(true);
        return nameLabel;
    }

    private JLabel addRegionField() {
        final JLabel regionLabel = new JLabel("Server: " + summoner.getRegion());
        regionLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        regionLabel.setBounds(50, 150, 150, 50);
        regionLabel.setVisible(true);
        return regionLabel;
    }
}
