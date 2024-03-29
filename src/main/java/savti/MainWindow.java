package savti;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.controlsfx.control.ToggleSwitch;
import savti.command.*;
import savti.sorting.BubbleSort;
import savti.sorting.SortAlgorithm;
import savti.utilities.ErrorUtilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainWindow extends BorderPane {

    //region Local variables' declaration
    OutputHandler outputHandler = new OutputHandler();
    UserSettings userSettings;
    SortAlgorithm algorithm;
    JMetro theme;
    TiledImage image;

    //endregion

    //region FXML variables declaration
    @FXML
    ImageView imageView;
    @FXML
    Label headerText;
    @FXML
    ToggleSwitch darkMode;
    @FXML
    VBox menuVBox;
    MainVBox mainVBox;
    MainMenu mainMenu;

    //endregion

    public MainWindow(Stage primaryStage) {
        //Load FXML for the scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            ErrorUtilities.fxmlLoadError();
        }
        //Instantiating and initializing non-JavaFX variables
        initComponents();
        //Add event listeners for the components
        addEventListeners();
    }

    //Creazione dei componenti
    private void initComponents() {
        //Create a default UserSettings object
        userSettings = new UserSettings();
        //We choose bubblesort as default algorithm
        algorithm = new BubbleSort(userSettings, image, imageView, null, outputHandler);

        image = new TiledImage();

        mainVBox = new MainVBox();

        mainMenu = new MainMenu();

        this.setLeft(mainVBox);

        Node tmp = menuVBox.getChildren().get(0);

        menuVBox.getChildren().set(0, mainMenu);

        menuVBox.getChildren().add(1, tmp);

        HBox.setHgrow(headerText, Priority.ALWAYS);

        BorderPane.setAlignment(headerText.getParent(), Pos.CENTER);

        addCssFiles();

        theme = new JMetro(this, Style.DARK);
    }

    private void addCssFiles() {
        setStyle("-fx-base: black");
    }

    private void setDarkModeListener() {
        darkMode.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                setStyle("-fx-base: black");
                theme.setStyle(Style.DARK);
                mainVBox.setDarkStyle();
            } else {
                setStyle("-fx-base: white");
                theme.setStyle(Style.LIGHT);
                mainVBox.setLightStyle();
            }
        });
    }

    private void addEventListeners() {

        mainVBox.getCleanButton().setOnAction(e -> new CleanImageCommand(image, userSettings, imageView).execute());
        mainVBox.getOutputButton().setOnAction(e -> new SetOutputPathCommand(userSettings, mainVBox).execute());
        mainVBox.getSortingButton().setOnAction(e -> new SortImageCommand(mainVBox, image, outputHandler, userSettings, imageView, algorithm, mainMenu).execute());
        mainVBox.getRandomizeButton().setOnAction(e -> new RandomShuffleCommand(image, userSettings, outputHandler, imageView, mainVBox).execute());
        mainVBox.getBurstMode().setOnAction(e -> new BurstModeSortingCommand(mainVBox, image, userSettings, imageView, mainMenu).execute());
        mainVBox.getPathLabel().setOnAction(e -> new ClickToPathCommand(userSettings).execute());

        mainMenu.getSongLoaderItem().setOnAction(e -> new LoadSongCommand(userSettings, this).execute());
        mainMenu.getImageLoaderItem().setOnAction(e -> new LoadImageCommand(image, userSettings, imageView, mainVBox).execute());
        mainMenu.getAdvSett().setOnAction(e -> new SetAdvancedSettingCommand(image, userSettings, theme).execute());

        setDarkModeListener();
    }
}
