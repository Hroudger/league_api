package frontend;

import region.Region;
import summoner.Summoner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class AccountSelector extends JFrame {

    private static final long serialVersionUID = 1L;

    private DefaultListModel<Summoner> summonerDefaultListModel;

    private JList<Summoner> summonerSelectionList;
    private JTextField userNameInput;
    private JButton confirmName;
    private JComboBox<Region> regionSelection;

    public AccountSelector(List<Summoner> summonersList) {
        setBounds(100, 100, 600, 600);
        setLocation(100, 100);
        setLayout(null);
        setForeground(Color.BLUE);
        setBackground(Color.BLUE);
        summonerDefaultListModel = new DefaultListModel<>();
        summonerDefaultListModel.insertElementAt(new Summoner(), 0);
        for (int i = 0; i < summonersList.size(); i++) {
            summonerDefaultListModel.insertElementAt(summonersList.get(i), i);
        }

        initalizeSelectionList();
        initalizeNameInput();
        initalizeConfirmButton();
        initalizeRegionSelection();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                final int confirm = JOptionPane.showOptionDialog(
                    null, "Really quit?",
                    "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    dispose();
                }
            }
        });
        int i = 3;
        while (i == 3) {

        }
    }

    private void initalizeRegionSelection() {
        final Region[] regionArray = new Region[1];
        regionArray[0] = Region.EUW;
        final ComboBoxModel<Region> comboBoxModel = new DefaultComboBoxModel<>(regionArray);
        comboBoxModel.setSelectedItem(Region.EUW);
        regionSelection = new JComboBox<>(comboBoxModel);
        regionSelection.setBounds(350, 450, 100, 100);
        add(regionSelection);
        regionSelection.setVisible(true);
    }

    private void initalizeConfirmButton() {
        confirmName = new JButton("Bestätigen");
        confirmName.setBounds(450, 450, 100, 100);
        add(confirmName);
        confirmName.setVisible(true);
        confirmName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!userNameInput.getText().isEmpty()) {
                    //TODO add summoner name and region here
                    summonerDefaultListModel.addElement(new Summoner());
                    userNameInput.setText("");
                }
            }
        });
    }

    private void initalizeNameInput() {
        userNameInput = new JTextField();
        userNameInput.setBounds(50, 450, 300, 100);
        add(userNameInput);
        userNameInput.setVisible(true);
    }

    private void initalizeSelectionList() {
        summonerSelectionList = new JList<>(summonerDefaultListModel);
        summonerSelectionList.setBounds(50, 50, 500, 300);
        summonerSelectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        summonerSelectionList.setLayoutOrientation(JList.VERTICAL);
        add(summonerSelectionList);
        summonerSelectionList.setVisible(true);
    }
}
