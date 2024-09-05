package tars;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for TARS using FXML.
 */
public class Main extends Application {

    private static final int STAGE_MIN_HEIGHT = 220;
    private static final int STAGE_MIN_WIDTH = 417;
    private Tars tars = new Tars();

    /**
     * Starts the GUI application.
     *
     * <p>This method is called when the JavaFX application is launched. It loads the FXML file
     * for the main window, sets up the primary stage with the scene, and injects the TARS instance
     * into the controller. The stage's minimum height and width are set to ensure the UI remains
     * properly visible.
     *
     * @param stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(STAGE_MIN_HEIGHT);
            stage.setMinWidth(STAGE_MIN_WIDTH);
            fxmlLoader.<MainWindow>getController().setTars(tars);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
