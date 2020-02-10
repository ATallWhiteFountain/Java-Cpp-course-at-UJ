package generics;

import java.util.ArrayList;
import java.util.Collections;

public class Heap<E extends Comparable<? super E>> {

    private ArrayList<E> nodes;

    public Heap(){
        this.nodes = new ArrayList<E>();
    }

    private int getLeftChildIndex(int index){
        return ((2*index) + 1);
    }

    private int getRightChildIndex(int index){
        return ((2*index) + 2);
    }

    private int getParentIndex(int index){
        return ((index - 1) / 2);
    }

    private boolean isLeaf(int index){
        return index >= nodes.size() / 2 && index <= nodes.size();
    }

    private void heapifyUp(int currentIndex){
        //E addedNode = nodes.get(currentIndex);
        int parentIndex = getParentIndex(currentIndex);

        while(nodes.get(currentIndex).compareTo(nodes.get(parentIndex)) < 0){
            Collections.swap(nodes, currentIndex, parentIndex);
            currentIndex = parentIndex;
            parentIndex = getParentIndex(currentIndex);
        }
    }

    private void heapifyDown(int current) {
        if (isLeaf(current)) return;

        if ((getLeftChildIndex(current) < nodes.size() && getRightChildIndex(current) < nodes.size()) &&
                ( nodes.get(current).compareTo(nodes.get(getLeftChildIndex(current))) > 0 ||
                nodes.get(current).compareTo(nodes.get(getRightChildIndex(current))) > 0 )) {

            if(nodes.get(getLeftChildIndex(current)).compareTo(nodes.get(getRightChildIndex(current))) < 0){
                Collections.swap(nodes, getLeftChildIndex(current), current);
                heapifyDown(getLeftChildIndex(current));
            }else{
                Collections.swap(nodes, getRightChildIndex(current), current);
                heapifyDown(getRightChildIndex(current));
            }
            // else if gdy nie ma prawego dziecka
        }else if(getLeftChildIndex(current) < nodes.size() && nodes.get(current).compareTo(nodes.get(getLeftChildIndex(current))) > 0){
            Collections.swap(nodes, getLeftChildIndex(current), current);
        }

    }

    //############################
    public void add(E newNode){
        nodes.add(newNode);
        int newNodeIndex = nodes.size() - 1;
        heapifyUp(newNodeIndex);
    }

    // get pobiera wartosc z korzenia
    public E get(){
        E minValue = nodes.get(0);
        Collections.swap(nodes, 0, nodes.size() -1);
        nodes.remove(nodes.size() - 1);
        heapifyDown(0);

        return minValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(E e : nodes){
            sb.append(e.toString());
            sb.append(' ');
        }
        return sb.toString();
    }
}
