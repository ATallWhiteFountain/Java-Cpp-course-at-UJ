
#include <iostream>
#include <ctime>
#include <string>

class Point{
private:
    double x;
    double y;

    double generate(){
        return 0.01*(double)(rand()%100);
    }
public:
    Point(){
        x = generate();
        y = generate();
    }

    void printOut(){
        std::cout << "(" << x << ";" << y << ")\n";
    }

    double getX(){
        return x;
    }

    double getY(){
        return y;
    }
};

class Pair{
    Point first;
    Point second;
public:
    Pair(Point p1, Point p2){
        first = p1;
        second = p2;
    }
    Pair(){}
    void printOut(){
        first.printOut();
        second.printOut();
    }
    double getDistanceSquared(){
        return (first.getX()-second.getX())*(first.getX()-second.getX())
                + (first.getY()-second.getY())*(first.getY()-second.getY());
    }
};

void genSquare(Point a[], int amount){
    for(int i = 0; i < amount; i++){
        a[i] = Point();
        a[i].printOut();
    }
}

Pair bruteForceDistance(Point a[], int n){
    double minDistance = 1;
    Pair currentPair, closestPair;
    for(int i = 0; i < n; i++){
        for(int j = i+1; j < n; j++){
            currentPair = Pair(a[i], a[j]);
            if(currentPair.getDistanceSquared() < minDistance*minDistance){
                minDistance = currentPair.getDistanceSquared();
                closestPair = currentPair;
            }
        }
    }
    return closestPair;
}

int lesserOfTwo(int x, int y) {
    return (x < y) ? x : y;
}

void merge(Point array[], int start, int mid, int end, bool sortingByX) {
    int n1 = mid - start + 1;
    Point leftArray[n1];
    int n2 = end - mid;
    Point rightArray[n2];
    int i, j, k;

    for (i = 0; i < n1; i++) {
        leftArray[i] = array[start + i];
    }
    for (j = 0; j < n2; j++) {
        rightArray[j] = array[mid + 1 + j];
    } // tworze leftArray i rightArray kopiujac wartosci z oryginalnej array

    i = 0;
    j = 0;
    k = start;
    while (i < n1 && j < n2) {
        if (sortingByX) {
            if (leftArray[i].getX() <= rightArray[j].getX()) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
        } else {
            if (leftArray[i].getY() <= rightArray[j].getY()) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
        } // scalam tablice, przypisujac znow do array wartosci, tym razem juz w porzadku niemalejacym
    k++;
    }
    while(i < n1){
        array[k] = leftArray[i];
        i++; k++;
    }
    while(j < n2){
        array[k] = rightArray[j];
        j++; k++;
    } // jesli ktoras z podtablic zawiera jeszcze jakies elementy (tylko jedna), to dopisuje do array reszte jej el.
} // merging

void merge_sort(Point array[], int n, bool flag){
    int leftStart, subarraySize;
    int mid, rightEnd;
    for(subarraySize = 1; subarraySize <= n-1; subarraySize = 2*subarraySize){ // do momentu uzyskania jednolitej listy
        // subarraySize - 1, 2, 4, 8, 16,...

        for(leftStart = 0; leftStart <= n-1; leftStart += 2*subarraySize) {
            // leftStart jest indeksem zerowym kazdej podlisty

            mid = lesserOfTwo(leftStart + subarraySize - 1, n - 1); // indeks ostatniego el. lewej podlisty
            rightEnd = lesserOfTwo(leftStart + 2 * subarraySize - 1, n - 1); // ostatni indeks prawej podlisty

            merge(array, leftStart, mid, rightEnd, flag);
        }
    }
} // Merge Sort (iteracyjny)



double getDistanceSquared(Point first, Point second){
    return (first.getX()-second.getX())*(first.getX()-second.getX())
           + (first.getY()-second.getY())*(first.getY()-second.getY());
}


Pair stripClosest(Point strip[], int size, double smallestDistance){
    double minVal = smallestDistance;
    Pair minDistPair;
    // petla ponizej nigdy nie zostala wywolana wiecej niz 6 razy
    for(int i = 0; i < size; i++){
        for(int j = i + 1; j < size && (strip[j].getY() - strip[i].getY()) < minVal; j++) {
            if (getDistanceSquared(strip[i], strip[j]) < minVal*minVal){
                minVal = getDistanceSquared(strip[i], strip[j]);
                minDistPair = Pair(strip[i], strip[j]);
            }
        }
    }
    return minDistPair;
}

Pair closest(Point aX[], Point aY[], int n){
    int mid = n/2;
    Point midPoint = aX[mid]; // midPoint biore z aX
    // Podzial punktow z aY[] na dwie podtablice wzgledem linii wertykalnej (mid=n/2)
    Point aLeftY[mid+1];
    Point aRightY[n-mid-1];
    int leftIndex = 0, rightIndex = 0;

    for(int i = 0; i < n; i++){
        if(aY[i].getX() <= midPoint.getX()){
            // do lewej podtablicy (aLeftY) trafiaja punkty z wartosciami x mniejszymi niz dla punktu srodkowego (midPoint)
            aLeftY[leftIndex++] = aY[i];
        }else{
            // do prawej (aRightY) trafiaja punkty z wartosciami y wiekszymi niz dla punktu srodkowego
            aRightY[rightIndex++] = aY[i];
        }
    }

    // rekurencyjne znajdowanie najmniejszych odleglosci miedzy punktami w obydwu podtablicach
    Pair minDistLeftPair = closest(aX, aLeftY, mid+1);
    double smallestDistanceLeft = minDistLeftPair.getDistanceSquared();
    Pair minDistRightPair = closest(aX + mid+1, aRightY, n-mid-1);
    double smallestDistanceRight = minDistRightPair.getDistanceSquared();
    // selekcja najmniejszej odleglosci
    Pair minDistPair;
    double smallestDistance;
    if(smallestDistanceLeft < smallestDistanceRight){
        smallestDistance = smallestDistanceLeft;
        minDistPair = minDistLeftPair;
    } else{
        smallestDistance = smallestDistanceRight;
        minDistPair = minDistRightPair;
    }
    // rozpatrywanie przypadku gdy to punkty w midPoint.x +/- smallestDistance sa jednak polozone siebie najblizej
    // budowanie nowej tablicy - strip[] zawierajacej punkty polozone blizej wertykalnej linii (midPoint.x) niz smallestDistance
    // bo najblizej siebie polozone punkty moga znajdowac sie po obydwu stronach midPoint.x
    Point strip[n];
    int j = 0;
    for(int i = 0; i < n; i++){
        // jezeli punkt jest oddalony od linii dzielacej zbior na pol (midPoint.x) o odleglosc mniejsza niz smallestDistance
        if((aY[i].getX() - midPoint.getX())*((aY[i].getX() - midPoint.getX())) < smallestDistance*smallestDistance){
            strip[j] = aY[i];
            j++;
        }
    }
    Pair minDistStripPair = stripClosest(strip, j, smallestDistance);
    double smallestDistanceStrip = minDistStripPair.getDistanceSquared();
    return smallestDistance < smallestDistanceStrip ? minDistPair : minDistStripPair;
}

Pair closestDistance(Point *a, int n){
    Point aX[n], aY[n];
    for(int i = 0; i < n; i++){
        aX[i] = a[i];
        aX[i] = a[i];
    }
    bool sortingByX = true;
    // aX to tablica punktow posortowana wzgledem wartosci x
    merge_sort(aX, n, sortingByX);
    // aY to tablica punktow posortowana wzgledem wartosci y
    sortingByX = false;
    merge_sort(aY, n, sortingByX);
    return closest(aX, aY, n);
}



int main(int argc, char *argv[]) {
    srand(time(NULL));
    int amount = std::stoi(argv[1]);
    Point array[amount];
    genSquare(array, amount);

    // test brute force
    Pair research = bruteForceDistance(array, amount);
    std::cout << "\nBrute Force: szukana para to: \n";
    research.printOut();

    // test optymalnego algorytmu
    std::cout << "\nOptymalny algorytm: szukana para to: \n";
    research = closestDistance(array, amount);
    research.printOut();
    return 0;
}
