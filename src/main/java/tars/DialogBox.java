package tars;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    private static final String DIALOG_BOX_FXML = "/view/DialogBox.fxml";
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox with the specified text and image.
     *
     * <p>This constructor loads the corresponding FXML layout for the dialog box and sets
     * the text and image for the dialog.
     *
     * @param text the text to be displayed in the dialog.
     * @param img the image representing the speaker.
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource(DIALOG_BOX_FXML));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert text != null && !text.isEmpty() : "Text for dialog should not be null or empty.";
        assert img != null : "Image for dialog should not be null.";
        dialog.setText(text);
        displayPicture.setImage(img);
        assert dialog.getText().equals(text) : "Dialog text was not set correctly.";
        assert displayPicture.getImage() == img : "Display picture was not set correctly.";
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flipDialogOrientation() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        assert getAlignment() == Pos.TOP_LEFT : "Alignment should be TOP_LEFT after flipping.";
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Creates a DialogBox for the user's dialog, with the image on the right and text on the left.
     *
     * @param text the text to be displayed in the dialog.
     * @param img the image representing the user.
     * @return a DialogBox representing the user's dialog.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates a DialogBox for the user's dialog.
     * Places the image on the right and the text on the left.
     *
     * @param text the text to be displayed in the dialog.
     * @param img the image representing the user.
     * @return a DialogBox for the user's dialog.
     */
    public static DialogBox getTarsDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flipDialogOrientation();
        return db;
    }
}
