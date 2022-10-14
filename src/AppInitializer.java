import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        URL resource = getClass().getResource("view/LogInForm.fxml");
        Parent load= FXMLLoader.load(resource);
        Scene scene =new Scene(load);
        primaryStage.setScene(scene);

        Image mainIcon = new Image("asserts/images/plant.png");
        primaryStage.getIcons().add(mainIcon);
        primaryStage.setTitle("  Dilco Plantation");
//        primaryStage.setFullScreen(true);

        primaryStage.centerOnScreen();
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.show();


    }
}
