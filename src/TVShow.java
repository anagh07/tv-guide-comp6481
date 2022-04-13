import java.util.Scanner;

public class TVShow implements Watchable {
    private String showID;
    private String showName;
    private double startTime;
    private double endTime;
    static Scanner sc = new Scanner(System.in);

    /**
     * Parameterized constructor
     *
     * @param showID    unique id for the show
     * @param showName  name of the show
     * @param startTime time the show starts airing
     * @param endTime   time the show finishes airing
     */
    public TVShow(String showID, String showName, double startTime, double endTime) {
        this.showID = showID;
        this.showName = showName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Copy constructor
     *
     * @param show   object of type TVShow a copy of which is created
     * @param showID the new id for the show
     */
    public TVShow(TVShow show, String showID) {
        this.showID = showID;
        this.showName = show.getShowName();
        this.startTime = show.getStartTime();
        this.endTime = show.getEndTime();
    }

    /**
     * Method creates and returns a clone of the show object it is called upon with the new showID entered by user.
     *
     * @return returns the new show object
     */
    public TVShow clone() {
        System.out.print("Enter new showID:\t");
        String newShowId = sc.nextLine();
        return new TVShow(this, newShowId);
    }

    /**
     * Compares the equality of two show objects.
     *
     * @param newShow The  object to which equality should be compared with
     * @return returns true if objects are equals
     */
    public boolean equals(TVShow newShow) {
        return (
                this.showName.equals(newShow.getShowName()) &&
                        this.startTime == newShow.getStartTime() &&
                        this.endTime == newShow.getEndTime()
        );
    }

    /**
     * Compares if two shows have any overlap.
     *
     * @param S the show which is to be compared to.
     * @return returns three different strings based on the time overlap between the shows.
     */
    @Override
    public String isOnSameTime(TVShow S) {
        if (this.startTime == S.getStartTime() && this.endTime == S.getEndTime()) {
            return "Same time";
        } else if (this.startTime > S.getStartTime()) {
            if (this.startTime > S.getEndTime()) return "Different time";
            else return "Some Overlap";
        } else {
            if (S.getStartTime() > this.endTime) return "Different time";
            else return "Some Overlap";
        }
    }

    public String toString() {
        return "Show-ID: " + this.showID + "\tName: " + this.showName + "\n\tStart-time: " + this.startTime + "\tEnd" +
                "-time: " + this.endTime;
    }

    public String getShowID() {
        return showID;
    }

    public void setShowID(String showID) {
        this.showID = showID;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }
}
