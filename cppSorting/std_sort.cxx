#include <iostream>
#include <string>
#include <algorithm>

int main(int argc, char *argv[]){
    int N = std::stoi(argv[1]);
    int *array = new int[N];
    for(int i=0; i < N; i++){
        std::cin >> array[i];    
    }

    std::sort(array, array + N);

    for(int i=0; i < N; i++){
        std::cout << array[i] << "\n";    
    }
}
