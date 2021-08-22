package main.lunarconcerto.autogetrjdata.Fuction;

import main.lunarconcerto.autogetrjdata.GUI.UIConsolePanel;
import main.lunarconcerto.autogetrjdata.Util.DataBase;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class RenameManager {

    public static boolean startRename(List<String> information , String path , JComboBox[] rule , JCheckBox checkBox) throws IOException {
        return rename(information , path , rule , checkBox);
    }

    private static boolean rename(List<String> information , String path  , JComboBox[] rule , JCheckBox checkBox) throws IOException {
        String name = checkBox.getText();
        File file = new File(path + "\\" + name);
        StringBuilder resultName = new StringBuilder();

        Properties properties = DataBase.getSETTING();
        for (int i = 0 , j = 0; i < rule.length; i++ , j+=2) {
            if (!"-未选择-".equals(rule[i].getSelectedItem())){
                if (rule[i].getSelectedItem()!="作品名"){
                    if (!" ".equals(properties.getProperty("rename_decorate" + j))){
                        resultName.append(properties.getProperty("rename_decorate" + j));
                    }
                    resultName.append(information.get(rule[i].getSelectedIndex()));
                    if (!" ".equals(properties.getProperty("rename_decorate" + (j + 1)))){
                        resultName.append(properties.getProperty("rename_decorate" + (j + 1)));
                    }
                }else {
                    resultName.append(information.get(0));
                }
            }
        }
        resultName = new StringBuilder(checkNamesLegitimacyAndModifyIt(resultName.toString()));

        boolean rename = file.renameTo(new File(path + "\\" + resultName));
        checkBox.setText(resultName.toString());

        System.out.println(resultName);

        if (rename){
            UIConsolePanel.printMessage("重命名完成！新文件名为"+resultName);
        }else {
            UIConsolePanel.printMessage("重命名失败...");
        }
        return rename ;
    }

    private static String checkNamesLegitimacyAndModifyIt(String string){
        return string.replaceAll("[<>:\"|\\\\*?]" , "").replaceAll("/" , "&") ;
    }
}
