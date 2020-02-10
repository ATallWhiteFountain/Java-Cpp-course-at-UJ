#include <iostream>
#include <ctime>
#include <cstdlib>
#include <string>

void generateSortedFirst(int n, int max){
    int *tablica = new int[n];
    srand(time(NULL));
    tablica[0] = rand()%max+n;
    for(int i = 1; i < n; i++){
        tablica[i] = i;
    }
    for(int i = 0; i < n; i++){
        std::cout << tablica[i] << "\n";
    }
   delete [] tablica;
}

int main(int argc, char *argv[]){
    generateSortedFirst(std::stoi(argv[1]), std::stoi(argv[2]));
}
