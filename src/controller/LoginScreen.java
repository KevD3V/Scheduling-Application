/**
 * Login Screen controller.
 * Users may log in through this screen, or receive an error if system detects invalid credentials.
 * Error messages will be translated to a different language depending on the user's system's location.
 *
 * @author Kevin Miller
 */

package controller;

import interfaces.ConfirmButton;
import interfaces.Traceable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import utilities.TimeManager;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A controller class for logging in.
 */
public class LoginScreen implements Initializable {

    /**
     * A string used to test username functions.
     */
    final String testUser = "root";

    /**
     * A string used to test password functions.
     */
    final String  testPass = "root";

    /**
     * A lable for the title of the screen.
     */
    public Label loginText_label;

    /**
     * A lable for the username.
     */
    public Label userNameLabel_text;
    /**
     * A lable for the password.
     */
    public Label passwordLabel_text;

    /**
     * A resource bundle to handle language translations
     */
    ResourceBundle rs = ResourceBundle.getBundle("main/Nat", Locale.getDefault());

    /**
     * A flag to check if the user will need a french translation.
     */
    private boolean french = false;

    /**
     * A static user object to manage which user is currently logged in.
     */
    private static User currentUser = null;


    /**
     * A text field for the password.
     */
    public TextField password_TextField;

    /**
     * A text field for the username.
     */
    public TextField userName_TextField;

    /**
     * A label for the zone ID.
     */
    public Label zoneID_Label;

    /**
     * A button to exit.
     */
    public Button exitBTN_text;

    /**
     * A button to enter, and log in.
     */
    public Button enterBTN_text;

    /**
     *  Lambda Expression used to print information to the console, confirming the GUI elements are working. The lambda can be modified in other classes to change information displayed while debugging.
     */
    ConfirmButton confirm = (actionEvent) -> System.out.println(actionEvent.getSource() + " Works!");


    /**
     * Lambda Expressions used to print to the log file.
     * The current representation of the lambda expression is used to log login attempts. The lambda can also be modified in other classes to log other activities.
     */
    Traceable logs = s -> {
        String result;
        if(s){
            result = "succeeded";
        }
        else{
            result = "failed";
        }
        return "Login attempt on " + TimeManager.fromLocalToUTCTS(Timestamp.valueOf(LocalDateTime.now())).toLocalDateTime() + " UTC, for  "+ userName_TextField.getText() + " " + result + ".\n";
    };

    /**
     * Initialize the Login screen.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main Screen is initialized.");
        zoneID_Label.setText(ZoneId.systemDefault().toString());

        if(Locale.getDefault().getLanguage().equals("fr")){
            french = true;
            translateToFrench();
        }
    }

    /**
     * Try to log user into application.
     *
     * @param actionEvent the Enter button action.
     * @throws IOException thrown if params cannot be resolved.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public void onEnter(ActionEvent actionEvent) throws IOException, SQLException {
        // Confirm button works
        System.out.println("Enter Button is Pressed");

        /**
         * Lambda function to check for the buttons.
         */
        confirm.confirmFunctional(actionEvent);

        // Store username and password in variables.
        String userName = userName_TextField.getText();
        String password = password_TextField.getText();

        // Search all users to find username
        User searchedUser = User.searchUsernames(userName);
        System.out.println(searchedUser);
        System.out.println(User.getAllUsers().size());

        // Check if username was found
        if(searchedUser != null){
            // Check if password matches
            if(searchedUser.getPassword().equals(password)){
                // Log user in.
                System.out.println("Login Successful!");
                try {
                    PrintWriter pw = new PrintWriter(new FileOutputStream(
                            new File("src/login_activity.txt"),
                            true /* append = true */));
                    /**
                     * Lambda function to ensure logs are recorded properly.
                     */
                    pw.append(logs.recordActions(true));
                    pw.append("\n");

                    pw.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

                // Set the current user
                LoginScreen.setCurrentUser(searchedUser);

                // Load next screen
                loadCustomerScreen(actionEvent);
            }
            else{
                try {
                    PrintWriter pw = new PrintWriter(new FileOutputStream(
                            new File("src/login_activity.txt"),
                            true /* append = true */));
                    /**
                     * Lambda function to ensure logs are recorded properly.
                     */
                    pw.append(logs.recordActions(false));
                    pw.append("\n");

                    pw.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                if(french){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(rs.getString("failed"));
                    alert.setContentText(rs.getString("password")
                            + " " + rs.getString("failed"));
                    alert.showAndWait();
                    return;
                }
                else {
                    // Display error.
                    System.out.println("Login Failed!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Login");
                    alert.setContentText("Password is incorrect.");
                    alert.showAndWait();
                    return;
                }
            }
        }
        else{
            try {
                PrintWriter pw = new PrintWriter(new FileOutputStream(
                        new File("src/login_activity.txt"),
                        true /* append = true */));
                /**
                 * Lambda function to ensure logs are recorded properly.
                 */
                pw.append(logs.recordActions(false));
                pw.append("\n");

                pw.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            if(french){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rs.getString("failed"));
                alert.setContentText(rs.getString("username") + " " + rs.getString("failed"));
                alert.showAndWait();
                return;
            }
            else {
                // Display error.
                System.out.println("Login Failed!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Login");
                alert.setContentText("Username is incorrect.");
                alert.showAndWait();
                return;
            }
        }
    }


    /**
     * @return the current user signed into the application.
     */
    public static User getCurrentUser(){
        return currentUser;
    }


    /**
     * @param newCurrentUser to be set as the current user logged into the application.
     */
    public static void setCurrentUser(User newCurrentUser){

        currentUser = newCurrentUser;
    }

    /**
     *  Logic to translate the Login Screen to French
     */
    private void translateToFrench(){
        loginText_label.setText(rs.getString("login"));
        userNameLabel_text.setText(rs.getString("username"));
        passwordLabel_text.setText(rs.getString("password"));
        enterBTN_text.setText(rs.getString("enter"));
        exitBTN_text.setText(rs.getString("exit"));
    }

    /**
     * Logic to handle loading the customer screen.
     *
     * @param actionEvent the Customer button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    private void loadCustomerScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customer_screen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900,600);
        stage.setTitle("Basic Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Exits the application.
     *
     * @param actionEvent the Exit button action.
     */
    public void onExit(ActionEvent actionEvent) {
        confirm.confirmFunctional(actionEvent);
        System.exit(0);
    }
}
