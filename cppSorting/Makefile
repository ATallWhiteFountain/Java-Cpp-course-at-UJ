CC := g++ -std=c++11

bubble.x: bubble_sort.cxx
	$(CC)  -o bubble_sort.x bubble_sort.cxx
runBubble:
	./bubble_sort.x 10000 < storageSortedLast.txt

selection.x: selection_sort.cxx
	$(CC)  -o selection_sort.x selection_sort.cxx
runSelection:
	./selection_sort.x 10000 < storageSortedLast.txt

counting.x: counting_sort.cxx
	$(CC)  -o counting_sort.x counting_sort.cxx
runCounting:
	./counting_sort.x 10000 < storageSortedFirst.txt

radix.x: radix_sort.cxx
	$(CC)  -o radix_sort.x radix_sort.cxx
runRadix:
	./radix_sort.x 10000 < storageRandom.txt

quick.x: quick_sort.cxx
	$(CC)  -o quick_sort.x quick_sort.cxx
runQuick:
	./quick_sort.x 10000 < storageRandom.txt

merge.x: merge_sort.cxx
	$(CC)  -o merge_sort.x merge_sort.cxx
runMerge:
	./merge_sort.x 10000 < storageRandom.txt
heap.x: heap_sort.cxx
	$(CC)  -o heap_sort.x heap_sort.cxx
runHeap:
	./heap_sort.x 10000 < storageSortedLast.txt

std_sort.x: std_sort.cxx
	$(CC) -o std_sort.x std_sort.cxx
runStd:
	./std_sort.x 160000 < storageRandom.txt > my_waste

sort.x: sort.c
	gcc -o sort.x sort.c
	./sort.x 160000 < storageRandom.txt > my_waste

random.x: genRandom.cxx
	$(CC) -o random.x genRandom.cxx 
runRandom:
	./random.x 160000 100000 > storageRandom.txt

sorted.x: genSorted.cxx
	$(CC) -o sorted.x genSorted.cxx 
runSorted:
	./sorted.x 160000 100000 > storageSorted.txt

reverse.x: genReverse.cxx
	$(CC) -o reverse.x genReverse.cxx 
runReverse:
	./reverse.x 160000 100000 > storageReverse.txt

sortedFirst.x: genSortedFirst.cxx
	$(CC) -o sortedFirst.x genSortedFirst.cxx 
runSortedFirst:
	./sortedFirst.x 160000 100000 > storageSortedFirst.txt

sortedLast.x: genSortedLast.cxx
	$(CC) -o sortedLast.x genSortedLast.cxx 
runSortedLast:
	./sortedLast.x 160000 100000 > storageSortedLast.txt

clean:
	rm -f *.x

