package frontend;

import region.Region;
import summoner.Summoner;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AccountSelector extends JFrame {

    private static final long serialVersionUID = 1L;

    private final DefaultListModel<String> summonerDefaultListModel;
    private final JPanel panel;

    private JList<String> summonerSelectionList;
    private JTextField userNameInput;
    private JComboBox<Region> regionSelection;

    public AccountSelector(List<String> summonersList) {
        setBounds(100, 100, 600, 600);
        setLocation(100, 100);
        setLayout(null);
        panel = new JPanel();
        panel.setBounds(0, 0, getWidth(), getHeight());
        summonerDefaultListModel = new DefaultListModel<>();
        summonerDefaultListModel.insertElementAt("DerMalko", 0);
        for (int i = 0; i < summonersList.size(); i++) {
            summonerDefaultListModel.insertElementAt(summonersList.get(i), i);
        }

        initalizeSelectionList();
        initalizeNameInput();
        initalizeConfirmButton();
        initalizeRegionSelection();
        addCloseSettings();
        addSelectionListener();
        panel.setLayout(null);
        panel.setVisible(true);
        add(panel);
        setVisible(true);
        while (3 == 3) {

        }
    }

    private void addSelectionListener() {
        summonerSelectionList.addListSelectionListener(listSelectionEvent -> {
            if (listSelectionEvent.getValueIsAdjusting()) {
                new OverviewFrame(this, listSelectionEvent.getSource().toString());
            }
        });
    }

    private void addCloseSettings() {
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
    }

    private void initalizeRegionSelection() {
        final Region[] regionArray = new Region[2];
        regionArray[0] = Region.EUW;
        regionArray[1] = Region.NA;
        final ComboBoxModel<Region> comboBoxModel = new DefaultComboBoxModel<>(regionArray);
        comboBoxModel.setSelectedItem(Region.EUW);
        regionSelection = new JComboBox<>(comboBoxModel);
        regionSelection.setBounds(350, 450, 100, 100);
        panel.add(regionSelection);
        regionSelection.setVisible(true);
    }

    private void initalizeConfirmButton() {
        final JButton confirmName = new JButton("Bestätigen");
        confirmName.setBounds(450, 450, 100, 100);
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
                    }
                    catch (IOException | NullPointerException ex) {
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

    private static void openNewErrorWindow(String input, Region region) {
        final JFrame errorWindow = new JFrame("Beschwörername existiert nicht!");
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
        userNameInput.setBounds(50, 450, 300, 100);
        panel.add(userNameInput);
        userNameInput.setVisible(true);
    }

    private void initalizeSelectionList() {
        summonerSelectionList = new JList<>(summonerDefaultListModel);
        summonerSelectionList.setBounds(50, 50, 500, 300);
        summonerSelectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        summonerSelectionList.setLayoutOrientation(JList.VERTICAL);
//        final JScrollPane scrollPane = new JScrollPane();
//        scrollPane.setViewportView(summonerSelectionList);
//        panel.add(scrollPane);
        panel.add(summonerSelectionList);
    }
}
