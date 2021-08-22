package main.lunarconcerto.autogetrjdata.Fuction;

import main.lunarconcerto.autogetrjdata.GUI.UIConsolePanel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadResources {

    public static List<String> startDownloadResources(String fileName) throws UnsupportedEncodingException {
        String rjNumber = getRJNumber(fileName);
        if (rjNumber!=null){
            return getInformation(rjNumber);
        }
        return getConnection(fileName);
    }

    private static List<String> getConnection(String fileName) throws UnsupportedEncodingException {
        String path = "https://www.dlsite.com/maniax/fsr/=/language/jp/sex_category%5B0%5D/male/keyword/" + URLEncoder.encode(fileName , String.valueOf(StandardCharsets.UTF_8));
        try {
            UIConsolePanel.printMessage("作品无RJ号,开始以作品名搜索取得RJ号!");
            Document document = Jsoup.connect(path).get();

            Elements searchResult = document.select("dt[class]");
            List<String> id = searchResult.eachAttr("id");

            if (id.size() == 1){
                UIConsolePanel.printMessage("搜索成功,取得作品RJ号为:"+ getRJNumber(id.get(0)));
                return getInformation(getRJNumber(id.get(0)));
            }else if (id.size() == 0 ){
                UIConsolePanel.printMessage("结果不存在...尝试修改文件名后重试");
            }else {
                UIConsolePanel.printMessage("存在多个结果...请手动选择匹配结果");
            }
        }catch (Exception e){
            UIConsolePanel.printMessage("发生错误！请查看日志"+e.getMessage() + Arrays.toString(e.getStackTrace()));
            return null;
        }
        return null ;
    }

    private static List<String> getInformation(String RJ_NUMBER){
        String path = "https://www.dlsite.com/maniax/work/=/product_id/" + RJ_NUMBER + ".html";
        try {
            UIConsolePanel.printMessage("获得RJ号，开始取得作品信息！");
            Document document = Jsoup.connect(path).get();

            Elements td = document.select("td");
            Elements th = document.select("th");

            Elements a = document.select("template");
            String PRODUCT_NAME = a.attr("data-product-name");

            //サークル名 販売日 声優
            List<String> tdList = td.eachText();
            List<String> thList = th.eachText();
            List<String> needingInformationList = new ArrayList<>();

            needingInformationList.add(0 , PRODUCT_NAME);
            needingInformationList.add(1 , RJ_NUMBER);

            for (int i = 0; i < thList.size(); i++) {
                if ("サークル名".equals(thList.get(i))){
                    needingInformationList.add(2 , tdList.get(i));
                }
                if ("販売日".equals(thList.get(i))){
                    needingInformationList.add(3 , getDate(tdList.get(i)));
                }
                if ("声優".equals(thList.get(i))){
                    needingInformationList.add(4 , tdList.get(i));
                }

                if (needingInformationList.size()==5){
                    break;
                }
            }
            tdList.clear();
            thList.clear();

            UIConsolePanel.printMessage("已取得作品信息："+
                    needingInformationList.get(1)+
                    needingInformationList.get(2)+
                    needingInformationList.get(3)+
                    needingInformationList.get(4)
            );
            return needingInformationList ;
        } catch (Exception e) {
            UIConsolePanel.printMessage("发生错误！请查看日志"+e.getMessage() + Arrays.toString(e.getStackTrace()));
        }
        return null ;
    }

    private static String getDate(String str){
        Pattern compile = Pattern.compile("[0-9]+");
        Matcher matcher = compile.matcher(str);
        StringBuilder result = new StringBuilder();
        while (matcher.find()){
            result.append(matcher.group());
        }
        return result.toString();
    }


    private static String getRJNumber(String str){
        Pattern compile = Pattern.compile("RJ[0-9]{1,6}");
        Matcher matcher = compile.matcher(str);
        if (matcher.find()){
            return matcher.group();
        }
        return null;
    }



}
