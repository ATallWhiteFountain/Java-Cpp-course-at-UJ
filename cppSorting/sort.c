#include <stdio.h>
#include <stdlib.h>

int comparator(const void *a, const void *b){
    const int *p1 = a;
    const int *p2 = b;
    return *p1 - *p2; // zwracam roznice ze zdereferencjowanych wskaznikow (roznice ich wartosci)
}

int main(int argc, char *argv[]){
    int N = atoi(argv[1]);
    int array[N];
    for(int i=0; i < N; i++){
        scanf("%d", &array[i]);    
    }

    qsort(array, N, sizeof(int), comparator);

    for(int i=0; i < N; i++){
        printf( "%d\n",array[i] );   
    }
}
