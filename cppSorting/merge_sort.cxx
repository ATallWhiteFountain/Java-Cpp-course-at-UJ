#include <iostream>
#include <string>
#include <chrono>

long long meter = 0;

int lesserOfTwo(int x, int y){
    return (x<y) ? x : y;
} // funkcja potrzebna do Merge Sort'a

void merge(int array[], int start, int mid, int end){
    int n1 = mid - start + 1; int leftArray[n1];
    int n2 = end - mid; int rightArray[n2];
    int i, j, k;

    for(i = 0; i < n1; i++){
        leftArray[i] = array[start + i];
    }
    for(j = 0; j < n2; j++){
        rightArray[j] = array[mid + 1 + j];
    } // tworze leftArray i rightArray kopiujac wartosci z oryginalnej array

    i = 0; j = 0; k = start;
    while (i < n1 && j < n2){
        meter++;
        if(leftArray[i] <= rightArray[j]){
            array[k] = leftArray[i];
            i++;
        }else{
            array[k] = rightArray[j];
            j++;
        }
        k++;
    } // scalam tablice, przypisujac znow do array wartosci, tym razem juz w porzadku niemalejacym

    while(i < n1){
        array[k] = leftArray[i];
        i++; k++;
    }
    while(j < n2){
        array[k] = rightArray[j];
        j++; k++;
    } // jesli ktoras z podtablic zawiera jeszcze jakies elementy (tylko jedna), to dopisuje do array reszte jej el.
} // funkcja potrzebna do Merge Sort'a

void merge_sort(int array[], int n){
    int leftStart, subarraySize;
    int mid, rightEnd;
    for(subarraySize = 1; subarraySize <= n-1; subarraySize = 2*subarraySize){ // do momentu uzyskania jednolitej listy
        // subarraySize - 1, 2, 4, 8, 16,...

        for(leftStart = 0; leftStart <= n-1; leftStart += 2*subarraySize) {
            // leftStart jest indeksem zerowym kazdej podlisty

            mid = lesserOfTwo(leftStart + subarraySize - 1, n - 1); // indeks ostatniego el. lewej podlisty
            rightEnd = lesserOfTwo(leftStart + 2 * subarraySize - 1, n - 1); // ostatni indeks prawej podlisty

            merge(array, leftStart, mid, rightEnd);
        }
    }
} // Merge Sort (iteracyjny)

int main(int argc, char *argv[]){
    int N = std::stoi(argv[1]);
    int *array = new int[N];
    for(int i=0; i < N; i++){
        std::cin >> array[i];    
    }
    auto start = std::chrono::high_resolution_clock::now();
    merge_sort(array, N);
    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> elapsed = end - start;
    std::cerr << "Elapsed time[s] = " << elapsed.count() << std::endl;

    //for(int i=0; i < N; i++){
    //    std::cout << array[i] << "\n";    
    //}
    //std::cout << "\nIlosc porownan:\t" << meter;
}
