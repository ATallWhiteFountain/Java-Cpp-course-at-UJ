#Autor zadania
Andrzej Görlich
andrzej.goerlich@uj.edu.pl
http://th.if.uj.edu.pl/~atg/Java

#Tresc zadania
Zadanie 1. CanvasFX (tutorial)
JavaFX to nowoczesny zbiór narzêdzi s³u¿¹cy miêdzy innymi do tworzenia graficznego interfejsu u¿ytkownika (GUI) w aplikacjach napisanych w Javie. Tak samo jak inne programy napisane w tym jêzyku, aplikacje JavaFX mo¿na tworzyæ w dowolnym edytorze tekstu lub œrodowisku programistycznym. Tutaj tworzenie aplikacji JavaFX przedstawiê na przyk³adzie darmowego edytora tekstu IntelliJ IDEA.

Przydatne linki:
Instrukcja IntelliJ IDEA i JavaFX
Dokumentacja JavaFX API
Dokument Getting Started with JavaFX
Uwaga: W przypadku braku dostêpu do programu IntelliJ IDEA mo¿na pobraæ wstêpny szablon (sample.tar.gz).

Przygotowanie projektu:
Proszê pobraæ i zainstalowaæ edytor IntelliJ IDEA. Wersja Community jest darmowa a jej kod jest otwarty. Edytor jest dostêpny pod systemy operacyjne Windows, Mac OS i Linux.
Uruchomiæ edytor i wybraæ Create New Project
W razie potrzeby zainstalowaæ Java Development Kit (JDK) i dodaæ/wybraæ odpowiednie œrodowisko.
Z lewej strony wybraæ typ projektu JavaFX. Wybraæ Next.
Wybraæ nazwê projektu np. mandelbrot i dogodny katalog. Wybraæ Finish.
Projekt
Projekt sk³ada siê (na razie) z trzech plików:

Main.java
Controller.java
sample.fxml
Informacje dotycz¹ce struktury aplikacji JavaFX:

G³ówna klasa aplikacji JavaFX (Main) dziedziczy po klasie javafx.application.Application. Metoda start() jest punktem wejœcia dla wszystkich aplikacji JavaFX.
Metoda main wywo³uje jedynie metodê launch z klasy javafx.application.Application, która uruchamia aplikacjê JavaFX i obs³uguje argumenty z linii komend. W przypadku utworzenia archiwum .jar z u¿yciem narzêdzia JavaFX Package metoda main() nie jest wymagana.
Plik sample.fxml jest skryptem FXML opartym na jêzyku XML, który opisuje strukturê/uk³ad interfejsu graficznego i wystêpuj¹ce w nim kontrolki/widgety. Taki podzia³ pozwala na odseparowanie interfejsu u¿ytkownika od logiki kodu. Plik ten jest automatycznie wczytywany i wykorzystywany przez plik Main.java w linijce
     Parent root = FXMLLoader.load(getClass().getResource("sample.fxml")); 
Zadania:
Aby automatycznie skompilowaæ i uruchomiæ domyœlnie stworzony program mo¿na u¿yæ skrótu Shift-F10
Zmieniæ nazwê pakietu na mandelbrot. Wybieramy plik Main.java, zaznaczamy pakiet sample w pierwszej linijce a nastêpnie wybieramy Refactor -> Rename (Shift-F6).
Zmieniæ nazwê klasy Main na CanvasFX. Postêpujemy podobnie jak w przypadku pakietu ale musimy najpierw zaznaczyæ klasê Main.
Dodaæ do pliku sample.fxml nastêpuj¹ce kontrolki wewn¹trz znacznika <GridPane> ... </GridPane>:
<Canvas fx:id="canvas" GridPane.columnIndex="0" GridPane.rowSpan="6" width="512" height="512"
                onMouseDragged="#mouseMoves" onMousePressed="#mousePressed" onMouseReleased="#mouseReleased"/>
<Button GridPane.columnIndex="1" GridPane.rowIndex="0" text="Clear" onAction="#clearCanvas" minWidth="100"/>
<Button GridPane.columnIndex="1" GridPane.rowIndex="1" text="Rectangle" onAction="#drawRect" minWidth="100"/>
<Button GridPane.columnIndex="1" GridPane.rowIndex="3" text="Draw" onAction="#draw" minWidth="100"/>
<Button GridPane.columnIndex="1" GridPane.rowIndex="4" text="Hello" onAction="#sayHello" minWidth="100"/>
<Label  GridPane.columnIndex="1" GridPane.rowIndex="5" fx:id="label" minWidth="100"/>
Czêœæ wyra¿eñ bêdzie na razie b³êdna/niezupe³na. Wówczas zostan¹ one podkreœlone na czerwono. Nale¿y na nie najechaæ kursorem i wcisn¹æ Alt-Enter w celu automatycznego uzupe³nienia w pliku Controller.java.
Aby okno automatycznie dostosowa³o wielkoœæ usuwamy wielkoœci 300, 275 z wywo³ania primaryStage.setScene().
    primaryStage.setScene(new Scene(root));
Sam wygl¹d okien i widgetów jest odseparowany od plików .java i .fxml. W celu ustalenia wygl¹du nale¿y u¿yæ plików .css. Tutaj mo¿na znaleŸæ wiêcej informacji.
W pliku sample.fxml dodajemy plik stylu main.css do znacznika <GridPane ...>
<GridPane fx:controller="mandelbrot.Controller"
                    xmlns:fx="http://javafx.com/fxml" alignment="center" hgap="10" vgap="10" stylesheets="/mandelbrot/main.css">
Œcie¿ka /mandelbrot/main.css zostanie podkreœlona na czerwono. Najechaæ na nazwê main.css, wcisn¹æ Alt-Enter i utworzyæ plik main.css. Ustawiæ wybrane style np.:
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
Uzupe³niæ plik Controller.java za³¹czonym przyk³adem tak aby
przycisk Clear czyœci³ obiekt canvas,
przycisk Rectangle rysowa³ pó³przeŸroczysty prostok¹t,
przycisk Draw rysowa³ trójk¹t Sierpiñskiego,
przycisk Hello wypisa³ tekst w etykiecie,
myszk¹ mo¿na by³o zaznaczaæ ramkê (prostok¹t) i wypisaæ jej wspó³rzêdne,
Przeanalizowaæ kod. W szczególnoœci zrozumieæ
znaczenie wywo³ania gc.setGlobalBlendMode(BlendMode.DIFFERENCE).
Zadanie 2. Mandelbrot
Proszê zapoznaæ siê z definicj¹ zbioru Mandelbrota i napisaæ program, który ten zbiór rysuje. Wykorzystaæ w³asn¹ klasê Complex z poprzedniego zestawu. Kolorem czarnym proszê rysowaæ te punkty p w p³aszczyŸnie zespolonej, dla której ci¹g zn + 1 = zn2 + p nie d¹¿y do ?. Dla pozosta³ych punktów, kolor uzale¿niæ od prêdkoœci ucieczki (jak szybko, czyli minimalne n, dla którego SqrAbs(zn) jest wiêksze od pewnego progu r2 - ma to byæ parametr, domyœlnie np. 4). Proszê korzystaæ z formatu RGB i wymyœliæ ³adn¹ funkcjê koloruj¹c¹. Ograniczyæ siê do maksymalnie ok. 100 iteracji ci¹gu zn, aby okreœliæ czy ci¹g jest rozbie¿ny.

Na podstawie Zadania 1. nale¿y napisaæ program Mandelbrot, który rysuje fraktal Mandelbrota. Program ma umo¿liwiaæ wybór prostok¹tnego obszaru (ramka), który nastêpnie bêdzie powiêkszony. W tym celu nale¿y obs³u¿yæ zdarzenia MouseDragged, MousePressed i MouseReleased. W szczególnoœci proszê zwróciæ uwagê na opcjê BlendMode.MULTIPLY w przyk³adzie.

Proszê zapoznaæ siê z klasami WritableImage, PixelWriter, i GraphicsContext. s³u¿¹cymi do przechowywania, tworzenia i wyœwietlania obrazu. Aby postawiæ pixel (lub prostok¹tny obszar pixeli) o danym kolorze mo¿na u¿yæ metod PixelWriter.setArgb() lub (lepiej) PixelWriter.setPixels().

Proszê napisaæ klasê MandelFractal, która implementuje interfejs

interface ComplexDrawable {
    void draw(PixelWriter pw, Complex a, Complex b, int w, int h);
}
Wywo³anie metody draw() powinno narysowaæ fraktal przy u¿yciu PixelWriter ograniczony prostok¹tem o szerokoœci w i wysokoœci h pixeli i rogach danymi przez liczby zespolone a i b .

Dodaæ pole tekstowe umo¿liwiaj¹ce wybór: zakresu obrazka (czyli zakres p), parametru r, wielkoœæ obrazka (liczba pixeli - width i height), przycisk rysuj¹cy fraktal.