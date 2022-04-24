/**
 * Interface forces the child classes to implement a isOnSameTime method.
 *
 * @author Anagh Mehran
 */
public interface Watchable {
    /**
     * Method checks if the show passed as argument is on the same time as the object it is called upon.
     *
     * @param S The tv show object to be compared with.
     * @return Returns string containing information about timing overlap.
     */
    String isOnSameTime(TVShow S);
}
