/**
 * A functional interface for recording actions.
 *
 * @author Kevin Miller
 */


package interfaces;

/**
 * A functional interface for recording actions.
 */
public interface Traceable {

    /**
     * Interface method to be implemented to customize logs.
     *
     * @param success Boolean representing if the action to be performed was successful or not.
     * @return a String to be recorded based on user activities.
     */
    String recordActions(Boolean success);


}
