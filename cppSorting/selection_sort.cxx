#include <iostream>
#include <string>
#include <algorithm>
#include <chrono>
long long meter = 0;

void  selection_sort(int array[], int n){
    for(int i = 0; i < n; i++) {
        int j_min_val = i; // j_min_val to indeks najmniejszej wartosci w danym obrocie petli
        for (int j = i + 1; j < n + 1; j++){
            meter++;
            if (array[j] < array[j_min_val]) j_min_val = j; // ustawiam j_min_val na indeks najmniejszej wartosci
            }
        if(j_min_val != i){
             std::swap(array[j_min_val], array[i]);
        }
    }
} // Selection Sort

int main(int argc, char *argv[]){
    int N = std::stoi(argv[1]);
    int *array = new int[N];
    for(int i=0; i < N; i++){
        std::cin >> array[i];    
    }
    auto start = std::chrono::high_resolution_clock::now();
    selection_sort(array, N);
    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> elapsed = end - start;
    std::cerr << "Elapsed time[s] = " << elapsed.count() << std::endl;



    //for(int i=0; i < N; i++){
     //   std::cout << array[i] << "\n";    
    //}
    //std::cout <<"\nIlosc porownan:\t" << meter;  
}
