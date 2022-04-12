package main.lunarconcerto.autogetrjdata.Util;

import java.awt.*;
import java.util.Calendar;

public class Util {

    //用以取得当前屏幕分辨率
    public static Dimension getScreen() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getScreenSize();
    }

    //取得当前应用所处文件夹路径
    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    //删除一些不必要的字符
    public static String DeleteString(String needStr, String deleteStr) {
        StringBuffer stringBuffer = new StringBuffer(needStr);
        int delCount = 0;
        String result;

        while (true) {
            int index = stringBuffer.indexOf(deleteStr);
            if (index == -1) {
                break;
            }
            stringBuffer.delete(index, index + deleteStr.length());
            if (stringBuffer.charAt(0) == '\\') {
                stringBuffer.delete(0, 1);
            }
            delCount++;
        }

        if (delCount != 0) {
            result = stringBuffer.toString();
            return result;
        } else {
            result = "-1";
            return result;
        }
    }

    //获取当前时间，并以：号隔开
    public static String getNowTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return appendZero(hour) + ":" + appendZero(min) + ":" + appendZero(second);
    }

    //获取当前时间，并以.号隔开
    public static String getNowTime2() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return appendZero(hour) + "." + appendZero(min) + "." + appendZero(second);
    }

    private static String appendZero(Integer num) {
        String MIN;
        if (num.toString().length() < 2) {
            MIN = "0" + num;
        } else {
            MIN = num.toString();
        }
        return MIN;
    }
}
