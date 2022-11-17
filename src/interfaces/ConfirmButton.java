/**
 * A functional interface for confirming functionality.
 *
 * @author Kevin Miller
 */



package interfaces;

import javafx.event.ActionEvent;

/**
 * A functional interface for confirming functionality.
 */
public interface ConfirmButton {

    /**
     * Interface method to be implemented to customize functionaity checks for GUI elements.
     *
     * @param actionEvent the button action.
     */
    void confirmFunctional(ActionEvent actionEvent);



}
