import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Read input files and create show linked list. Read interest files and check if show can be watched.
 *
 * @author Anagh Mehran
 */
public class ProcessWishlist {
    static String workingDir = System.getProperty("user.dir");
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // iv->a+b
        // Two lists created. Second list initialized using copy constructor.
        ShowList showList = new ShowList();
        try {
            processTvGuide(showList);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        ShowList copyOfShowList = new ShowList(showList);
        System.out.println(copyOfShowList.toString());

        // iv->c
        try {
            processInterest(showList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /**
         * Testing methods inside the implemented classes
         */
//        // showlist.equals()
//        System.out.println("\nShowlists equal? " + showList.equals(copyOfShowList) + "\n");
//        // insertAtIndex()
//        TVShow newShow = new TVShow("XYZ10", "Kim's Convenience", 10.00, 11.30);
//        copyOfShowList.insertAtIndex(newShow, 2);
//        // replaceAtIndex()
//        copyOfShowList.replaceAtIndex(new TVShow(newShow, "XYZ11"), 5);
//        System.out.println(copyOfShowList.toString());
//        // deleteFromIndex()
//        copyOfShowList.deleteFromIndex(2);
//        // deleteFromStart()
//        copyOfShowList.deleteFromStart();
//        System.out.println(copyOfShowList.toString());
//
//        System.out.println("Showlists equal? " + showList.equals(copyOfShowList));
//
//        /**
//         * Privacy leak
//         *
//         * Problem: The private fields in the show objects can be accessed and changed.
//         * Solution: Make the 'setter' methods protected.
//         */
//        TVShow leakShow = showList.find("CBS22").getTvShow();
//        leakShow.setShowID("CBS1001");
//        leakShow.setShowName("Innocent Minds");
//        System.out.println(showList.toString());
    }

    /**
     * Processes TVGuide txt file that creates the linked list from show information.
     *
     * @param showList Show list object that is to be populated with shows from the file.
     * @throws FileNotFoundException Thrown when file does not exist in project root directory.
     */
    private static void processTvGuide(ShowList showList) throws FileNotFoundException {
        // Open TVGuide.txt and init showlist
        File tvguidefile = new File(workingDir, "TVGuide.txt");
        if (!tvguidefile.exists() || !tvguidefile.isFile()) {
            throw new FileNotFoundException();
        }
        try (BufferedReader tvguideReader = new BufferedReader((new FileReader(tvguidefile)))) {
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

    /**
     * Processes interest file. Prompts user for file name.
     *
     * @param showList List of shows where each wish/watch show will be searched in.
     * @throws FileNotFoundException Thrown when the filename does not exist.
     */
    private static void processInterest(ShowList showList) throws FileNotFoundException {
        System.out.print("Please enter filename with extension: ");
        String filename = sc.nextLine();
        File interestFile = new File(workingDir, filename);
        List<String> watching = new ArrayList<>();
        List<String> wishList = new ArrayList<>();

        // Read the file and create list of watching and wish.
        if (!interestFile.exists() || !interestFile.isFile()) {
            throw new FileNotFoundException();
        }
        try (BufferedReader interestReader = new BufferedReader(new FileReader(interestFile))) {
            String line = interestReader.readLine();
            while (!(line = interestReader.readLine()).equals("Wishlist")) {
                watching.add(line);
            }
            while ((line = interestReader.readLine()) != null) {
                wishList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Check which shows can be watched from wishlist
        ShowList.ShowNode node;
        TVShow wishShow, watchShow;
        for (String wish : wishList) {
            // status: a->can watch, b->overlap with watching, c->overlap with wishlist
            node = showList.find(wish);
            boolean flag = true;
            if (node != null) {
                wishShow = node.getTvShow();
                for (String watch : watching) {
                    watchShow = showList.find(watch).getTvShow();
                    if (wishShow.isOnSameTime(watchShow).equals("Different time")) {
                        continue;
                    } else if (wishShow.getStartTime() > watchShow.getStartTime() && wishShow.getStartTime() < watchShow.getEndTime()) {
                        System.out.println("User can't watch show " + wish + " as he/she is not finished with a show " +
                                "he/she is watching");
                        flag = false;
                        break;
                    } else {
                        System.out.println("User can't watch show " + wish + " as he/she will begin another " +
                                "show at the same time");
                        flag = false;
                        break;
                    }
                }
                if (flag) System.out.println("User can watch show " + wish + " as he/she is not watching " +
                        "anything else during that time.");
            }
        }
    }
}
