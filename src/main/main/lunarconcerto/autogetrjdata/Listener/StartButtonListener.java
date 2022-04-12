package main.lunarconcerto.autogetrjdata.Listener;

import main.lunarconcerto.autogetrjdata.AutoGetRJData;
import main.lunarconcerto.autogetrjdata.Fuction.DownloadResources;
import main.lunarconcerto.autogetrjdata.Fuction.RenameManager;
import main.lunarconcerto.autogetrjdata.GUI.UIConsolePanel;
import main.lunarconcerto.autogetrjdata.Util.DataBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartButtonListener implements ActionListener {

    private static List<JCheckBox> inLookingFileList;
    private static JComboBox[] renameRuleList ;
    private static String[] ruleNameList ;
    private static String path ;


    @Override
    public void actionPerformed(ActionEvent e) {
        startButtonEvent();
    }

    private void startButtonEvent() {
        try{
        SwingWorker<String, Object> task = new SwingWorker<String, Object>() {
            @Override
            protected String doInBackground() throws Exception {
                inLookingFileList = DataBase.getNeedingRenameFileList();
                path = DataBase.getNeedingRenameFilePath();
                renameRuleList = DataBase.getRenameRuleList();
                ruleNameList = DataBase.getRenameRuleName();

                //运行开始先检查规则是否填写
                List<JCheckBox> needRenameFileList = checkAndSortFileList();
                boolean flag1, flag2;
                flag1 = checkRuleExist();
                flag2 = needRenameFileList.size() != 0;
                if (!flag1 || !flag2) {
                    String message;
                    if (!flag1 && flag2) {
                        message = "未选择命名规则";
                    } else if (flag1) {
                        message = "未选择文件";
                    } else {
                        message = "未选择命名规则\n且未选择文件";
                    }
                    JOptionPane.showMessageDialog(null, message, "提示", JOptionPane.INFORMATION_MESSAGE);


                    UIConsolePanel.printMessage("未运行...由于:" + message);
                } else {
                    //确认文件列表后  进行处理...
                    UIConsolePanel.printMessage("已确认文件，开始进行处理！");
                    for (JCheckBox checkBox : needRenameFileList) {
                        checkBox.setSelected(false);

                        UIConsolePanel.printMessage("处理文件:" + checkBox.getText() + "...");

                        List<String> downloadResources = DownloadResources.startDownloadResources(checkBox.getText());

                        if (downloadResources != null) {

                            boolean ACTION_RESULT =
                                    RenameManager.startRename(downloadResources , path , renameRuleList, checkBox);

                            if (ACTION_RESULT) {
                                UIConsolePanel.printMessage("已完成" + checkBox.getText() + "的重命名工作");
                            }
                        }
                    }
                }
                return null;

            }

            @Override
            protected void done() {

            }
        };
            task.execute();
    }catch (Exception e){
            UIConsolePanel.printMessage(Arrays.toString(e.getStackTrace()));
        }

    }

    private boolean checkRuleExist(){
        int num = 0 ;
        for (JComboBox box : renameRuleList) {
            if (box.getSelectedItem()==ruleNameList[0]){
                num++ ;
            }
        }
        return num != 4;
    }
    private List<JCheckBox> checkAndSortFileList(){
        List<JCheckBox> needRenameFileList = new ArrayList<>();
        for (JCheckBox checkBox : inLookingFileList) {
            if (checkBox.isSelected()){
                needRenameFileList.add(checkBox);
            }
        }
        return needRenameFileList ;
    }
}
