//
// Created by richardpalinsky on 26.12.2019.
//
#include <iostream>
#include <algorithm>

int seekLeader(int array[], int n){

    int leader = 0, maxLen = 0, currentVal = 0, currentLen = 0;
    for(int i = 0; i < n; i++){
        if(array[i] != currentVal){
            currentVal = array[i];
            currentLen = 0;
        }
        currentLen++;
        // jesli element (currentVal) powtarza sie najczesciej (currentLen)jest potencjalnym przywodca ciagu
        if(currentLen > maxLen){
            maxLen = currentLen;
            leader = currentVal;
        }
    }
    if(maxLen <= n/2) leader = -1;
    return leader;
}

int main(int argc, char *argv[]){
    int i = 0, n = std::stoi(argv[argc-1]);
    int *array = new int[n];
    while(i < n){
        std::cin >> array[i];
        i++;
    }

    // posortowanie danych (O(nlogn)) optymalizuje algorytm
    std::sort(array, array + n);
    int leader = seekLeader(array, n);
    if(leader != -1){
        std::cout << "\nPrzywodca ciagu jest: " << leader;
    } else {
        std::cout << "\nCiag nie ma przywodcy.";
    }
    delete[] array;
}
