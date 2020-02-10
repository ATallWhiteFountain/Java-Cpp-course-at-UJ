#include <iostream>
#include <ctime>
#include <cstdlib>
#include <string>

void generateSortedLast(int n, int max){
    int *tablica = new int[n];
    srand(time(NULL));
    tablica[n-1] = rand()%(max-n)+1;
    for(int i = 0; i < n-1; i++){
        tablica[i] = i;
    }
    for(int i = 0; i < n; i++){
        std::cout << tablica[i] << "\n";
    }
   delete [] tablica;
}

int main(int argc, char *argv[]){
    generateSortedLast(std::stoi(argv[1]), std::stoi(argv[2]));
}
