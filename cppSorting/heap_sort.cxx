#include <iostream>
#include <string>
#include <chrono>

long long meter = 0;

class Heap{
private:
    int *array;
    int N;
    void heapify(int element, int size){
        int root = element;
        int leftChild = 2 * element + 1;
        int rightChild = 2 * element + 2;

        meter++;
        if(array[leftChild] > array[root] && leftChild < size){
            root = leftChild;
        }
        meter++;
        if(array[rightChild] > array[root] && rightChild < size){
            root = rightChild;
        } // indeks root musi wskazywac na najwieksza wartosc
        meter++;
        if(root != element){
            swap(element, root);
            // zamiana miejscami elementow zaburzajacych wlasciwosci Max Heap
            heapify(root, size);
            // rekurencyjne schodzenie w dol stogu sprawdzajac czy wlasciwosci sa zachowane
        }
    } // metoda odpowiedzialna za wlasciwosci kopca
    void swap(int a, int b){
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
public:
    void getOutput(int a[], int n){
        for(int i = 0; i < n; i++){
            a[i] = array[i];
        }
        delete[] array;
    } // zwracam posortowana tablice
    void build(int a[], int n){
        N = n;
        array = new int[N];
        for(int i = 0; i < N; i++){
            array[i] = a[i];
        } // kopiuje do wewnetrznej tablicy kopca
        for(int i = N/2-1; i >= 0; i--){
            // zaczynam od polowy w kierunku poczatku
            heapify(i, N);
        }
    } // buduje kopiec, korzystam z kopii tablicy wejsciowej
    void sort(){
        for(int i = N - 1; i >= 0; i--){
            swap(0, i); // zamiana miejscami elementow na pozycji 0 (maxval) i ostatniej (minval)
            heapify(0, i); // nadal zachowujemy wlasnosci kopca
        }
    }
}; // Heap Max type

void heap_sort(int array[], int n){
    Heap kopiec;
    kopiec.build(array, n);
    kopiec.sort();
    kopiec.getOutput(array, n);
} // Heap Sort (rekurencyjny)

int main(int argc, char *argv[]){
    int N = std::stoi(argv[1]);
    int *array = new int[N];
    for(int i=0; i < N; i++){
        std::cin >> array[i];    
    }
    auto start = std::chrono::high_resolution_clock::now();
    heap_sort(array, N);
    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> elapsed = end - start;
    std::cerr << "Elapsed time[s] = " << elapsed.count() << std::endl;

    //for(int i=0; i < N; i++){
    //    std::cout << array[i] << "\n";    
    //}
    //std::cout << "\nIlosc porownan:\t" << meter;
}
