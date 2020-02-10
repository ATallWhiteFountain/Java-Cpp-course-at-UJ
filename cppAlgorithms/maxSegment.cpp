#include<iostream>

int maxSegment(int array[], int n){
    int max_segment_value = 0, current_segment_value = 0, current_value = INT32_MAX;
    for (int i = 0; i < n; ++i) {
        // jesli nastepny element jest mniejszy od poprzedniego
        // resetujemy wartosc sumy podciagu
        if(array[i] < current_value){
            current_segment_value = 0;
        }
        // wartosc sumy podciagu jest powiekszana o wartosc rozpatrywanego elementu
        current_segment_value = current_segment_value + array[i];
        current_value = array[i];
        if(current_segment_value > max_segment_value){
            max_segment_value = current_segment_value;
        }
    }
    return max_segment_value;
}

int main(int argc, char *argv[])
{
    int i=0, n = std::stoi(argv[argc-1]);
    int *array = new int[n];
    while(i < n){
        std::cin >> array[i];
        i++;
    }

    std::cout << "Maksymalny segment ma wartosc: " << maxSegment(array, n) << "\n";

    delete[] array;
}