package JavaFXVersion.utilities;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class ErrorUtilities {
    public static void noImageError() {
        Alert errorAlert = new Alert(ERROR , "Select an image to shuffle first!" , ButtonType.OK);
        errorAlert.setHeaderText("Image is not valid");
        errorAlert.showAndWait();
    }

    public static void alreadyOrderedImage() {
        Alert alert = new Alert(INFORMATION , "" , ButtonType.OK);
        alert.setHeaderText("The image is already ordered. Shuffle it first!");
        alert.showAndWait();
    }

    public static void SWW() {
        Alert errorAlert = new Alert(ERROR , "We don't know what happened, but we're sorry." , ButtonType.OK);
        errorAlert.setHeaderText("Something went wrong");
        errorAlert.showAndWait();
    }

    public static void FXMLLoadError() {
        Alert errorAlert = new Alert(ERROR , "Something went wrong while trying loading UI. Try again!" , ButtonType.OK);
        errorAlert.setHeaderText("Oops!");
        errorAlert.showAndWait();
        Platform.exit();
        System.exit(-1);
    }

    public static void loadImageError() {
        Alert errorAlert = new Alert(ERROR , "Impossible to load the image." , ButtonType.OK);
        errorAlert.setHeaderText("Try again, and if the problem persists please change the image.");
        errorAlert.showAndWait();
    }

    public static void writeError() {
        Platform.runLater(() -> {
            Alert errorAlert = new Alert(ERROR , "Impossible to load the image." , ButtonType.OK);
            errorAlert.setHeaderText("Try again, and if the problem persists please change the image.");
            errorAlert.showAndWait();
            //TODO IMPORTANT !!!!!!!!!!
            Platform.exit();
            System.exit(-1);
        });
    }

    public static void FFError() {
    }
}
