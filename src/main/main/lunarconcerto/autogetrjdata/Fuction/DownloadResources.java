package main.lunarconcerto.autogetrjdata.Fuction;

import main.lunarconcerto.autogetrjdata.GUI.UIConsolePanel;
import main.lunarconcerto.autogetrjdata.Util.DataBase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Predicate;
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

            String PRODUCT_NAME = document.select("[id=work_name]").text();
            String MAKER_NAME = document.select("[class=maker_name]").text();

            //サークル名 販売日 声優
            List<String> needingInformationList = new ArrayList<>();

            needingInformationList.add(0 , PRODUCT_NAME);
            needingInformationList.add(1 , RJ_NUMBER);
            needingInformationList.add(2 , MAKER_NAME);
            needingInformationList.add(3 , findTrTagResource(document , "販売日"));
            needingInformationList.add(4 , findTrTagResource(document , "声優"));

            UIConsolePanel.printMessage("已取得作品信息："+
                    needingInformationList.get(1)+
                    needingInformationList.get(2)+
                    needingInformationList.get(3)+
                    needingInformationList.get(4)
            );

            Properties properties = DataBase.getSETTING();
            String download_image = properties.getProperty("download_image");
            if (download_image!=null && download_image.equals("true")){
                UIConsolePanel.printMessage("开始下载封面。");
                Elements picture = document.select("img");
                List<String> attr = picture.eachAttr("src");
                attr.stream()
                        .filter(s -> !s.startsWith("/modpub") && !s.equals("/images/web/common/logo/pc/logo-dlsite-r18.png"))
                        .forEach(needingInformationList::add);
            }

            return needingInformationList ;
        } catch (Exception e) {
            UIConsolePanel.printMessage("发生错误！请查看日志"+e.getMessage() + Arrays.toString(e.getStackTrace()));
            UIConsolePanel.saveLog();
        }
        return null ;
    }

    private static String findTrTagResource(Document document , String tagName){
        Element element = document.select("tr").stream().filter(element1 -> {
            Elements matchingText = element1.getElementsMatchingText(tagName);
            return !matchingText.isEmpty();
        }).findFirst().get();
       return element.select("td").text();
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
