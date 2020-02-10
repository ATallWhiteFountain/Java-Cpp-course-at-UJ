//
// Created by richardpalinsky on 27.12.2019.
//
#include <iostream>

void getArray(int *array, int n){
    for(int i = 0; i < n; i++){
        std::cin >> array[i];
    }
}

int find(int array[], int difference, int start, int end){

    // sprawdzanie czy srodkowy element jest tym szukanym
    if(array[start+end/2] == difference) return 1;

    // gdy nie znaleziono elementu po przejsciach rekurencyjnych
    if(start-end == -1 || start-end == 0 || start-end == 1) return 0;

    // jesli srodkowy element jest mniejszy od difference, to szukamy w prawej podtablicy wiekszych elementow
    if(array[start+end/2] < difference) return find(array, difference, start+end/2, end);
    // gdy zbyt duzy - lewa w lewej podtablicy szukajac wsrod mniejszych wartosci
    if(array[start+end/2] > difference) return find(array, difference, start, start+end/2);
}

int main(int argc, char *argv[]){
    int n = std::stoi(argv[argc-1]);
    int A[n], B[n], x;
    bool ifExists = false;

    // input - zalozeniem jest ze wprowadzane tablice sa posortowane rosnaco
    std::cout << "Enter parameter x\n";
    std::cin >> x;
    std::cout << "Enter a first set\n";
    getArray(A, n);
    std::cout << "Enter a second set\n";
    getArray(B, n);

    for(int i = 0; i < n; i++){
        if(B[i] > x){
            if(find(A, B[i]-x, 0, n)==1) ifExists = true;
        }else{
            if(find(A, x-B[i], 0, n)==1) ifExists = true;
        }
    }

    if(ifExists){
        std::cout << "\nIstnieja takie a z A[] i b z B[] ze x=a+b\n";
    }else{
        std::cout << "\nNie znaleziono liczb a, b takich ze x=a+b\n";
    }

}