package main.lunarconcerto.autogetrjdata.GUI;

import main.lunarconcerto.autogetrjdata.Util.DataBase;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

import static javax.swing.BoxLayout.LINE_AXIS;

public class RenameRuleSettingPanel extends JPanel {

    private static String[] names = {"-未选择-" , "RJ号" , "サークル名" , "販売日" , "声優" , "作品名"};
    private static JComboBox[] comboBoxes = new JComboBox[5];
    private static JPanel[] jPanels = new JPanel[5];
    private static JLabel[] jLabels = new JLabel[6];

    public RenameRuleSettingPanel() {
        init();
    }
    private void init(){
        this.setLayout(new GridLayout(0 , 9 , 0 , 0));
        this.setAlignmentX(LEFT_ALIGNMENT);

        jLabels[0] = new JLabel("选择命名规则:");
        jLabels[1] = new JLabel("位1");
        jLabels[2] = new JLabel("位2");
        jLabels[3] = new JLabel("位3");
        jLabels[4] = new JLabel("位4");
        jLabels[5] = new JLabel("位5");

        this.add(jLabels[0]);
        for (int i = 0; i < jPanels.length; i++) {
            comboBoxes[i] = new JComboBox();
            for (String name : names) {
                comboBoxes[i].addItem(name);
            }
            jPanels[i] = new JPanel();
            jPanels[i].setLayout(new BoxLayout(jPanels[i] , LINE_AXIS));
            jPanels[i].add(jLabels[i+1]);
            jPanels[i].add(comboBoxes[i]);
            this.add(jPanels[i]);
        }

        DataBase.setRenameRuleList(comboBoxes);
        DataBase.setRenameRuleName(names);

        loadSetting();
    }

    public void loadSetting(){
        Properties properties = DataBase.getSETTING();
        for (int i = 0; i < comboBoxes.length; i++) {
            String s = properties.getProperty("rename_rule_" + i);
            if (s!=null){
                comboBoxes[i].setSelectedIndex(Integer.valueOf(s));
            }
        }
    }
}
