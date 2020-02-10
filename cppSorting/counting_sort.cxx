#include <iostream>
#include <string>
#include <chrono>

void  counting_sort(int array[], int n){
    const int RANGE = 200000; // liczba elementow ktorych wystepowanie bede zliczal
    int *copied_array = new int[n];
    int *count = new int[RANGE];
    for(int i = 0; i < n; i++){
        copied_array[i] = array[i];
    }
    for(int i = 0; i < RANGE; i++){
        count[i] = 0;
    }
    for(int i = 0; i < n; i++){
        count[copied_array[i]]++;
    }
    for(int i = 0; i < RANGE; i++){
        count[i] += count[i -1];
    }
    for(int i = n - 1; i >= 0; i--){
        array[count[copied_array[i]] - 1] = copied_array[i];
        count[copied_array[i]]--;
    }
    delete[] copied_array;
    delete[] count;
} // Counting Sort

int main(int argc, char *argv[]){
    int N = std::stoi(argv[1]);
    int *array = new int[N];
    for(int i=0; i < N; i++){
        std::cin >> array[i];    
    }
    auto start = std::chrono::high_resolution_clock::now();
    counting_sort(array, N);
    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> elapsed = end - start;
    std::cerr << "Elapsed time[s] = " << elapsed.count() << std::endl;

    //for(int i=0; i < N; i++){
    //    std::cout << array[i] << "\n";    
    //}
}
