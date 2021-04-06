package PayrollProcessingSystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class serves as a driver class and launches the GUI display.
 * @author Kathleen Eife, Isha Vora
 */
public class Main extends Application {

    /**
     * This method sets the stage for the GUI display.
     * @param primaryStage the GUI display stage
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        primaryStage.setTitle("Payroll Processing System");
        primaryStage.setScene(new Scene(root, 800, 591));
        primaryStage.show();
    }

    /**
     * This method serves as the main method and launches the program.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
