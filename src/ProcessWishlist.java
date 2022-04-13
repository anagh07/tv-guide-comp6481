import java.io.*;

public class ProcessWishlist {
    static String workingDir = System.getProperty("user.dir");

    public static void main(String[] args) {
        ShowList showList1 = new ShowList();
        ShowList showList2 = new ShowList();

        try {
            processTvGuide(showList1);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }


    }

    private static void processTvGuide(ShowList showList) throws FileNotFoundException {
        // Open TVGuide.txt and init showlist
        File tvguidfile = new File(workingDir, "TVGuide.txt");
        if (!tvguidfile.exists() || !tvguidfile.isFile()) {
            throw new FileNotFoundException();
        }
        try (BufferedReader tvguideReader = new BufferedReader((new FileReader(tvguidfile)))) {
            String line1, line2, line3;
            while ((line1 = tvguideReader.readLine()) != null &&
                    (line2 = tvguideReader.readLine()) != null &&
                    (line3 = tvguideReader.readLine()) != null) {
                String showId = line1.split(" ")[0];
                String showName = line1.split(" ")[1];
                double startTime = Double.parseDouble(line2.split(" ")[1]);
                double endTime = Double.parseDouble(line3.split(" ")[1]);
                TVShow newShow = new TVShow(showId, showName, startTime, endTime);
                if (!showList.contains(newShow)) showList.addToStart(newShow);
                tvguideReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
