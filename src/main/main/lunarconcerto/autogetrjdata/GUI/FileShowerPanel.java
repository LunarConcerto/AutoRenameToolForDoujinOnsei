package main.lunarconcerto.autogetrjdata.GUI;
import main.lunarconcerto.autogetrjdata.Listener.ShowFileMenuButtonListener;
import main.lunarconcerto.autogetrjdata.Util.DataBase;
import main.lunarconcerto.autogetrjdata.Util.Util;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileShowerPanel extends JPanel {

    //复选框集合，顺便存储文件名
    private List<JCheckBox> jCheckboxList = new ArrayList<>();

    private JScrollPane pathScroll;
    private static JPanel checkBoxPanel = new JPanel();
    private static JLabel pathLabel = new JLabel("文件列表");
    private static JPanel upPanel = new JPanel();

    //全选，反选和取消选择和刷新的四个按钮
    private static JButton[] buttons =  new JButton[4];

    public FileShowerPanel() {
        init();
        this.add(upPanel , BorderLayout.NORTH);
        this.add(pathScroll , BorderLayout.CENTER);
    }

    private void init(){
        this.setLayout(new BorderLayout());
        this.setAlignmentX(LEFT_ALIGNMENT);

        upPanel.setLayout(new GridLayout(0 , 10 , 0 , 0));
        upPanel.setSize(200 , 30);
        upPanel.add(pathLabel);

        ShowFileMenuButtonListener showFileMenuButtonListener = new ShowFileMenuButtonListener(this.jCheckboxList);
        buttons[0] = CreateButton("全选" , "check_all");
        buttons[1] = CreateButton("反选" , "inverse");
        buttons[2] = CreateButton("取消选择" , "cancel");
        buttons[3] = CreateButton("刷新" , "refresh" );
        for (JButton button : buttons) {
            button.addActionListener(showFileMenuButtonListener);
            upPanel.add(button);
        }

        pathScroll = new JScrollPane(checkBoxPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        checkBoxPanel.setAlignmentX(LEFT_ALIGNMENT);
    }

    //处理选择好文件路径时的导入操作
    private void inputFileList(List<String> list, String path){

        //处理新导入时 旧导入则舍弃  全部清空
        for (JCheckBox checkBox : jCheckboxList) {
            checkBoxPanel.remove(checkBox);
        }
        jCheckboxList.clear();

        //列表最小行数至少为20
        if (list.size() < 20) {
            checkBoxPanel.setLayout(new GridLayout(20 , 1 , 0 , 0));
        }else {
            checkBoxPanel.setLayout(new GridLayout(list.size() , 1 , 0 , 0));
        }

        int index = 0;
        for (String s : list) {
            String result = Util.DeleteString(s , path);
            JCheckBox checkBox = new JCheckBox(result);
            checkBox.setActionCommand(""+index++);
            checkBoxPanel.add(checkBox);
            jCheckboxList.add(checkBox);
        }

        //打印日志
        UIConsolePanel.printMessage("已展示"+path+"中的"+list.size()+"个文件");
        DataBase.setNeedingRenameFilePath(path);
        DataBase.setNeedingRenameFileList(jCheckboxList);
        checkBoxPanel.updateUI();
    }

    private static JButton CreateButton(String key1 , String key2){
        JButton button = new JButton();
        button.setText(key1);
        button.setActionCommand(key2);
        return button;
    }

    public void fileInput(String Path){
        List<String> fileList = new ArrayList<>();
        File file = new File(Path);
        if (file.exists()){
            File[] files = file.listFiles();
            if (files!=null || files.length != 0){
                fileList.clear();
                for (File FILE : files) {
                    fileList.add(FILE.toString());
                }
                inputFileList(fileList , Path);
            }
        }
        fileList.clear();
    }

    public void refresh(){
        if (DataBase.getNeedingRenameFilePath() != null){
            fileInput(DataBase.getNeedingRenameFilePath());
        }else {
            UIConsolePanel.printMessage("未指定文件路径，无法刷新");
        }
    }

}
