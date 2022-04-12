package main.lunarconcerto.autogetrjdata.Fuction;

import main.lunarconcerto.autogetrjdata.GUI.UIConsolePanel;
import main.lunarconcerto.autogetrjdata.Util.DataBase;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RenameManager {

    public static boolean startRename(List<String> information , String path , JComboBox[] rule , JCheckBox checkBox) throws IOException {
        Properties properties = DataBase.getSETTING();
        String download_image = properties.getProperty("download_image");
        if (download_image!=null && download_image.equals("true")) {
            downloadCover(information , path , checkBox);
        }
        return rename(information , path , rule , checkBox);
    }

    private static void downloadCover(List<String> information , String path , JCheckBox checkBox){
        List<String> imgPath = new ArrayList<>() ;
        for (int i = 5 ; i < information.size(); i++) {
            imgPath.add(information.get(i));
        }
        int i = imgPath.size() ;
        for (String s : imgPath) {
            s = "https:"+s ;
            String[] split = s.split("/");
            String name = split[split.length-1];
            int i1 = name.indexOf(".");
            String substring = name.substring(i1);
            try {
                URL url = new URL(s);

                URLConnection urlConnection = url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();

                String pathName = checkBox.getText();

                OutputStream outputStream = new FileOutputStream(path+"\\"+pathName+"\\IMG"+i+substring);

                int num = 0 ;
                while ((num = inputStream.read()) != -1){
                    outputStream.write(num);
                }

                inputStream.close();
                outputStream.close();

                i-- ;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
