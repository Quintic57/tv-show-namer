public class Driver {
    public static void main(String[] args) {
//        String srcDirectory = "E:\\Storage\\TV Shows\\test\\";
        String srcDirectory = "/Users/Destin/Documents/tv-show-namer/demo/";
        String fileExtension = ".mkv";
        String showName = "Community";
        int seasonNumber = 4;

        TVShowNamer test = new TVShowNamer(srcDirectory, fileExtension, showName, seasonNumber);
        test.run();
    }
}
