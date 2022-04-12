import main.lunarconcerto.autogetrjdata.GUI.UIConsolePanel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

public class test {

    @Test
    public void test1() throws IOException {
        System.setProperty("https.proxyHost" , "127.0.0.1");
        System.setProperty("https.proxyPort" , "7890");
        String path = "https://www.dlsite.com/maniax/work/=/product_id/RJ359306.html";

        Document document = Jsoup.connect(path).get();

        String PRODUCT_NAME = document.select("[id=work_name]").text();
        String MAKER_NAME = document.select("[class=maker_name]").text();

        Element voice = document.select("tr").stream().filter(element1 -> {
            Elements matchingText = element1.getElementsMatchingText("声優");
            return !matchingText.isEmpty();
        }).findFirst().get();

        String VOICE_NAME = voice.select("td").text();



        }
}
