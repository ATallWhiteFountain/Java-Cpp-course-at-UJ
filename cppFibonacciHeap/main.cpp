#include <iostream>
#include <cmath>

// Liczby Fibonacciego sa uzywane w analizie zlozonosci. Kazdy wezel ma ilosc dzieci maksymalnie O(log n)
// Rozmiar poddrzewa o root z liczba dzieci k, to przynajmniej Fk+2

class Heap{

    struct node{
        int value;
        // wskazniki na inne wezly
        node* parent;
        node* child;
        node* left;
        node* right;
        // oznaczenia wezlow potrzebne dla metod prywatnych
        int rank; // stopien - ile dzieci ma root Node - do pop()
        bool isMarked; // oznaczenie wezla - gdy wezel stracil juz jedno dziecko - do decreaseKey()
        bool isQuestioned; // oznaczenie wezla - do findAndDecreaseKey()
    };

    node* minimalElement = nullptr;
    int nodeCount = 0;

public:

    void push(int x){

        node* newNode = new node;
        newNode->value = x;
        newNode->parent = nullptr;
        newNode->child = nullptr;
        // potrzebne przy wprowadzeniu pierwszego elementu
        newNode->left = newNode;
        newNode->right = newNode;

        if(minimalElement != nullptr){

            // gdy to kolejny element trafiajacy do kopca, uzgadniamy wskazniki
            minimalElement->left->right = newNode;
            newNode->right = minimalElement;
            newNode->left = minimalElement->left;
            minimalElement->left = newNode;

            // jesli element jest mniejszy od minimalElement, na niego bedzie pokazywac minimalElement
            if(newNode->value < minimalElement->value) minimalElement = newNode;

        }else{

            // jesli to pierwszy element w kopcu, jest to wiec tez minimalElement
            minimalElement = newNode;
        }

        nodeCount++;
    }

    int top(){
        return minimalElement->value;
    }

    int size(){
        return nodeCount;
    }

    int pop(){

        if(minimalElement == nullptr){
            std::cout << "\nKopiec jest pusty";
            return -INT32_MAX;
        }

        else {

            int value = minimalElement->value;
            nodeCount--;

            node* temp = minimalElement;
            node* child = nullptr;
            node* nextChild = nullptr;

            // gdy minimalElement ma dzieci
            if (temp->child != nullptr) {

                // dzieci minimalElement dodaje do rootList (kazdy dokladnie jak w push())
                child = temp->child;
                do {
                    nextChild = child->right;

                    minimalElement->left->right = child;
                    child->right = minimalElement;
                    child->left = minimalElement->left;
                    minimalElement->left = child;
                    if (child->value < minimalElement->value) minimalElement = child;
                    child->parent = nullptr;

                    child = nextChild;
                } while (nextChild != temp->child);
            }

            // wykluczamy minimalElement z rootList
            temp->left->right = temp->right;
            temp->right->left = temp->left;

            // jesli w kopcu nie zostaje zaden element
            if (temp == temp->right && temp->child == nullptr)
                minimalElement = nullptr;
            else {
                minimalElement = temp->right; // tymczasowe przypisanie na potrzeby consolidate()
                consolidate();
            }
            return value;
        }
    }

    void remove(int x){
            if (minimalElement == nullptr) std::cout << "\nKopiec jest pusty";
            else {
                // Zmniejszanie wartosci do najmniejszej mozliwej
                findAndDecreaseKey(minimalElement, x, -INT32_MAX);

                pop();
                std::cout << "\nWartosc " << x << " usunieta";
            }
        }

    void displayRootList(){
        node* ptr = minimalElement;
        if (ptr == nullptr) std::cout << "Kopiec pusty";

        else {
            std::cout << "\n\nrootList: ";
            do {
                std::cout << ptr->value;
                ptr = ptr->right;
                if (ptr != minimalElement) {
                    std::cout << "<-->";
                }
            } while (ptr != minimalElement && ptr->right != nullptr);
            std::cout << "\nKopiec zawiera elementow: " << nodeCount << "\n";
        }
    }

private:

    void cutMarked(node* tempNode){

        node* parentNode = tempNode->parent;
        if(parentNode != nullptr){

            if(tempNode->isMarked == false){
                tempNode->isMarked = true;
            }
            else{
                cut(tempNode, parentNode);
                cutMarked(parentNode);
            }

        }
    }

    void cut(node* targetNode, node* parentNode){

        // gdy targetNode jest pojedynczym dzieckiem
        if (targetNode == targetNode->right) parentNode->child = nullptr;

        targetNode->left->right = targetNode->right;
        targetNode->right->left = targetNode->left;

        // parentNode->child musi wskazywac ktores dziecko
        if (targetNode == parentNode->child) parentNode->child = targetNode->right;

        parentNode->rank = parentNode->rank - 1;
        parentNode->isMarked = true;
        targetNode->right = targetNode;
        targetNode->left = targetNode;

        // targetNode trafia do rootList
        minimalElement->left->right = targetNode;
        targetNode->right = minimalElement;
        targetNode->left = minimalElement->left;
        minimalElement->left = targetNode;
        targetNode->parent = nullptr;
    }

    void decreaseKey(node* targetNode, int x){

        if (minimalElement == nullptr) std::cout << "\nKopiec jest pusty";

        if (targetNode == nullptr) std::cout << "Nie ma takiego wezla w kopcu";

        targetNode->value = x;

        // po zmianie wartosci zaburzajacej zasade dzialania kopca, nalezy kopiec naprawic
        node* parentNode = targetNode->parent;
        if (parentNode != nullptr && targetNode->value < parentNode->value) {
            // odciecie targetNode od drzewa i dodanie do rootList
            cut(targetNode, parentNode);
            // jesli parentNode traci drugie dziecko, rowniez trafia do rootList (rekurencyjnie, dopoki ta zaleznosc zachodzi wyzej)
            cutMarked(parentNode);
        }
        if (targetNode->value < minimalElement->value) minimalElement = targetNode;
    }

    void linking(node* biggerValueRoot, node* smallerValueRoot)
    {
        biggerValueRoot->left->right = biggerValueRoot->right;
        biggerValueRoot->right->left = biggerValueRoot->left;

        if (smallerValueRoot->right == smallerValueRoot) minimalElement = smallerValueRoot;

        biggerValueRoot->left = biggerValueRoot;
        biggerValueRoot->right = biggerValueRoot;
        biggerValueRoot->parent = smallerValueRoot;

        // gdy biggerValueRoot staje sie dzieckiem, trzeba zrobic update listy dzieci smallerValueRoot
        // ktora to lista zachowuje sie dokladnie tak samo jak rootList (circular doubly linked list)
        if (smallerValueRoot->child == nullptr) smallerValueRoot->child = biggerValueRoot;

        biggerValueRoot->right = smallerValueRoot->child;
        biggerValueRoot->left = smallerValueRoot->child->left;
        smallerValueRoot->child->left->right = biggerValueRoot;
        smallerValueRoot->child->left = biggerValueRoot;
        if (biggerValueRoot->value < smallerValueRoot->child->value)
            smallerValueRoot->child = biggerValueRoot;

        smallerValueRoot->rank++;
    }

    void consolidate(){

        int currentRank;
        // maksymalna ilosc dzieci drzewa przez ilosc poziomow brane pod uwage
        int size = (int)((log(nodeCount)) / (log(2)));

        // tablica node* wrzucamy do niej wezly na indeksy o ich rank / liczbie dzieci
        node* rankArray[size];
        for (int i = 0; i <= size; i++) rankArray[i] = nullptr;
        node* currentRoot = minimalElement;
        node* previousRoot;
        node* tempForSwap;

        // przechodzimy cale rootList, porownujac rank wszystkich rootow
        do {
            currentRank = currentRoot->rank;

            while (rankArray[currentRank] != nullptr) {
                previousRoot = rankArray[currentRank]; //  pokazuje na poprzedni root z tym samym rank

                // jesli value aktualnego roota > value poprzedniego: swap (do linking())
                if (currentRoot->value > previousRoot->value) {
                    tempForSwap = currentRoot;
                    currentRoot = previousRoot;
                    previousRoot = tempForSwap;
                }

                if (previousRoot == minimalElement) minimalElement = currentRoot;

                // linking: root z wiekszym value staje sie dzieckiem roota z mniejszym value, cale jego drzewo staje sie poddrzewem
                linking(previousRoot, currentRoot);

                if (currentRoot->right == currentRoot) minimalElement = currentRoot;
                rankArray[currentRank] = nullptr;
                currentRank++;
            }

            rankArray[currentRank] = currentRoot;
            currentRoot = currentRoot->right;
        } while (currentRoot != minimalElement);

        minimalElement = nullptr;
        // odtwarzanie rootList z wezlow w rankArray[]
        for (int i = 0; i <= size; i++) {
            if (rankArray[i] != nullptr) {
                rankArray[i]->left = rankArray[i];
                rankArray[i]->right = rankArray[i];

                if (minimalElement != nullptr) {
                    minimalElement->left->right = rankArray[i];
                    rankArray[i]->right = minimalElement;
                    rankArray[i]->left = minimalElement->left;
                    minimalElement->left = rankArray[i];
                    if (rankArray[i]->value < minimalElement->value) minimalElement = rankArray[i];
                }
                else {
                    minimalElement = rankArray[i];
                }
            }

        }
    }

    void findAndDecreaseKey(node* questionedNode, int searchedValue, int smallestPossibleValue) {

        node* foundNode = nullptr;
        node* temp = questionedNode;
        temp->isQuestioned = true;

        // akcja gdy znalezlismy wezel z zadana wartoscia
        if (temp->value == searchedValue){
            temp->isQuestioned = false;
            foundNode = temp;
            decreaseKey(foundNode, smallestPossibleValue);
        }

        // gdy nie znalezlismy
        if (foundNode == nullptr){

            // szukanie na poziomie dzieci wezla
            if (temp->child != nullptr)
                findAndDecreaseKey(temp->child, searchedValue, smallestPossibleValue);

            // szukanie na poziomie wezla - wsrod niesprawdzanych
            if (temp->right->isQuestioned == false)
                findAndDecreaseKey(temp->right, searchedValue, smallestPossibleValue);
        }
        temp->isQuestioned = false;
    }

};

int main() {

    Heap kopiec = Heap();
    kopiec.push(1);
    kopiec.push(2);
    kopiec.push(3);
    kopiec.push(4);
    kopiec.push(5);
    kopiec.push(6);
    kopiec.push(7);
    kopiec.push(8);
    kopiec.push(9);
    kopiec.push(10);
    kopiec.displayRootList();
    kopiec.pop();
    kopiec.displayRootList();
    kopiec.remove(7);
    kopiec.displayRootList();

}