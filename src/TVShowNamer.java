import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TVShowNamer {
    private static String srcDirectory = "E:\\Storage\\TV Shows\\test\\";
    private static String fileExtension = ".mkv";
    private static String showName = "Community";
    private static int seasonNumber = 4;

//    public ShowNamer(String srcDirectory, String fileExtension, String showName, int seasonNumber) {
//
//    }
//
//    public String getSrcDirectory() {
//        return srcDirectory;
//    }
//
//    public String getFileExtension() {
//        return fileExtension;
//    }
//
//    public String getShowName() {
//        return showName;
//    }
//
//    public int getSeasonNumber() {
//        return seasonNumber;
//    }
//
//    public String getURL() {
//        String url = "https://www.thetvdb.com/series/";
//        url = url + showName.toLowerCase().replace(" ", "-") + "/seasons/" + seasonNumber;
//
//        return url;
//    }

    public static void main(String[] args) {
        File directory = new File(srcDirectory);
        File[] fileList = directory.listFiles();

        String fileName = showName + " - " + "S" + (seasonNumber < 10 ? "0" + seasonNumber : seasonNumber);

        String url = "https://www.thetvdb.com/series/";
        url = url + showName.toLowerCase().replace(" ", "-") + "/seasons/" + seasonNumber;
//        System.out.println(url);

        Elements episodes = new Elements();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements body = doc.body().getElementsByClass("table table-hover");
            episodes = body.get(0).getElementsByTag("tbody").get(0).getElementsByTag("tr");
        } catch (Exception e) {
            System.out.println("Can't find show URL, please input it manually");
        }

//        for (Element e : episodes) {
//            System.out.println(e.getElementsByTag("td").get(1).getElementsByTag("span").get(0).text());
//        }

        /*
        For reference:
        - There is only one element returned from "tbody" tag
        - From all of the elements returned in "span" tag, the one referenced by index 0 is the English one
         */
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                String episodeName = episodes.get(i).getElementsByTag("td").get(1).getElementsByTag("span").get(0).text();
                episodeName = episodeName.replace(":", "-");
//            System.out.println(episodeName);
                File temp = new File(srcDirectory + fileName + "E" + ((i + 1) < 10 ? "0" + (i + 1) : (i + 1)) + " - " + episodeName + fileExtension);
                System.out.println(fileList[i].renameTo(temp));
            }
        }
    }
}
