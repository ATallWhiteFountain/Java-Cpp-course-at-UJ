#Autor zadania
Andrzej G�rlich
andrzej.goerlich@uj.edu.pl
http://th.if.uj.edu.pl/~atg/Java

#Tresc zadania
Zadanie 3. Pong
Wykorzystuj�c w�tki w Javie napisa� wersj� gry Pong.
Przyk�adowe video.

Wskaz�wka: Aby zmodyfikowa� stan wid�etu JavaFX (np. u�ywaj�c setText() na Label) z w�asnego w�tku (np. pi�ki lub paletki) nale�y wys�a� zadanie do kolejki zdarze� g��wnego w�tku aplikacji JavaFX wed�ug nast�puj�cego schematu

Platform.runLater(() -> label.setText("..."));
Wskazane jest aby stworzy� metod�, kt�ra uaktualnia wynik gry w klasie gry i kt�ra b�dzie wywo�ywana przez w�tek pi�ki lub paletki.