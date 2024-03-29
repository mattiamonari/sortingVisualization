package savti.command;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import savti.*;
import savti.sorting.*;
import savti.utilities.ErrorUtilities;

import java.util.List;
import java.util.concurrent.Executors;

import static savti.utilities.GUIUtilities.ableNodes;

/**
 * SortImageCommand is used to create the command to sort the image after the shuffle.
 *
 * @author: Daniele Gasparini && Mattia Monari
 * @version: 2022.11.22
 */
public class SortImageCommand implements Command {
    static final String HOVER_BUTTON = "-fx-background-color: #cd5c5c; \n-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);";

    MainVBox mainVBox;
    TiledImage image;
    UserSettings userSettings;
    ImageView imageView;
    SortAlgorithm algorithm;
    AlgorithmProgressBar algorithmProgressBar;
    OutputHandler outputHandler;
    MainMenu mainMenu;

    public SortImageCommand(MainVBox mainVBox, TiledImage image, OutputHandler outputHandler, UserSettings userSettings, ImageView imageView, SortAlgorithm algorithm, MainMenu mainMenu) {
        this.mainVBox = mainVBox;
        this.image = image;
        this.userSettings = userSettings;
        this.imageView = imageView;
        this.algorithm = algorithm;
        this.algorithmProgressBar = new AlgorithmProgressBar("Sorting...");
        this.mainMenu = mainMenu;
        this.outputHandler = outputHandler;
    }

    public SortAlgorithm choice() {
        String choice = mainVBox.getChooseAlgo().getValue();
        switch (choice) {
            case "QuickSort" ->
                    algorithm = new QuickSort(userSettings, image, imageView, algorithmProgressBar, outputHandler);
            case "SelectionSort" ->
                    algorithm = new SelectionSort(userSettings, image, imageView, algorithmProgressBar, outputHandler);
            case "BubbleSort" ->
                    algorithm = new BubbleSort(userSettings, image, imageView, algorithmProgressBar, outputHandler);
            case "InsertionSort" ->
                    algorithm = new InsertionSort(userSettings, image, imageView, algorithmProgressBar, outputHandler);
            case "RadixSort" ->
                    algorithm = new RadixSort(userSettings, image, imageView, algorithmProgressBar, outputHandler);
            case "MergeSort" ->
                    algorithm = new MergeSort(userSettings, image, imageView, algorithmProgressBar, outputHandler);
            case "CocktailSort" ->
                    algorithm = new CocktailSort(userSettings, image, imageView, algorithmProgressBar, outputHandler);
            case "GnomeSort" ->
                    algorithm = new GnomeSort(userSettings, image, imageView, algorithmProgressBar, outputHandler);
            case "CycleSort" ->
                    algorithm = new CycleSort(userSettings, image, imageView, algorithmProgressBar, outputHandler);
            default -> ErrorUtilities.somethingWentWrong();
        }
        return algorithm;
    }

    @Override
    public void execute() {
        if (checkSortingConditions()) {
            SortAlgorithm algorithm = choice();
            mainVBox.disableOrEnableAll(true);
            ableNodes(List.of(mainMenu.getImageLoaderItem().getStyleableNode(), mainMenu.getSongLoaderItem().getStyleableNode()), List.of());
            ((Group) imageView.getParent()).getChildren().add(algorithmProgressBar);
            imageView.setVisible(false);
            imageView.setManaged(false);
            ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
            ListenableFuture<?> future = pool.submit(algorithm);
            //TODO CREATE METHOD
            future.addListener(() -> Platform.runLater(() -> {
                mainVBox.disableOrEnableAll(false);
                ableNodes(List.of(), List.of(mainMenu.getImageLoaderItem().getStyleableNode(), mainMenu.getSongLoaderItem().getStyleableNode()));
            }), MoreExecutors.directExecutor());
        }
    }


    private boolean checkSortingConditions() {

        if (!userSettings.verifyOutputPath()) {
            mainVBox.getOutputButton().setStyle(HOVER_BUTTON);
            ErrorUtilities.outputPath();
            return false;
        } else if (image == null || image.getImage() == null) {
            ErrorUtilities.noImageError();
            return false;
        } else if (image.isAlreadyOrdered() || image.isArrayEmpty()) {
            ErrorUtilities.alreadyOrderedImage();
            return false;
        }
        return true;
    }
}
