#include <iostream>
#include <string>
#include <ctime>

void printResult(int a[], int n){
    for (int i = 0; i < n; ++i) {
        std::cout << a[i] << ", ";
    }
    std::cout << std::endl;
}

void shuffle(int a[], int n){
    srand(time(NULL));
    int j;
    for(int i = n-1; i >= 1; i--){
        j = rand()%i;
        std::swap(a[i], a[j]);
    }
} // Knuth shuffle

int simpleInvCount(int a[], int n){
    int inv_count = 0;
    for(int i = 0; i < n - 1; i++){
        for(int j = i + 1; j < n; j++){
            if(a[i] > a[j]) inv_count++;
        }
    }
    return inv_count;
}

int merge(int a[], int temp[], int left, int mid, int right){
    int inv_count = 0;
    int i = left, j = mid, k = left;
    // dopoki obydwie podlisty sie nie wyczerpaly - do temp
    while((i <= mid-1)&&(j <= right)){
        if(a[i] <= a[j]){
            temp[k++] = a[i++];
        }else{
            temp[k++] = a[j++];
            // At any step in merge(), if a[i] is greater than a[j], then there are (mid – i) inversions,
            // because left and right subarrays are sorted, so all the
            // remaining elements in left-subarray (a[i+1], a[i+2] … a[mid]) will be greater than a[j]
            inv_count = inv_count + (mid - i);
        }
    }
    while(i <= mid-1){
        temp[k++] = a[i++];
    }
    while(j <= right){
        temp[k++] = a[j++];
    }
    for(i = left; i <= right; i++){
        a[i] = temp[i];
    }
    return inv_count;
}

int mergeSort(int a[], int temp[], int left, int right){
    int mid, inv_count = 0;
    if(left < right){
        mid = (left + right)/2;
        /* Inversion count will be sum of inversions in left-part, right-part and number of inversions in merging */
        inv_count += mergeSort(a, temp, left, mid);
        inv_count += mergeSort(a, temp, mid+1, right);
        inv_count += merge(a, temp, left, mid+1, right);
    }
    return inv_count;
}

int mergeInvCount(int a[], int n){
    int temp[n];
    return mergeSort(a, temp, 0, n-1);
}

int main(int argc, char *argv[]){
    int n = std::stoi(argv[argc-1]);
    int *array = new int[n];
    for(int i = 0; i < n; i++){
        array[i] = i + 1;
    }
    printResult(array, n);

    shuffle(array, n);
    printResult(array, n);
    std::cout << "Naive: inversions number: " << simpleInvCount(array, n) << "\n";
    std::cout << "Optimal: inversions number: " << mergeInvCount(array, n) << "\n";
    delete[] array;
}