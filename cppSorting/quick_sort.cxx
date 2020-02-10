#include <iostream>
#include <string>
#include <chrono>

long long meter = 0;

int particioning(int *tab, int start, int end){

    int piwot = tab[end];       //piwot jako ostatni element tablicy
    int pIndex = start;

    for (int i = start; i < end ; ++i) {//ustawia elementy mniejsze od piwotu po lewej od pIndex
        meter++;
        if( tab[i] < piwot){            // a większe po prawiej
            std::swap( tab[i], tab[pIndex]);
            pIndex++;
        }
    }
    std::swap( tab[pIndex], tab[end]);//zamiana skrajnie prawego elementu z pIndex
    return pIndex;
} // funkcja bedaca elementem Quick Sort'a

void quick_sort(int tab[], int n){
    int start = 0; int end = n -1;
    int stos[ end - start +1];  //tworzenie stosu o rozmiarze tablicy
    int top = -1;
    //wkładanie na stos indeksów początku i końca tablicy
    stos[ ++top ] = start;
    stos[ ++top ] = end;

    while( top >= 0 ){

        //zdejmowanie ze stosu indeksów początka i końca
        end = stos[ top--];
        start = stos[ top--];

        // zapisz indeks piwotu
        int index_piwot = particioning(tab, start, end);

        //wkładanie na stos indeksów początku i końca lewej podtablicy
        if( index_piwot -1 > start){
            stos[ ++top ] = start;
            stos[ ++top ] = index_piwot -1;
        }
        //wkładanie na stos indeksów początku i końca prawej podtablicy
        if( index_piwot + 1 < end){
            stos[ ++top ] = index_piwot + 1;
            stos[ ++top ] = end;
        }

    }

} // Quick Sort (iteracyjny)

int main(int argc, char *argv[]){
    int N = std::stoi(argv[1]);
    int *array = new int[N];
    for(int i=0; i < N; i++){
        std::cin >> array[i];    
    }
    auto start = std::chrono::high_resolution_clock::now();
    quick_sort(array, N);
    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> elapsed = end - start;
    std::cerr << "Elapsed time[s] = " << elapsed.count() << std::endl;

    //for(int i=0; i < N; i++){
     //   std::cout << array[i] << "\n";    
    //}
    //std::cout << "\nIlosc porownan:\t" << meter;
}
