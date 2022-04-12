package main.lunarconcerto.autogetrjdata;

import main.lunarconcerto.autogetrjdata.Fuction.SProxy;
import main.lunarconcerto.autogetrjdata.GUI.*;
import main.lunarconcerto.autogetrjdata.Util.DataBase;
import main.lunarconcerto.autogetrjdata.Util.Util;

import java.io.*;
import java.util.Properties;

public class AutoGetRJData {

    public static final String VERSION = "a0.6";

    public static SettingFrame SETTING_WINDOW ;
    public static AppMainWindowFrame MAIN_WINDOW ;

    public static void main(String[] args) throws Exception {
        loadProperties();
        MAIN_WINDOW = new AppMainWindowFrame();
        DataBase.setPROXY(new SProxy());
        SETTING_WINDOW = new SettingFrame();
    }

    private static void loadProperties() throws Exception {
        Properties properties = new Properties();
        File file = new File(Util.getUserDir() + "\\rename_tool_setting.xml");
        if (file.exists()){
            FileInputStream fileInputStream = new FileInputStream(file);
            properties.loadFromXML(fileInputStream);
            fileInputStream.close();
        }
        DataBase.setSETTING(properties);
    }

    public static void saveProperties(){
        try {
            Properties properties = DataBase.getSETTING();
            File file = new File(Util.getUserDir() + "\\rename_tool_setting.xml");
            if (file.exists()){
                file.delete();
            }
            FileOutputStream writer = new FileOutputStream(file, false);
            properties.storeToXML(writer, "A setting key list using by rename tool");
            writer.close();
            Runtime.getRuntime().exec("attrib " + "\"" + file.getAbsolutePath() + "\"" + " +H");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
