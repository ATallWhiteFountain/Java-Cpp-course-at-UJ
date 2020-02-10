#include <iostream>
#include <string>
#include <cmath>
#include <vector>
#include <chrono>

class Macierz{
private:
    std::vector<std::vector<int>> tablica_kolejek;
    std::vector<int> kolejka;
public:
    Macierz(){
        for(int i=0;i<10;i++){
            tablica_kolejek.push_back(kolejka);
        }
    }
    int get_size(int a){
        return tablica_kolejek[a].size();
    }
    int get_val(int a,int b){
        return tablica_kolejek[a][b];
    }
    void push_back(int a, int c){
        tablica_kolejek[a].push_back(c);
    }
    void clear(int a){
        tablica_kolejek[a].clear();
    }
};

int getDigitsQuantity (int arr[], int N){
    int digits , maxval = arr[0];
    for(int i=1; i<N; i++){
        if(maxval < arr[i]) maxval = arr[i];
    }
    for(int i=1;;i++){
        maxval /= 10;
        if(maxval < 1){
            digits = i;
            break;
        }
    }
    return digits;
} // funkcja potrzebna do Radix Sort'a (wyznacza liczbe cyfr na ktorych bedziemy operowac)

void radix_sort(int array[], int n){
    int digits_quant = getDigitsQuantity(array, n);
    int divisor, divisor2, count = 0;
    Macierz matrix;
    for(int digit_position = 0; digit_position < digits_quant; digit_position++){
        // dla cyfr jednosci, dziesiatek, setek, itp.

        divisor = pow(10, digit_position);
        divisor2 = pow(10, digit_position + 1);

        for(int i = 0; i < n; i++){
            matrix.push_back((((array[i] % divisor2) - (array[i] % divisor)) / divisor), array[i]);
            // wrzucam element tablicy do kolejki o indeksie rownym cyfrze na odpowiedniej pozycji
        }

        for(int i = 0; i < 10; i++){
            if (matrix.get_size(i) > 0) { // jesli kolejka z dana cyfra jest niepusta
                for (int j = 0; j < matrix.get_size(i); j++){ // przechodzimy wszystkie jej elementy
                    array[count] = matrix.get_val(i, j);
                    // wypelniam tablice wartosciami wstepnie posortowanymi (posortowanymi 'po cyfrze')
                    count++;
                }
                matrix.clear(i);
            }
        }
        count = 0;
    }
} // Radix Sort

int main(int argc, char *argv[]){
    int N = std::stoi(argv[1]);
    int *array = new int[N];
    for(int i=0; i < N; i++){
        std::cin >> array[i];    
    }
    auto start = std::chrono::high_resolution_clock::now();
    radix_sort(array, N);
    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> elapsed = end - start;
    std::cerr << "Elapsed time[s] = " << elapsed.count() << std::endl;

    //for(int i=0; i < N; i++){
     //   std::cout << array[i] << "\n";    
    //}
}
