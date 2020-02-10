#include <iostream>
#include <ctime>
#include <cstdlib>
#include <string>

void generateReverse(int n, int max){
    int *tablica = new int[n];
    for(int i = n-1, j = 0; i >= 0; i--, j++){
        tablica[j] = i;
    }   
    for(int i = 0; i < n; i++){
        std::cout << tablica[i] << "\n";
    }
   delete [] tablica;
}

int main(int argc, char *argv[]){
    generateReverse(std::stoi(argv[1]), std::stoi(argv[2]));
}
