package main.lunarconcerto.autogetrjdata.Listener;

import main.lunarconcerto.autogetrjdata.GUI.AppMainWindowFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowFileMenuButtonListener implements ActionListener {

    private List<JCheckBox> checkBoxes ;

    public ShowFileMenuButtonListener(List<JCheckBox> checkBoxList) {
        checkBoxes = checkBoxList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("check_all".equals(e.getActionCommand())){
            for (JCheckBox checkBox : checkBoxes) {
                checkBox.setSelected(true);
            }
        }else if ("inverse".equals(e.getActionCommand())){
            for (JCheckBox checkBox : checkBoxes) {
                checkBox.setSelected(!checkBox.isSelected());
            }
        }else if ("cancel".equals(e.getActionCommand())){
            for (JCheckBox checkBox : checkBoxes) {
                checkBox.setSelected(false);
            }
        }else if ("refresh".equals(e.getActionCommand())){
            AppMainWindowFrame.refresh();
        }
    }
}
