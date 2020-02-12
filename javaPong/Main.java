/*package watki;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
       // FXMLLoader loader = new FXMLLoader();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Pong");
        Scene scene = new Scene(root, 1024, 700);
        primaryStage.setScene(scene);
        //Pong controller = (Pong) loader.getController();
        //Platform.runLater(()->scene.setOnKeyPressed(controller::keyPressed));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
*/
package watki;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("sample.fxml").openStream());
        Scene mainScene = new Scene(root);
        Pong pong = (Pong) loader.getController();
        mainScene.setOnKeyPressed(pong::keyPressed);
        mainScene.setOnKeyReleased(pong::keyReleased);
        primaryStage.setTitle("Pong");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
