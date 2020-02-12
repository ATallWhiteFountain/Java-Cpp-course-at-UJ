#Autor zadania
Andrzej Görlich
andrzej.goerlich@uj.edu.pl
http://th.if.uj.edu.pl/~atg/Java

#Tresc zadania
Zadanie 3. Pong
Wykorzystuj¹c w¹tki w Javie napisaæ wersjê gry Pong.
Przyk³adowe video.

Wskazówka: Aby zmodyfikowaæ stan wid¿etu JavaFX (np. u¿ywaj¹c setText() na Label) z w³asnego w¹tku (np. pi³ki lub paletki) nale¿y wys³aæ zadanie do kolejki zdarzeñ g³ównego w¹tku aplikacji JavaFX wed³ug nastêpuj¹cego schematu

Platform.runLater(() -> label.setText("..."));
Wskazane jest aby stworzyæ metodê, która uaktualnia wynik gry w klasie gry i która bêdzie wywo³ywana przez w¹tek pi³ki lub paletki.