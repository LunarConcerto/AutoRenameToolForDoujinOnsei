package main.lunarconcerto.autogetrjdata.Listener;
import main.lunarconcerto.autogetrjdata.GUI.UIConsolePanel;
import main.lunarconcerto.autogetrjdata.Util.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static main.lunarconcerto.autogetrjdata.GUI.AppMainWindowFrame.inputFile;

public class FileChooserListener implements ActionListener {

    private JTextField field ;
    private String Path = " ";

    public FileChooserListener(JTextField textField) {
        field = textField;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        JFileChooser chooser ;
        if (" ".equals(Path)){
            chooser = new JFileChooser(Util.getUserDir());
        }else {
            chooser = new JFileChooser(Path);
        }
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int val = chooser.showOpenDialog(null);
        if (val == JFileChooser.APPROVE_OPTION){
            field.setText(chooser.getSelectedFile().toString());
            UIConsolePanel.printMessage("已选择路径:"+chooser.getSelectedFile().toString());
            inputFile(chooser.getSelectedFile().toString());
            Path = chooser.getSelectedFile().toString();
        }else if (val == JFileChooser.CANCEL_OPTION && !" ".equals(Path)){
            field.setText(Path);
        }else {
            field.setText("未选择文件");
        }
    }
}
