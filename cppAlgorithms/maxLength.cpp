#include <iostream>

int findSum(int a[], int n){
    int prev_largest = 0, largest = 0, prev_largest_temp, largest_temp;
    for(int i = 0; i < n; i++){
        largest_temp = prev_largest;
        prev_largest_temp = std::max( largest+a[i]-a[i-1] , prev_largest_temp);
        largest = largest_temp;
        prev_largest = prev_largest_temp;
    }
    return largest;
}

int main(int argc, char *argv[]){
    int i = 0, n = std::stoi(argv[argc-1]);
    int *array = new int[n];
    // input - zal: wprowadzana tablica jest posortowana
    for (i; i < n; ++i) {
        std::cin >> array[i];
    }

    std::cout << "\nMaksymalna suma długości rozłącznych odcinków: " << findSum(array, n) << "\n";

    delete[]array;
}
