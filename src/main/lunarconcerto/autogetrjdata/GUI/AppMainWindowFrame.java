package main.lunarconcerto.autogetrjdata.GUI;

import main.lunarconcerto.autogetrjdata.AutoGetRJData;
import main.lunarconcerto.autogetrjdata.Listener.FileChooserListener;
import main.lunarconcerto.autogetrjdata.Util.DataBase;
import main.lunarconcerto.autogetrjdata.Util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

public class AppMainWindowFrame extends JFrame {

    private static FileShowerPanel fileShowerPanel = new FileShowerPanel();
    private static RenameRuleSettingPanel renameRuleSettingPanel = new RenameRuleSettingPanel();
    private static UIConsolePanel uiConsolePanel = new UIConsolePanel();

    public AppMainWindowFrame() {
        Init();
    }

    private void Init(){

        int screenHeight = Util.getScreen().height;
        int screenWidth = Util.getScreen().width;

        this.setBounds(screenWidth / 4 , screenHeight / 4 , 1152 , 648);
        this.setTitle("文件自动重命名工具");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //关闭窗口时，保存设置
                JComboBox[] boxes = DataBase.getRenameRuleList();
                Properties properties = DataBase.getSETTING();

                for (int i = 0; i < boxes.length; i++) {
                    properties.setProperty("rename_rule_"+i , String.valueOf(boxes[i].getSelectedIndex()));
                }
                if (DataBase.getNeedingRenameFilePath()!=null){
                    properties.setProperty("selected_path" , DataBase.getNeedingRenameFilePath());
                }

                AutoGetRJData.saveProperties();
            }
        });

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(this.CreateFileChooserPanel() , BorderLayout.NORTH);
        this.getContentPane().add(fileShowerPanel , BorderLayout.CENTER);
        this.getContentPane().add(uiConsolePanel, BorderLayout.EAST);
        this.getContentPane().add(renameRuleSettingPanel, BorderLayout.SOUTH);

        this.setResizable(false);
        this.setVisible(true);
    }

    private JPanel CreateFileChooserPanel(){
        JPanel jPanel = new JPanel();
        JLabel label = new JLabel("所选文件路径:");
        label.setSize(1152/16 , 648/16);
        label.setLocation(0 , 0);
        JTextField textField = new JTextField(25);
        Properties properties = DataBase.getSETTING();
        if (properties!=null){
            String path = properties.getProperty("selected_path");
            if (path!=null){
                textField.setText(path);
                inputFile(path);
            }
        }else {
            textField.setText("未选择文件");
        }
        JButton jButton = new JButton("浏览");
        jPanel.add(label);
        jPanel.add(textField);
        jPanel.add(jButton);
        FileChooserListener listener = new FileChooserListener(textField);
        jButton.addActionListener(listener);
        return jPanel;
    }

    public static void inputFile(String Path){
        fileShowerPanel.fileInput(Path);
    }

    public static void refresh(){
        fileShowerPanel.refresh();
    }
}
