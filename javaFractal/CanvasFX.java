package mandelbrot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CanvasFX extends Application {
// Główna klasa aplikacji JavaFX (Main) dziedziczy po klasie javafx.application.Application.
// Metoda start() jest punktem wejścia dla wszystkich aplikacji JavaFX.
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        // Plik sample.fxml jest skryptem FXML opartym na języku XML, który opisuje strukturę/układ interfejsu graficznego
        // i występujące w nim kontrolki/widgety.
        // Taki podział pozwala na odseparowanie interfejsu użytkownika od logiki kodu.
        // Plik ten jest automatycznie wczytywany i wykorzystywany przez plik Main.java w powyzszej linijce kodu
        primaryStage.setTitle("Mandelbrot");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    // Metoda main wywołuje jedynie metodę launch z klasy javafx.application.Application, która uruchamia
    // aplikację JavaFX i obsługuje argumenty z linii komend. W przypadku utworzenia archiwum .jar
    // z użyciem narzędzia JavaFX Package metoda main() nie jest wymagana.

}
