
//
// Created by richardpalinsky on 25.12.2019.
//

#include <iostream>
#include <stack>

constexpr int R = 6, C = 5;

int max(int a, int b){
    return a > b ? a : b;
}

int maxHist(int row[])
{
    // Stos przechowuje indeksy elementow wiersza / 'slupki' histogramu
    // Indeksy w stosie reprezentuja wartosci w porzadku rosnacym (ostatni najwiekszy)
    std::stack<int> result;
    int top_value, area = 0, max_area = 0;
    int i = 0;
    while (i < C)
    {
        // Wrzucam na stos indeks elementu jesli element jest wiekszy (wysokosc slupka histogramu) od tego ktorego
        // indeks jest na wierzcholku stosu
        if (result.empty() || row[result.top()] <= row[i]) {
            result.push(i++); // inkrementacja odbywa sie po operacji
        }
        else
        {
            // Jesli wartosc nie przekracza maksymalnej ze stosu, liczymy pole prostokata
            top_value = row[result.top()];
            result.pop();
            if(result.empty()){
                // szerokosc w przypadku najmniejszej wartosci jaka znalazla sie na stosie
                area = top_value * i;
            }
            else{
                // result.top() reprezentuje indeks poprzedniego najwiekszego slupka, jest to lewy indeks
                // tymczasem i to prawy indeks; ich roznica(i -1) daje szerokosc
                area = top_value * (i - result.top() - 1 );
            }
            max_area = max(area, max_area);
        }
    }

    // dopoki stos nie jest pusty powtarzamy operacje wyliczania max_area
    // kalkulujemy prostokaty schodzac wysokoscia w dol stosu(top_value), jednoczesnie zwiekszajac szerokosc(i-result.top())
    while (!result.empty())
    {
        top_value = row[result.top()];
        result.pop();
        if (result.empty()) {
            area = top_value * i;
        }else{
            area = top_value * (i - result.top() - 1);
        }
        max_area = max(area, max_area);
    }
    return max_area;
}

int maxRectField(int A[][C])
{
    int max_field = maxHist(A[0]);

    for (int i = 1; i < R; i++)
    {
        for (int j = 0; j < C; j++) {
            // gdy element != 0, sumuje go z elementem powyzej, zliczajac jedynki
            if (A[i][j]) A[i][j] += A[i - 1][j];
        }
        // Po przejsciu kolejnego wiersza porownuje aktualny histogram z dotychczasowo najwiekszym
        max_field = max(max_field, maxHist(A[i]));
    }
    return max_field;
}

void ctoi(char A[][C], int newA[][C]){
    for(int i = 0; i < R; i++){
        for(int j = 0; j < C; j++){
            if(A[i][j]=='1') newA[i][j] = 1;
            else newA[i][j] = 0;
        }
    }
}

int main(int argc, char *argv[]){
    char A[][C] = {{'0', '1', '1', '0', '1'},
                   {'1', '1', '0', '1', '0'},
                   {'0', '1', '1', '1', '0'},
                   {'1', '1', '1', '1', '0'},
                   {'1', '1', '1', '1', '1'},
                   {'0', '0', '0', '0', '0'}};
    int newA[R][C];
    ctoi(A, newA);
    std::cout << "\nPole prostokata wynosi: " << maxRectField(newA) << "\n";
}
