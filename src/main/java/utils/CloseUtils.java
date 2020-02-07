package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CloseUtils {

    public static void addCloseListener(Window panel) {
        panel.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                final int confirm = JOptionPane.showOptionDialog(
                    null, "Really quit?",
                    "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    panel.dispose();
                }
            }
        });
    }
}
