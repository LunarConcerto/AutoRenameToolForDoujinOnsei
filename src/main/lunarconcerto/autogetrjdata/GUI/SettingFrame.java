package main.lunarconcerto.autogetrjdata.GUI;

import main.lunarconcerto.autogetrjdata.AutoGetRJData;
import main.lunarconcerto.autogetrjdata.Util.DataBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public class SettingFrame extends JFrame {

    public SettingFrame(){
        init();
    }

    private void init(){
        setTitle("设置");
        setSize(500 , 600);
        setLocation(600 , 200);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        JLabel LABEL_AUTHOR = new JLabel("作者 : LunarConcerto");
        JLabel LABEL_QQ = new JLabel("QQ : 1399265255");
        JLabel LABEL_VERSION = new JLabel("版本 :" + AutoGetRJData.VERSION);
        JButton SAVE_BUTTON = new JButton("保存");
        JButton CANCEL_BUTTON = new JButton("取消");

        JPanel PANEL_FRAME = new JPanel();
        add(PANEL_FRAME);
        JPanel PANEL_CENTER = new JPanel();
        JPanel PANEL_SOUTH = new JPanel();
        JPanel PANEL_BUTTON = new JPanel();
        PANEL_FRAME.add(PANEL_CENTER ,BorderLayout.CENTER);
        PANEL_FRAME.add(PANEL_SOUTH ,BorderLayout.SOUTH);
        PANEL_BUTTON.add(SAVE_BUTTON);
        PANEL_BUTTON.add(CANCEL_BUTTON);
        PANEL_BUTTON.setLayout(new GridLayout(1 , 2 , 20, 0));
        PANEL_SOUTH.setLayout(new GridLayout(4 , 0, 0 , 0));
        PANEL_SOUTH.add(PANEL_BUTTON);
        PANEL_SOUTH.add(LABEL_AUTHOR);
        PANEL_SOUTH.add(LABEL_QQ);
        PANEL_SOUTH.add(LABEL_VERSION);
        JPanel PANEL_PROXY_SETTING = new JPanel();
        JLabel LABEL_PROXY_SETTING = new JLabel("设置本地代理端口:");
        JTextField TEXT_FIELD_PROXY_SETTING = new JTextField(24);
        TEXT_FIELD_PROXY_SETTING.setText(DataBase.getPROXY().getProxyPort());
        PANEL_PROXY_SETTING.add(LABEL_PROXY_SETTING);
        PANEL_PROXY_SETTING.add(TEXT_FIELD_PROXY_SETTING);
        JPanel PANEL_RENAME_DECORATE_RULE_SETTING = new JPanel();
        PANEL_RENAME_DECORATE_RULE_SETTING.setLayout(new GridLayout(5 , 3 , 10 , 5));
        JLabel[] DECORATE_MESSAGE_LABEL = new JLabel[5];
        JTextField[] DECORATE_INPUT = new JTextField[10];
        loadFromProp();
        String[] decorate = DataBase.getRenameDecorateList();
        for (int i = 0 , j = 0 ; i < DECORATE_MESSAGE_LABEL.length; i++ , j+=2) {
            DECORATE_MESSAGE_LABEL[i] = new JLabel("设置位"+(i+1)+"信息的修饰:");
            PANEL_RENAME_DECORATE_RULE_SETTING.add(DECORATE_MESSAGE_LABEL[i]);
            DECORATE_INPUT[j] = new JTextField(4);
            DECORATE_INPUT[j+1] = new JTextField(4);
            if (decorate[j]!=null && decorate[j+1]!= null){
                DECORATE_INPUT[j].setText(decorate[j]);
                DECORATE_INPUT[j+1].setText(decorate[j+1]);
            }else {
                DECORATE_INPUT[j].setText("[");
                DECORATE_INPUT[j+1].setText("]");
            }
            PANEL_RENAME_DECORATE_RULE_SETTING.add(DECORATE_INPUT[j]);
            PANEL_RENAME_DECORATE_RULE_SETTING.add(DECORATE_INPUT[j+1]);
        }
        JLabel LABEL_TIPS1 = new JLabel("注意请不要输入诸如此类:/\\<>|*\":等运算符性质的符号" );
        JLabel LABEL_TIPS2 = new JLabel("因为这在系统重命名中是被禁止的");
        JLabel LABEL_TIPS3 = new JLabel("如果不需要加修饰请键入空格");
        //向中心面板放置组件
        Box BOX = Box.createVerticalBox();
        BOX.add(Box.createVerticalStrut(10));
        BOX.add(PANEL_PROXY_SETTING);
        BOX.add(LABEL_TIPS1);
        BOX.add(LABEL_TIPS2);
        BOX.add(LABEL_TIPS3);
        BOX.add(PANEL_RENAME_DECORATE_RULE_SETTING);

        PANEL_CENTER.add(BOX);

        //设置保存按钮逻辑
        SAVE_BUTTON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!"".equals(TEXT_FIELD_PROXY_SETTING.getText())){
                    DataBase.getPROXY().setProxyPort(TEXT_FIELD_PROXY_SETTING.getText());
                    DataBase.getSETTING().setProperty("proxy_port" , TEXT_FIELD_PROXY_SETTING.getText());
                }
                saveDecorate(translationTextToString(DECORATE_INPUT));
                AutoGetRJData.SETTING_WINDOW.setVisible(false);
            }
        });

        //取消按钮，隐藏面板且不作任何动作
        CANCEL_BUTTON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AutoGetRJData.SETTING_WINDOW.setVisible(false);
            }
        });
    }

    private static void loadFromProp(){
        Properties properties = DataBase.getSETTING();
        String[] strings = new String[10];
        for (int i = 0; i < 10; i++) {
            String property = properties.getProperty("rename_decorate" + i);
            if (!"".equals(property)){
                strings[i] = property;
            }
        }
        DataBase.setRenameDecorateList(strings);
    }

    private static String[] translationTextToString(JTextField[] fields){
        String[] strings = new String[10];
        for (int i = 0; i < fields.length; i++) {
            strings[i] = fields[i].getText();
        }
        return strings;
    }

    private static void saveDecorate(String[] strings){
        DataBase.setRenameDecorateList(strings);
        Properties properties = DataBase.getSETTING();
        for (int i = 0; i < strings.length; i++) {
            properties.setProperty("rename_decorate"+i , strings[i]);
        }
    }
}
