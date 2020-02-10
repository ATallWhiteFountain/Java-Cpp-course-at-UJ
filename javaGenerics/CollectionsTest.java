package generics;

import java.util.*;

public class CollectionsTest{
    int n = 320000;
    // HashSet - reprezentuje zbiory bez porzadku, kazdy element wystepuje tylko raz
    // PriorityQueue - elementy sa w porzadku priorytetowym (posortowane), zaleznie od sposobu
    // porownywania obiektow klasy dla ktorej kolejke priorytetowa uzywamy
    // TreeSet- obiekty posortowane

    public void measureArrayList(){
        long start, end, add, contains, remove;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();

        start = System.currentTimeMillis();
        for(int i=0; i<n; i++){
            arrayList.add(i);
        }
        end = System.currentTimeMillis();
        add = end - start;

        start = System.currentTimeMillis();
        arrayList.contains(320001);
        end = System.currentTimeMillis();
        contains = end - start;

        start = System.currentTimeMillis();
        for(int i=n-1; i>=0; i--){
            arrayList.remove(i);
        }
        end = System.currentTimeMillis();
        remove = end - start;

        System.out.println("ArrayList:");
        System.out.println("add(): " + add);
        System.out.println("contains(): " + contains);
        System.out.println("remove(): " + remove);
    }

    public void measureHashSet(){
        long start, end, add, contains, remove;
        HashSet<Integer> hashSet = new HashSet<>();

        start = System.currentTimeMillis();
        for(int i=0; i<n; i++){
            hashSet.add(i);
        }
        end = System.currentTimeMillis();
        add = end - start;

        start = System.currentTimeMillis();
        hashSet.contains(320001);
        end = System.currentTimeMillis();
        contains = end - start;

        start = System.currentTimeMillis();
        for(int i=n-1; i>=0; i--){
            hashSet.remove(i);
        }
        end = System.currentTimeMillis();
        remove = end - start;

        System.out.println("\nHashSet:");
        System.out.println("add(): " + add);
        System.out.println("contains(): " + contains);
        System.out.println("remove(): " + remove);
    }

    public void measureLinkedList(){
        long start, end, add, contains, remove;
        LinkedList<Integer> linkedList = new LinkedList<>();

        start = System.currentTimeMillis();
        for(int i=0; i<n; i++){
            linkedList.add(i);
        }
        end = System.currentTimeMillis();
        add = end - start;

        start = System.currentTimeMillis();
        linkedList.contains(320001);
        end = System.currentTimeMillis();
        contains = end - start;

        start = System.currentTimeMillis();
        for(int i=n-1; i>=0; i--){
            linkedList.remove(i);
        }
        end = System.currentTimeMillis();
        remove = end - start;

        System.out.println("\nLinkedList:");
        System.out.println("add(): " + add);
        System.out.println("contains(): " + contains);
        System.out.println("remove(): " + remove);
    }

    public void measureVector(){
        long start, end, add, contains, remove;
        Vector<Integer> vector = new Vector<>();

        start = System.currentTimeMillis();
        for(int i=0; i<n; i++){
            vector.add(i);
        }
        end = System.currentTimeMillis();
        add = end - start;

        start = System.currentTimeMillis();
        vector.contains(320001);
        end = System.currentTimeMillis();
        contains = end - start;

        start = System.currentTimeMillis();
        for(int i=n-1; i>=0; i--){
            vector.remove(i);
        }
        end = System.currentTimeMillis();
        remove = end - start;

        System.out.println("\nVector:");
        System.out.println("add(): " + add);
        System.out.println("contains(): " + contains);
        System.out.println("remove(): " + remove);
    }

    public void measurePriorityQueue(){
        long start, end, add, contains, remove;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        start = System.currentTimeMillis();
        for(int i=0; i<n; i++){
            priorityQueue.add(i);
        }
        end = System.currentTimeMillis();
        add = end - start;

        start = System.currentTimeMillis();
        priorityQueue.contains(320001);
        end = System.currentTimeMillis();
        contains = end - start;

        start = System.currentTimeMillis();
        for(int i=n-1; i>=0; i--){
            priorityQueue.remove(i);
        }
        end = System.currentTimeMillis();
        remove = end - start;

        System.out.println("\nPriorityQueue:");
        System.out.println("add(): " + add);
        System.out.println("contains(): " + contains);
        System.out.println("remove(): " + remove);
    }

    public void measureTreeSet(){
        long start, end, add, contains, remove;
        TreeSet<Integer> treeSet = new TreeSet<>();

        start = System.currentTimeMillis();
        for(int i=0; i<n; i++){
            treeSet.add(i);
        }
        end = System.currentTimeMillis();
        add = end - start;

        start = System.currentTimeMillis();
        treeSet.contains(320000);
        end = System.currentTimeMillis();
        contains = end - start;

        start = System.currentTimeMillis();
        for(int i=n-1; i>=0; i--){
            treeSet.remove(i);
        }
        end = System.currentTimeMillis();
        remove = end - start;

        System.out.println("\nTreeSet:");
        System.out.println("add(): " + add);
        System.out.println("contains(): " + contains);
        System.out.println("remove(): " + remove);
    }
}
