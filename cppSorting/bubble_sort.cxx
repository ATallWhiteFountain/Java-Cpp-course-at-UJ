#include <iostream>
#include <string>
#include <chrono>

long long meter = 0;

void bubble_sort(int array[], int n){
    for(int i=0; i<(n-1); i++)
    {
        for(int j=0; j<(n-i-1); j++)
        {
            meter++;
            if(array[j]>array[j+1])
            {
                std::swap(array[j], array[j+1]);
            }
        }
    }
} // Bubble Sort


int main(int argc, char *argv[]){
    int N = std::stoi(argv[1]);
    int *array = new int[N];
    for(int i=0; i < N; i++){
        std::cin >> array[i];    
    }
    auto start = std::chrono::high_resolution_clock::now();
        bubble_sort(array, N);
    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> elapsed = end - start;
    std::cerr << "Elapsed time[s] = " << elapsed.count() << std::endl;


    //for(int i=0; i < N; i++){
    //    std::cout << array[i] << "\n";    
    //}
    //std::cout <<"\nIlosc porownan:\t" << meter;  
}
