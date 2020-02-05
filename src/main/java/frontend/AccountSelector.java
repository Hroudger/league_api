import summoner.Summoner;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AccountSelector {

    private DefaultListModel<Summoner> summonerDefaultListModel;

    private JPanel panel;
    private JList<Summoner> summonerSelectionList;
    private JTextField userNameInput;
    private JButton confirmName;
    private JPopupMenu regionSelection;

    public AccountSelector(List<Summoner> summonersList) {
        summonerDefaultListModel = new DefaultListModel<>();
        summonerDefaultListModel.insertElementAt(new Summoner(), 0);
        for (int i = 0; i < summonersList.size(); i++) {
            summonerDefaultListModel.insertElementAt(summonersList.get(i), i);
        }
        initalizeComponents();
        int i = 3;
        panel.setSize(500, 500);
        while (i == 3) {

        }
    }

    private void initalizeComponents() {
        summonerSelectionList.setModel(summonerDefaultListModel);
        summonerSelectionList.setAlignmentX(0);
        summonerSelectionList.setAlignmentY(0);
        userNameInput.setAlignmentX(0);
        userNameInput.setAlignmentY(400);
        regionSelection = new JPopupMenu("Region");
        regionSelection.setAlignmentX(300);
        regionSelection.setAlignmentY(400);
        regionSelection.add(new JMenuItem("EUW"));
        regionSelection.setVisible(true);
        summonerSelectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        summonerSelectionList.setLayoutOrientation(JList.VERTICAL);
        confirmName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }
        });
    }

    private void createUIComponents() {
        confirmName = new JButton("Bestätigen");
        confirmName.setSize(100, 100);
        confirmName.setVisible(true);
        confirmName.setAlignmentX(400);
        confirmName.setAlignmentY(400);
    }
}
