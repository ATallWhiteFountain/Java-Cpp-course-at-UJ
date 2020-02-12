#Autor zadania
Andrzej G�rlich
andrzej.goerlich@uj.edu.pl
http://th.if.uj.edu.pl/~atg/Java

#Tresc zadania
Zadanie 1. CanvasFX (tutorial)
JavaFX to nowoczesny zbi�r narz�dzi s�u��cy mi�dzy innymi do tworzenia graficznego interfejsu u�ytkownika (GUI) w aplikacjach napisanych w Javie. Tak samo jak inne programy napisane w tym j�zyku, aplikacje JavaFX mo�na tworzy� w dowolnym edytorze tekstu lub �rodowisku programistycznym. Tutaj tworzenie aplikacji JavaFX przedstawi� na przyk�adzie darmowego edytora tekstu IntelliJ IDEA.

Przydatne linki:
Instrukcja IntelliJ IDEA i JavaFX
Dokumentacja JavaFX API
Dokument Getting Started with JavaFX
Uwaga: W przypadku braku dost�pu do programu IntelliJ IDEA mo�na pobra� wst�pny szablon (sample.tar.gz).

Przygotowanie projektu:
Prosz� pobra� i zainstalowa� edytor IntelliJ IDEA. Wersja Community jest darmowa a jej kod jest otwarty. Edytor jest dost�pny pod systemy operacyjne Windows, Mac OS i Linux.
Uruchomi� edytor i wybra� Create New Project
W razie potrzeby zainstalowa� Java Development Kit (JDK) i doda�/wybra� odpowiednie �rodowisko.
Z lewej strony wybra� typ projektu JavaFX. Wybra� Next.
Wybra� nazw� projektu np. mandelbrot i dogodny katalog. Wybra� Finish.
Projekt
Projekt sk�ada si� (na razie) z trzech plik�w:

Main.java
Controller.java
sample.fxml
Informacje dotycz�ce struktury aplikacji JavaFX:

G��wna klasa aplikacji JavaFX (Main) dziedziczy po klasie javafx.application.Application. Metoda start() jest punktem wej�cia dla wszystkich aplikacji JavaFX.
Metoda main wywo�uje jedynie metod� launch z klasy javafx.application.Application, kt�ra uruchamia aplikacj� JavaFX i obs�uguje argumenty z linii komend. W przypadku utworzenia archiwum .jar z u�yciem narz�dzia JavaFX Package metoda main() nie jest wymagana.
Plik sample.fxml jest skryptem FXML opartym na j�zyku XML, kt�ry opisuje struktur�/uk�ad interfejsu graficznego i wyst�puj�ce w nim kontrolki/widgety. Taki podzia� pozwala na odseparowanie interfejsu u�ytkownika od logiki kodu. Plik ten jest automatycznie wczytywany i wykorzystywany przez plik Main.java w linijce
     Parent root = FXMLLoader.load(getClass().getResource("sample.fxml")); 
Zadania:
Aby automatycznie skompilowa� i uruchomi� domy�lnie stworzony program mo�na u�y� skr�tu Shift-F10
Zmieni� nazw� pakietu na mandelbrot. Wybieramy plik Main.java, zaznaczamy pakiet sample w pierwszej linijce a nast�pnie wybieramy Refactor -> Rename (Shift-F6).
Zmieni� nazw� klasy Main na CanvasFX. Post�pujemy podobnie jak w przypadku pakietu ale musimy najpierw zaznaczy� klas� Main.
Doda� do pliku sample.fxml nast�puj�ce kontrolki wewn�trz znacznika <GridPane> ... </GridPane>:
<Canvas fx:id="canvas" GridPane.columnIndex="0" GridPane.rowSpan="6" width="512" height="512"
                onMouseDragged="#mouseMoves" onMousePressed="#mousePressed" onMouseReleased="#mouseReleased"/>
<Button GridPane.columnIndex="1" GridPane.rowIndex="0" text="Clear" onAction="#clearCanvas" minWidth="100"/>
<Button GridPane.columnIndex="1" GridPane.rowIndex="1" text="Rectangle" onAction="#drawRect" minWidth="100"/>
<Button GridPane.columnIndex="1" GridPane.rowIndex="3" text="Draw" onAction="#draw" minWidth="100"/>
<Button GridPane.columnIndex="1" GridPane.rowIndex="4" text="Hello" onAction="#sayHello" minWidth="100"/>
<Label  GridPane.columnIndex="1" GridPane.rowIndex="5" fx:id="label" minWidth="100"/>
Cz�� wyra�e� b�dzie na razie b��dna/niezupe�na. W�wczas zostan� one podkre�lone na czerwono. Nale�y na nie najecha� kursorem i wcisn�� Alt-Enter w celu automatycznego uzupe�nienia w pliku Controller.java.
Aby okno automatycznie dostosowa�o wielko�� usuwamy wielko�ci 300, 275 z wywo�ania primaryStage.setScene().
    primaryStage.setScene(new Scene(root));
Sam wygl�d okien i widget�w jest odseparowany od plik�w .java i .fxml. W celu ustalenia wygl�du nale�y u�y� plik�w .css. Tutaj mo�na znale�� wi�cej informacji.
W pliku sample.fxml dodajemy plik stylu main.css do znacznika <GridPane ...>
<GridPane fx:controller="mandelbrot.Controller"
                    xmlns:fx="http://javafx.com/fxml" alignment="center" hgap="10" vgap="10" stylesheets="/mandelbrot/main.css">
�cie�ka /mandelbrot/main.css zostanie podkre�lona na czerwono. Najecha� na nazw� main.css, wcisn�� Alt-Enter i utworzy� plik main.css. Ustawi� wybrane style np.:
.root {
        -fx-background: #eee;
}
.button {
        -fx-color: #f00;
        -fx-font-weight: bold;
}
.label {
        -fx-background-color: #ddf;
}
Uzupe�ni� plik Controller.java za��czonym przyk�adem tak aby
przycisk Clear czy�ci� obiekt canvas,
przycisk Rectangle rysowa� p�prze�roczysty prostok�t,
przycisk Draw rysowa� tr�jk�t Sierpi�skiego,
przycisk Hello wypisa� tekst w etykiecie,
myszk� mo�na by�o zaznacza� ramk� (prostok�t) i wypisa� jej wsp�rz�dne,
Przeanalizowa� kod. W szczeg�lno�ci zrozumie�
znaczenie wywo�ania gc.setGlobalBlendMode(BlendMode.DIFFERENCE).
Zadanie 2. Mandelbrot
Prosz� zapozna� si� z definicj� zbioru Mandelbrota i napisa� program, kt�ry ten zbi�r rysuje. Wykorzysta� w�asn� klas� Complex z poprzedniego zestawu. Kolorem czarnym prosz� rysowa� te punkty p w p�aszczy�nie zespolonej, dla kt�rej ci�g zn + 1 = zn2 + p nie d��y do ?. Dla pozosta�ych punkt�w, kolor uzale�ni� od pr�dko�ci ucieczki (jak szybko, czyli minimalne n, dla kt�rego SqrAbs(zn) jest wi�ksze od pewnego progu r2 - ma to by� parametr, domy�lnie np. 4). Prosz� korzysta� z formatu RGB i wymy�li� �adn� funkcj� koloruj�c�. Ograniczy� si� do maksymalnie ok. 100 iteracji ci�gu zn, aby okre�li� czy ci�g jest rozbie�ny.

Na podstawie Zadania 1. nale�y napisa� program Mandelbrot, kt�ry rysuje fraktal Mandelbrota. Program ma umo�liwia� wyb�r prostok�tnego obszaru (ramka), kt�ry nast�pnie b�dzie powi�kszony. W tym celu nale�y obs�u�y� zdarzenia MouseDragged, MousePressed i MouseReleased. W szczeg�lno�ci prosz� zwr�ci� uwag� na opcj� BlendMode.MULTIPLY w przyk�adzie.

Prosz� zapozna� si� z klasami WritableImage, PixelWriter, i GraphicsContext. s�u��cymi do przechowywania, tworzenia i wy�wietlania obrazu. Aby postawi� pixel (lub prostok�tny obszar pixeli) o danym kolorze mo�na u�y� metod PixelWriter.setArgb() lub (lepiej) PixelWriter.setPixels().

Prosz� napisa� klas� MandelFractal, kt�ra implementuje interfejs

interface ComplexDrawable {
    void draw(PixelWriter pw, Complex a, Complex b, int w, int h);
}
Wywo�anie metody draw() powinno narysowa� fraktal przy u�yciu PixelWriter ograniczony prostok�tem o szeroko�ci w i wysoko�ci h pixeli i rogach danymi przez liczby zespolone a i b .

Doda� pole tekstowe umo�liwiaj�ce wyb�r: zakresu obrazka (czyli zakres p), parametru r, wielko�� obrazka (liczba pixeli - width i height), przycisk rysuj�cy fraktal.