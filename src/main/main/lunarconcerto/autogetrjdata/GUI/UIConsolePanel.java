package main.lunarconcerto.autogetrjdata.GUI;

import main.lunarconcerto.autogetrjdata.AutoGetRJData;
import main.lunarconcerto.autogetrjdata.Listener.StartButtonListener;
import main.lunarconcerto.autogetrjdata.Util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class UIConsolePanel extends JPanel {

    private static final JPanel logPanel = new JPanel();
    private static final JLabel logLabel = new JLabel("运行日志");

    private static final JPanel startPanel = new JPanel();
    private static final JButton startButton = new JButton("开始运行");
    private static final JButton settingButton = new JButton("设置");

    private static final DefaultListModel<String> consoleLogListModel = new DefaultListModel<>();
    private final JList<String> consoleLogList = new JList<String>(consoleLogListModel);

    private static final List<String> messageList = new ArrayList<>();

    public UIConsolePanel() {
        init();
        this.add(logLabel);
        this.add(logPanel);
        this.add(startPanel);
    }

    private void init(){
        logPanel.setSize(Util.getScreen().width/ 2 / 3 , Util.getScreen().height/ 2 / 2);

        consoleLogList.setVisibleRowCount(26);
        this.setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        this.setAlignmentX(LEFT_ALIGNMENT);
        JScrollPane logScroll = new JScrollPane(consoleLogList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        consoleLogList.setAutoscrolls(true);
        logScroll.setMaximumSize(new Dimension(Util.getScreen().width/2/4 , 0));
        logPanel.add(logScroll);

        startPanel.setLayout(new GridLayout(2 , 0 , 10 , 10));
        startPanel.add(startButton);
        startPanel.add(settingButton);

        StartButtonListener startButtonListener = new StartButtonListener();
        startButton.addActionListener(startButtonListener);
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AutoGetRJData.SETTING_WINDOW.setVisible(true);
            }
        });
    }

    public static void printMessage(String args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String message = Util.getNowTime() + ":" +args ;
                consoleLogListModel.addElement(message);
                messageList.add(message);
            }
        });
    }

    public static void saveLog() {
        String dir = Util.getUserDir() + "\\log\\";
        File contents = new File(dir);
        contents.mkdir();

        if (messageList.size() != 0) {
            try {
                String path2 = dir + "log[" + Util.getNowTime2() + "].txt";
                File file2 = new File(path2);
                file2.createNewFile();

                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2.getAbsoluteFile()));
                for (String s : messageList) {
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
