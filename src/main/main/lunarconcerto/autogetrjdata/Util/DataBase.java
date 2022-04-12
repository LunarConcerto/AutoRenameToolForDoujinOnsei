package main.lunarconcerto.autogetrjdata.Util;

import main.lunarconcerto.autogetrjdata.Fuction.SProxy;

import javax.swing.*;
import java.util.List;
import java.util.Properties;

public class DataBase {

    private static JComboBox[] RENAME_RULE_LIST ;
    private static String[] RENAME_RULE_NAME ;
    private static String NEEDING_RENAME_FILE_PATH ;
    private static List<JCheckBox> NEEDING_RENAME_FILE_LIST ;
    private static String[] RENAME_DECORATE_LIST ;
    private static SProxy PROXY;
    private static Properties SETTING ;

    public static String[] getRenameDecorateList() {
        return RENAME_DECORATE_LIST;
    }

    public static void setRenameDecorateList(String[] renameDecorateList) {
        RENAME_DECORATE_LIST = renameDecorateList;
    }

    public static Properties getSETTING() {
        return SETTING;
    }

    public static void setSETTING(Properties SETTING) {
        DataBase.SETTING = SETTING;
    }

    public static SProxy getPROXY() {
        return PROXY;
    }

    public static void setPROXY(SProxy PROXY) {
        DataBase.PROXY = PROXY;
    }

    public static List<JCheckBox> getNeedingRenameFileList() {
        return NEEDING_RENAME_FILE_LIST;
    }

    public static void setNeedingRenameFileList(List<JCheckBox> needingRenameFileList) {
        NEEDING_RENAME_FILE_LIST = needingRenameFileList;
    }

    public static String getNeedingRenameFilePath() {
        return NEEDING_RENAME_FILE_PATH;
    }

    public static void setNeedingRenameFilePath(String needingRenameFilePath) {
        NEEDING_RENAME_FILE_PATH = needingRenameFilePath;
    }

    public static String[] getRenameRuleName() {
        return RENAME_RULE_NAME;
    }

    public static void setRenameRuleName(String[] renameRuleName) {
        RENAME_RULE_NAME = renameRuleName;
    }

    public static JComboBox[] getRenameRuleList() {
        return RENAME_RULE_LIST;
    }

    public static void setRenameRuleList(JComboBox[] renameRuleList) {
        RENAME_RULE_LIST = renameRuleList;
    }
}
