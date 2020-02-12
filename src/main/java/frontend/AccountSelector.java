package frontend;

import region.Region;
import summoner.Summoner;
import summoner.SummonerList;
import utils.CloseUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

public class AccountSelector extends JFrame {

    private static final long serialVersionUID = 1L;

    private final DefaultListModel<String> summonerDefaultListModel;
    private final JPanel panel;
    private final SummonerList summonersList;

    private JList<String> summonerSelectionList;
    private JTextField userNameInput;
    private JComboBox<Region> regionSelection;

    public AccountSelector(SummonerList summonersList) {
        setBounds(100, 100, 600, 600);
        setLocation(100, 100);
        setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setBounds(0, 0, getWidth(), getHeight());
        summonerDefaultListModel = new DefaultListModel<>();
        this.summonersList = summonersList;
        final List<Summoner> summoners = summonersList.getSummonerList();
        for (Summoner summoner : summoners) {
            final String region = "    ";
            final StringBuilder sb = new StringBuilder(region);
            final String regionUnprepared = summoner.getRegion().toString();
            for (int i = 0; i < regionUnprepared.toCharArray().length; i++) {
                sb.setCharAt(i, regionUnprepared.toCharArray()[i]);
            }
            summonerDefaultListModel.addElement(region + summoner.getName());
        }
        initalizeSelectionList();
        initalizeNameInput();
        initalizeConfirmButton();
        initalizeRegionSelection();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CloseUtils.addCloseListener(this);
        initalizeNextButton();
        initalizeDeleteButton();
        panel.setLayout(new BorderLayout());
        panel.setVisible(true);
        add(panel);
        setVisible(true);
        setResizable(false);
        while (3 == 3) {

        }
    }

    private void initalizeRegionSelection() {
        final Region[] regionArray = new Region[2];
        regionArray[0] = Region.EUW;
        regionArray[1] = Region.NA;
        final ComboBoxModel<Region> comboBoxModel = new DefaultComboBoxModel<>(regionArray);
        comboBoxModel.setSelectedItem(Region.EUW);
        regionSelection = new JComboBox<>(comboBoxModel);
        regionSelection.setBounds(350, 350, 100, 50);
        panel.add(regionSelection);
        regionSelection.setVisible(true);
    }

    private void initalizeConfirmButton() {
        final JButton confirmName = new JButton("Bestätigen");
        confirmName.setBounds(450, 350, 100, 50);
        panel.add(confirmName);
        confirmName.setVisible(true);
        confirmName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final String name = userNameInput.getText();
                if (!name.isEmpty()) {
                    if (isNameAlreadySaved(name)) {
                        userNameInput.setText("");
                        return;
                    }
                    try {
                        Summoner.addSummoner(userNameInput.getText(), Objects.requireNonNull(regionSelection.getSelectedItem()).toString());
                    } catch (Exception ex) {
                        openNewErrorWindow(name, (Region) Objects.requireNonNull(regionSelection.getSelectedItem()));
                    }
                    final String region = "    ";
                    final StringBuilder sb = new StringBuilder(region);
                    final String regionUnprepared = regionSelection.getSelectedItem().toString();
                    for (int i = 0; i < regionUnprepared.toCharArray().length; i++) {
                        sb.setCharAt(i, regionUnprepared.toCharArray()[i]);
                    }
                    summonerDefaultListModel.addElement(sb.toString() + " " + userNameInput.getText());
                    userNameInput.setText("");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseClicked(e);
            }
        });
    }

    private void initalizeNextButton() {
        final JButton nextName = new JButton("Fortfahren");
        nextName.setBounds(350, 450, 200, 50);
        panel.add(nextName);
        nextName.setVisible(true);
        nextName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final String summonerNameAndRegion = summonerSelectionList.getSelectedValue();
                if (summonerNameAndRegion == null) {
                    return;
                }
                final String summonerName = summonerNameAndRegion.substring(4);
                final Region region;
                switch (summonerNameAndRegion.substring(0, 5).trim()) {
                    case "NA":
                        region = Region.NA;
                        break;
                    case "EUW":
                        region = Region.EUW;
                        break;
                    default:
                        region = Region.EUW;
                }
                new OverviewFrame(AccountSelector.this, summonerName, region);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseClicked(e);
            }
        });
    }

    private void initalizeDeleteButton() {
        final JButton confirmName = new JButton("Entfernen");
        confirmName.setBounds(50, 450, 200, 50);
        panel.add(confirmName);
        confirmName.setVisible(true);
        confirmName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final int selectedIndex = summonerSelectionList.getSelectedIndex();
                if (selectedIndex != -1) {
                    ((DefaultListModel<String>) summonerSelectionList.getModel()).remove(selectedIndex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseClicked(e);
            }
        });
    }

    private static void openNewErrorWindow(String input, Region region) {
        final JFrame errorWindow = new JFrame("Beschwörername existiert nicht!");
        errorWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorWindow.setBounds(300, 300, 300, 300);
        final JLabel label = new JLabel(input + " ist kein existiertender Beschwörer für den Server:" + region.toString());
        errorWindow.add(label);
        label.setBounds(100, 100, 200, 100);
        label.setVisible(true);
        errorWindow.setVisible(true);
    }

    private boolean isNameAlreadySaved(String name) {
        for (int i = 0; i < summonerDefaultListModel.getSize(); i++) {
            final String data = summonerDefaultListModel.get(i);
            final String savedName = data.substring(5).trim();
            if (savedName.equals(name)) {
                final String region = data.substring(0, 5).trim();
                if (region.equals(Objects.requireNonNull(regionSelection.getSelectedItem()).toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void initalizeNameInput() {
        userNameInput = new JTextField();
        userNameInput.setBounds(50, 350, 300, 50);
        panel.add(userNameInput);
        userNameInput.setVisible(true);
    }

    private void initalizeSelectionList() {
        summonerSelectionList = new JList<>(summonerDefaultListModel);
        summonerSelectionList.setBounds(50, 50, 500, 300);
        summonerSelectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        summonerSelectionList.setLayoutOrientation(JList.VERTICAL);
        final JScrollPane scrollPane = new JScrollPane(summonerSelectionList);
        scrollPane.setBounds(50, 50, 500, 300);
        scrollPane.setViewportView(summonerSelectionList);
        final JPanel scrollPanel = new JPanel(new BorderLayout());
        scrollPanel.setBounds(0, 0, 500, 300);
        scrollPanel.setVisible(true);
        scrollPanel.add(summonerSelectionList, BorderLayout.NORTH);
        scrollPane.add(scrollPanel);
        scrollPane.setVisible(true);
        panel.add(scrollPane);
    }

    public SummonerList getSummoners() {
        return summonersList;
    }
}
