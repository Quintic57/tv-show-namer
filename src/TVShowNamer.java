import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
//import java.io.IOException;
//import java.util.Scanner;

public class TVShowNamer {
    private String srcDirectory;
    private String fileExtension;
    private String showName;
    private int seasonNumber;

    public TVShowNamer(String srcDirectory, String fileExtension, String showName, int seasonNumber) {
        this.srcDirectory = srcDirectory;
        this.fileExtension = fileExtension;
        this.showName = showName;
        this.seasonNumber = seasonNumber;
    }

    /**
     * Returns source directory variable. Used for testing purposes only
     *
     * @return source directory
     */
    public String getSrcDirectory() {
        return srcDirectory;
    }

    /**
     * Returns file extension variable. Used for testing purposes only
     *
     * @return file extension
     */
    public String getFileExtension() {
        return fileExtension;
    }

    /**
     * Returns show name variable. Used for testing purposes only
     *
     * @return show name
     */
    public String getShowName() {
        return showName;
    }

    /**
     * Returns season number variable. Used for testing purposes only
     *
     * @return season number
     */
    public int getSeasonNumber() {
        return seasonNumber;
    }

    /**
     * Runs the script
     * @return true if the script runs succesfully, false if it fails
     */
    public boolean run() {
        File directory = new File(srcDirectory);
        File[] fileList = directory.listFiles();

        String fileName = showName + " - " + "S" + (seasonNumber < 10 ? "0" + seasonNumber : seasonNumber);

        // URL
        String url = "https://www.thetvdb.com/series/";
        url = url + showName.toLowerCase().replace(" ", "-") + "/seasons/" + seasonNumber;

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

        if (fileList == null || (fileList.length != episodes.size())) {
            System.out.println("The number of files in the directory and the number of episodes in the season do not match");
            System.out.println("File List Size: " + fileList.length);
            System.out.println("Episode Array Size: " + episodes.size());
            for (int i = 0; i < fileList.length; i++) {
                System.out.println(fileList[i]);
            }
            return false;
        }

        /*
        For reference:
        - There is only one element returned from "tbody" tag
        - From all of the elements returned in "span" tag, the one referenced by index 0 is the English one
         */
        for (int i = 0; i < fileList.length; i++) {
            String episodeName = episodes.get(i).getElementsByTag("td").get(1).getElementsByTag("span").get(0).text();
            episodeName = episodeName.replace(":", "-");
            File temp = new File(srcDirectory + fileName + "E" + ((i + 1) < 10 ? "0" + (i + 1) : (i + 1)) + " - " + episodeName + fileExtension);

            if (!fileList[i].renameTo(temp)) {
                return false;
            }
        }

        return true;
    }
}
