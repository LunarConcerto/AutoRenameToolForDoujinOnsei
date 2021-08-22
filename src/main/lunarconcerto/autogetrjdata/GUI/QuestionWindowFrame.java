package main.lunarconcerto.autogetrjdata.GUI;

import main.lunarconcerto.autogetrjdata.Fuction.DownloadResources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

public class QuestionWindowFrame extends JFrame {

    private static JTextField field = new JTextField();

    public QuestionWindowFrame(){
    }

    private void init(){
        setSize(300 , 150);
        setLocation(600,600);
        JPanel panel = new JPanel();

        JPanel SOUTH_PANEL = new JPanel();
        JPanel CENTER_PANEL = new JPanel();

        JButton SAVE_BUTTON = new JButton("保存");
        JButton SKIP_BUTTON = new JButton("跳过");

        panel.add(CENTER_PANEL , BorderLayout.CENTER);
        panel.add(SOUTH_PANEL , BorderLayout.SOUTH);

        SOUTH_PANEL.add(SAVE_BUTTON);
        SOUTH_PANEL.add(SKIP_BUTTON);


        add(panel);
        setVisible(true);

        JLabel jLabel = new JLabel("未找到匹配的作品...尝试修改文件名:");
        CENTER_PANEL.setLayout(new GridLayout(2, 0 , 0 , 0));
        CENTER_PANEL.add(jLabel);
        CENTER_PANEL.add(field);

        SAVE_BUTTON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DownloadResources.startDownloadResources(field.getText());
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                setVisible(false);
            }
        });
        SKIP_BUTTON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    public static void nameErrorQuestion(String Path){
        field.setText(Path);
    }
}
