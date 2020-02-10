#include <iostream>
#include <ctime>
#include <cstdlib>
#include <string>

void generateRandom(int n, int max){
    int *tablica = new int[n];
    srand(time(NULL));
    for(int i = 0; i < n; i++){
        tablica[i] = rand()%max;
    }
    for(int i = 0; i < n; i++){
        std::cout << tablica[i] << "\n";
    }
    delete[] tablica;
}

int main(int argc, char *argv[]){
    generateRandom(std::stoi(argv[1]), std::stoi(argv[2]));
}
